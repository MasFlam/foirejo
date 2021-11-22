package com.masflam.foirejo;

public class Utils {
	public static String humanizeMoneroAmount(long pico) {
		long abs = pico < 0L ? -pico : pico;
		long sign = pico < 0L ? -1L : 1L;
		if (abs < 1_000L) {
			return pico + " pɱ";
		} else if (abs < 1_000_000L) {
			return abs / 1_000L * sign + "." + abs % 1_000L + " nɱ";
		} else if (abs < 1_000_000_000L) {
			return abs / 1_000_000L * sign + "." + abs % 1_000_000L / 1_000L + " µɱ";
		} else if (abs < 1_000_000_000_000L) {
			return abs / 1_000_000_000L * sign + "." + abs % 1_000_000_000L / 1_000_000L + " mɱ";
		} else {
			return abs / 1_000_000_000_000L * sign + "." + abs % 1_000_000_000_000L / 1_000_000_000L + " ɱ";
		}
	}
}
