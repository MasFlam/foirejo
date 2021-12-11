package com.masflam.foirejo.data.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Rating {
	@Embeddable
	public static class Id implements Serializable {
		@ManyToOne(optional = false)
		private User rater;
		
		@ManyToOne(optional = false)
		private User ratee;
		
		public User getRater() {
			return rater;
		}
		
		public void setRater(User rater) {
			this.rater = rater;
		}
		
		public User getRatee() {
			return ratee;
		}
		
		public void setRatee(User ratee) {
			this.ratee = ratee;
		}

		@Override
		public int hashCode() {
			return Objects.hash(rater, ratee);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null || getClass() != obj.getClass()) return false;
			Id that = (Id) obj;
			return Objects.equals(rater, that.rater) && Objects.equals(ratee, that.ratee);
		}
	}
	
	@EmbeddedId
	private Id id;
	
	@Column(nullable = false)
	private Boolean rating;
	
	public Id getId() {
		return id;
	}
	
	public void setId(Id id) {
		this.id = id;
	}
	
	public Boolean getRating() {
		return rating;
	}
	
	public void setRating(Boolean rating) {
		this.rating = rating;
	}
}
