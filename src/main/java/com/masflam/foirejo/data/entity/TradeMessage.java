package com.masflam.foirejo.data.entity;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TradeMessage {
	@GeneratedValue
	@Id
	private Long id;
	
	@ManyToOne(optional = false)
	private Trade trade;
	
	@ManyToOne(optional = false)
	private User author;
	
	@Column(nullable = false)
	private LocalDate dateUtc;
	
	@Column(nullable = false)
	private String content;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Trade getTrade() {
		return trade;
	}
	
	public void setTrade(Trade trade) {
		this.trade = trade;
	}
	
	public User getAuthor() {
		return author;
	}
	
	public void setAuthor(User author) {
		this.author = author;
	}
	
	public LocalDate getDate() {
		return dateUtc;
	}
	
	public void setDate(LocalDate date) {
		this.dateUtc = date;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
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
		TradeMessage that = (TradeMessage) obj;
		return Objects.equals(id, that.id);
	}
}
