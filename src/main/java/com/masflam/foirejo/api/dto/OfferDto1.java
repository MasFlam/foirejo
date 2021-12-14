package com.masflam.foirejo.api.dto;

import com.masflam.foirejo.data.Currency;
import com.masflam.foirejo.data.entity.Offer;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class OfferDto1 {
	private String title;
	private String shortDesc;
	private Long price;
	private Currency currency;
	
	public OfferDto1(String title, String shortDesc, Long price, Currency currency) {
		this.title = title;
		this.shortDesc = shortDesc;
		this.price = price;
		this.currency = currency;
	}
	
	public OfferDto1(Offer offer) {
		this(offer.getTitle(), offer.getShortDesc(), offer.getPrice(), offer.getCurrency());
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getShortDesc() {
		return shortDesc;
	}
	
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
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
