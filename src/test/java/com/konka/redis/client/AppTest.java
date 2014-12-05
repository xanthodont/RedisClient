package com.konka.redis.client;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;



/**
 * Unit test for simple App.
 */
public class AppTest {
    
    
    
    @Test
    public void iterateTest() {
    	JSONObject json = new JSONObject();
    	json.put("Imei", "1234567890321");
    	
    	JSONArray apps = new JSONArray();
    	JSONObject app = new JSONObject();
    	app.put("AppName", "微信");
    	app.put("PackageName", "com.tencent.weixin");
    	app.put("Times", "100");
    	apps.add(app);
    	json.put("Apps", apps);
    	//System.out.printf("%s", JSONArray.fromObject(apps).toString());
    	
    	JSONArray mems = new JSONArray();
    	JSONObject mem = new JSONObject();
    	mem.put("Date", "1415846512302");
    	mem.put("Type", "ROM");
    	mem.put("Size", "1232552");
    	mem.put("Min", "123123");
    	mem.put("Max", "2232552");
    	mem.put("Count", "12");
    	mems.add(mem);
    	json.put("Men", mems);
    	
    	JSONArray shutdowns = new JSONArray();
    	shutdowns.add(1394784878000L);
    	shutdowns.add(1394784008011L);
    	json.put("Shutdown", shutdowns);
    	
    	JSONArray updates = new JSONArray();
    	JSONObject update = new JSONObject();
    	update.put("Version", "V1.0");
    	update.put("Time", "1394784878000");
    	updates.add(update);
    	json.put("Update", updates);
    	
    	JSONArray locations = new JSONArray();
    	JSONObject location = new JSONObject();
    	location.put("Lng", 122.1222);
    	location.put("Lat", 155.2211);
    	locations.add(location);
    	json.put("Location", locations);
    	System.out.printf("%s", JSONObject.fromObject(json).toString());
    }
    
    @Test
    public void jsonTest() {
    	JSONObject jsonObject = new JSONObject();
		jsonObject.put("package", "umservices");
		jsonObject.put("content", "{content: 'content'}");
		jsonObject.put("to", "358888000000014");
		System.out.printf("%s\n", jsonObject.toString());
    }
}
