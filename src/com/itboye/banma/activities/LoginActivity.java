package com.itboye.banma.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.itboye.banma.R;
import com.itboye.banma.api.ApiClient;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
import com.itboye.banma.entity.User;
import com.itboye.banma.util.AESEncryptor;
import com.itboye.banma.utils.SharedConfig;
public class LoginActivity extends Activity implements StrUIDataListener {
	TextView tvRegist;//注册view
	Button btnLogin;//登陆按钮
	EditText etName;//用户账号，一般为手机号
	EditText etPassword;//用户密码/明文
   TextView tvForget;//忘记密码
   TextView tvQuXiao;//取消
	private AppContext appContext;
	private StrVolleyInterface networkHelper;
	private Gson gson = new Gson();
	private ProgressDialog dialog;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initId(this);
		dialog = new ProgressDialog(LoginActivity.this);
		appContext = (AppContext) getApplication();
		networkHelper = new StrVolleyInterface(this);
		networkHelper.setStrUIDataListener(this);
		
		
		tvRegist.setOnClickListener(new RegistListener());
		btnLogin.setOnClickListener(new LoginListener());
		tvForget.setOnClickListener(forgetListener);
		tvQuXiao.setOnClickListener(quxiaoListener );
	}

	private void initId(LoginActivity loginActivity) {
		// TODO Auto-generated method stub
		
		SharedPreferences sp = this.getSharedPreferences(Constant.MY_PREFERENCES, 0);  
        String account = sp.getString(Constant.MY_ACCOUNT, "");
        String pass = sp.getString(Constant.MY_PASSWORD, "");  
        Log.v("账号",account);
        Log.v("密码", pass);
      /*  //对密码进行AES解密  
        try{  
            pass = AESEncryptor.decrypt("41227677", pass);  
        }catch(Exception ex){  
            Toast.makeText(this, "获取密码时产生解密错误!", Toast.LENGTH_LONG).show();
            pass = "";  
        }  */
		 tvQuXiao=(TextView)findViewById(R.id.tv_quxiao);
        tvForget=(TextView)findViewById(R.id.tv_forget);
		tvRegist=(TextView)findViewById(R.id.tv_regist);
		btnLogin=(Button)findViewById(R.id.btn_login);
		etName=(EditText)findViewById(R.id.et_name);
		etPassword=(EditText)findViewById(R.id.et_password);
		etName.addTextChangedListener(new TextChange());
		etPassword.addTextChangedListener(new TextChange());
		etName.setText(account);
		etPassword.setText(pass);
	}
	
	//各种监听
	
	//取消 返回键
	OnClickListener quxiaoListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	};
	
	//忘记密码
	OnClickListener forgetListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(LoginActivity.this,RegistActivity.class);
			intent.putExtra("forgetFlag", "forget");
			startActivity(intent);
		}
	};
	
	class LoginListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//登陆请求
			SharedPreferences sp = LoginActivity.this.getSharedPreferences(Constant.MY_PREFERENCES, 0);  
	        String name = LoginActivity.this.etName.getText().toString();// sp.getString(Constant.MY_ACCOUNT, "");
	        String password = LoginActivity.this.etPassword.getText().toString();// sp.getString(Constant.MY_ACCOUNT, "");
//	        String password = sp.getString(Constant.MY_PASSWORD, "");  
			ApiClient.Login(LoginActivity.this, name, password, networkHelper);
			dialog.setMessage("正在登录...");
	        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        dialog.show();
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
		dialog.dismiss();
		appContext.setLogin(false);
		Toast.makeText(LoginActivity.this, "登陆发生异常", Toast.LENGTH_LONG).show();
	}
	@Override
	public void onDataChanged(String data) {
		// TODO Auto-generated method stub
		JSONObject jsonObject=null;
		int code = -1;
		String content = null;
		try {
			jsonObject = new JSONObject(data);
			code = jsonObject.getInt("code");
			content = jsonObject.getString("data");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		if (code == 0) {
			//String userId=jsonObject.getString("data");
			//System.out.println("code=" + data.toString());
			User user = gson.fromJson(content, User.class);
			appContext.setLogin(true);
			appContext.setLoginUid(user.getId());
			Log.v("用户id", user.getId()+"");
		    String use = etName.getText().toString();   
		    String pas = etPassword.getText().toString(); 
		/*	//并使用AES加密算法给密码加密。
	        String use = etName.getText().toString().trim();   
	        String pas = etPassword.getText().toString().trim(); 
	       try{  
	        	pas = AESEncryptor.encrypt("41227677", pas);  
	        }catch(Exception ex){  
	            Toast.makeText(this, "给密码加密时产生错误!", Toast.LENGTH_SHORT).show();
	            pas = "";  
	        }  */
	        //获取名字为“MY_PREFERENCES”的参数文件对象。  
	        SharedPreferences sp = this.getSharedPreferences(Constant.MY_PREFERENCES, 0);  
	        //使用Editor接口修改SharedPreferences中的值并提交。  
	        Editor editor = sp.edit();  
	        editor.putString(Constant.MY_ACCOUNT, use);  
	        editor.putString(Constant.MY_PASSWORD,pas);  
	        editor.commit();
	        dialog.dismiss();
	        Toast.makeText(LoginActivity.this, "登陆成功",Toast.LENGTH_LONG).show();
	        finish();
	        overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
		} else {
			dialog.dismiss();
			appContext.setLogin(false);
			Toast.makeText(LoginActivity.this, "登陆失败，请检查用户名和密码" + data.toString(), Toast.LENGTH_LONG)
			.show();
			System.out.println("code=" + data.toString());
		}
	}
	
	// EditText监听器
    class TextChange implements TextWatcher {

        @Override
        public void afterTextChanged(Editable arg0) {

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before,
                int count) {

            boolean Sign2 = etName.getText().length() > 0;
            boolean Sign3 = etPassword.getText().length() > 0;

            if (Sign2 & Sign3) {
            	btnLogin.setTextColor(0xFFFFFFFF);
            	btnLogin.setEnabled(true);
            }
            // 在layout文件中，对Button的text属性应预先设置默认值，否则刚打开程序的时候Button是无显示的
            else {
            	btnLogin.setTextColor(0xFF808080);
            	btnLogin.setEnabled(false);
            }
        }

    }
}
