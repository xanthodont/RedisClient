package com.konka.redis.utils;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class SwitchConversionUtilTest {
	private static Logger logger = Logger.getLogger(SwitchConversionUtilTest.class);
	//@Test
	public void getSwitchTest() {
		int v1 = 0x80000001;
		logger.debug(String.format("%h", v1));
		Assert.assertEquals(true, SwitchConversionUtil.getSwitch(v1, 31));
		Assert.assertEquals(true, SwitchConversionUtil.getSwitch(v1, 0));
		//int v2 = 
	}
}
