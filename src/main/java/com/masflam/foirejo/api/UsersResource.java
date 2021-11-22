package com.masflam.foirejo.api;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import com.masflam.foirejo.annotation.RestResource;
import com.masflam.foirejo.api.dto.OfferDto;
import com.masflam.foirejo.api.dto.TradeDto;
import com.masflam.foirejo.api.dto.UserDto;
import com.masflam.foirejo.data.OfferRepository;
import com.masflam.foirejo.data.TradeRepository;
import com.masflam.foirejo.data.User;
import com.masflam.foirejo.data.UserRepository;

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
	
	@Authenticated
	@GET
	@Path("/me")
	public UserDto me(@Context SecurityContext sec) {
		User user = userRepo.findByUsername(sec.getUserPrincipal().getName());
		return new UserDto(user);
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
}
