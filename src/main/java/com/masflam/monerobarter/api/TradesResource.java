package com.masflam.monerobarter.api;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import com.masflam.monerobarter.annotation.RestResource;
import com.masflam.monerobarter.api.dto.TradeDto;
import com.masflam.monerobarter.data.Offer;
import com.masflam.monerobarter.data.OfferRepository;
import com.masflam.monerobarter.data.Trade;
import com.masflam.monerobarter.data.TradeRepository;
import com.masflam.monerobarter.data.User;
import com.masflam.monerobarter.data.UserRepository;

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
		trade.setPrice(offer.getPrice());
		trade.setCurrency(offer.getCurrency());
		trade.setBuyer(buyer);
		tradeRepo.persist(trade);
		return Response.ok(new TradeDto(trade)).build();
	}
}
