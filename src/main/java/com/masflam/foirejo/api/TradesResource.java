package com.masflam.foirejo.api;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import com.masflam.foirejo.annotation.RestResource;
import com.masflam.foirejo.api.dto.TradeDto;
import com.masflam.foirejo.data.Currency;
import com.masflam.foirejo.data.entity.Offer;
import com.masflam.foirejo.data.entity.Trade;
import com.masflam.foirejo.data.entity.User;
import com.masflam.foirejo.data.repo.OfferRepository;
import com.masflam.foirejo.data.repo.TradeRepository;
import com.masflam.foirejo.data.repo.UserRepository;
import com.masflam.foirejo.service.CurrencyPriceService;

import io.quarkus.security.Authenticated;

@Path("/api/trades")
@RestResource
public class TradesResource {
	@Inject
	public OfferRepository offerRepo;
	
	@Inject
	public UserRepository userRepo;
	
	@Inject
	public TradeRepository tradeRepo;
	
	@Inject
	public CurrencyPriceService priceService;
	
	@Authenticated
	@GET
	@Path("/{tradeId}")
	public Response getById(@Context SecurityContext sec, @PathParam("tradeId") Long tradeId) {
		User user = userRepo.findByUsername(sec.getUserPrincipal().getName());
		Trade trade = tradeRepo.findById(tradeId);
		if (
			trade == null ||
			trade.getBuyer().getId() != user.getId() &&
			trade.getOffer().getOwner().getId() != user.getId()
		) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(new TradeDto(trade)).build();
	}
	
	@Authenticated
	@POST
	@Path("/new/{offerId}")
	@Transactional
	public Response newTrade(@Context SecurityContext sec, @PathParam("offerId") Long offerId) {
		Offer offer = offerRepo.findById(offerId);
		if (offer == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		User buyer = userRepo.findByUsername(sec.getUserPrincipal().getName());
		Trade trade = new Trade();
		trade.setOffer(offer);
		trade.setPrice(Math.round(priceService.convert(offer.getPrice(), offer.getCurrency(), Currency.XMR)));
		trade.setBuyer(buyer);
		tradeRepo.persist(trade);
		return Response.ok(new TradeDto(trade)).build();
	}
}
