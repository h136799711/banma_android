package com.itboye.banma.api;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.android.volley.toolbox.StringRequest;
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
		
        params.put("mobile",mobile);
        params.put("type",type);
        VolleyRequest.StrRequestPost(context, url, "getCode",params, networkHelper);
	}
	
	//验证验证码
	public static void judgeCheckCode(Context context,String username,String checkcode,String type,String userId, StrVolleyInterface networkHelper){
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/Message/checkCode?access_token="+access_token+"";
		Map<String,String> params = new HashMap<String, String>();
        params.put("username",username);
        params.put("type",type);
        params.put("code", checkcode);
     //   params.put("uid", userId);
        VolleyRequest.StrRequestPost(context, url, "judgeCode",params, networkHelper);
	}
	
	//发送密码，用户完成用手机号的注册
	public static void finishRegisit(Context context,String password,StrVolleyInterface networkHelper){
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/User/register?access_token="+access_token+"";
		Map<String,String> params = new HashMap<String, String>();
		//params.put("access_token", access_token);
		
        params.put("username","17764590011");
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
        //VolleyRequest.StrRequestPost(context, url, "getCheckCode",params, networkHelper);
	}

	public static void saveAdress(Context mContext, int loginUid, String province, String city, String area, String detailinfo,
			String contactname, String mobile, String postal_code, StrVolleyInterface networkHelper) {
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/Address/add?access_token="+access_token;
		Map<String,String> params = new HashMap<String, String>();
		params.put("uid",""+loginUid);
		params.put("country","中国");
		params.put("province",province);
		params.put("city",city);
		params.put("area",area);
		params.put("detailinfo",detailinfo);
		params.put("contactname",contactname);
		params.put("mobile",mobile);
		params.put("postal_code",postal_code);

		VolleyRequest.StrRequestPost(mContext, url, "saveAdress",params, networkHelper);
	}

	public static void getAddressList(Context mContext, int loginUid,
			StrVolleyInterface networkHelper) {
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/Address/queryNoPaging?access_token="+access_token+"&uid="+loginUid;
		VolleyRequest.StrRequestGet(mContext, url, "getAddressList", networkHelper);
	}
}
