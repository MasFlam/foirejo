package com.masflam.foirejo.website;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import com.masflam.foirejo.Utils;
import com.masflam.foirejo.data.Currency;
import com.masflam.foirejo.data.entity.Offer;
import com.masflam.foirejo.data.entity.User;
import com.masflam.foirejo.data.repo.OfferRepository;
import com.masflam.foirejo.data.repo.UserRepository;
import com.masflam.foirejo.service.CurrencyPriceService;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.Authenticated;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class WebsiteResource {
	@Inject
	public UserRepository userRepo;
	
	@Inject
	public OfferRepository offerRepo;
	
	@Inject
	public CurrencyPriceService priceService;
	
	@Inject
	@Location("index.html")
	public Template indexTemplate;
	
	@Inject
	@Location("offers.html")
	public Template offersTemplate;
	
	@Inject
	@Location("trades.html")
	public Template tradesTemplate;
	
	@Inject
	@Location("about.html")
	public Template aboutTemplate;
	
	@Inject
	@Location("offer.html")
	public Template offerTemplate;
	
	@GET
	@Path("/")
	@PermitAll
	public TemplateInstance index(@Context SecurityContext sec) {
		//User user = userRepo.findByUsername("user");
		User user = null;
		if (sec.getUserPrincipal() != null) {
			user = userRepo.findByUsername(sec.getUserPrincipal().getName());
		}
		return indexTemplate.data("user", user);
	}
	
	@GET
	@Path("/offers")
	@PermitAll
	public TemplateInstance offers(
		@Context SecurityContext sec,
		@QueryParam("pageno") @DefaultValue("1") int pageIndex
	) {
		//User user = userRepo.findByUsername("user");
		User user = null;
		if (sec.getUserPrincipal() != null) {
			user = userRepo.findByUsername(sec.getUserPrincipal().getName());
		}
		var query = offerRepo.findAll().page(pageIndex - 1, 20); // TODO make page size configable?
		int pageCount = (int) query.pageCount();
		//pageCount = 15;
		int[] pages = Utils.pageNumbersFor(pageIndex, pageCount);
		List<Offer> offers = query.list();
		List<String> offerPrices = offers.stream()
			.map(offer -> Utils.humanizeXmrAmount(Math.round(
				priceService.convert(offer.getPrice(), offer.getCurrency(), Currency.XMR)
			)))
			.collect(Collectors.toList());
		return offersTemplate
			.data("user", user)
			.data("pageNums", pages)
			.data("currentPageNum", pageIndex)
			.data("prevPageNum", pageIndex == 1 ? null : pageIndex - 1)
			.data("nextPageNum", pageIndex == pageCount ? null : pageIndex + 1)
			.data("offers", offers)
			.data("offerPrices", offerPrices);
	}
	
	@GET
	@Path("/offers/{offerId}")
	@PermitAll
	public Response offer(@Context SecurityContext sec, @PathParam("offerId") Long offerId) {
		//User user = userRepo.findByUsername("user");
		User user = null;
		if (sec.getUserPrincipal() != null) {
			user = userRepo.findByUsername(sec.getUserPrincipal().getName());
		}
		Offer offer = offerRepo.findById(offerId);
		if (offer == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		long xmr = Math.round(priceService.convert(offer.getPrice(), offer.getCurrency(), Currency.XMR));
		long btc = Math.round(priceService.convert(offer.getPrice(), offer.getCurrency(), Currency.BTC));
		long usd = Math.round(priceService.convert(offer.getPrice(), offer.getCurrency(), Currency.USD));
		long eur = Math.round(priceService.convert(offer.getPrice(), offer.getCurrency(), Currency.EUR));
		return Response.ok(
			offerTemplate
				.data("user", user)
				.data("offer", offer)
				.data("xmrPrice", Utils.humanizeXmrAmount(xmr))
				.data("btcPrice", Utils.humanizeBtcAmount(btc))
				.data("usdPrice", Utils.humanizeUsdAmount(usd))
				.data("eurPrice", Utils.humanizeEurAmount(eur))
		).build();
	}
	
	@Authenticated
	@GET
	@Path("/trades")
	public TemplateInstance trades(@Context SecurityContext sec) {
		//User user = userRepo.findByUsername("user");
		User user = null;
		if (sec.getUserPrincipal() != null) {
			user = userRepo.findByUsername(sec.getUserPrincipal().getName());
		}
		return tradesTemplate.data("user", user);
	}
	
	@GET
	@Path("/about")
	@PermitAll
	public TemplateInstance about(@Context SecurityContext sec) {
		//User user = userRepo.findByUsername("user");
		User user = null;
		if (sec.getUserPrincipal() != null) {
			user = userRepo.findByUsername(sec.getUserPrincipal().getName());
		}
		return aboutTemplate.data("user", user);
	}
}
