package com.masflam.foirejo.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Trade {
	@GeneratedValue
	@Id
	private Long id;
	
	@ManyToOne(optional = false)
	private Offer offer;
	
	@Column(nullable = false)
	private Long price;
	
	@ManyToOne(optional = false)
	private User buyer;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Offer getOffer() {
		return offer;
	}
	
	public void setOffer(Offer offer) {
		this.offer = offer;
	}
	
	public Long getPrice() {
		return price;
	}
	
	public void setPrice(Long price) {
		this.price = price;
	}
	
	public User getBuyer() {
		return buyer;
	}
	
	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}
}
