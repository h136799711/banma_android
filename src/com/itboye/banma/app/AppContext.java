package com.itboye.banma.app;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
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
	
	public static String access_token; //访问令牌 
	public static RequestQueue queues;  //volley请求队列

	@Override
	public void onCreate() {
		super.onCreate();
		queues = Volley.newRequestQueue(getApplicationContext());
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
}
