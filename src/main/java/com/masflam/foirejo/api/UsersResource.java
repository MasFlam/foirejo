package com.masflam.foirejo.api;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import com.masflam.foirejo.annotation.RestResource;
import com.masflam.foirejo.api.dto.OfferDto;
import com.masflam.foirejo.api.dto.TradeDto;
import com.masflam.foirejo.api.dto.UserDto;
import com.masflam.foirejo.data.entity.Rating;
import com.masflam.foirejo.data.entity.User;
import com.masflam.foirejo.data.repo.OfferRepository;
import com.masflam.foirejo.data.repo.RatingRepository;
import com.masflam.foirejo.data.repo.TradeRepository;
import com.masflam.foirejo.data.repo.UserRepository;

import io.quarkus.security.Authenticated;

@Path("/api/users")
@RestResource
public class UsersResource {
	@Inject
	public OfferRepository offerRepo;
	
	@Inject
	public UserRepository userRepo;
	
	@Inject
	public TradeRepository tradeRepo;
	
	@Inject
	public RatingRepository ratingRepo;
	
	@Authenticated
	@GET
	@Path("/me")
	public UserDto me(@Context SecurityContext sec) {
		User user = userRepo.findByUsername(sec.getUserPrincipal().getName());
		return new UserDto(user);
	}
	
	@Authenticated
	@GET
	@Path("/me/offers")
	public Collection<OfferDto> getMyOffers(
		@Context SecurityContext sec,
		@QueryParam("pageno") @DefaultValue("0") int pageIndex,
		@QueryParam("pagesz") @DefaultValue("20") int pageSize
	) {
		User user = userRepo.findByUsername(sec.getUserPrincipal().getName());
		return offerRepo
			.find("owner", user)
			.page(pageIndex, pageSize)
			.stream()
				.map(OfferDto::new)
				.collect(Collectors.toList());
	}
	
	@Authenticated
	@GET
	@Path("/me/offers/count")
	public long countMyOffers(@Context SecurityContext sec) {
		User user = userRepo.findByUsername(sec.getUserPrincipal().getName());
		return offerRepo.count("owner", user);
	}
	
	@Authenticated
	@GET
	@Path("/me/trades")
	public Collection<TradeDto> getMyTrades(
		@Context SecurityContext sec,
		@QueryParam("pageno") @DefaultValue("0") int pageIndex,
		@QueryParam("pagesz") @DefaultValue("20") int pageSize
	) {
		User user = userRepo.findByUsername(sec.getUserPrincipal().getName());
		return tradeRepo
			.find("buyer = ?1 OR offer.owner = ?1", user)
			.page(pageIndex, pageSize)
			.stream()
				.map(TradeDto::new)
				.collect(Collectors.toList());
	}
	
	@Authenticated
	@GET
	@Path("/me/trades/count")
	public long countMyTrades(@Context SecurityContext sec) {
		User user = userRepo.findByUsername(sec.getUserPrincipal().getName());
		return tradeRepo.find("buyer = ?1 OR offer.owner = ?1", user).count();
	}
	
	@GET
	@Path("/{userId}")
	@PermitAll
	public Response getUserById(@PathParam("userId") Long userId) {
		User user = userRepo.findById(userId);
		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(new UserDto(user)).build();
	}
	
	@GET
	@Path("/{userId}/offers")
	@PermitAll
	public Response getOffersByOwnerId(
		@PathParam("userId") Long userId,
		@QueryParam("pageno") @DefaultValue("0") int pageIndex,
		@QueryParam("pagesz") @DefaultValue("20") int pageSize
	) {
		User user = userRepo.findById(userId);
		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		List<OfferDto> offers = offerRepo
			.find("owner", user)
			.page(pageIndex, pageSize)
			.stream()
				.map(OfferDto::new)
				.collect(Collectors.toList());
		return Response.ok(offers).build();
	}
	
	@GET
	@Path("/{userId}/offers/count")
	@PermitAll
	public Response countOffersByOwnerId(@PathParam("userId") Long userId) {
		User user = userRepo.findById(userId);
		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(offerRepo.count("owner", user)).build();
	}
	
	@Authenticated
	@GET
	@Path("/{userId}/rate")
	public Response getRating(@Context SecurityContext sec, @PathParam("userId") Long userId) {
		User rater = userRepo.findByUsername(sec.getUserPrincipal().getName());
		User ratee = userRepo.findById(userId);
		if (ratee == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		Rating rating = ratingRepo.findById(rater, ratee);
		if (rating == null) {
			return Response.ok(null).build();
		}
		System.out.println(rating.getRating());
		return Response.ok(rating.getRating()).build();
	}
	
	@Authenticated
	@Consumes(MediaType.APPLICATION_JSON)
	@POST
	@Path("/{userId}/rate")
	@Transactional
	public Response rate(@Context SecurityContext sec, @PathParam("userId") Long userId, Boolean body) {
		User rater = userRepo.findByUsername(sec.getUserPrincipal().getName());
		User ratee = userRepo.findById(userId);
		if (ratee == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		var ratingId = new Rating.Id();
		ratingId.setRater(rater);
		ratingId.setRatee(ratee);
		var rating = new Rating();
		rating.setId(ratingId);
		rating.setRating(body);
		ratingRepo.persist(rating);
		return Response.ok().build();
	}
	
	@Authenticated
	@DELETE
	@Path("/{userId}/rate")
	@Transactional
	public Response unrate(@Context SecurityContext sec, @PathParam("userId") Long userId) {
		User rater = userRepo.findByUsername(sec.getUserPrincipal().getName());
		User ratee = userRepo.findById(userId);
		if (ratee == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		var rating = ratingRepo.findById(rater, ratee);
		if (rating != null) {
			ratingRepo.delete(rating);
		}
		return Response.ok().build();
	}
}
