package com.masflam.foirejo.data;

import javax.inject.Singleton;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@Singleton
public class RatingRepository implements PanacheRepositoryBase<Rating, Rating.Id> {
	public Rating findById(User rater, User ratee) {
		var id = new Rating.Id();
		id.setRater(rater);
		id.setRatee(ratee);
		return findById(id);
	}
}
