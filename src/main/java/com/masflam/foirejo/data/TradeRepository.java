package com.masflam.foirejo.data;

import javax.inject.Singleton;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@Singleton
public class TradeRepository implements PanacheRepository<Trade> {
}
