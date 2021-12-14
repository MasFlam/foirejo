package com.masflam.foirejo.data.entity;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;

@Entity
@Table(name = "users", indexes = @Index(columnList = "username"))
@UserDefinition
public class User {
	@GeneratedValue
	@Id
	private Long id;
	
	@Column(unique = true, nullable = false)
	@Username
	private String username;
	
	@Column(nullable = false)
	@Password
	private String passwordHash;
	
	@Column(nullable = false)
	@Roles
	private String roles;
	
	@Column(nullable = false)
	private LocalDate joinDateUtc;
	
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
	
	public String getPasswordHash() {
		return passwordHash;
	}
	
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	public String getRoles() {
		return roles;
	}
	
	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	public LocalDate getJoinDate() {
		return joinDateUtc;
	}
	
	public void setJoinDate(LocalDate joinDate) {
		this.joinDateUtc = joinDate;
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
		User that = (User) obj;
		return Objects.equals(id, that.id);
	}
}
