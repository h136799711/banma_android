package com.itboye.banma.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.JsonObject;
import com.itboye.banma.R;
import com.itboye.banma.api.ApiClient;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
import com.itboye.banma.shoppingcart.ShoppingCartActivity;
import com.itboye.banma.util.CaptureActivity;

public class CenterActivity extends Activity implements OnClickListener,StrUIDataListener{
	ImageView ivPersonheadFail;//未登录头头像
	ImageView ivPersonhead;//登陆的头像
	TextView tvCheckList;//选择按钮
	ImageView ivBack;//返回按钮
	TextView tvPersonnamefail;//未登录提示
	private TextView tvYongJin;//我的返佣
	private ImageView ivShare;//扫描二维码
	private RelativeLayout rlMoney;//我的佣金
	private RelativeLayout mailing_address;//地址管理
	private RelativeLayout morePersonal;//更多个人相关
	private RelativeLayout order_goods;
	private AppContext appContext;
	private SharedPreferences sp;
	private String nickname=null;//显示用户昵称
	private StrVolleyInterface networkHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);
        appContext = (AppContext) getApplication();
		networkHelper = new StrVolleyInterface(this);
		networkHelper.setStrUIDataListener(this);
       initId(this);
    }
	private void initId(CenterActivity centerActivity) {
		// TODO Auto-generated method stub
	//	ivPersonhead=(ImageView)findViewById(R.id.iv_personhead);
       // SharedPreferences sp = this.getSharedPreferences(Constant.MY_PREFERENCES, 0);  
      // String userId= sp.getString("MY_USERID", "");
		String userId=appContext.getLoginUid()+"";
		ivBack=(ImageView)findViewById(R.id.iv_back);
		ivPersonheadFail=(ImageView)findViewById(R.id.iv_personheadfail);
		tvPersonnamefail=(TextView)findViewById(R.id.tv_personnamefail);
		sp=getSharedPreferences(Constant.MY_PREFERENCES, MODE_PRIVATE);
		
		if (userId!="") {
			String number=sp.getString(Constant.MY_ACCOUNT, "");
			String psw=sp.getString(Constant.MY_PASSWORD, "");
			ApiClient.Login(CenterActivity.this, number, psw, networkHelper);//请求用户数据
																															//请求用户头像数据
		}
		tvYongJin=(TextView)findViewById(R.id.tv_yongjin);
		ivShare=(ImageView)findViewById(R.id.iv_share);
	   rlMoney=(RelativeLayout)findViewById(R.id.rl_money);
		ivBack=(ImageView)findViewById(R.id.iv_back);
		tvCheckList=(TextView)findViewById(R.id.tv_check_list);
		order_goods = (RelativeLayout) findViewById(R.id.order_goods);
		mailing_address = (RelativeLayout) findViewById(R.id.address);
		morePersonal=(RelativeLayout)findViewById(R.id.rl_more);
		rlMoney.setOnClickListener(this);
		order_goods.setOnClickListener(this);
		mailing_address.setOnClickListener(this);
		ivPersonheadFail.setOnClickListener(this);
		ivBack.setOnClickListener(this);
		morePersonal.setOnClickListener(this);
		ivShare.setOnClickListener(this);
		tvYongJin.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_money:
			if (appContext.isLogin()) {
				Intent intent = new Intent(this, OrderActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			}else{
				Toast.makeText(CenterActivity.this, "请先登录",
						Toast.LENGTH_LONG).show();
			}
			break;
			
		case R.id.address:
			if(appContext.isLogin()){   //判断登陆
				Intent intent = new Intent(this, MailingAddressActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			}else{
				Toast.makeText(CenterActivity.this, "请先登录",
						Toast.LENGTH_LONG).show();
			}
			break;
			
		case R.id.order_goods:
			Intent intent = new Intent(this, BabyActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right,
					R.anim.out_to_left);
			
			break;
		case R.id.iv_personheadfail://点击成功头像 跳转
		if (!appContext.isLogin()) {
				startActivity(new Intent(CenterActivity.this,LoginActivity.class));
				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			}
			break;
			
		case R.id.iv_back:
			CenterActivity.this.finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			break;
		case R.id.rl_more:
				startActivity(new Intent(CenterActivity.this,MorePersonal.class));
				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
				break;
			
//	case R.id.rl_money:
//		startActivity(new Intent(CenterActivity.this,ShoppingCartActivity.class));
//			overridePendingTransition(R.anim.in_from_right,
//				R.anim.out_to_left);
//		break;
			
		case R.id.iv_share:
			if(appContext.isLogin()){
			Intent intent2=new Intent(CenterActivity.this,WebActivity.class);
			intent2.putExtra("Url", "MingPian");
			startActivity(intent2);
			overridePendingTransition(R.anim.in_from_right,
					R.anim.out_to_left);
			}else{
				Toast.makeText(CenterActivity.this, "请先登录",
						Toast.LENGTH_LONG).show();
			}
			break;
			
		case R.id.tv_yongjin:
			if (appContext.isLogin()) {
				Intent intent3=new Intent(CenterActivity.this,WebActivity.class);
				intent3.putExtra("Url", "FanYong");
				startActivity(intent3);
				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			}else{
				Toast.makeText(CenterActivity.this, "请先登录",
						Toast.LENGTH_LONG).show();
			}
			break;
			
		default:
			break;
		}
	}
	
	@Override 
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (appContext.isLogin()) {
			String number=sp.getString(Constant.MY_ACCOUNT, "");
			String psw=sp.getString(Constant.MY_PASSWORD, "");
			ApiClient.Login(CenterActivity.this, number, psw, networkHelper);//请求用户数据
																															//请求用户头像数据
		}else {
			tvPersonnamefail.setText("登陆/注册");
		}
		if (appContext.isLogin()) {
			tvPersonnamefail.setText(nickname);
		}
	}
	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onDataChanged(String data) {
		// TODO Auto-generated method stub
		JSONObject jsonObject=null;
		String content=null;
		int code = -1;
		JSONObject jsonObject2 = null;

		try {
			jsonObject = new JSONObject(data);
			code = jsonObject.getInt("code");
			content=jsonObject.getString("data");
			jsonObject2=new JSONObject(content);
		    nickname=jsonObject2.getString("nickname");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		if (code == 0) {
				if (appContext.isLogin()) {
					tvPersonnamefail.setText(nickname);
				}
	     	}
		}  
}
