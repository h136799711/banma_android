package com.itboye.banma.welcome;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.itboye.banma.R;
import com.itboye.banma.activities.HomePageActivity;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.service.TokenIntentService;
import com.itboye.banma.utils.SharedConfig;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class WelcomeActivity extends Activity implements StrUIDataListener, OnPageChangeListener,
		OnClickListener {
	private Context context;
	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;
	private Button startButton,enter;
	private LinearLayout indicatorLayout;
	private ArrayList<View> views;
	private ImageView[] indicators = null;
	private int[] images;
	private AppContext appContext;
	private StrVolleyInterface networkHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		context = this;
		
		appContext = (AppContext) getApplication();
		networkHelper = new StrVolleyInterface(this);
		networkHelper.setStrUIDataListener(this);
		
		
		//友盟启动
				MobclickAgent.updateOnlineConfig( this );
				//加密处理
				AnalyticsConfig.enableEncrypt(true);
				
				MobclickAgent.setDebugMode( true );
				
				//开启推送服务
				PushAgent mPushAgent = PushAgent.getInstance(getApplicationContext());
				mPushAgent.enable();
				//统计应用启动次数
				PushAgent.getInstance(getApplicationContext()).onAppStart();
				//获取测试设备的Device Token。
				String device_token = UmengRegistrar.getRegistrationId(this);
				System.out.println(device_token+"设备");
		
		images = new int[] { R.drawable.welcome_01, R.drawable.welcome2,
				 };
		initView();

	}
	
	//友盟统计
		@Override
		protected void onResume() {

			super.onResume();
			MobclickAgent.onResume(this);
		}

		public void onPause() {
			super.onPause();
			MobclickAgent.onPause(this);
		}
	       
	

	private void initView() {
		
		//请求token
				if (appContext.isNetworkConnected()==false) {
					Toast.makeText(WelcomeActivity.this, "请检查网络是否连接", Toast.LENGTH_LONG).show();
				}
				try {
					appContext.getToken(WelcomeActivity.this,
							"client_credentials", "by559a8de1c325c1",
							"aedd16f80c192661016eebe3ac35a6e7",networkHelper);
				} catch (Exception e) {
//					Toast.makeText(WelcomeActivity.this, "访问异常" + e,
//							Toast.LENGTH_LONG).show();
					e.printStackTrace();
					Log.v("获取token异常",e+"" );
				}
		
		viewPager = (ViewPager) findViewById(R.id.viewpage);
		startButton = (Button) findViewById(R.id.start_Button);
		startButton.setOnClickListener(this);
		enter=(Button)findViewById(R.id.enter_app);
		enter.setOnClickListener(this);
		indicatorLayout = (LinearLayout) findViewById(R.id.indicator);
		views = new ArrayList<View>();
		indicators = new ImageView[images.length]; 
		for (int i = 0; i < images.length; i++) {
			ImageView imageView = new ImageView(context);
			imageView.setBackgroundResource(images[i]);
			views.add(imageView);
			indicators[i] = new ImageView(context);
			indicators[i].setBackgroundResource(R.drawable.indicators_default);
			if (i == 0) {
				indicators[i].setBackgroundResource(R.drawable.indicators_now);
			}
			indicatorLayout.addView(indicators[i]);
		}
		pagerAdapter = new BasePagerAdapter(views);
		viewPager.setAdapter(pagerAdapter); 
		viewPager.setOnPageChangeListener(this);
	}
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.start_Button) {
			new SharedConfig(this);
			SharedPreferences shared = SharedConfig.GetConfig();
			Editor editor = shared.edit();
			editor.putBoolean("First", false);
			editor.commit();
			
			startActivity(new Intent(WelcomeActivity.this, HomePageActivity.class));
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			this.finish();
		}
		if (v.getId() == R.id.enter_app) {
			new SharedConfig(this);
			SharedPreferences shared = SharedConfig.GetConfig();
			Editor editor = shared.edit();
			editor.putBoolean("First", false);
			editor.commit();
			
			startActivity(new Intent(WelcomeActivity.this, HomePageActivity.class));
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			this.finish();
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		if (arg0 == indicators.length - 1) {
			startButton.setVisibility(View.VISIBLE);
			enter.setVisibility(View.GONE);
			
		} else {
			startButton.setVisibility(View.GONE);
			enter.setVisibility(View.VISIBLE);
		}
		for (int i = 0; i < indicators.length; i++) {
			indicators[i]
					.setBackgroundResource(R.drawable.indicators_default);
			if (arg0 == i) {
				indicators[arg0].setBackgroundResource(R.drawable.indicators_now);
			}
		}
	}

	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
//		Toast.makeText(WelcomeActivity.this, "加载失败" + error, Toast.LENGTH_LONG)
//		.show();
		this.startService(new Intent(this,TokenIntentService.class));
		AppContext.setTokenSuccess(false);
		Log.v("获取token",error.toString() );
	}

	@Override
	public void onDataChanged(String data) {
		// TODO Auto-generated method stub
		String access_token = null;
		JSONObject jsonObject = null;
		int code = -1;
		try {
			jsonObject=new JSONObject(data);
			code = jsonObject.getInt("code");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		if (code == 0) {
			try {
			JSONObject	tempdata=(JSONObject) jsonObject.get("data");
				access_token = tempdata.getString("access_token");
				Log.v("获取token",access_token+"1");
				AppContext.setAccess_token(access_token);
				AppContext.setTokenSuccess(true);
				this.startService(new Intent(this,TokenIntentService.class));
			} catch (JSONException e) {
				e.printStackTrace();
			}
//			Toast.makeText(WelcomeActivity.this, "获取token成功：" + access_token, Toast.LENGTH_LONG)
//			.show();
		} else {
			AppContext.setTokenSuccess(false);
			this.startService(new Intent(this,TokenIntentService.class));
//			Toast.makeText(WelcomeActivity.this, "获取token失败：code=" + code, Toast.LENGTH_LONG)
//			.show();
		}
	}
}
