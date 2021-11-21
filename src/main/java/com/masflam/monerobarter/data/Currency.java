package com.masflam.monerobarter.data;

public enum Currency {
	XMR("monero", 1_000_000_000_000L, false),
	BTC("bitcoin", 100_000_000L, false),
	USD("usd", 100L, true),
	EUR("eur", 100L, true);
	
	private final String id;
	private final long denom;
	private final boolean fiat;
	
	private Currency(String id, long denom, boolean fiat) {
		this.id = id;
		this.denom = denom;
		this.fiat = fiat;
	}
	
	public String getId() {
		return id;
	}
	
	public long getDenom() {
		return denom;
	}
	
	public boolean isFiat() {
		return fiat;
	}
}
