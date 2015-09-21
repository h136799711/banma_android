package com.itboye.banma.activities;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.itboye.banma.R;
import com.itboye.banma.R.id;
import com.itboye.banma.api.ApiClient;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
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
	        
	    	appContext = (AppContext) getApplication();
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
				Intent nextIntent=new Intent(RegistActivity.this,RegsitedActivity.class);
				startActivity(nextIntent);
		}
	};
	//实现URL的跳转
private OnClickListener urlOnClick =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			getCheck();
				// TODO Auto-generated method stub
			String url="http://banma.itboye.com/Public/html/copyright.html";
			//Uri uri = Uri.parse("http://banma.itboye.com/Public/html/copyright.html");
		// intent = new Intent(Intent.ACTION_VIEW, uri);
		//	startActivity(intent);
			wvShowView.loadUrl(url);
		}
	};
	
	private OnClickListener checkCodeOnclick =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
				// TODO Auto-generated method stub
			
			String mobile=edPhoneNumber.getText().toString();
			Log.v("手机号", mobile);
			ApiClient.getCheckCode(RegistActivity.this, mobile, "1", networkHelper);
		}
	};

	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
		Toast.makeText(RegistActivity.this, "获取验证码失败" + error, Toast.LENGTH_LONG)
		.show();
		Log.v("获取token",error.toString() );
	}

	@Override
	public void onDataChanged(String data) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(data);
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		String checkCode = null;
		int code = -1;
		try {
			int dat = jsonObject.getInt("code");
		//	System.out.println(dat+"");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		if (code == 0) {
			try {
			
				checkCode= jsonObject.getString("data");
				edCheckCode.setText(checkCode);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Toast.makeText(RegistActivity.this, "获取验证码成功：" + checkCode, Toast.LENGTH_LONG)
			.show();
		} else {
			Toast.makeText(RegistActivity.this, "获取验证码失败：code=" + data.toString(), Toast.LENGTH_LONG)
			.show();
		}
	}
	
	private void getCheck(){
		RequestQueue requestQueue =Volley.newRequestQueue(RegistActivity.this);
			System.out.println("service 已经运行");
		String access=appContext.getAccess_token();
			String httpurl="http://m.weather.com.cn/data/101010100.html";
					//+ "send?access_token="+access+"";;
			// TODO Auto-generated method stub
				//判断应用是否仍在进行,如果是则重新请求token
//					Map<String, String> map = new HashMap<String, String>();  
//					map.put("mobile", "17764592053");
//					map.put("type", "手机");  
//					
					JSONObject jsonObject = new JSONObject();
					try {
						jsonObject.put("mobie", "17764592053");
						jsonObject.put("type", "1");
						Log.v("传递的json参数", jsonObject.toString());
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	
					JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(httpurl, null,
					    new Response.Listener<JSONObject>() {
					        @Override
					        public void onResponse(JSONObject response) {
					        	String token;
								//String  data= response.getString("data");
								Log.v("获得验证码", response.toString());
					            android.util.Log.v("打印信息", "response -> " + response.toString());
					        }
					    }, new Response.ErrorListener() {
					        @Override
					        public void onErrorResponse(VolleyError error) {
					        	android.util.Log.v("获得失败", "error -> " +error.toString());
					    }});
			  requestQueue.add(jsonRequest);
			   requestQueue.start();
				}
}

