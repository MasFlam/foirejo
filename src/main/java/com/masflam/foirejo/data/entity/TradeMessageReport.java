package com.masflam.foirejo.data.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class TradeMessageReport {
	@Embeddable
	public static class Id implements Serializable {
		@ManyToOne(optional = false)
		private User reporter;
		
		@ManyToOne(optional = false)
		private TradeMessage message;
		
		public User getReporter() {
			return reporter;
		}
		
		public void setReporter(User reporter) {
			this.reporter = reporter;
		}
		
		public TradeMessage getMessage() {
			return message;
		}
		
		public void setMessage(TradeMessage message) {
			this.message = message;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(reporter, message);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null || getClass() != obj.getClass()) return false;
			Id that = (Id) obj;
			return Objects.equals(reporter, that.reporter) && Objects.equals(message, that.message);
		}
	}
	
	@EmbeddedId
	private Id id;
	
	public Id getId() {
		return id;
	}
	
	public void setId(Id id) {
		this.id = id;
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
		TradeMessageReport that = (TradeMessageReport) obj;
		return Objects.equals(id, that.id);
	}
}
