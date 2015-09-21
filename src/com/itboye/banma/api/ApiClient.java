package com.itboye.banma.api;

import java.util.HashMap;
import java.util.Map;

import android.R.integer;
import android.content.Context;

import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
import com.itboye.banma.entity.AccessToken;

/**
 * API客户端接口：用于访问网络数据
 * @author gaojian
 *
 */
public class ApiClient {
	public static final String UTF_8 = "UTF-8";
	
	public static StringRequest stringRequest;
	
	public static void getToken(Context mContext, String grant_type, String client_id,
			String client_secret, VolleyInterface networkHelper) {
		String url = Constant.URL+"/Token/index ";
		Map<String,String> params = new HashMap<String, String>();
        params.put("grant_type",grant_type);
        params.put("client_id",client_id);
        params.put("client_secret", client_secret);
        VolleyRequest.RequestPost(mContext, url, "getToken",params, networkHelper);
		
	} 
	
	public static void getCheckCode(Context context,String mobile,String type,VolleyInterface networkHelper){
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/Message/send?access_token="+access_token+"";
		System.out.println(url);
		System.out.println(type);
		Map<String,String> params = new HashMap<String, String>();
        params.put("mobile",mobile);
        params.put("type",type);
        VolleyRequest.RequestPost(context, url, "getCheckCode",params, networkHelper);
	}
}
