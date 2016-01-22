package com.eagle.parking.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	/**
	 * 与当前时间比较，当大于当前时间时，返回true
	 * @param date
	 * @return
	 */
	public static boolean afterNowDate(String date){
		
		long nowDate = getNowDateOnlyLong();
		long parseDate = parseDateStringToLong(date);
		
		if(parseDate > nowDate){
			return true;
		}else{
			return false;
		}
		
	}
	/**
	 * 与当前时间比较，当大于等于当前时间时，返回true
	 * @param date
	 * @return
	 */
	public static boolean afterNowDate2(String date){
		
		long nowDate = getNowDateOnlyLong();
		long parseDate = parseDateStringToLong(date);
		
		if(parseDate >=nowDate){
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * 与当前时间比较，当小于当前时间时，返回true
	 * @param date
	 * @return
	 */
	public static boolean beforeNowDate(String date){
		
		long nowDate = getNowDateOnlyLong();
		long parseDate = parseDateStringToLong(date);
		
		if(parseDate < nowDate){
			return true;
		}else{
			return false;
		}
		
	}

	/**
	 * 返回时间：yyyy-MM-dd（字符串转换）
	 * @param str
	 * @return
	 */
	public static  Date parseStringToDate(String str) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			return dateFormat.parse(str);
		} catch (Exception ex) {
			System.out.println("DateUtils.java : " + ex.getMessage());
			return null;
		}
	}
	

	/**
	 * 将时间yyyy-MM-dd HH:mm:ss转换为yyyyMMddHHmmss样式的字符串
	 * @param date
	 * @return
	 */
    public static String formatFullDateToString(Date date) {
    	if(date == null) {
    		return "";
    	}
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }
	
    /**
     * 获取当前时间，返回格式为  yyyyMMddHHmmss
     * @return
     */
    public static long getNowDateTimeLong(){
    	
		return parseDateStringToLong(getNowDate());
    	
    }
	
	/**
	 * 获取当前时间，返回格式为  yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getNowDate(){
		
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = dateFormat.format(date);
		
		return str;
	}
	
	
	/**
	 * 获取当前时间，返回格式为  yyyy-MM-dd
	 * @return
	 */
	public static String getNowDateOnly(){
		
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String str = dateFormat.format(date);
		
		return str;
	}	
	
	
	/**
	 * 获取当前时间，返回格式为  yyyyMMdd
	 * @return
	 */
	public static String getNowDateOnlyStr(){
		
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String str = dateFormat.format(date);
		
		return str;
	}	
	
	/**
	 * 获取当前时间，返回格式为  long类型，yyyyMMdd
	 * @return
	 */
	public static long getNowDateOnlyLong(){
		
		return Long.valueOf(getNowDateOnlyStr());
	}
	
	/**
	 * 获取当前时间之后几天
	 * @param day
	 * @return
	 */
	public static Calendar getDateAfterDaysCalendar(int day){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, day);
		return calendar;
	}
	
	/**
	 * 获取当前时间之后几天
	 * @param day
	 * @return
	 */
	public static Date getDateAfterDaysDate(int day){
		return getDateAfterDaysCalendar(day).getTime();
	}
	
	/**
	 * 获取当前时间向后推迟的几天
	 * @param day
	 * @param format 时间格式
	 * @return
	 */
	public static String getDateAfterDays(int day,String format){
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String str = dateFormat.format(getDateAfterDaysDate(day));
		
		return str;
	}
	
	/**
	 * 获取当前时间向后推迟的几天，格式为"yyyy-MM-dd"
	 * @param day
	 * @return
	 */
	public static String getDateAfterDays(int day){
		return getDateAfterDays(day, "yyyy-MM-dd");
	}
	
	/**
	 * 获取当前时间以后的三天时间，返回格式为  yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getDateTimeAfterThreeDays(){
		
		return getDateAfterDays(3, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 将时间串转化为数组类型，可转换字符有 -:和空格
	 * @param str
	 * @return
	 */
	public static String[] parseDateStrToArr(String str){
		
		if (null == str) {
			return null;
		}
	
		str = str.replace(" ", "_").replace("-", "_").replace(":", "_");
		
		String[] arrStr = str.split("_");
		//String bString=str.split("_")[0];
		return arrStr;
	}
	
	/**
	 * 将时间串转换为long类型,去除“ ”，“-”，“：”符号
	 * @param str
	 * @return
	 */
	public static long parseDateStringToLong(String str){
		
		if(null == str){
			return 0;
		}
		
		str = str.replace(" ", "").replace("-", "").replace(":", "");
		
		return Long.valueOf(str);
	}
	
	
	/**
	 * 处理时间，将一位数转换为两位数：如，当整数时间为1时，转换为01
	 * @param i
	 * @return
	 */
	public static String parseDateAddOneZero(int i){
		String str = null;
		if(i<10){
			str = "0"+i;
		}else{
			str = ""+i;
		}
		
		return str;
	}
	
	
	/**
	 * 返回格式为  yyyy-MM-dd
	 * @return
	 */
	public static String parseDateStrFromDateTimeStr(String dateTime){
		
		if(dateTime!=null && !"".equals(dateTime)){
			return dateTime.substring(0, 10);
		}
		
		return "0000-00-00";
	}
}
