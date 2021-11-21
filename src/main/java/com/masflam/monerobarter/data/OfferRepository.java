package com.masflam.monerobarter.data;

import javax.inject.Singleton;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@Singleton
public class OfferRepository implements PanacheRepository<Offer> {
}
