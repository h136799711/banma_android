package com.itboye.banma.api;

import java.util.HashMap;
import java.util.Map;

import android.R.integer;
import android.content.Context;

import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;

/**
 * API客户端接口：用于访问网络数据
 * @author gaojian
 *
 */
public class ApiClient {
	public static final String UTF_8 = "UTF-8";
	
	public static StringRequest stringRequest;
	
	public static void getToken(Context mContext, String grant_type, String client_id,
			String client_secret, StrVolleyInterface networkHelper) {
		String url = Constant.URL+"/Token/index ";
		Map<String,String> params = new HashMap<String, String>();
        params.put("grant_type",grant_type);
        params.put("client_id",client_id);
        params.put("client_secret", client_secret);
        VolleyRequest.StrRequestPost(mContext, url, "getToken",params, networkHelper);
		
	} 
	//获取验证码
	public static void getCheckCode(Context context,String mobile,String type,StrVolleyInterface networkHelper){
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/Message/send?access_token="+access_token+"";
		Map<String,String> params = new HashMap<String, String>();
		//params.put("access_token", access_token);
		
        params.put("mobile","17764590001");
        params.put("type",type);
        VolleyRequest.StrRequestPost(context, url, "getToken",params, networkHelper);
	}
	//发送密码，用户完成用手机号的注册
	public static void finishRegisit(Context context,String password,StrVolleyInterface networkHelper){
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/User/register?access_token="+access_token+"";
		Map<String,String> params = new HashMap<String, String>();
		//params.put("access_token", access_token);
		
        params.put("username","17764590000");
        params.put("password",password);
        params.put("from", "0");
        params.put("type", "4");
        VolleyRequest.StrRequestPost(context, url, "finishRegisit",params, networkHelper);
	}
	
	//通过账号和密码，完成用户登陆
	public static void Login(Context context,String name,String password,StrVolleyInterface networkHelper){
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/User/login?access_token="+access_token+"";
		Map<String,String> params = new HashMap<String, String>();
		//params.put("access_token", access_token);
		
        params.put("username",name);
        params.put("password",password);
    //    params.put("from", "0");
    //    params.put("type", "4");
        VolleyRequest.StrRequestPost(context, url, "Login",params, networkHelper);
	}
}
