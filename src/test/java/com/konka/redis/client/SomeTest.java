package com.konka.redis.client;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;

public class SomeTest {
	
	@Test
	public void jsonArrayTest() {
		String jsonString = "[1394784878000, 1394784878000]";
		JSONArray array = JSONArray.fromObject(jsonString); 
		for (int i = 0; i < array.size(); i++) {
			long json = (Long) (array.get(i));
			System.out.printf("%d\n", json);
		}
	}
}
