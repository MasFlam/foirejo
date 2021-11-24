package com.masflam.foirejo;

import java.util.Locale;

import com.masflam.foirejo.data.Currency;

public class Utils {
	// Page number 0 in th array means a place for an ellipsis
	public static int[] pageNumbersFor(int pageIndex, int pageCount) {
		int[] pages;
		if (pageCount == 0) {
			pageCount = 1;
			pages = new int[] {1};
		} else if (pageCount <= 10) {
			pages = new int[pageCount];
			for (int i = 0; i < pageCount; ++i) {
				pages[i] = i+1;
			}
		} else if (pageIndex <= 4) {
			pages = new int[7];
			for (int i = 0; i < 5; ++i) {
				pages[i] = i+1;
			}
			pages[5] = 0;
			pages[6] = pageCount;
		} else if (pageIndex >= pageCount - 3) {
			pages = new int[7];
			for (int i = 0; i < 5; ++i) {
				pages[i + 2] = pageCount - (4-i);
			}
			pages[1] = 0;
			pages[0] = 1;
		} else {
			pages = new int[9];
			for (int i = 0; i < 5; ++i) {
				pages[i + 2] = pageIndex + i-2;
			}
			pages[7] = 0;
			pages[8] = pageCount;
			pages[1] = 0;
			pages[0] = 1;
		}
		return pages;
	}
	
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
