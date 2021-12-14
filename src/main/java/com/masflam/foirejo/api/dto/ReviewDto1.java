package com.masflam.foirejo.api.dto;

import com.masflam.foirejo.data.entity.Review;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ReviewDto1 {
	private Integer rating;
	private String comment;
	
	public ReviewDto1(Integer rating, String comment) {
		this.rating = rating;
		this.comment = comment;
	}
	
	public ReviewDto1(Review review) {
		this(review.getRating(), review.getComment());
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
