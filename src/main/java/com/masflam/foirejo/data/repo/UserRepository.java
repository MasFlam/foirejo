package com.masflam.foirejo.data.repo;

import java.util.Optional;

import javax.inject.Singleton;

import com.masflam.foirejo.data.entity.User;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@Singleton
public class UserRepository implements PanacheRepository<User> {
	public User findByUsername(String username) {
		return find("username", username).firstResult();
	}
	
	public Optional<User> findByUsernameOptional(String username) {
		return find("username", username).firstResultOptional();
	}
}
