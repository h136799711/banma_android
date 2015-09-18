package com.itboye.banma.utils;


import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.itboye.banma.entity.AccessToken;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.Time;
import android.util.Log;

public class SharedConfigs {
		Context context;
		SharedPreferences shared;
		static String GetToken=" http://192.168.0.100/oschina/itboye20150817/api.php/Token/index";
		public SharedConfigs(Context context){
				this.context=context;
				shared=context.getSharedPreferences("banma", Context.MODE_PRIVATE);
		}
		
		public SharedPreferences GetConfig(){
			return shared;
		}
		public void ClearConfig(){
			shared.edit().clear().commit();
		}
		
		/*
		 * 取得token，并判断token是否超过时限
		 */
		public static String getAccessToken(final Context context){
			SharedPreferences perfenerces=(SharedPreferences) new SharedConfigs(context).GetConfig();
			String accessToken=perfenerces.getString("token", "");
			if (accessToken.isEmpty()) {
				//若为空则重新请求
				accessToken=getTokenFromServerAndSaveObj(context);
			}
			else {
				//判断时间是否超出一个小时
				Gson gson=new Gson();
				AccessToken  orginToken=gson.fromJson(accessToken,AccessToken.class);
				Time orginTime=orginToken.getTime();
				Log.v("时间", orginTime+"");
				//添加判断时间的逻辑
//				if (orginTime-()) {
//					
//				}
				//这里假设重新获取
				accessToken=getTokenFromServerAndSaveObj(context);
			}
			return accessToken;
		}

		private static String  getTokenFromServerAndSaveObj(Context context) {
			final Gson gsonTemp;
			final String  info = null;
			// TODO Auto-generated method stub
			JSONObject parms=new JSONObject();
			try {
				parms.put("client_secret", "f63515ffc8c1a200dedeffce9bd63492");
				parms.put("grant_type", "client_credentials");	
				parms.put("client_id", "by55bec42a8e5431");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.v("获得token", "json异常");
			}
			JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.POST,GetToken, parms,
	                new Response.Listener<JSONObject>() {
	                    @Override
	                    public void onResponse(JSONObject obj) {
	                  //      AccessToken accessToken=new AccessToken();
	                    	Log.v("返回token信息", obj.toString());
	                    }
	                }, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							// TODO Auto-generated method stub
						}
	                });
			return info;
		}
}



