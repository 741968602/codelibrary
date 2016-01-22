package com.eagle.parking.utils;

import java.util.Random;

/**
 * 生成随机数
 * @author Xie Wenqian
 *
 */
public class MyRandom {
	
	/**
	 * 取当前时间的毫秒后八位，作为八位随机码
	 * @return
	 */
	public static int currentTimeMillisRandom(){
		
		return (int) (System.currentTimeMillis()%100000000);
	}
	
	/**
	 * 获取八位随机数
	 * @return
	 */
	public static String GetEightRandomNo() {
		String dataStr = "";
		Random r = new Random();
		int checknum = r.nextInt(99999999);
		if (checknum > 10000000) {
			dataStr = checknum + "";
		}
		return dataStr;
	}
	
	/**
	 * 获取16随机数
	 * @return
	 */
	public static String GetSixteenRandomNo() {
		String dataStr = "";
		Random r = new Random();
		for(int i=0;i<2;i++){
			int checknum = r.nextInt(99999999);
			if (checknum >= 99999999) {
				dataStr = checknum + dataStr;
			}
			i++;
		}
		
		return dataStr;
	}

}
