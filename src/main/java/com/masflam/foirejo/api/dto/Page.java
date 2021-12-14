package com.masflam.foirejo.api.dto;

import java.util.Collection;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Page<T> {
	private Collection<T> items;
	private Long total;
	
	public Page(Collection<T> items, Long total) {
		this.items = items;
		this.total = total;
	}
	
	public Collection<T> getItems() {
		return items;
	}
	
	public void setItems(Collection<T> items) {
		this.items = items;
	}
	
	public Long getTotal() {
		return total;
	}
	
	public void setTotal(Long total) {
		this.total = total;
	}
}
