package com.masflam.foirejo.api;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import com.masflam.foirejo.annotation.RestResource;
import com.masflam.foirejo.api.dto.Page;
import com.masflam.foirejo.api.dto.Paginated;
import com.masflam.foirejo.api.dto.TradeDto;
import com.masflam.foirejo.api.dto.TradeMessageDto;
import com.masflam.foirejo.api.dto.TradeMessageDto1;
import com.masflam.foirejo.data.Currency;
import com.masflam.foirejo.data.entity.Offer;
import com.masflam.foirejo.data.entity.Trade;
import com.masflam.foirejo.data.entity.TradeMessage;
import com.masflam.foirejo.data.entity.TradeMessageReport;
import com.masflam.foirejo.data.entity.User;
import com.masflam.foirejo.data.repo.OfferRepository;
import com.masflam.foirejo.data.repo.TradeMessageReportRepository;
import com.masflam.foirejo.data.repo.TradeMessageRepository;
import com.masflam.foirejo.data.repo.TradeRepository;
import com.masflam.foirejo.data.repo.UserRepository;
import com.masflam.foirejo.service.CurrencyPriceService;

import io.quarkus.panache.common.Sort;
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
	public TradeMessageRepository messageRepo;
	
	@Inject
	public TradeMessageReportRepository messageReportRepo;
	
	@Inject
	public CurrencyPriceService priceService;
	
	private boolean isAuthorized(User user, Trade trade) {
		return trade.getBuyer().getId() == user.getId() ||
			trade.getOffer().getOwner().getId() == user.getId() ||
			user.getRoles().contains("admin"); // TODO this check is prone to vulnerabilities
	}
	
	@Authenticated
	@GET
	@Path("/{tradeId}")
	public Response getById(@Context SecurityContext sec, @PathParam("tradeId") Long tradeId) {
		User user = userRepo.findByUsername(sec.getUserPrincipal().getName());
		Trade trade = tradeRepo.findById(tradeId);
		if (trade == null || !isAuthorized(user, trade)) {
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
	
	@Authenticated
	@GET
	@Path("/{tradeId}/messages")
	public Response getTradeMessages(
		@Context SecurityContext sec,
		@PathParam("tradeId") Long tradeId,
		Paginated pagi
	) {
		User user = userRepo.findByUsername(sec.getUserPrincipal().getName());
		Trade trade = tradeRepo.findById(tradeId);
		if (trade == null || !isAuthorized(user, trade)) {
			return Response.status(Status.NOT_FOUND).build();
		}
		List<TradeMessageDto> messages = messageRepo.find("trade", Sort.descending("dateUtc"), trade)
			.page(pagi.getPageno(), pagi.getPagesz())
			.stream()
				.map(TradeMessageDto::new)
				.collect(Collectors.toList());
		long total = messageRepo.count("trade", trade);
		return Response.ok(new Page<>(messages, total)).build();
	}
	
	@Authenticated
	@POST
	@Path("/{tradeId}/messages")
	public Response sendTradeMessage(
		@Context SecurityContext sec,
		@PathParam("tradeId") Long tradeId,
		TradeMessageDto1 messageDto
	) {
		User user = userRepo.findByUsername(sec.getUserPrincipal().getName());
		Trade trade = tradeRepo.findById(tradeId);
		if (trade == null || !isAuthorized(user, trade)) {
			return Response.status(Status.NOT_FOUND).build();
		}
		var tm = new TradeMessage();
		tm.setAuthor(user);
		tm.setTrade(trade);
		tm.setContent(messageDto.getContent());
		tm.setDate(LocalDate.now());
		messageRepo.persist(tm);
		return Response.ok(tm.getId()).build();
	}
	
	@Authenticated
	@GET
	@Path("/messages/{messageId}")
	public Response getTradeMessage(
		@Context SecurityContext sec,
		@PathParam("messageId") Long messageId
	) {
		User user = userRepo.findByUsername(sec.getUserPrincipal().getName());
		TradeMessage message = messageRepo.findById(messageId);
		if (message == null || !isAuthorized(user, message.getTrade())) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(new TradeMessageDto(message)).build();
	}
	
	@Authenticated
	@POST
	@Path("/messages/{messageId}/report")
	public Response reportMessage(
		@Context SecurityContext sec,
		@PathParam("messageId") Long messageId
	) {
		User user = userRepo.findByUsername(sec.getUserPrincipal().getName());
		TradeMessage message = messageRepo.findById(messageId);
		if (message == null || !isAuthorized(user, message.getTrade())) {
			return Response.status(Status.NOT_FOUND).build();
		}
		if (messageReportRepo.findById(user, message) != null) {
			return Response.ok().build();
		}
		var id = new TradeMessageReport.Id();
		id.setReporter(user);
		id.setMessage(message);
		var report = new TradeMessageReport();
		report.setId(id);
		messageReportRepo.persist(report);
		return Response.ok().build();
	}
	
	@POST
	@Path("/messages/{messageId}/unreport")
	@RolesAllowed("admin")
	public Response unreportMessage(
		@Context SecurityContext sec,
		@PathParam("messageId") Long messageId
	) {
		User user = userRepo.findByUsername(sec.getUserPrincipal().getName());
		TradeMessage message = messageRepo.findById(messageId);
		if (message == null || !isAuthorized(user, message.getTrade())) {
			return Response.status(Status.NOT_FOUND).build();
		}
		TradeMessageReport report = messageReportRepo.findById(user, message);
		if (report != null) {
			messageReportRepo.delete(report);
		}
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/messages/{messageId}")
	@RolesAllowed("admin")
	public Response deleteMessage(@PathParam("messageId") Long messageId) {
		messageRepo.deleteById(messageId);
		return Response.ok().build();
	}
	
	@Authenticated
	@POST
	@Path("/{tradeId}/cancel")
	public Response cancelTrade(@Context SecurityContext sec, @PathParam("tradeId") Long tradeId) {
		User user = userRepo.findByUsername(sec.getUserPrincipal().getName());
		Trade trade = tradeRepo.findById(tradeId);
		if (trade == null || !isAuthorized(user, trade)) {
			return Response.status(Status.NOT_FOUND).build();
		}
		if (!user.equals(trade.getBuyer())) {
			return Response.status(Status.FORBIDDEN).build();
		}
		// TODO set trade status to cancelled or sth
		return Response.ok().build();
	}
	
	@Authenticated
	@POST
	@Path("/{tradeId}/close")
	public Response closeTrade(@Context SecurityContext sec, @PathParam("tradeId") Long tradeId) {
		User user = userRepo.findByUsername(sec.getUserPrincipal().getName());
		Trade trade = tradeRepo.findById(tradeId);
		if (trade == null || !isAuthorized(user, trade)) {
			return Response.status(Status.NOT_FOUND).build();
		}
		if (!user.equals(trade.getBuyer())) {
			return Response.status(Status.FORBIDDEN).build();
		}
		// TODO set trade status to successful or sth
		return Response.ok().build();
	}
	
	
}
