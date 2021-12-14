package com.masflam.foirejo.api.dto;

import java.time.LocalDate;

import com.masflam.foirejo.data.entity.TradeMessage;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class TradeMessageDto {
	private Long id;
	private Long tradeId;
	private Boolean isAdmin;
	private LocalDate date;
	private String content;
	
	public TradeMessageDto(Long id, Long tradeId, Boolean isAdmin, LocalDate date, String content) {
		this.id = id;
		this.tradeId = tradeId;
		this.isAdmin = isAdmin;
		this.date = date;
		this.content = content;
	}
	
	public TradeMessageDto(TradeMessage tm) {
		this(
			tm.getId(), tm.getTrade().getId(), tm.getAuthor().getRoles().contains("admin"),
			tm.getDate(), tm.getContent()
		);
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getTradeId() {
		return tradeId;
	}
	
	public void setTradeId(Long tradeId) {
		this.tradeId = tradeId;
	}
	
	public Boolean getIsAdmin() {
		return isAdmin;
	}
	
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}
