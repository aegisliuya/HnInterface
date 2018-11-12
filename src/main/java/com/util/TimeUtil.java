package com.util;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TimeUtil {

	private static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 得到多少天之前的日期
	 * 
	 * @return
	 */
	public static Date getDate(int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();

	}
	
	public static boolean withinDays(Date dt, int count)
	  {
	    Date now = new Date();
	    long day = (now.getTime() - dt.getTime()) / 43200000L;
	    return day <= count-1;
	  }
	
	/**
	 * 获取interval*12小时之前的时间
	 * */
	public static Date getHalfDate(int interval){
		Calendar calendar = Calendar.getInstance();
		Date now=new Date();
		long targetTime=now.getTime()- interval*43200000L;
		calendar.setTimeInMillis(targetTime);
		return calendar.getTime();
	}
	/**
	 * 获得特定小时之前的时间
	 * */
	public static Date getHourAgoTime(int hour)
	{
		if(hour <= 0 )
			return null;
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY,-hour);
		return calendar.getTime();
	}
	
	/**
	 * 获得特定
	 */
	public static Date getHourLaterTime(int hour)
	{
		if(hour<=0)
			return null;
		
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, hour);
		return calendar.getTime();
	}
	
	public static String getDayTime(){
		Date date=new Date();
		String h=""+date.getHours();
		String dateStr=sf.format(date);
		return dateStr.substring(0, 11)+h+dateStr.substring(13,19);
	}
	/**
	 * 转换成
	 * yyyy-MM-dd hh:mm:ss
	 * @param date
	 * @return
	 */
	public static String format(Date date){
		if(date == null)
			return null;
		
		String h=""+date.getHours();
		String dateStr=sf.format(date);
		return dateStr.substring(0, 11)+h+dateStr.substring(13,19);
	}
	
	public static String formatNew(Date date){
		return sf.format(date);
	}
	
	public static void main(String[] args) {
		System.out.println(TimeUtil.getDate(0));
	}
}
