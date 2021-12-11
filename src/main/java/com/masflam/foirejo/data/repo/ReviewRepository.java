package com.masflam.foirejo.data.repo;

import javax.inject.Singleton;

import com.masflam.foirejo.data.entity.Review;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@Singleton
public class ReviewRepository implements PanacheRepository<Review> {
}
