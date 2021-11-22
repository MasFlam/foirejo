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
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import com.masflam.foirejo.Utils;
import com.masflam.foirejo.data.Currency;
import com.masflam.foirejo.data.Offer;
import com.masflam.foirejo.data.OfferRepository;
import com.masflam.foirejo.data.User;
import com.masflam.foirejo.data.UserRepository;
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
	
	@Inject
	@Location("style.css")
	public Template styleCssTemplate;
	
	// TODO: this should really be a static resource thing idk how that's done tho
	@GET
	@Path("/style.css")
	@PermitAll
	@Produces("text/css")
	public TemplateInstance styleCss() {
		return styleCssTemplate.instance();
	}
	
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
		int pageCount = (int) query.count();
		pageCount = 20;
		int[] pages;
		if (pageCount == 0) {
			pageCount = 1;
			pages = new int[] {1};
		} else if (pageCount <= 10) {
			pages = new int[pageCount];
			for (int i = 0; i < pageCount; ++i) {
				pages[i] = i+1;
			}
		} else if (pageIndex <= 4) {
			pages = new int[7];
			for (int i = 0; i < 5; ++i) {
				pages[i] = i+1;
			}
			pages[5] = 0;
			pages[6] = pageCount;
		} else if (pageIndex >= pageCount - 3) {
			pages = new int[7];
			for (int i = 0; i < 5; ++i) {
				pages[i + 2] = pageCount - (4-i);
			}
			pages[1] = 0;
			pages[0] = 1;
		} else {
			pages = new int[9];
			for (int i = 0; i < 5; ++i) {
				pages[i + 2] = pageIndex + i-2;
			}
			pages[7] = 0;
			pages[8] = pageCount;
			pages[1] = 0;
			pages[0] = 1;
		}
		List<Offer> offers = query.list();
		List<String> offerPrices = offers.stream()
			.map(offer -> {
				if (offer.getCurrency() == Currency.XMR) {
					return Utils.humanizeMoneroAmount(offer.getPrice());
				}
				return Utils.humanizeMoneroAmount(
					priceService.asPiconero(offer.getPrice(), offer.getCurrency())
				);
			}).collect(Collectors.toList());
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
		String humanPrice = Utils.humanizeMoneroAmount(
			priceService.asPiconero(offer.getPrice(), offer.getCurrency())
		);
		return Response.ok(
			offerTemplate
				.data("user", user)
				.data("offer", offer)
				.data("humanPrice", humanPrice)
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
