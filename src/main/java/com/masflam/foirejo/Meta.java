package com.masflam.foirejo;

import java.time.LocalDate;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import javax.transaction.Transactional;

import com.masflam.foirejo.data.Currency;
import com.masflam.foirejo.data.entity.Offer;
import com.masflam.foirejo.data.entity.User;
import com.masflam.foirejo.data.repo.OfferRepository;
import com.masflam.foirejo.data.repo.UserRepository;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.runtime.StartupEvent;

@Singleton
public class Meta {
	@Transactional
	public void startup(@Observes StartupEvent evt, UserRepository userRepo, OfferRepository offerRepo) {
		var user = new User();
		user.setUsername("admin");
		user.setPasswordHash(BcryptUtil.bcryptHash("admin"));
		user.setRoles("user,admin");
		user.setJoinDate(LocalDate.now());
		userRepo.persist(user);
		
		user = new User();
		user.setUsername("user");
		user.setPasswordHash(BcryptUtil.bcryptHash("pass"));
		user.setRoles("user");
		user.setJoinDate(LocalDate.now());
		userRepo.persist(user);
		
		var offer = new Offer();
		offer.setTitle("Foo bar 1");
		offer.setShortDesc("A bar of foo.");
		offer.setLongDesc("Long description.");
		offer.setPrice(150_000_000_000L);
		offer.setCurrency(Currency.XMR);
		offer.setOwner(user);
		offerRepo.persist(offer);
		
		offer = new Offer();
		offer.setTitle("Foo bar 2");
		offer.setShortDesc("A very high quality bar of foo.");
		offer.setLongDesc("Long description.");
		offer.setPrice(250_00L);
		offer.setCurrency(Currency.USD);
		offer.setOwner(user);
		offerRepo.persist(offer);
		
		offer = new Offer();
		offer.setTitle("Foo bar 3");
		offer.setShortDesc("The best foo bar on the market.");
		offer.setLongDesc("Long description.");
		offer.setPrice(100_000_000L);
		offer.setCurrency(Currency.BTC);
		offer.setOwner(user);
		offerRepo.persist(offer);
		
		offer = new Offer();
		offer.setTitle("Foo bar 4");
		offer.setShortDesc("Some foo in the form of a bar.");
		offer.setLongDesc("Long description.");
		offer.setPrice(2_00L);
		offer.setCurrency(Currency.EUR);
		offer.setOwner(user);
		offerRepo.persist(offer);
	}
}
