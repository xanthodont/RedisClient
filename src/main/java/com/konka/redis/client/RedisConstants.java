package com.konka.redis.client;

public class RedisConstants {
	public static final String REQUEST_REGISTER = "request-register";
	public static final String REQUEST_INFO = "request-info";
	
	public static final String SWITCH_ = "sw-";
	public static final String APP_ = "app-";
	
	public static String buildSwitch(String phoneType) {
		return SWITCH_ + phoneType;
	}

	public static String buildApp(String systemVersion) {
		// TODO Auto-generated method stub
		return APP_ + systemVersion;
	}
}
