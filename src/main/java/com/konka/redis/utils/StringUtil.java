package com.konka.redis.utils;

public class StringUtil {
	public static boolean isEmpty(String value) {
		return value == null || "".equals(value);
	}
}
