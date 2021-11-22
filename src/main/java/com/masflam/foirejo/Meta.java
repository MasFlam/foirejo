package com.masflam.foirejo;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import javax.transaction.Transactional;

import com.masflam.foirejo.data.Currency;
import com.masflam.foirejo.data.Offer;
import com.masflam.foirejo.data.OfferRepository;
import com.masflam.foirejo.data.User;
import com.masflam.foirejo.data.UserRepository;

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
