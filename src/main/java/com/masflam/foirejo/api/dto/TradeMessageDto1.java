package com.masflam.foirejo.api.dto;

import com.masflam.foirejo.data.entity.TradeMessage;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class TradeMessageDto1 {
	private String content;
	
	public TradeMessageDto1(String content) {
		this.content = content;
	}
	
	public TradeMessageDto1(TradeMessage tm) {
		this(tm.getContent());
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}
