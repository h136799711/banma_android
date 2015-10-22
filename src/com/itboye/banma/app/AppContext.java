package com.itboye.banma.app;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.itboye.banma.activities.AddAddressActivity;
import com.itboye.banma.api.ApiClient;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.entity.Area;

import android.R.integer;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 * 
 * @author gaojian
 * 
 */
public class AppContext extends Application {
	final static String TAG = "AppContext.java";
	
	private static boolean login = false; // 登录状态
	private static  int loginUid = 0; // 登录用户的id
	public static String access_token; //访问令牌 
	public static RequestQueue queues;  //volley请求队列
	public String password;//登陆密码
	public static String pathHeadImage;//头像存储路径
	public static  boolean hasHead=false;//是否已经设置头像
	 public static final String APP_ID = "wx0d259d7e9716d3dd";//微信
	 public static final String AppSecret = "94124fb74284c8dae6f188c7e269a5a0";//微信

	public static boolean isHasHead() {
		return hasHead;
	}

	public static void setHasHead(boolean hasHead) {
		AppContext.hasHead = hasHead;
	}

	public static String getPathHeadImage() {
		return pathHeadImage;
	}

	public static void setPathHeadImage(String pathHeadImage) {
		AppContext.pathHeadImage = pathHeadImage;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		queues = Volley.newRequestQueue(getApplicationContext());
	}
	
	public boolean isLogin() {
		return login;
	}

	public void  setLogin(boolean login) {
		this.login = login;
	}

	public int getLoginUid() {
		return loginUid;
	}

	public void setLoginUid(int loginUid) {
		this.loginUid = loginUid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
	public Boolean saveAdress(Context mContext, Area province, Area city, Area area, String detailinfo,
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
	
	/**
	 * 修改收货地址
	 * @param id 
	 * @return
	 * @throws Exception 
	 */

	public Boolean updateAdress(Context mContext, Area province, Area city, Area area, String detailinfo,
			String contactname, String mobile, String postal_code, int id, StrVolleyInterface networkHelper) throws Exception {
		if (isNetworkConnected()) {
			try {
				ApiClient.updateAdress(mContext, loginUid, province, city, area, detailinfo, 
						contactname, mobile, postal_code, id, networkHelper);
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
	 * 删除地址
	 * @return
	 * @throws Exception 
	 */
	public Boolean deleteAdress(Context mContext, int id, StrVolleyInterface networkHelper) throws Exception {
		if (isNetworkConnected()) {
			try {
				ApiClient.deleteAdress(mContext, loginUid, id, networkHelper);
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
	 * 设置默认收货地址
	 * @param id
	 * @throws Exception 
	 */
	public Boolean setDefaultAddress(Context mContext, int id, StrVolleyInterface networkHelper) throws Exception {
		if (isNetworkConnected()) {
			try {
				ApiClient.setDefaultAddress(mContext, id, loginUid, networkHelper);
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
	 * 获取商品列表
	 * @return
	 * @throws Exception 
	 */
	public Boolean getProductList(Context mContext, int pageNo, int pageSize, StrVolleyInterface networkHelper) throws Exception {
		if (isNetworkConnected()) {
			try {
				ApiClient.getProductList(mContext, pageNo, pageSize, networkHelper);
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
	 * 获取商品详情
	 * @return
	 * @throws Exception 
	 */
	public Boolean getProductDetail(Context mContext, int id, StrVolleyInterface networkHelper) throws Exception {
		if (isNetworkConnected()) {
			try {
				ApiClient.getProductDetail(mContext, id, networkHelper);
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
	 * 获取购物车列表
	 * @return
	 * @throws Exception 
	 */
	public Boolean getCartListByPage(Context mContext, int Uid, StrVolleyInterface networkHelper) throws Exception {
		if (isNetworkConnected()) {
			try {
				ApiClient.getCartListByPage(mContext, Uid, networkHelper);
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
