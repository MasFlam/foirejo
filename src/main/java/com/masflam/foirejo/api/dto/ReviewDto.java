package com.masflam.foirejo.api.dto;

import com.masflam.foirejo.data.entity.Review;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ReviewDto {
	private Long id;
	private Long offerId;
	private Long reviewerId;
	private Integer rating;
	private String comment;
	
	public ReviewDto(Long id, Long offerId, Long reviewerId, Integer rating, String comment) {
		this.id = id;
		this.offerId = offerId;
		this.reviewerId = reviewerId;
		this.rating = rating;
		this.comment = comment;
	}
	
	public ReviewDto(Review review) {
		this(
			review.getId(), review.getOffer().getId(), review.getReviewer().getId(),
			review.getRating(), review.getComment()
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
	
	public Long getReviewerId() {
		return reviewerId;
	}
	
	public void setReviewerId(Long reviewerId) {
		this.reviewerId = reviewerId;
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
}
