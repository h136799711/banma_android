package com.itboye.banma.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.itboye.banma.app.AppContext;

public class VolleyRequest {
	public static StringRequest strRequest;
	public static JsonRequest<JSONObject> jsonRequest;
	public static JsonRequest<JSONArray> jsonArrayRequest;
	public static StringRequest stringRequest;
	public static Context context;
	public AppContext appContext;

	public static void StrRequestGet(Context mContext, String url, String tag,
			StrVolleyInterface vif) {
		AppContext.getHttpQueues().cancelAll(tag);
		strRequest = new StringRequest(Method.GET, url, vif, vif){

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Accept", "application/json");
				headers.put("Content-Type", "charset=UTF-8");
				return headers;
			}
			
		};
		strRequest.setTag(tag);
		AppContext.getHttpQueues().add(strRequest);
		AppContext.getHttpQueues().start();
	}

	public static void StrRequestPost(Context mContext, String url, String tag,
			final Map<String, String> params, StrVolleyInterface vif) {
		
		JSONObject jsonObject = new JSONObject(params);
		System.out.println("Map转化成JSON="+jsonObject);

		strRequest = new StringRequest(Method.POST, url, vif, vif){
			@Override
			protected Map<String,String> getParams() throws AuthFailureError {
//				params.put("notify_time",getTime());
				params.put("notify_time", (int)(System.currentTimeMillis()/1000)+"");
				System.out.println((int)(System.currentTimeMillis()/1000)+""+"时间戳");
				return params;
			}

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				// TODO Auto-generated method stub
				return super.getHeaders();
			}
			
		};
		strRequest.setTag(tag);
		AppContext.getHttpQueues().add(strRequest);
		AppContext.getHttpQueues().start();
	}
	public  static String getTime(){
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String time=format.format(date);
		return time;
//		long time=new java
//		long epoch = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").
//				parse("01/01/1970 01:00:00");
	}
}
