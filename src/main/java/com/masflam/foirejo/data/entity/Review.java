package com.masflam.foirejo.data.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Review {
	@GeneratedValue
	@Id
	private Long id;
	
	@ManyToOne(optional = false)
	private Offer offer;
	
	@ManyToOne(optional = false)
	private User reviewer;
	
	@Column(nullable = false)
	private Integer rating;
	
	@Column(nullable = false)
	private String comment;
	
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
	
	public User getReviewer() {
		return reviewer;
	}
	
	public void setReviewer(User reviewer) {
		this.reviewer = reviewer;
	}
	
	public Integer getRating() {
		return rating;
	}
	
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Review that = (Review) obj;
		return Objects.equals(id, that.id);
	}
}
