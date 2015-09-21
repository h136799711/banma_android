package com.itboye.banma.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.itboye.banma.R;
import com.itboye.banma.api.UIDataListener;
import com.itboye.banma.api.VolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.utils.SharedConfig;
public class LoginActivity extends Activity implements UIDataListener {
	TextView tvRegist;//注册view
	Button btnLogin;//登陆按钮
	private AppContext appContext;
	private VolleyInterface networkHelper;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initId(this);
		appContext = (AppContext) getApplication();
		networkHelper = new VolleyInterface(this);
		networkHelper.setUiDataListener(this);
		
		
		tvRegist.setOnClickListener(new RegistListener());
		btnLogin.setOnClickListener(new LoginListener());
	}

	private void initId(LoginActivity loginActivity) {
		// TODO Auto-generated method stub
		tvRegist=(TextView)findViewById(R.id.tv_regist);
		btnLogin=(Button)findViewById(R.id.btn_login);
	}
	
	//各种监听
	class LoginListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//登陆请求
		}
	}

	
	class RegistListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intentRegist=new Intent(LoginActivity.this,RegistActivity.class);
			LoginActivity.this.startActivity(intentRegist);
		}
	}


	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onDataChanged(JSONObject data) {
		// TODO Auto-generated method stub
		
	}
}
