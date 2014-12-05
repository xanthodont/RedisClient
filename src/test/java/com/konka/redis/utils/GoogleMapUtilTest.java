package com.konka.redis.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GoogleMapUtilTest {
	private String geocodeJson;
	
	@Before
	public void init() {
		StringBuilder builder = new StringBuilder();
		String filePath = this.getClass().getClassLoader().getResource(".").getPath() + "geocode.json";
		try {
			File file = new File(filePath);
			if (file.exists() && file.isFile() ) {
				InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
				BufferedReader bufferReader = new BufferedReader(reader); 
				String line = null;
				while ((line = bufferReader.readLine()) != null) {
					builder.append(line);
				}
			} 
			else {
				throw new FileNotFoundException("未找到文件,路径："+filePath);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		geocodeJson = builder.toString();
	}
	
	@Test
	public void praseAddressTest() {
		Assert.assertEquals(false, StringUtil.isEmpty(geocodeJson));
		String[] rs = GoogleMapUtil.praseAddress(geocodeJson);
		Assert.assertEquals(2, rs.length);
		Assert.assertEquals("美国", rs[1]);
	}
}
