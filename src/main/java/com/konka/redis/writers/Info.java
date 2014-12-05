package com.konka.redis.writers;

import java.nio.charset.Charset;

public class Info {
	private long imei;
	private String key;
	private String params;

	public Info(String imei, String jsonKey, String jsonParam) {
		// TODO Auto-generated constructor stub
		this.imei = Long.parseLong(imei);
		this.key = jsonKey;
		this.params = jsonParam;
	}
	public Info(long imei, String jsonKey, String jsonParam) {
		// TODO Auto-generated constructor stub
		this.imei = imei;
		this.key = jsonKey;
		this.params = jsonParam;
	}

	public String getParams() {
		// TODO Auto-generated method stub
		return params;
	}

	public long getImei() {
		// TODO Auto-generated method stub
		return imei;
	}

}
