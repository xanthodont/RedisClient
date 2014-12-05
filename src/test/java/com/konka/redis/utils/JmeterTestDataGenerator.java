package com.konka.redis.utils;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class JmeterTestDataGenerator {
	private String filepath;
	private String registerInfoFile;
	private String[] phoneTypes = {"IQ4415 Quad", "Symphony W160", "I277", "BK919", "I300", "W981", "W900", "W990", "SIII"};
	
	@Before
	public void init() {
		filepath = this.getClass().getClassLoader().getResource(".").getPath();
		registerInfoFile = "register_test.cvs";
	}
	
	@Test
	public void generateRegisterData() throws IOException {
		File registerFile = new File(filepath + File.separator + registerInfoFile);
		if (registerFile.exists()) {
			PrintWriter writer = new PrintWriter(registerFile);
			for (int i = 1; i < 1000001; i++) {
				String phoneType = phoneTypes[new Random().nextInt(100) % phoneTypes.length];
				writer.println(String.valueOf(i) + "," +  phoneType);
			}
			writer.flush();
			writer.close();
		} else {
			fail();
		}
	}
}
