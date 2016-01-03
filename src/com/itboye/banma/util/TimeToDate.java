package com.itboye.banma.util;

import android.annotation.SuppressLint;
import android.provider.ContactsContract.Contacts.Data;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeToDate {
	@SuppressLint({ "SimpleDateFormat", "UseValueOf" })
	public static String timeToDate(String time){
		String date = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
				.format(new java.util.Date(new Long(time) * 1000));
		System.out.println(date+"PPPPPPPPPPPPPPPPP");
		return date;
	}
	@SuppressLint("SimpleDateFormat")
	public static int remianTime(String time1,String time2){
		String date1=timeToDate(time1);
		String date2=timeToDate(time2);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = sdf.parse(date1);
			d2=sdf.parse(date2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		long t1=d1.getTime();
		long t2=d2.getTime();
		int day = (int)(t2-t1)/3600/1000/24; 
		return day;
	}
	
	public static String isOvertime(String time1,String time2,String is_used){
		int day=remianTime(time1, time2)+1;
		String temp="";
		if (is_used.equals("1")) {
			temp="已使用";
		}
		if (is_used.equals("0")) {
			if (day>0) {
				temp="还有"+day+"天过期";
			}else{
				temp="已过期";
			}
		}
		return temp;
	}
}
