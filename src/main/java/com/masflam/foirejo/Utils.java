package com.masflam.foirejo;

import java.util.Locale;

import com.masflam.foirejo.data.Currency;

public class Utils {
	// TODO: format amounts differently so that there aren't multiple zeros after '.'
	
	public static String humanizeAmount(long amount, Currency currency) {
		switch (currency) {
			case BTC: return humanizeBtcAmount(amount);
			case XMR: return humanizeXmrAmount(amount);
			case USD: return humanizeUsdAmount(amount);
			case EUR: return humanizeEurAmount(amount);
			default: return amount + " atomic units of " + currency;
		}
	}
	
	public static String humanizeBtcAmount(long satoshis) {
		long abs = Math.abs(satoshis);
		if (abs < 1_000) {
			return satoshis + " sat";
		} else {
			return String.format(Locale.ENGLISH, "%d.%08d", satoshis / 100_000_000L, abs % 100_000_000L) + " ₿";
		}
	}
	
	public static String humanizeXmrAmount(long piconero) {
		long abs = Math.abs(piconero);
		if (abs < 1_000L) {
			return piconero + " pɱ";
		} else if (abs < 1_000_000L) {
			return String.format(Locale.ENGLISH, "%d.%03d nɱ", piconero / 1_000L, abs % 1_000L);
		} else if (abs < 1_000_000_000L) {
			return String.format(Locale.ENGLISH, "%d.%03d µɱ", piconero / 1_000_000L, abs % 1_000_000L / 1_000L);
		} else if (abs < 1_000_000_000_000L) {
			return String.format(Locale.ENGLISH, "%d.%03d mɱ", piconero / 1_000_000_000L, abs % 1_000_000_000L / 1_000_000L);
		} else {
			return String.format(Locale.ENGLISH, "%d.%03d ɱ", piconero / 1_000_000_000_000L, abs % 1_000_000_000_000L / 1_000_000_000L);
		}
	}
	
	public static String humanizeUsdAmount(long cents) {
		long abs = Math.abs(cents);
		return String.format(Locale.ENGLISH, "%d.%02d $", cents / 100L, abs % 100L);
	}
	
	public static String humanizeEurAmount(long eurocents) {
		long abs = Math.abs(eurocents);
		return String.format(Locale.ENGLISH, "%d.%02d €", eurocents / 100L, abs % 100L);
	}
}
