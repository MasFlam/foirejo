package com.masflam.foirejo.api.dto;

import com.masflam.foirejo.data.Currency;
import com.masflam.foirejo.data.Offer;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class OfferDto {
	private Long id;
	private String title;
	private Long ownerId;
	private Long price;
	private Currency currency;
	
	public OfferDto(Long id, String title, Long ownerId, Long price, Currency currency) {
		this.id = id;
		this.title = title;
		this.ownerId = ownerId;
		this.price = price;
		this.currency = currency;
	}
	
	public OfferDto(Offer offer) {
		this(
			offer.getId(), offer.getTitle(), offer.getOwner().getId(),
			offer.getPrice(), offer.getCurrency()
		);
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Long getOwnerId() {
		return ownerId;
	}
	
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
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
}
