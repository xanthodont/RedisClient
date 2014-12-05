package com.konka.redis.utils;



import org.junit.Assert;
import org.junit.Test;

public class StringUtilTest {
	@Test
	public void isEmptyTest() {
		String a = null;
		String b = "";
		String c = "1";
		Assert.assertEquals(true, StringUtil.isEmpty(a));
		Assert.assertEquals(true, StringUtil.isEmpty(b));
		Assert.assertEquals(false, StringUtil.isEmpty(c));
	}
}
