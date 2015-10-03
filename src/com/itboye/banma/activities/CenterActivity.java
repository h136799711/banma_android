package com.itboye.banma.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itboye.banma.R;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
import com.itboye.banma.shoppingcart.ShoppingCartActivity;
import com.itboye.banma.util.CaptureActivity;

public class CenterActivity extends Activity implements OnClickListener{
	ImageView ivPersonheadFail;//未登录头头像
	ImageView ivPersonhead;//登陆的头像
	TextView tvCheckList;//选择按钮
	ImageView ivBack;//返回按钮
	TextView tvPersonnamefail;//未登录提示
	private ImageView ivShaoMa;//扫描二维码
	private RelativeLayout rlMoney;//我的佣金
	private RelativeLayout mailing_address;//地址管理
	private RelativeLayout morePersonal;//更多个人相关
	private RelativeLayout order_goods;
	private AppContext appContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);
        appContext = (AppContext) getApplication();
       initId(this);
    }
	private void initId(CenterActivity centerActivity) {
		// TODO Auto-generated method stub
	//	ivPersonhead=(ImageView)findViewById(R.id.iv_personhead);
        SharedPreferences sp = this.getSharedPreferences(Constant.MY_PREFERENCES, 0);  
       String userId= sp.getString("MY_USERID", "");
		ivBack=(ImageView)findViewById(R.id.iv_back);
		ivPersonheadFail=(ImageView)findViewById(R.id.iv_personheadfail);
		tvPersonnamefail=(TextView)findViewById(R.id.tv_personnamefail);
		if (!(userId=="")) {
			tvPersonnamefail.setText("老刘");
		}
		ivShaoMa=(ImageView)findViewById(R.id.iv_shaoma);
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
		ivShaoMa.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
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
			}
			break;
			
		case R.id.iv_back:
			CenterActivity.this.finish();
			break;
		case R.id.rl_more:
		//	if (appContext.isLogin()) {
				startActivity(new Intent(CenterActivity.this,MorePersonal.class));
				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
		//	}
		case R.id.rl_money:
			startActivity(new Intent(CenterActivity.this,ShoppingCartActivity.class));
			overridePendingTransition(R.anim.in_from_right,
					R.anim.out_to_left);
			break;
			
		case R.id.iv_shaoma:
			startActivity(new Intent(CenterActivity.this,CaptureActivity.class));
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
			tvPersonnamefail.setText("老王");
		}
	}
    
}
