
package com.itboye.banma.app;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.itboye.banma.activities.AddAddressActivity;
import com.itboye.banma.api.ApiClient;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.entity.Area;
import com.itboye.banma.utils.BitmapCache;
import com.tencent.stat.StatConfig;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 * 
 * @author gaojian
 * 
 */
public class AppContext extends Application {
	final static String TAG = "AppContext.java";
	private static boolean isTokenSuccess=false;//判断token是否拿到，启动server
	public static BitmapCache bitmapCache = new BitmapCache();
	public static String coin;//我的钱包
	public static String moblie;//手机号
	public static String username;//用户登录账户
	
	public static String beizhu="";//備註 提交備註
	
	public static String getBeizhu() {
		return beizhu;
	}

	public static void setBeizhu(String beizhu) {
		AppContext.beizhu = beizhu;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		AppContext.username = username;
	}

	public static String getMoblie() {
		return moblie;
	}

	public static void setMoblie(String moblie) {
		AppContext.moblie = moblie;
	}

	public static String getCoin() {
		return coin;
	}

	public static void setCoin(String coin) {
		AppContext.coin = coin;
	}

	public static boolean isTokenSuccess() {
		return isTokenSuccess;
	}

	public static void setTokenSuccess(boolean isTokenSuccess) {
		AppContext.isTokenSuccess = isTokenSuccess;
	}

	private static boolean login = false; // 登录状态
	private static  int loginUid = 0; // 登录用户的id
	public static String access_token; //访问令牌 
	public static RequestQueue queues;  //volley请求队列
	public static String getIdcode() {
		return idcode;
	}

	public static void setIdcode(String idcode) {
		AppContext.idcode = idcode;
	}
	private String discount_ratio;//优惠比例
	
	public String getDiscount_ratio() {
		return discount_ratio;
	}
	public void setDiscount_ratio(String discount_ratio) {
		this.discount_ratio = discount_ratio;
	}

	public static String img = null; //图片地址
	public String password;//登陆密码
	public static String pathHeadImage;//头像存储路径
	public static  boolean hasHead=false;//是否已经设置头像
	public static boolean isHasHead() {
		return hasHead;
	}

	public static void setHasHead(boolean hasHead) {
		AppContext.hasHead = hasHead;
	}

	private static String idcode;//用户优惠码
	public static boolean isWeixin() {
		return isWeixin;
	}

	public static void setWeixin(boolean isWeixin) {
		AppContext.isWeixin = isWeixin;
	}

	public static boolean isWeixin=false;//是否是微信登陆的
	 public static final String APP_ID = "wx0d259d7e9716d3dd";//微信
	 public static final String AppSecret = "94124fb74284c8dae6f188c7e269a5a0";//微信
	 public static String code="";
	 
	 public static String getCode() {
		return code;
	}
		
	public static void setCode(String code) {
		AppContext.code = code;
	}

	public static String getNickname() {
		return nickname;
	}

	public static void setNickname(String nickname) {
		AppContext.nickname = nickname;
	}

	public static String getHeadurl() {
		return headurl;
	}

	public static String getImg() {
		return img;
	}

	public static void setImg(String img) {
		AppContext.img = img;
	}

	public static void setHeadurl(String headurl) {
		AppContext.headurl = headurl;
	}

