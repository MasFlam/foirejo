package com.masflam.foirejo.api.dto;

import com.masflam.foirejo.data.Currency;
import com.masflam.foirejo.data.Trade;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class TradeDto {
	private Long id;
	private Long offerId;
	private Long price;
	private Currency currency;
	private Long buyerId;
	
	public TradeDto(Long id, Long offerId, Long price, Currency currency, Long buyerId) {
		this.id = id;
		this.offerId = offerId;
		this.price = price;
		this.currency = currency;
		this.buyerId = buyerId;
	}
	
	public TradeDto(Trade trade) {
		this(
			trade.getId(), trade.getOffer().getId(), trade.getPrice(),
			trade.getCurrency(), trade.getBuyer().getId()
		);
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getOfferId() {
		return offerId;
	}
	
	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}
	
	public Long getPrice() {
		return price;
	}
	
	public void setPrice(Long price) {
		this.price = price;
	}
	
	public Currency getCurrency() {
		return currency;
	}
	
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	public Long getBuyerId() {
		return buyerId;
	}
	
	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
}
