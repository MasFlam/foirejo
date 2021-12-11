package com.masflam.foirejo.data.repo;

import javax.inject.Singleton;

import com.masflam.foirejo.data.entity.TradeMessage;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@Singleton
public class TradeMessageRepository implements PanacheRepository<TradeMessage> {
}