	public static String nickname;
	 private static String headurl;

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
			String contactname, String mobile, String postal_code, String id_card, StrVolleyInterface networkHelper) throws Exception {
		if (isNetworkConnected()) {
			try {
				ApiClient.saveAdress(mContext, loginUid, province, city, area, detailinfo, 
						contactname, mobile, postal_code, id_card, networkHelper);
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
			String contactname, String mobile, String postal_code, int id, String id_card, StrVolleyInterface networkHelper) throws Exception {
		if (isNetworkConnected()) {
			try {
				ApiClient.updateAdress(mContext, loginUid, province, city, area, detailinfo, 
						contactname, mobile, postal_code, id, id_card, networkHelper);
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
	 * @param index 
	 * @return
	 * @throws Exception 
	 */
	public Boolean getProductList(Context mContext, int pageNo, int pageSize, String index, StrVolleyInterface networkHelper) throws Exception {
		if (isNetworkConnected()) {
			try {
				ApiClient.getProductList(mContext, pageNo, pageSize, index, networkHelper);
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
//	/**
//	 * @return
//	 * @throws Exception
//	 */
//	public Boolean getCartListByPage(Context mContext, int Uid, StrVolleyInterface networkHelper) throws Exception {
//		if (isNetworkConnected()) {
//			try {
//				ApiClient.getCartListByPage(mContext, Uid, networkHelper);
//			} catch (Exception e) {
//				Log.i(TAG, "readObject(key)");
//				throw e;
//			}
//			return true;
//		}else{
//			return false;
//		}	
//	}
	
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
	
	/**
	 * 订单添加
	 * @return
	 * @throws Exception 
	 */
	public Boolean ordersAdd(Context mContext,int uid, String cart_ids, String idcode, String note,
			int addr_id, int from, int payType, String redID, StrVolleyInterface networkHelper) throws Exception {
		if (isNetworkConnected()) {
			try {
				ApiClient.ordersAdd(mContext, uid, cart_ids, idcode, note, addr_id,
						from, payType, redID, networkHelper);
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
	 * 分页获取用户所有订单信息
	 * @return
	 * @throws Exception 
	 */
	public boolean getAllOrder(Context mContext, int pageNo, int pageSize, int status,
			StrVolleyInterface networkHelper) throws Exception {
		if (isNetworkConnected()) {
			try {
				ApiClient.getAllOrder(mContext, loginUid, pageNo, pageSize, status, networkHelper);
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
	 * 获取订单详情
	 * @return
	 * @throws Exception 
	 */
	public Boolean getOrderDetail(Context mContext, int id, StrVolleyInterface networkHelper) throws Exception {
		if (isNetworkConnected()) {
			try {
				ApiClient.getOrderDetail(mContext, id, networkHelper);
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
	 * 重新发起订单支付
	 * @return
	 * @throws Exception 
	 */
	public Boolean ordersPay(Context mContext, String order_id,
			StrVolleyInterface strnetworkHelper) throws Exception {
		
		if (isNetworkConnected()) {
			try {
				ApiClient.ordersPay(mContext, loginUid, order_id, strnetworkHelper);
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
	 * 确认收货
	 * @return
	 * @throws Exception 
	 */
	public Boolean ordersSureorder(Context context, String order_code,
			StrVolleyInterface strnetworkHelper) throws Exception {
		if (isNetworkConnected()) {
			try {
				ApiClient.ordersSureorder(context, order_code, loginUid, strnetworkHelper);
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
	 * 查看物流
	 * @return
	 * @throws Exception 
	 */
	public Boolean getLogistics(Context context, String order_code,
			StrVolleyInterface strnetworkHelper) throws Exception {
		if (isNetworkConnected()) {
			try {
				ApiClient.getLogistics(context, order_code, loginUid, strnetworkHelper);
			} catch (Exception e) {
				Log.i(TAG, "readObject(key)");
				throw e;
			}
			return true;
		}else{
			return false;
		}	
	}

	//购物车添加
	public Boolean addCart(Context context,String uid,String  store_id, String p_id,
			String sku_id,String sku_desc,String icon_url,String count,String name,String express,
			String price ,String ori_price,String psku_id, Double weight, String taxrate, StrVolleyInterface networkHelper) throws Exception{
		if (isNetworkConnected()) {
			try {
				ApiClient.addCart(context, uid, store_id,  p_id, sku_id, sku_desc, icon_url, count, name, express,
						 price , ori_price, psku_id,  weight,  taxrate,  networkHelper);
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
	 * 购物车单个查询接口
	 * @return
	 * @throws Exception 
	 */
	public Boolean getCartById(Context mContext, int id, StrVolleyInterface networkHelper) throws Exception {
		if (isNetworkConnected()) {
			try {
				ApiClient.getCartById(mContext, id, networkHelper);
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
	 * 申请退款接口
	 * @return
	 * @throws Exception 
	 */
	public Boolean refund(Context mContext, String order_price, String order_code, String reason,
			StrVolleyInterface strnetworkHelper) throws Exception {
		if (isNetworkConnected()) {
			try {
				ApiClient.refund(mContext, order_price, order_code, reason, strnetworkHelper);
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