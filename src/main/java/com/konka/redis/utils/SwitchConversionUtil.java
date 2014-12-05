package com.konka.redis.utils;

public class SwitchConversionUtil {
	/**
	 * 获取指定位置的开关状态
	 * @param value switch的值
	 * @param i 开关位置，从0算起
	 * @return
	 */
	public static boolean getSwitch(int value, int i) {
		return (value & (1 << (i-1))) > 0;
	}
	
	public static int setSwitch(int value, int i) {
		value = value | (1 << (i-1));
		return value;
	} 
}
