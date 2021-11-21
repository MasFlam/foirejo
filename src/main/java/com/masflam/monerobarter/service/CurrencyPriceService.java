package com.masflam.monerobarter.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masflam.monerobarter.data.Currency;

import io.quarkus.cache.CacheResult;

@Singleton
public class CurrencyPriceService {
	private static final String COINGECKO_ENDPOINT = "https://api.coingecko.com/api/v3/simple/price";
	
	private HttpClient httpClient = HttpClient.newHttpClient();
	
	@Inject
	ObjectMapper mapper;
	
	
	@CacheResult(cacheName = "btc-price-cache")
	double btcPriceInFiat(Currency currency) throws JsonMappingException, JsonProcessingException, IOException, InterruptedException {
		return mapper
			.readTree(
				httpClient.send(
					HttpRequest.newBuilder(URI.create(COINGECKO_ENDPOINT + "?ids=bitcoin&vs_currencies=" + currency.getId()))
						.header("Accept", "application/json")
						.build(),
					BodyHandlers.ofString()
				).body()
			).get("bitcoin")
			.get(currency.getId())
			.asDouble();
	}
	
	@CacheResult(cacheName = "crypto-price-cache")
	double cryptoPriceInBtc(Currency currency) throws JsonMappingException, JsonProcessingException, IOException, InterruptedException {
		return mapper
			.readTree(
				httpClient.send(
					HttpRequest.newBuilder(URI.create(COINGECKO_ENDPOINT + "?vs_currencies=btc&ids=" + currency.getId()))
						.header("Accept", "application/json")
						.build(),
					BodyHandlers.ofString()
				).body()
			).get(currency.getId())
			.get("btc")
			.asDouble();
	}
	
	public double getPriceInBtc(Currency currency) {
		try {
			if (currency == Currency.BTC) {
				return 1;
			} else if (currency.isFiat()) {
				return 1 / btcPriceInFiat(currency);
			} else {
				return cryptoPriceInBtc(currency);
			}
		} catch(Throwable t) {
			t.printStackTrace();
			return -1;
		}
	}
}
