package com.masflam.foirejo.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masflam.foirejo.data.Currency;

import io.quarkus.cache.CacheResult;

@Singleton
public class CurrencyPriceService {
	// TODO: work out if doubles have enough precision for every case
	
	private static final String COINGECKO_ENDPOINT = "https://api.coingecko.com/api/v3/simple/price";
	
	private static final String CRYPTO_IDS = Arrays.stream(Currency.values())
		.filter(c -> !c.isFiat() && c != Currency.BTC)
		.map(Currency::getId)
		.collect(Collectors.joining(","));
	
	private static final String FIAT_IDS = Arrays.stream(Currency.values())
		.filter(Currency::isFiat)
		.map(Currency::getId)
		.collect(Collectors.joining(","));
	
	private static final URI BTC_PRICE_ENDPOINT_URI = URI.create(COINGECKO_ENDPOINT + "?ids=bitcoin&vs_currencies=" + FIAT_IDS);
	private static final URI CRYPTO_PRICE_ENDPOINT_URI = URI.create(COINGECKO_ENDPOINT + "?vs_currencies=btc&ids=" + CRYPTO_IDS);
	
	private HttpClient httpClient = HttpClient.newHttpClient();
	
	@Inject
	ObjectMapper mapper;
	
	@CacheResult(cacheName = "btc-price-cache")
	Map<Currency, Double> btcPriceInFiats() throws JsonMappingException, JsonProcessingException, IOException, InterruptedException {
		var resp = mapper.readTree(
			httpClient.send(
				HttpRequest.newBuilder(BTC_PRICE_ENDPOINT_URI)
					.header("Accept", "application/json")
					.build(),
				BodyHandlers.ofString()
			).body()
		);
		Map<Currency, Double> map = new EnumMap<>(Currency.class);
		for (var currency : Currency.values()) {
			if (!currency.isFiat()) continue;
			map.put(currency, resp.get("bitcoin").get(currency.getId()).asDouble() * currency.getDenom() / Currency.BTC.getDenom());
		}
		return map;
	}
	
	@CacheResult(cacheName = "crypto-price-cache")
	Map<Currency, Double> cryptoPricesInBtc() throws JsonMappingException, JsonProcessingException, IOException, InterruptedException {
		var resp = mapper.readTree(
			httpClient.send(
				HttpRequest.newBuilder(CRYPTO_PRICE_ENDPOINT_URI)
					.header("Accept", "application/json")
					.build(),
				BodyHandlers.ofString()
			).body()
		);
		Map<Currency, Double> map = new EnumMap<>(Currency.class);
		for (var currency : Currency.values()) {
			if (currency.isFiat() || currency == Currency.BTC) continue;
			map.put(currency, resp.get(currency.getId()).get("btc").asDouble() * Currency.BTC.getDenom() / currency.getDenom());
		}
		return map;
	}
	
	
	// This is a separate method so that no cache invalidations happen before the recursive call
	double convertWith(Map<Currency, Double> btcInFiat, Map<Currency, Double> cryptoInBtc, double amount, Currency from, Currency to) {
		System.out.println("convert(" + amount + ", " + from + ", " + to + ")");
		if (from == to) {
			return amount;
		} else if (from == Currency.BTC) {
			if (to.isFiat()) {
				return amount * btcInFiat.get(to);
			} else {
				return amount / cryptoInBtc.get(to);
			}
		} else if (to == Currency.BTC) {
			if (from.isFiat()) {
				return amount / btcInFiat.get(from);
			} else {
				return amount * cryptoInBtc.get(from);
			}
		} else {
			return convertWith(btcInFiat, cryptoInBtc, convertWith(btcInFiat, cryptoInBtc, amount, from, Currency.BTC), Currency.BTC, to);
		}
	}
	
	// This all works on minimal denominations of the currencies
	public double convert(double amount, Currency from, Currency to) {
		try {
			return convertWith(btcPriceInFiats(), cryptoPricesInBtc(), amount, from, to);
		} catch (Throwable t) {
			t.printStackTrace();
			return -1;
		}
	}
}
