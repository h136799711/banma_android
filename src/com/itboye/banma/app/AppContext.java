package com.itboye.banma.app;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.itboye.banma.activities.AddAddressActivity;
import com.itboye.banma.api.ApiClient;
import com.itboye.banma.api.StrVolleyInterface;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 * 
 * @author gaojian
 * 
 */
public class AppContext extends Application {
	final static String TAG = "AppContext.java";
	
	private boolean login = false; // 登录状态
	private int loginUid = 0; // 登录用户的id
	public static String access_token; //访问令牌 
	public static RequestQueue queues;  //volley请求队列

	@Override
	public void onCreate() {
		super.onCreate();
		queues = Volley.newRequestQueue(getApplicationContext());
	}
	
	public boolean isLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}

	public int getLoginUid() {
		return loginUid;
	}

	public void setLoginUid(int loginUid) {
		this.loginUid = loginUid;
	}



	public static RequestQueue getHttpQueues() {
		return queues;
	}
	
	public static String getAccess_token() {
		return access_token;
	}

	public static void setAccess_token(String access_token) {
		AppContext.access_token = access_token;
	}
	

	/**
	 * 检测网络是否可用
	 * 
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}
	
	/**
	 * 获取token
	 * @param mContext
	 * @param grant_type
	 * @param client_id
	 * @param client_secret
	 * @param networkHelper
	 * @throws Exception
	 */
	public void getToken(Context mContext, String grant_type, String client_id,
			 String client_secret,StrVolleyInterface networkHelper) throws Exception {
		if (isNetworkConnected()) {

			try {
				ApiClient.getToken(mContext, grant_type, client_id, client_secret, networkHelper);
			} catch (Exception e) {
				Log.i(TAG, "readObject(key)");
				throw e;
			}
		}
	}

	/**
	 * 保存收货地址
	 * @return true:网络正常  false：无网络连接
	 * @throws Exception 
	 */
	public Boolean saveAdress(Context mContext, String province, String city, String area, String detailinfo,
			String contactname, String mobile, String postal_code, StrVolleyInterface networkHelper) throws Exception {
		if (isNetworkConnected()) {
			try {
				ApiClient.saveAdress(mContext, loginUid, province, city, area, detailinfo, 
						contactname, mobile, postal_code, networkHelper);
			} catch (Exception e) {
				Log.i(TAG, "readObject(key)");
				throw e;
			}
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * 获取收货地址
	 * @param mContext
	 * @param networkHelper
	 * @return
	 * @throws Exception 
	 */
	public Boolean getAddressList(Context mContext, StrVolleyInterface networkHelper) throws Exception{
		if (isNetworkConnected()) {
			try {
				ApiClient.getAddressList(mContext, loginUid, networkHelper);
			} catch (Exception e) {
				Log.i(TAG, "readObject(key)");
				throw e;
			}
			return true;
		}else{
			return false;
		}
	}
}
