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
    public static final String MY_HEAD_URL="MY_HEAD_URL";//我的头像
    public static final String MY_BANGDING="MY_BANGDING";//绑定的手机号
    public static final String 	IS_LOGIN="IS_LOGIN";//是否登陆
    public static final String WEIXIN="WEIXIN";//微信是否绑定
    
    public static String[] SKU_INFO = {"","","","","",""};
    public static String[] SKU_INFOSTR = {"","","","","",""};
    public static int SKU_ALLNUM = 0;  //规格总种类
    public static int[] SKU_NUM = {0,0,0,0};   //所选规格数

    public static final int arrayList_cart_id=0;
	public static ArrayList<HashMap<String, Object>> arrayList_cart=new ArrayList<HashMap<String,Object>>();
    public static float Allprice_cart=0;
    
    public static final int PAGE_SIZE = 20;  //分页显示条数
    
    /**
     * 订单退回
     */
    public static final int ORDER_BACK = 12;
    /**
     * 待确认
     */
    public static final int ORDER_TOBE_CONFIRMED = 2;
    /**
     * 待发货
     */
    public static final int ORDER_TOBE_SHIPPED = 3;
    /**
     * 已发货
     */
    public static final int ORDER_SHIPPED = 4;
    /**
     * 已收货
     */
    public static final int ORDER_RECEIPT_OF_GOODS = 5;
    /**
     * 已退货
     */
    public static final int ORDER_RETURNED = 6;
    /**
     * 已完成
     */
    public static final int ORDER_COMPLETED = 7;
    /**
     * 取消或交易关闭
     */
    public static final int ORDER_CANCEL = 8;
    /**
     * 正在退款
     */
    public static final int ORDER_RESENDS = 9;
    
    
    //订单支付状态
    /**
     * 待支付
     */
    public static final int ORDER_TOBE_PAID = 0;
    /**
     * 货到付款
     */
    public static final int ORDER_CASH_ON_DELIVERY = 5;
    /**
     * 已支付
     */
    public static final int ORDER_PAID = 1;
    /**
     * 已退款
     */
    public static final int ORDER_REFUND = 2;
    
    //订单评论状态
    
    
    /**
     * 待评论
     */
    public static final int ORDER_TOBE_EVALUATE = 0;
    /**
     * 已评论
     */
    public static final int ORDER_HUMAN_EVALUATED = 1;
    /**
     * 超时、系统自动评论
     */
    public static final int ORDER_SYSTEM_EVALUATED = 2;
    
    /**
     * 订单支付状态
     * @return
     */
    public static String getPayStatus(int state){
    	String payStatus = "";
    	switch (state) {
		case 0:
			payStatus = "待支付";
			break;
		case 5:
			payStatus = "货到付款";
			break;
		case 1:
			payStatus = "已支付";
			break;
		case 2:
			payStatus = "已退款";
			break;
		default:
			break;
		}
		return payStatus;
    }
    /**
     * 订单状态
     * @return
     */
    public static String getOrderStatus(int state){
    	String OrderStatus = "";
    	switch (state) {
		case 12:
			OrderStatus = "订单退回";
			break;
		case 2:
			OrderStatus = "待确认";
			break;
		case 3:
			OrderStatus = "待发货";
			break;
		case 4:
			OrderStatus = "已发货";
			break;
		case 5:
			OrderStatus = "已收货";
			break;
		case 6:
			OrderStatus = "已退货";
			break;
		case 7:
			OrderStatus = "已完成";
			break;
		case 8:
			OrderStatus = "交易关闭";
			break;
		case 9:
			OrderStatus = "正在退款";
			break;
			
		default:
			break;
		}
		return OrderStatus;
    }
}
