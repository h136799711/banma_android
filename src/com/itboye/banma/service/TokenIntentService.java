package com.itboye.banma.service;
/*
 * 完美实现定时，service在后台定时，50分钟后判断应用是否正在运行，如果是，重新请求Token
 * 如果不是，则将当前service结束掉
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;

public class TokenIntentService extends IntentService
{
	RequestQueue requestQueue = AppContext.getHttpQueues();
	public TokenIntentService()
	{
		super("TokenIntentService");
	}
	//必须要写的intentservice 方法
	protected void onHandleIntent(Intent intent)
	{
		System.out.println("service 已经运行");
		String httpurl=Constant.URL+"/Token/index ";
		// TODO Auto-generated method stub
		long endTime=System.currentTimeMillis()+1000*60*55;//50分钟后重新请求
		while(System.currentTimeMillis()<endTime){
			//判断应用是否仍在进行,如果是则重新请求token
			synchronized (this) {
				try {
					wait(endTime-System.currentTimeMillis());
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			Boolean isRunning=getAppRunState();
			if (isRunning) {
				endTime=System.currentTimeMillis()+1000*60*55;//重新定时五十分钟			
				StringRequest jsonRequest = new StringRequest(Request.Method.POST,httpurl, new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						System.out.println(response.toString());
						try {
							JSONObject jsonObject=new JSONObject(response);
					     	JSONObject 	data=(JSONObject) jsonObject.get("data");
							String   access_token=data.getString("access_token");
							AppContext.setAccess_token(access_token);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				},new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						System.out.println(error.toString());
					}
				}){
					@Override 
					  protected Map<String, String> getParams() throws AuthFailureError {  
						Map<String, String> map = new HashMap<String, String>();  
						map.put("client_secret", "aedd16f80c192661016eebe3ac35a6e7");
						map.put("grant_type", "client_credentials");  
						map.put("client_id", "by559a8de1c325c1");
						return map;
					}
				};
			requestQueue.add(jsonRequest);
			}
			else {
				stopSelf();//若应用已经退出，则不进行请求
				System.out.println("service结束");
		}
		}
	}
	
	//判断应用是否在运行
		private Boolean getAppRunState() {
			System.out.println("app正在运行吗");
			ActivityManager am = (ActivityManager)TokenIntentService.this.getSystemService(Context.ACTIVITY_SERVICE);
			List<RunningTaskInfo> list = am.getRunningTasks(100);
			boolean isAppRunning = false;
			String MY_PKG_NAME = "com.itboye.banma";
			for (RunningTaskInfo info : list) {
				if (info.topActivity.getPackageName().equals(MY_PKG_NAME) || info.baseActivity.getPackageName().equals(MY_PKG_NAME)) {
					isAppRunning = true;
					break;
				}
			}
			return isAppRunning;
	}
}
