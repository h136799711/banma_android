package com.itboye.banma.app;

import java.util.ArrayList;
import java.util.HashMap;

import android.R.string;

public class Constant {
	public static final String URL = "http://banma.itboye.com/api.php";
	
	public static final String MY_PREFERENCES = "MY_PREFERENCES";    //Preferences文件的名称   ,存登陆信息
	public static final String MY_ACCOUNT = "MY_ACCOUNT";            //用户账号
    public static final String MY_PASSWORD = "MY_PASSWORD";			//用户密码
    public static final String MY_USERID="MY_USERID";//当前登陆用户的id
    public static final String MY_HEAD="MY_HEAD";//我的头像
    public static final String MY_BANGDING="MY_BANGDING";//绑定的手机号
    public static final String 	IS_LOGIN="IS_LOGIN";//是否登陆
    public static final String WEIXIN="WEIXIN";//微信是否绑定	
   
    public static int arrayList_cart_id=0;
	public static ArrayList<HashMap<String, Object>> arrayList_cart=new ArrayList<HashMap<String,Object>>();
    public static float Allprice_cart=0;
}
