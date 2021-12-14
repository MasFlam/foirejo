package com.masflam.foirejo.api;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import com.masflam.foirejo.annotation.RestResource;
import com.masflam.foirejo.api.dto.OfferDto;
import com.masflam.foirejo.api.dto.OfferDto1;
import com.masflam.foirejo.api.dto.ReviewDto;
import com.masflam.foirejo.api.dto.ReviewDto1;
import com.masflam.foirejo.data.entity.Offer;
import com.masflam.foirejo.data.entity.Review;
import com.masflam.foirejo.data.entity.User;
import com.masflam.foirejo.data.repo.OfferRepository;
import com.masflam.foirejo.data.repo.ReviewRepository;
import com.masflam.foirejo.data.repo.UserRepository;

import io.quarkus.security.Authenticated;

@Path("/api/offers")
@RestResource
public class OffersResource {
	@Inject
	public OfferRepository offerRepo;
	
	@Inject
	public ReviewRepository reviewRepo;
	
	@Inject
	public UserRepository userRepo;
	
	@GET
	@Path("/{offerId}")
	@PermitAll
	public Response getOfferById(@PathParam("offerId") long offerId) {
		Offer offer = offerRepo.findById(offerId);
		if (offer == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(new OfferDto(offer)).build();
	}
	
	@Authenticated
	@POST
	@Transactional
	public Long createOffer(@Context SecurityContext sec, OfferDto1 offerDto) {
		User owner = userRepo.findByUsername(sec.getUserPrincipal().getName());
		var offer = new Offer();
		offer.setTitle(offerDto.getTitle());
		offer.setShortDesc(offerDto.getShortDesc());
		offer.setOwner(owner);
		offer.setPrice(offerDto.getPrice());
		offer.setCurrency(offerDto.getCurrency());
		offerRepo.persist(offer);
		return offer.getId();
	}
	
	@Authenticated
	@Path("/{offerId}")
	@PUT
	@Transactional
	public Response updateOffer(
		@Context SecurityContext sec,
		@PathParam("offerId") Long offerId,
		OfferDto1 offerDto
	) {
		Offer offer = offerRepo.findById(offerId);
		if (offer == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		if (offer.getOwner().getUsername() != sec.getUserPrincipal().getName()) {
			return Response.status(Status.FORBIDDEN).build();
		}
		offer.setTitle(offerDto.getTitle());
		offer.setShortDesc(offerDto.getShortDesc());
		offer.setPrice(offerDto.getPrice());
		offer.setCurrency(offerDto.getCurrency());
		offerRepo.persist(offer);
		return Response.ok().build();
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
	
	@GET
	@Path("/{offerId}/reviews")
	@PermitAll
	public Response getOfferReviews(
		@PathParam("offerId") Long offerId,
		@QueryParam("pageno") @DefaultValue("0") int pageIndex,
		@QueryParam("pagesz") @DefaultValue("20") int pageSize
	) {
		Offer offer = offerRepo.findById(offerId);
		if (offer == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		List<ReviewDto> reviews = reviewRepo
			.find("offer", offer)
			.page(pageIndex, pageSize)
			.stream()
				.map(ReviewDto::new)
				.collect(Collectors.toList());
		return Response.ok(reviews).build();
	}
	
	@Authenticated
	@GET
	@Path("/{offerId}/review")
	public Response getMyOfferReview(
		@Context SecurityContext sec,
		@PathParam("offerId") Long offerId
	) {
		User user = userRepo.findByUsername(sec.getUserPrincipal().getName());
		Offer offer = offerRepo.findById(offerId);
		if (offer == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		Review review = reviewRepo.find("offer = ?1 AND reviewer = ?2", offer, user).firstResult();
		return Response.ok(review == null ? null : new ReviewDto(review)).build();
	}
	
	@Authenticated
	@Path("/{offerId}/review")
	@POST
	@Transactional
	public Response setMyOfferReview(
		@Context SecurityContext sec,
		@PathParam("offerId") Long offerId,
		ReviewDto1 newReview
	) {
		User user = userRepo.findByUsername(sec.getUserPrincipal().getName());
		Offer offer = offerRepo.findById(offerId);
		if (offer == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		Review review = reviewRepo.find("offer = ?1 AND reviewer = ?2", offer, user).firstResult();
		if (review == null) {
			review = new Review();
			review.setOffer(offer);
			review.setReviewer(user);
		}
		review.setRating(newReview.getRating());
		review.setComment(newReview.getComment());
		reviewRepo.persist(review);
		return Response.ok().build();
	}
	
	@Authenticated
	@DELETE
	@Path("/{offerId}/review")
	@Transactional
	public Response deleteMyOfferReview(
		@Context SecurityContext sec,
		@PathParam("offerId") Long offerId
	) {
		User user = userRepo.findByUsername(sec.getUserPrincipal().getName());
		Offer offer = offerRepo.findById(offerId);
		if (offer == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		reviewRepo.delete("offer = ?1 AND reviewer = ?2", offer, user);
		return Response.ok().build();
	}
}
