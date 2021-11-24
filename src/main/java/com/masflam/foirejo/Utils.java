package com.masflam.foirejo;

import java.text.DecimalFormat;

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
	
	// The javadoc says nothing about DecimalFormat being safe to use concurrently,
	// so use these only inside synchronized blocks
	private static DecimalFormat DECIMAL_FORMAT_BTC = new DecimalFormat("#.########");
	private static DecimalFormat DECIMAL_FORMAT_XMR = new DecimalFormat("#.###");
	private static DecimalFormat DECIMAL_FORMAT_FIAT = new DecimalFormat("#.##");
	
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
		}
		synchronized (DECIMAL_FORMAT_BTC) {
			return DECIMAL_FORMAT_BTC.format(satoshis / 1e8) + " ₿";
		}
	}
	
	public static String humanizeXmrAmount(long piconero) {
		long abs = Math.abs(piconero);
		if (abs < 1_000L) {
			return piconero + " pɱ";
		}
		synchronized (DECIMAL_FORMAT_XMR) {
			if (abs < 1_000_000L) {
				return DECIMAL_FORMAT_XMR.format(piconero / 1e3) + " nɱ";
			} else if (abs < 1_000_000_000L) {
				return DECIMAL_FORMAT_XMR.format(piconero / 1e6) + " µɱ";
			} else if (abs < 1_000_000_000_000L) {
				return DECIMAL_FORMAT_XMR.format(piconero / 1e9) + " mɱ";
			} else {
				return DECIMAL_FORMAT_XMR.format(piconero / 1e12) + " ɱ";
			}
		}
	}
	
	public static String humanizeUsdAmount(long cents) {
		synchronized (DECIMAL_FORMAT_FIAT) {
			return DECIMAL_FORMAT_FIAT.format(cents / 100.0) + " $";
		}
	}
	
	public static String humanizeEurAmount(long eurocents) {
		synchronized (DECIMAL_FORMAT_FIAT) {
			return DECIMAL_FORMAT_FIAT.format(eurocents / 100.0) + " €";
		}
	}
}
