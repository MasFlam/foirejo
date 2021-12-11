package com.masflam.foirejo.data.repo;

import javax.inject.Singleton;

import com.masflam.foirejo.data.entity.Trade;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@Singleton
public class TradeRepository implements PanacheRepository<Trade> {
}
