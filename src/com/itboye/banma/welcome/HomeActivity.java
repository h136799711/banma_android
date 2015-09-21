package com.itboye.banma.welcome;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.itboye.banma.R;
import com.itboye.banma.activities.CenterActivity;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.utils.SharedConfig;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

public class HomeActivity extends Activity   implements StrUIDataListener{
	Button btnEnter;//进入主界面按钮，这里请求token的操作暂时放到这里
	Button btnClear;//清除第一次进入的缓存；
	private AppContext appContext;
	private StrVolleyInterface networkHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		btnEnter=(Button)findViewById(R.id.btn_enter);
		btnClear=(Button) findViewById(R.id.btn_clear);
		appContext = (AppContext) getApplication();
		networkHelper = new StrVolleyInterface(this);
		networkHelper.setStrUIDataListener(this);
		
		btnClear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new SharedConfig(getApplicationContext()).ClearConfig();
			}
		});
		
		//点击进入的监听，同时获取token
		btnEnter.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(HomeActivity.this,CenterActivity.class));
				//请求token
				try {
					appContext.getToken(HomeActivity.this,
							"client_credentials", "by559a8de1c325c1",
							"aedd16f80c192661016eebe3ac35a6e7", networkHelper);
				} catch (Exception e) {
					Toast.makeText(HomeActivity.this, "访问异常" + e,
							Toast.LENGTH_LONG).show();
					e.printStackTrace();
					Log.v("获取token异常",e+"" );
				}
			}
		});
	}
	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
		Toast.makeText(HomeActivity.this, "加载失败" + error, Toast.LENGTH_LONG)
		.show();
		Log.v("获取token",error.toString() );
	}
	@Override
	public void onDataChanged(String data) {
		// TODO Auto-generated method stub
		JSONObject jsObject = null;
		try {
			jsObject = new JSONObject(data);
		} catch (JSONException e2) {
			
			e2.printStackTrace();
		}
		String access_token = null;
		int code = -1;
		try {
			code = jsObject.getInt("code");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		if (code == 0) {
			try {
			JSONObject	tempdata=jsObject.getJSONObject("data");
				access_token = tempdata.getString("access_token");
				Log.v("获取token",access_token+"1");
				appContext.setAccess_token(access_token);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Toast.makeText(HomeActivity.this, "获取token成功：" + access_token, Toast.LENGTH_LONG)
			.show();
		} else {
			Toast.makeText(HomeActivity.this, "获取token失败：code=" + code, Toast.LENGTH_LONG)
			.show();
		}
	}
}
