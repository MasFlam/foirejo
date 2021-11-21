package com.masflam.monerobarter;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import javax.transaction.Transactional;

import com.masflam.monerobarter.data.Currency;
import com.masflam.monerobarter.data.Offer;
import com.masflam.monerobarter.data.OfferRepository;
import com.masflam.monerobarter.data.User;
import com.masflam.monerobarter.data.UserRepository;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.runtime.StartupEvent;

@Singleton
public class Meta {
	@Transactional
	public void startup(@Observes StartupEvent evt, UserRepository userRepo, OfferRepository offerRepo) {
		var user = new User();
		user.setUsername("user");
		user.setPasswordHash(BcryptUtil.bcryptHash("pass"));
		user.setRoles("user");
		userRepo.persist(user);
		
		var offer = new Offer();
		offer.setTitle("Foo bar 1");
		offer.setPrice(150_000_000_000L);
		offer.setCurrency(Currency.XMR);
		offer.setOwner(user);
		offerRepo.persist(offer);
		
		offer = new Offer();
		offer.setTitle("Foo bar 2");
		offer.setPrice(250_00L);
		offer.setCurrency(Currency.USD);
		offer.setOwner(user);
		offerRepo.persist(offer);
		
		offer = new Offer();
		offer.setTitle("Foo bar 3");
		offer.setPrice(100_000_000L);
		offer.setCurrency(Currency.BTC);
		offer.setOwner(user);
		offerRepo.persist(offer);
		
		offer = new Offer();
		offer.setTitle("Foo bar 4");
		offer.setPrice(2_00L);
		offer.setCurrency(Currency.EUR);
		offer.setOwner(user);
		offerRepo.persist(offer);
	}
}
