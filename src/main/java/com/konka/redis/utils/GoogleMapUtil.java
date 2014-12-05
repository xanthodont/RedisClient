package com.konka.redis.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

public class GoogleMapUtil {
	private static Logger logger = Logger.getLogger(GoogleMapUtil.class);
	private static int ConnectTimeOut = 10*1000; 
	private static int ReadTimeOut = 10*1000; 
	private static String HttpMethod = "GET"; 
	
	public static String[] googleGeocode(double lng, double lat) {
		StringBuilder builder = new StringBuilder();
		String urlString = String.format("http://ditu.google.com/maps/api/geocode/json?latlng=%f,%f&sensor=false&&language=zh-CN", lng, lat);
		URL url;
		try {
			url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("contentType", "UTF-8");
			conn.setConnectTimeout(ConnectTimeOut);
			conn.setReadTimeout(ReadTimeOut);
			conn.setRequestMethod(HttpMethod);
			
			InputStream inStream = conn.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
			String line = "";
			while ((line = in.readLine()) != null) {
				builder.append(line);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			logger.error("Protocol is error", e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Connection IO Exception", e);
		} finally {
			
		}
		
		return praseAddress(builder.toString());
	}
	
	/**
	 * 解析谷歌地图API的地理位置信息
	 * 注-- 想要查看接口调用返回的数据格式，请在浏览器中输入：
	 * 		http://ditu.google.com/maps/api/geocode/json?latlng=40.714224,-73.961452&sensor=false&&language=zh-CN
	 * @param json
	 * @return
	 */
	public static String[] praseAddress(String json) {
		String[] rs = {"", ""};
		if (StringUtil.isEmpty(json)) return rs;
		JSONObject rsJson = JSONObject.fromObject(json);
		String status = rsJson.getString("status");
		if (!StringUtil.isEmpty(status) && "OK".equals(status)) {
			JSONObject result = rsJson.getJSONArray("results").getJSONObject(0);
			rs[0] = result.getString("formatted_address");
			JSONArray components = result.getJSONArray("address_components");
			for (int i = 0; i < components.size(); i++) {
				JSONObject cpn = components.getJSONObject(i);
				if (cpn.getJSONArray("types").getString(0).equals("country")) {
					rs[1] = cpn.getString("long_name");
					return rs;
				}
			}
		}
		return rs;
	}
}
