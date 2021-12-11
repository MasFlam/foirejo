package com.masflam.foirejo.data.repo;

import javax.inject.Singleton;

import com.masflam.foirejo.data.entity.Offer;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@Singleton
public class OfferRepository implements PanacheRepository<Offer> {
}
