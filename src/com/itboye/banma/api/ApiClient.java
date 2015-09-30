package com.itboye.banma.api;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.android.volley.toolbox.StringRequest;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
import com.itboye.banma.entity.Area;

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
	
	//绑定新手机
	public  static void changePhone(Context mContext, String uid, String code,String mobile,
			String password, StrVolleyInterface networkHelper) {
		 String access_token=AppContext.getAccess_token();
			String url = Constant.URL+"/User/changePhone?access_token="+access_token+"";
		Map<String,String> params = new HashMap<String, String>();
        params.put("uid",uid);
        params.put("code",code);
        params.put("mobile", mobile);
        params.put("password", password);
        VolleyRequest.StrRequestPost(mContext, url, "changPhone",params, networkHelper);
		
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
		String url = Constant.URL+"/Message/checkCode?access_token="+access_token;
		Map<String,String> params = new HashMap<String, String>();
        params.put("username",username);
        params.put("type",type);
        params.put("code", checkcode);
     //   params.put("uid", userId);
        VolleyRequest.StrRequestPost(context, url, "judgeCode",params, networkHelper);
	}
	
	//用户忘记密码 更新密码
	public static void forgetPassword(Context context,String username,String password,String  code, StrVolleyInterface networkHelper){
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/User/updatePsw?access_token="+access_token+"";
		Map<String,String> params = new HashMap<String, String>();
		//params.put("access_token", access_token);
		Log.v("信息", username);
		Log.v("信息", password);
		Log.v("信息", code);
        params.put("username",username);
        params.put("psw",password);
        params.put("code", code);
        VolleyRequest.StrRequestPost(context, url, "forgetPass",params, networkHelper);
	}
	
	//发送密码，用户完成用手机号的注册
	public static void finishRegisit(Context context,String username,String  password, StrVolleyInterface networkHelper){
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/User/register?access_token="+access_token+"";
		Map<String,String> params = new HashMap<String, String>();
		//params.put("access_token", access_token);
		
        params.put("username",username);
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

	public static void saveAdress(Context mContext, int loginUid, Area province, Area city, Area area, String detailinfo,
			String contactname, String mobile, String postal_code, StrVolleyInterface networkHelper) {
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/Address/add?access_token="+access_token;
		Map<String,String> params = new HashMap<String, String>();
		params.put("uid",""+loginUid);
		params.put("country","1017");
		params.put("provinceid",province.getCode());
		params.put("cityid",city.getCode());
		params.put("areaid",area.getCode());
		params.put("detailinfo",detailinfo);
		params.put("contactname",contactname);
		params.put("mobile",mobile);
		params.put("postal_code",postal_code);
		params.put("province",province.getName());
		params.put("city",city.getName());
		params.put("area",area.getName());

		VolleyRequest.StrRequestPost(mContext, url, "saveAdress",params, networkHelper);
	}

	public static void getAddressList(Context mContext, int loginUid,
			StrVolleyInterface networkHelper) {
		Log.v("用户Id ", loginUid+"");
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/Address/queryNoPaging?access_token="+access_token;
		Map<String,String> params = new HashMap<String, String>();
		params.put("uid",""+loginUid);
		VolleyRequest.StrRequestPost(mContext, url, "getAddressList",params, networkHelper);
	}

	public static void updateAdress(Context mContext, int loginUid, Area province, Area city, Area area, String detailinfo,
			String contactname, String mobile, String postal_code, int id, StrVolleyInterface networkHelper) {
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/Address/update?access_token="+access_token;
		Map<String,String> params = new HashMap<String, String>();
		params.put("uid",""+loginUid);
		params.put("country","1017");
		params.put("provinceid",province.getCode());
		params.put("cityid",city.getCode());
		params.put("areaid",area.getCode());
		params.put("detailinfo",detailinfo);
		params.put("contactname",contactname);
		params.put("mobile",mobile);
		params.put("postal_code",postal_code);
		params.put("province",province.getName());
		params.put("city",city.getName());
		params.put("area",area.getName());
		params.put("id",""+id);

		VolleyRequest.StrRequestPost(mContext, url, "updateAdress",params, networkHelper);
	}

	public static void deleteAdress(Context mContext, int loginUid, int id,
			StrVolleyInterface networkHelper) {
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/Address/delete?access_token="+access_token;
		Map<String,String> params = new HashMap<String, String>();
		params.put("id",""+id);
		VolleyRequest.StrRequestPost(mContext, url, "deleteAdress",params, networkHelper);
		
	}

	public static void getProductDetail(Context mContext, int id,
			StrVolleyInterface networkHelper) {
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/Product/detail?access_token="+access_token;
		Map<String,String> params = new HashMap<String, String>();
		params.put("pid",""+id);
		VolleyRequest.StrRequestPost(mContext, url, "getProductDetail",params, networkHelper);
	}
}
