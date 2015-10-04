package com.itboye.banma.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.itboye.banma.R;
import com.itboye.banma.api.ApiClient;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;

public class RegistActivity extends Activity implements StrUIDataListener {
private 	Button btnNextStep;//下一步按钮
private Button btnGetCheckCode;//获取验证码
private  EditText edPhoneNumber;//手机号
private EditText edCheckCode;//验证码编辑框
private TextView tvUrl;//用户服务条款连接
private ImageView tvBackRegist;//注册返回按钮
private String checkcode=null;//验证码内容
private String forgetFlag=null;//忘记密码intent标记
private TextView tvRegist;//注册titlebar
private LinearLayout llTiaoKuan;//条款总布局
public  int  Flags;//定义状态常量，用于表示几种验证码校验接口，对应是1 用户注册 2 忘记密码3 旧手机验证
//4 新手机绑定
String tempCode=null;//临时存储code
private Intent intent;
private StrVolleyInterface networkHelper;

	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_regist);
			networkHelper = new StrVolleyInterface(this);
			networkHelper.setStrUIDataListener(this);
			intent=getIntent();
			
		    initId(this);
		       
		    forgetFlag=intent.getStringExtra("forgetFlag");
		    if (forgetFlag==null) {
				forgetFlag=" ";
			}else {
				tvRegist.setText("找回密码");
				llTiaoKuan.setVisibility(View.GONE);
				
			}

	       tvBackRegist.setOnClickListener(backOnclickListener);
	       btnNextStep.setOnClickListener(nextClickListener);
	       btnGetCheckCode.setOnClickListener(checkCodeOnclick);
	       tvUrl.setOnClickListener(urlOnClick);
	    }

	private void initId(RegistActivity registActivity) {
		// TODO Auto-generated method stub
		tvBackRegist=(ImageView)findViewById(R.id.tv_back_regesit);
		tvUrl=(TextView) findViewById(R.id.tv_url);
	//	wvShowView=(WebView)findViewById(R.id.wv_show_view);
		btnNextStep=(Button)findViewById(R.id.btn_next_step);
		btnGetCheckCode=(Button)findViewById(R.id.btn_getcheckcode);
		edCheckCode=(EditText)findViewById(R.id.et_check_code);
		edCheckCode.setText("");
		edPhoneNumber=(EditText)findViewById(R.id.et_phone_number);
		tvRegist=(TextView)findViewById(R.id.tv_regist);
		llTiaoKuan=(LinearLayout)findViewById(R.id.ll_tiaokuan);
	}
	
	//返回按钮
	private OnClickListener backOnclickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	};
	
	//下一步按钮,同时验证验证码
	private OnClickListener nextClickListener =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
				// TODO Auto-generated method stub
				if (checkcode==null) {
					Toast.makeText(RegistActivity.this, "请先获取验证码", Toast.LENGTH_LONG).show();
				}else if (forgetFlag.equals("forget")){
					Flags=2;
					Intent nextIntent=new Intent(RegistActivity.this,PasswordActivity.class);
					nextIntent.putExtra("username", edPhoneNumber.getText().toString());
					nextIntent.putExtra("code",edCheckCode.getText().toString());
					nextIntent.putExtra("Flags", Flags);
					startActivity(nextIntent);
					//ApiClient.judgeCheckCode(RegistActivity.this, edPhoneNumber.getText().toString(), checkcode, "2", " ", networkHelper);
				}
				else
				{
					Flags=1;
					ApiClient.judgeCheckCode(RegistActivity.this, edPhoneNumber.getText().toString(), checkcode, "1", " ", networkHelper);
				}
		}
	};
	//实现URL的跳转
private OnClickListener urlOnClick =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
				// TODO Auto-generated method stub
			String Url="http://banma.itboye.com/Public/html/copyright.html";
			Intent intent=new Intent(RegistActivity.this,WebActivity.class);
			intent.putExtra("Url", Url);
			startActivity(intent);
		}
	};
	//获取验证码
	private OnClickListener checkCodeOnclick =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
				// TODO Auto-generated method stub
			String mobile=edPhoneNumber.getText().toString();
			if (mobile.length()!=11) {
				Toast.makeText(RegistActivity.this, "请输入手机号", Toast.LENGTH_LONG).show();
			}else if  (forgetFlag.equals("forget")){
				ApiClient.getCheckCode(RegistActivity.this, mobile, "2", networkHelper);
				System.out.println("forget");
			}
			else {
				ApiClient.getCheckCode(RegistActivity.this, mobile, "1", networkHelper);
				System.out.println("1");
			}
		}
	};

	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
		Toast.makeText(RegistActivity.this, "服务器发生错误" + error, Toast.LENGTH_LONG)
		.show();
		System.out.println(error+"");
	}

	@Override
	public void onDataChanged(String data) {
		// TODO Auto-generated method stub
		JSONObject jsonObject=null;
		int code = -1 ;
		try {
			jsonObject=new JSONObject(data);
			checkcode=jsonObject.getString("data");
			code=jsonObject.getInt("code");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		if (code ==0) {
			Log.v("数据", checkcode);
			if (checkcode.equals("验证通过!")) {
				Intent nextIntent=new Intent(RegistActivity.this,PasswordActivity.class);
				nextIntent.putExtra("username", edPhoneNumber.getText().toString());
				nextIntent.putExtra("Flags", Flags);
				startActivity(nextIntent);
			}else {
				edCheckCode.setText(checkcode);
				System.out.println("2");
			}
		} else {
			Toast.makeText(RegistActivity.this, "验证码失败：" +checkcode, Toast.LENGTH_LONG)
			.show();
		}
	}
}

