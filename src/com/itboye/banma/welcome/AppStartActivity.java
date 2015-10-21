package com.itboye.banma.welcome;

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

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class AppStartActivity extends Activity implements StrUIDataListener{
	private boolean first;
	private View view;
	private Context context;
	private Animation animation;
	private SharedPreferences shared;
	private static int TIME = 500; 
	private AppContext appContext;
	private StrVolleyInterface networkHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = View.inflate(this, R.layout.activity_main, null);
		appContext = (AppContext) getApplication();
		networkHelper = new StrVolleyInterface(this);
		networkHelper.setStrUIDataListener(this);
		setContentView(view);
		new SharedConfig(this);
		shared = SharedConfig.GetConfig(); 
		into();
		this.startService(new Intent(this,TokenIntentService.class));
	}

	@Override
	protected void onResume() {

		super.onResume();
	}

	public void onPause() {
		super.onPause();
	}

	private void into() {
		//请求token
		try {
			appContext.getToken(AppStartActivity.this,
					"client_credentials", "by559a8de1c325c1",
					"aedd16f80c192661016eebe3ac35a6e7",networkHelper);
		} catch (Exception e) {
			Toast.makeText(AppStartActivity.this, "访问异常" + e,
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
			Log.v("获取token异常",e+"" );
		}
		first = shared.getBoolean("First", true);

		animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
		view.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						Intent intent;
						if (first) {
							intent = new Intent(AppStartActivity.this,
									WelcomeActivity.class);
						} else {
							intent = new Intent(AppStartActivity.this,
									HomePageActivity.class);
						}
						startActivity(intent);
						overridePendingTransition(R.anim.in_from_right,
								R.anim.out_to_left);
						AppStartActivity.this.finish();
					}
				}, TIME);
			}
		});
	}

	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
		Toast.makeText(AppStartActivity.this, "加载失败" + error, Toast.LENGTH_LONG)
		.show();
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
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Toast.makeText(AppStartActivity.this, "获取token成功：" + access_token, Toast.LENGTH_LONG)
			.show();
		} else {
			Toast.makeText(AppStartActivity.this, "获取token失败：code=" + code, Toast.LENGTH_LONG)
			.show();
		}
	}

}
