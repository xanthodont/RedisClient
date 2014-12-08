package com.konka.redis.client;

public class RedisConstants {
	public static final String REQUEST_REGISTER = "request-register";
	public static final String REQUEST_INFO = "request-info";
	public static final String INFO_APPS = "info-apps";
	public static final String INFO_LOCATION = "info-location";
	public static final String INFO_MEMORY = "info-momory";
	public static final String INFO_SHUTDOWN = "info-shutdown";
	public static final String INFO_UPDATE = "info-update";
	
	
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
