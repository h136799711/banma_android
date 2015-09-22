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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.itboye.banma.R;
import com.itboye.banma.api.ApiClient;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.api.UIDataListener;
import com.itboye.banma.api.VolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.utils.SharedConfig;
public class LoginActivity extends Activity implements StrUIDataListener {
	TextView tvRegist;//注册view
	Button btnLogin;//登陆按钮
	EditText etName;//用户账号，一般为手机号
	EditText etPassword;//用户密码/明文
	private AppContext appContext;
	private StrVolleyInterface networkHelper;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initId(this);
		appContext = (AppContext) getApplication();
		networkHelper = new StrVolleyInterface(this);
		networkHelper.setStrUIDataListener(this);
		
		
		tvRegist.setOnClickListener(new RegistListener());
		btnLogin.setOnClickListener(new LoginListener());
	}

	private void initId(LoginActivity loginActivity) {
		// TODO Auto-generated method stub
		tvRegist=(TextView)findViewById(R.id.tv_regist);
		btnLogin=(Button)findViewById(R.id.btn_login);
		etName=(EditText)findViewById(R.id.et_name);
		etPassword=(EditText)findViewById(R.id.et_password);
	}
	
	//各种监听
	class LoginListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//登陆请求
			System.out.println(SharedConfig.GetConfig().getString("USER_ONLY_ID", "-1"));
			String name=etName.getText().toString();
			String password=etPassword.getText().toString();
			ApiClient.Login(LoginActivity.this, name, password, networkHelper);
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
		Toast.makeText(LoginActivity.this, "登陆发生异常", Toast.LENGTH_LONG).show();
	}
	@Override
	public void onDataChanged(String data) {
		// TODO Auto-generated method stub
		JSONObject jsonObject=null;
		int code = -1;
		try {
			jsonObject=new JSONObject(data);
			code=jsonObject.getInt("code");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		if (code == 0) {
			//String userId=jsonObject.getString("data");
			System.out.println("code=" + data.toString());
			Toast.makeText(LoginActivity.this, "登陆成功",Toast.LENGTH_LONG).show();
			//获得本程序的shareperference，并放入用户唯一的id,用于以后访问
//			SharedPreferences sharedPreferences=	SharedConfig.GetConfig();
//				Editor editor=sharedPreferences.edit();
//				editor.putString("USER_ONLY_ID", userId);
		} else {
			Toast.makeText(LoginActivity.this, "登陆失败，请检查用户名和密码" + data.toString(), Toast.LENGTH_LONG)
			.show();
			System.out.println("code=" + data.toString());
		}
	}
}
