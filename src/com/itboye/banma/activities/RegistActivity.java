package com.itboye.banma.activities;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.itboye.banma.R;
import com.itboye.banma.R.id;
import com.itboye.banma.api.ApiClient;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.api.UIDataListener;
import com.itboye.banma.api.VolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
import com.itboye.banma.welcome.HomeActivity;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistActivity extends Activity implements StrUIDataListener {
private 	Button btnNextStep;//下一步按钮
private Button btnGetCheckCode;//获取验证码
private  EditText edPhoneNumber;//手机号
private EditText edCheckCode;//验证码编辑框
private TextView tvUrl;//用户服务条款连接
private WebView wvShowView;//用于显示webview
private AppContext appContext;
private StrVolleyInterface networkHelper;


	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_regist);
			networkHelper = new StrVolleyInterface(this);
			networkHelper.setStrUIDataListener(this);
			
	       initId(this);
	       
	       btnNextStep.setOnClickListener(nextClickListener);
	       btnGetCheckCode.setOnClickListener(checkCodeOnclick);
	       tvUrl.setOnClickListener(urlOnClick);
	    }

	private void initId(RegistActivity registActivity) {
		// TODO Auto-generated method stub
		tvUrl=(TextView) findViewById(R.id.tv_url);
		wvShowView=(WebView)findViewById(R.id.wv_show_view);
		btnNextStep=(Button)findViewById(R.id.btn_next_step);
		btnGetCheckCode=(Button)findViewById(R.id.btn_getcheckcode);
		edCheckCode=(EditText)findViewById(R.id.et_check);
		edPhoneNumber=(EditText)findViewById(R.id.et_phone_number);
	}
	
	//实现注册
	private OnClickListener nextClickListener =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent nextIntent=new Intent(RegistActivity.this,PasswordActivity.class);
				startActivity(nextIntent);
		}
	};
	//实现URL的跳转
private OnClickListener urlOnClick =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
				// TODO Auto-generated method stub
			String url="http://banma.itboye.com/Public/html/copyright.html";
			//Uri uri = Uri.parse("http://banma.itboye.com/Public/html/copyright.html");
		// intent = new Intent(Intent.ACTION_VIEW, uri);
		//	startActivity(intent);
			wvShowView.loadUrl(url);
			getCheck();
		}
	};
	
	private OnClickListener checkCodeOnclick =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
				// TODO Auto-generated method stub
			
			String mobile=edPhoneNumber.getText().toString();
			Log.v("手机号", mobile);
			getCheck();
			//ApiClient.getCheckCode(RegistActivity.this, mobile, "1", networkHelper);
		}
	};

	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
		Toast.makeText(RegistActivity.this, "获取验证码失败" + error, Toast.LENGTH_LONG)
		.show();
		System.out.println(error+"");
	}

	@Override
	public void onDataChanged(String  data) {
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
				String checkdata=jsonObject.getString("data");
				System.out.println(checkdata);
				edCheckCode.setText(checkdata);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			//Toast.makeText(RegistActivity.this, "获取验证码成功：" + checkCode, Toast.LENGTH_LONG)
			//.show();
		} else {
			Toast.makeText(RegistActivity.this, "获取验证码失败：code=" + data.toString(), Toast.LENGTH_LONG)
			.show();
			System.out.println("code=" + data.toString());
		}
	}
	
		private void getCheck(){
			RequestQueue requestQueue=AppContext.getHttpQueues();
			final String token=AppContext.getAccess_token();
			System.out.println(token);
			String httpurl=Constant.URL+"/Message/send?access_token="+token+"";
			StringRequest jsonRequest = new StringRequest(Request.Method.POST,httpurl, new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					// TODO Auto-generated method stub
					System.out.println(response.toString());
					try {
					JSONObject jsonObject=new JSONObject(response);
				     //	JSONObject 	data=(JSONObject) jsonObject.get("data");
					//	String   access_token=data.getString("access_token");
					//	AppContext.setAccess_token(access_token);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			},new Response.ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					// TODO Auto-generated method stub
					System.out.println(error.toString());
				}
			}){
				@Override 
				  protected Map<String, String> getParams() throws AuthFailureError {  
					Map<String, String> map = new HashMap<String, String>();  
				//	map.put("access_token", token);
					map.put("mobile", "17764592053");
					map.put("type", "1");  
		//			map.put("client_id", "by559a8de1c325c1");
					return map;
				}
			};
		requestQueue.add(jsonRequest);
		//}
		}
}

