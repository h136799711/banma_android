package com.itboye.banma.activities;

import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.VolleyError;
import com.itboye.banma.R;
import com.itboye.banma.api.ApiClient;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.utils.SharedConfig;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordActivity extends Activity implements StrUIDataListener{
	EditText etName;//用户名
	EditText etPassword;//用户密码
	Button btnRegist;//注册按钮
	private AppContext appContext;
	private StrVolleyInterface networkHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
		networkHelper = new StrVolleyInterface(this);
		networkHelper.setStrUIDataListener(this);
		 initId(this);
		btnRegist.setOnClickListener(regisitOnClickListener);
    }
    
    OnClickListener regisitOnClickListener =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String name=etName.getText().toString();
			String password=etPassword.getText().toString();
			if (name.equals(password)) {
				System.out.println("密码一致");
				ApiClient.finishRegisit(PasswordActivity.this, password, networkHelper);
			}	
			else {
				Toast.makeText(PasswordActivity.this, "两次密码输入不一致" , Toast.LENGTH_LONG)
				.show();
			}
		}
	};

	private void initId(PasswordActivity regsitedActivity) {
		// TODO Auto-generated method stub
		etName=(EditText)findViewById(R.id.et_name);
		etPassword=(EditText)findViewById(R.id.et_password);
		btnRegist=(Button)findViewById(R.id.btn_regist);
	}

	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
		Toast.makeText(PasswordActivity.this, "密码请求发生异常，请重新尝试" , Toast.LENGTH_LONG)
		.show();
	}

	@SuppressLint("CommitPrefEdits")
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
			try {
				String userId=jsonObject.getString("data");
				System.out.println(userId);
				//获得本程序的shareperference，并放入用户唯一的id,用于以后访问
			    SharedPreferences sharedPreferences=	SharedConfig.GetConfig();
				Editor editor=sharedPreferences.edit();
				editor.putString("USER_ONLY_ID", userId);
				editor.commit();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			//Toast.makeText(RegistActivity.this, "获取验证码成功：" + checkCode, Toast.LENGTH_LONG)
			//.show();
		} else {
			Toast.makeText(PasswordActivity.this, "注册失败" ,Toast.LENGTH_LONG)
			.show();
			Log.v("注册返回结果", data.toString());
		}
	}
}