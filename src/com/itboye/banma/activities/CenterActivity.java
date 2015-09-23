package com.itboye.banma.activities;

import com.itboye.banma.R;
import com.itboye.banma.app.AppContext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CenterActivity extends Activity implements OnClickListener{
	ImageButton itnBackCenter;//
	ImageView ivPersonheadFail;//未登录头头像
	ImageView ivPersonhead;//登陆的头像
	TextView tvCheckList;//选择按钮
	ImageView ivBack;//返回按钮
	private RelativeLayout mailing_address;
	private RelativeLayout order_goods;
	private AppContext appContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);
        appContext = (AppContext) getApplication();
       initId(this);
       ivPersonheadFail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(CenterActivity.this,LoginActivity.class));
				
			}
	});
    }
	private void initId(CenterActivity centerActivity) {
		// TODO Auto-generated method stub
		ivPersonhead=(ImageView)findViewById(R.id.iv_personhead);
		ivBack=(ImageView)findViewById(R.id.iv_back);
		ivPersonheadFail=(ImageView)findViewById(R.id.iv_personheadfail);
		//判断是否登陆，改变布局，或者可以通过改变组件
	/*	RelativeLayout RelLoginSuccess=(RelativeLayout) findViewById(R.id.Rel_loginsuccess);
		RelativeLayout  RelLoginfail=(RelativeLayout)findViewById(R.id.Rel_loginfail);
		if (!Constant.MY_ACCOUNT.isEmpty()&&!Constant.MY_PASSWORD.isEmpty()) {
			RelLoginSuccess.setVisibility(View.VISIBLE);
			RelLoginfail.setVisibility(View.GONE);
			RelLoginfail.setFocusable(false);
		}*/
		tvCheckList=(TextView)findViewById(R.id.tv_check_list);
		order_goods = (RelativeLayout) findViewById(R.id.order_goods);
		mailing_address = (RelativeLayout) findViewById(R.id.address);
		order_goods.setOnClickListener(this);
		mailing_address.setOnClickListener(this);
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
		case R.id.iv_personhead://点击成功头像 跳转
			startActivity(new Intent(CenterActivity.this,LoginActivity.class));
			break;
		default:
			break;
		}
	}
    
}
