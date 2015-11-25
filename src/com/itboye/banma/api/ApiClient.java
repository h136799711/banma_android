package com.itboye.banma.api;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Base64;
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
	
	//意见反馈
			public static void suggest(Context mContext, String text, String uid,StrVolleyInterface networkHelper) {
				String access_token=AppContext.getAccess_token();
				String url = Constant.URL+"/Suggest/add?access_token="+access_token;
				Map<String,String> params = new HashMap<String, String>();
		        params.put("text",text);
		        params.put("uid",uid);
		        VolleyRequest.StrRequestPost(mContext, url, "Suggest",params, networkHelper);
				
			} 
	
			//优惠码历史
			public static void youHuiHistory(Context mContext, String uid, StrVolleyInterface networkHelper) {
				String access_token=AppContext.getAccess_token();
				String url = Constant.URL+"/Coupon/history?access_token="+access_token;
				Map<String,String> params = new HashMap<String, String>();
		        params.put("uid",uid);
		        VolleyRequest.StrRequestPost(mContext, url, "youHuiHistory",params, networkHelper);
				
			} 
			
	//优惠码
		public static void youHuiMa(Context mContext, String idcode, StrVolleyInterface networkHelper) {
			String access_token=AppContext.getAccess_token();
			String url = Constant.URL+"/Coupon/info?access_token="+access_token;
			Map<String,String> params = new HashMap<String, String>();
	        params.put("idcode",idcode);
	        VolleyRequest.StrRequestPost(mContext, url, "youHuiMa",params, networkHelper);
		} 
		
	
	//微信登陆
	public static void wxLogin(Context mContext, String code, StrVolleyInterface networkHelper) {
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/Weixin/login?access_token="+access_token+"&code="+code;
		Map<String,String> params = new HashMap<String, String>();
        params.put("code",code);
        VolleyRequest.StrRequestPost(mContext, url, "wxLogin",params, networkHelper);
		
	} 
	
	//微信绑定
		public static void wxBangDing(Context mContext, String uid, String code,StrVolleyInterface networkHelper) {
			String access_token=AppContext.getAccess_token();
			System.out.println(access_token);
			String url = Constant.URL+"/Weixin/bind?access_token="+access_token+"&code="+code;
			System.out.println(url+"地址");
			Map<String,String> params = new HashMap<String, String>();
	        params.put("uid",uid);
	        VolleyRequest.StrRequestPost(mContext, url, "wxBangDing",params, networkHelper);
			
		} 
	
	public static void getToken(Context mContext, String grant_type, String client_id,
			String client_secret, StrVolleyInterface networkHelper) {
		String url = Constant.URL+"/Token/index ";
		Map<String,String> params = new HashMap<String, String>();
        params.put("grant_type",grant_type);
        params.put("client_id",client_id);
        params.put("client_secret", client_secret);
        VolleyRequest.StrRequestPost(mContext, url, "getToken",params, networkHelper);
		
	} 
	//订单添加
	public static void addOrder(Context context,String uid,String  cartids, String idcode,
			String note,String contactname,String mobile,String country,String province,String city,
			String area ,String wxno,String detailinfo, String from,StrVolleyInterface networkHelper){
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/ShoppingCart/add?access_token="+access_token;
		Map<String,String> params = new HashMap<String, String>();
		//params.put("access_token", access_token);
        params.put("uid",uid+"");  
        params.put("cartids",cartids+"");
        params.put("idcode", idcode+"");
        params.put("note", note+"");
        params.put("contactname",contactname+""); 
        params.put("mobile",mobile+"");
        params.put("country",country+"");  
        params.put("province",province+"");
        params.put("city", city+"");
        params.put("area", area+"");
        params.put("wxno", wxno+"");
        params.put("detailinfo", detailinfo+"");
        params.put("from", 2+"");
        VolleyRequest.StrRequestPost(context, url, "addCart",params, networkHelper);
	}
	
	//购物车修改接口
		public  static void modifyCart(Context mContext, String id, String count,String express,
				String p_id,String psku_id, StrVolleyInterface networkHelper) {
			 String access_token=AppContext.getAccess_token();
				String url = Constant.URL+"/ShoppingCart/update?access_token="+access_token+"";
			Map<String,String> params = new HashMap<String, String>();
	        params.put("id",id);
	        params.put("count",count);
	        params.put("express", express);
	        params.put("p_id", p_id);
	        params.put("psku_id", psku_id);
	        VolleyRequest.StrRequestPost(mContext, url, "modifyCart",params, networkHelper);
			
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
	//购物车删除单个商品
	public static void deleteCart(Context context,String id, StrVolleyInterface networkHelper){
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/ShoppingCart/delete?access_token="+access_token;
		Map<String,String> params = new HashMap<String, String>();
		//params.put("access_token", access_token);
        params.put("id",id+"");  
        VolleyRequest.StrRequestPost(context, url, "deleteCart",params, networkHelper);
	}
	
	//购物车添加
			public static void addCart(Context context,String uid,String  store_id, String p_id,
					String sku_id,String sku_desc,String icon_url,String count,String name,String express,
					String price ,String ori_price,String psku_id, Double weight, String taxrate, StrVolleyInterface networkHelper){
				String access_token=AppContext.getAccess_token();
				String url = Constant.URL+"/ShoppingCart/add?access_token="+access_token;
				Map<String,String> params = new HashMap<String, String>();
				//params.put("access_token", access_token);
		        params.put("uid",uid+"");  
		        params.put("store_id",store_id+"");
		        params.put("p_id", p_id+"");
		        params.put("sku_id", sku_id+"");
		        params.put("sku_desc",sku_desc+""); 
		        params.put("icon_url",icon_url+"");
		        params.put("count",count+"");  
		        params.put("name",name+"");
		        params.put("express", "0");
		        params.put("price", price+"");
		        params.put("ori_price", ori_price+"");
		        params.put("psku_id", psku_id+"");
		        params.put("psku_id", weight+"");
		        params.put("psku_id", taxrate);
		        VolleyRequest.StrRequestPost(context, url, "addCart",params, networkHelper);
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
       params.put("uid", userId);
        VolleyRequest.StrRequestPost(context, url, "judgeCode",params, networkHelper);
	}
	
	//用户忘记密码 更新密码
	public static void forgetPassword(Context context,String username,String password,String  code, StrVolleyInterface networkHelper){
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/User/updatePsw?access_token="+access_token+"";
		System.out.println(url);
		Map<String,String> params = new HashMap<String, String>();
		//params.put("access_token", access_token);
		Log.v("用户名", username);
		
		Log.v("验证码", code);
			byte[] encode;
			try {
			 encode = Base64.encode(password.getBytes(UTF_8), Base64.NO_WRAP);
			 password = new String(encode);  
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	       
	        Log.v("密码","base 64 encode = " + password);  
	
		Log.v("原密码", password);
        params.put("username",username);
        params.put("psw",password);
        params.put("code", code);
        VolleyRequest.StrRequestPost(context, url, "forgetPass",params, networkHelper);
	}
	
	//发送密码，用户完成用手机号的注册
	public static void finishRegisit(Context context,String username,String  password, StrVolleyInterface networkHelper){
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/User/register?access_token="+access_token;
		Map<String,String> params = new HashMap<String, String>();
		//params.put("access_token", access_token);
		System.out.println(username);
		System.out.println(password);
        params.put("username",username);  
        params.put("password",password);
        params.put("from", "0");
        params.put("type", "3");
        VolleyRequest.StrRequestPost(context, url, "finishRegisit",params, networkHelper);
	}

	
	
	//用户信息修改
		public static void modifyPersonal(Context context,String uid,String  sex, String nickname,
				String realname,String idnumber,String qq,String head,String birthday,String email,
				String moble ,StrVolleyInterface networkHelper){
			String access_token=AppContext.getAccess_token();
			String url = Constant.URL+"/User/update?access_token="+access_token;
			Map<String,String> params = new HashMap<String, String>();
			//params.put("access_token", access_token);
	        params.put("uid",uid);  
	        params.put("sex",sex);
	        params.put("nickname", nickname);
	        params.put("realname", realname);
	        params.put("idnumber",idnumber);  //身份证号
	        params.put("qq",qq);
	        params.put("head",head);  
	        params.put("birthday",birthday);
	        params.put("email", email);
	        params.put("moble", moble);
	        VolleyRequest.StrRequestPost(context, url, "modifyPersonal",params, networkHelper);
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
			String contactname, String mobile, String postal_code, String id_card, StrVolleyInterface networkHelper) {
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
		params.put("id_card",id_card);

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
			String contactname, String mobile, String postal_code, int id, String id_card,StrVolleyInterface networkHelper) {
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
		params.put("id_card",id_card);
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
	
	public static void setDefaultAddress(Context mContext, int id,
			int loginUid, StrVolleyInterface networkHelper) {
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/Address/setDefaultAddress?access_token="+access_token;
		Map<String,String> params = new HashMap<String, String>();
		params.put("uid",""+loginUid);
		params.put("id",""+id);
		VolleyRequest.StrRequestPost(mContext, url, "setDefaultAddress",params, networkHelper);
		
	}

	public static void getProductDetail(Context mContext, int id,
			StrVolleyInterface networkHelper) {
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/Product/detail?access_token="+access_token;
		Map<String,String> params = new HashMap<String, String>();
		params.put("id",""+id);
		VolleyRequest.StrRequestPost(mContext, url, "getProductDetail",params, networkHelper);
	}
	
	public static void getProductList(Context mContext, int pageNo, 
			int pageSize, StrVolleyInterface networkHelper) {
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/Product/query?access_token="+access_token;
		Map<String,String> params = new HashMap<String, String>();
		params.put("pageNo",""+pageNo);
		params.put("pageSize",""+pageSize);
		VolleyRequest.StrRequestPost(mContext, url, "getProductList",params, networkHelper);
	}
	
//获得购物车数据
	public static void getCartListByPage(Context mContext, int uid,
			StrVolleyInterface networkHelper) {
		// TODO Auto-generated method stub
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/ShoppingCart/query?access_token="+access_token;
		Map<String,String> params = new HashMap<String, String>();
		params.put("uid",""+uid);
		VolleyRequest.StrRequestPost(mContext, url, "getCardListByPage",params, networkHelper);
	}
	
	public static void ordersAdd(Context mContext, int uid, String cart_ids,
			String idcode, String note, int addr_id, int from, int payType,
			StrVolleyInterface networkHelper) {
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/Orders/add?access_token="+access_token;
		Map<String,String> params = new HashMap<String, String>();
		params.put("uid",""+uid);
		params.put("cart_ids",cart_ids);
		if(idcode!=null){
			params.put("idcode",idcode);
		}
		if(note!=null){
			params.put("note",note);
		}
		params.put("addr_id",""+addr_id);
		params.put("from",""+from);
		params.put("payType",""+payType);
		
		VolleyRequest.StrRequestPost(mContext, url, "ordersAdd",params, networkHelper);
	}
	public static void getAllOrder(Context mContext, int loginUid, int pageNo,
			int pageSize, int status, StrVolleyInterface networkHelper) {
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/Orders/query?access_token="+access_token;
		Map<String,String> params = new HashMap<String, String>();
		//params.put("uid",loginUid+"");
		params.put("uid",""+loginUid);
		params.put("curpage",""+pageNo);
		params.put("pagesize",""+pageSize);
		params.put("status",""+status);
		VolleyRequest.StrRequestPost(mContext, url, "getAllOrder",params, networkHelper);
	}
	
	public static void getOrderDetail(Context mContext, int id,
			StrVolleyInterface networkHelper) {
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/Orders/detail?access_token="+access_token;
		Map<String,String> params = new HashMap<String, String>();
		params.put("id",""+id);
		VolleyRequest.StrRequestPost(mContext, url, "getOrderDetail",params, networkHelper);
	}

	public static void ordersPay(Context mContext, int uid,
			String order_id, StrVolleyInterface strnetworkHelper) {
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/Orders/pay?access_token="+access_token;
		Map<String,String> params = new HashMap<String, String>();
		params.put("uid",""+uid);
		params.put("order_id",""+order_id);
		VolleyRequest.StrRequestPost(mContext, url, "ordersPay",params, strnetworkHelper);
	}

	public static void ordersSureorder(Context mContext, String order_code,
			int loginUid, StrVolleyInterface strnetworkHelper) {
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/Orders/pay?access_token="+access_token;
		Map<String,String> params = new HashMap<String, String>();
		params.put("order_code",""+order_code);
		params.put("uid",""+loginUid);
		VolleyRequest.StrRequestPost(mContext, url, "ordersSureorder",params, strnetworkHelper);
	}

	public static void getLogistics(Context mContext, String order_code,
			int loginUid, StrVolleyInterface strnetworkHelper) {
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/Orders/express?access_token="+access_token;
		Map<String,String> params = new HashMap<String, String>();
		params.put("uid",""+loginUid);
		params.put("order_code",""+order_code);
		VolleyRequest.StrRequestPost(mContext, url, "getLogistics",params, strnetworkHelper);
	}
	public static void getCartById(Context mContext, int id, StrVolleyInterface strnetworkHelper) {
		String access_token=AppContext.getAccess_token();
		String url = Constant.URL+"/ShoppingCart/getInfo?access_token="+access_token;
		Map<String,String> params = new HashMap<String, String>();
		params.put("id",""+id);
		VolleyRequest.StrRequestPost(mContext, url, "getCartById",params, strnetworkHelper);
	}
}
