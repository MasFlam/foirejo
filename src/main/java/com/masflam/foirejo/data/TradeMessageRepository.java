package com.masflam.foirejo.data;

import javax.inject.Singleton;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@Singleton
public class TradeMessageRepository implements PanacheRepository<TradeMessage> {
}
