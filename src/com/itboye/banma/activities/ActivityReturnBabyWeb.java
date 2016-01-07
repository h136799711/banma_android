package com.itboye.banma.activities;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.itboye.banma.R;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityReturnBabyWeb extends Activity implements OnClickListener{

	TextView title;
	ImageView iv_back;
	WebView refund_web;
	
	String order_code;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_return_order_web);
		init();
	}

	private void init() {
		Intent intent = getIntent();
		order_code = intent.getStringExtra("order_code");
		title = (TextView) findViewById(R.id.title);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		
		title.setText("退款业务");
		iv_back.setOnClickListener(this);
		refund_web = (WebView) findViewById(R.id.refund_web);
		refund_web.loadUrl("http://www.bammar.com/index.php/Home/Refund/index?order_code="+order_code);
		//启用支持javascript
		WebSettings settings = refund_web.getSettings();
		settings.setJavaScriptEnabled(true);
		refund_web.setWebViewClient(new WebViewClient(){
	           @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            // TODO Auto-generated method stub
	               //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
	             view.loadUrl(url);
	            return true;
	        }
	       });
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(this ,BabyOrderActivity.class);
          	intent.putExtra("orderState", Constant.STATE_ALL);
          	startActivity(intent);
          	overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
		}
		return false;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.iv_back:
			// 返回
			Intent intent = new Intent(this ,BabyOrderActivity.class);
          	intent.putExtra("orderState", Constant.STATE_ALL);
          	startActivity(intent);
          	overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			break;
		}
	}

}
