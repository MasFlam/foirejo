package com.masflam.monerobarter.api;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import com.masflam.monerobarter.annotation.RestResource;
import com.masflam.monerobarter.api.dto.OfferDto;
import com.masflam.monerobarter.data.Offer;
import com.masflam.monerobarter.data.OfferRepository;
import com.masflam.monerobarter.data.User;
import com.masflam.monerobarter.data.UserRepository;

import io.quarkus.security.Authenticated;

@Path("/api/offers")
@RestResource
public class OffersResource {
	@Inject
	public OfferRepository offerRepo;
	
	@Inject
	public UserRepository userRepo;
	
	@GET
	@Path("/{offerId}")
	@PermitAll
	public Response getOfferById(@PathParam("offerId") Long offerId) {
		Offer offer = offerRepo.findById(offerId);
		if (offer == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(new OfferDto(offer)).build();
	}
	
	@Authenticated
	@POST
	@Transactional
	public OfferDto createOffer(@Context SecurityContext sec, OfferDto offerDto) {
		User owner = userRepo.findByUsername(sec.getUserPrincipal().getName());
		var offer = new Offer();
		offer.setTitle(offerDto.getTitle());
		offer.setOwner(owner);
		offer.setPrice(offerDto.getPrice());
		offer.setCurrency(offerDto.getCurrency());
		offerRepo.persist(offer);
		return new OfferDto(offer);
	}
	
	@Authenticated
	@Path("/{offerId}")
	@PUT
	@Transactional
	public Response updateOffer(
		@Context SecurityContext sec,
		@PathParam("offerId") Long offerId,
		OfferDto offerDto
	) {
		Offer offer = offerRepo.findById(offerId);
		if (offer == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		if (offer.getOwner().getUsername() != sec.getUserPrincipal().getName()) {
			return Response.status(Status.FORBIDDEN).build();
		}
		offer.setTitle(offerDto.getTitle());
		offer.setPrice(offerDto.getPrice());
		offer.setCurrency(offerDto.getCurrency());
		offerRepo.persist(offer);
		return Response.ok(new OfferDto(offer)).build();
	}
	
	@Authenticated
	@DELETE
	@Path("/{offerId}")
	@Transactional
	public Response deleteOffer(@Context SecurityContext sec, @PathParam("offerId") Long offerId) {
		Offer offer = offerRepo.findById(offerId);
		if (offer == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		if (offer.getOwner().getUsername() != sec.getUserPrincipal().getName()) {
			return Response.status(Status.FORBIDDEN).build();
		}
		offerRepo.deleteById(offerId);
		return Response.ok().build();
	}
}
