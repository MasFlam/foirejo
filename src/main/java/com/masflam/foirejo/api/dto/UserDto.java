package com.masflam.foirejo.api.dto;

import com.masflam.foirejo.data.entity.User;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class UserDto {
	private Long id;
	private String username;
	
	public UserDto(Long id, String username) {
		this.id = id;
		this.username = username;
	}
	
	public UserDto(User user) {
		this(user.getId(), user.getUsername());
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
}
