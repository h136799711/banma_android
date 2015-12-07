package com.itboye.banma.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.itboye.banma.R;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.entity.OrderExpress;
import com.itboye.banma.entity.OrderPayData;
import com.itboye.banma.payalipay.PayAlipay;

public class ActivityLogistics  extends Activity implements StrUIDataListener,
OnClickListener {
	private AppContext appContext;
	private StrVolleyInterface strnetworkHelper;
	private Boolean YesOrNo;
	private ImageView back;
	private TextView title;
	private WebView logistics;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logistics);
		appContext = (AppContext) getApplication();
		strnetworkHelper = new StrVolleyInterface(ActivityLogistics.this);
		strnetworkHelper.setStrUIDataListener(ActivityLogistics.this);
		init();
	}

	private void init() {
		Intent intent = getIntent();
		String Order_code = intent.getStringExtra("order_code");
		getLogistics(Order_code);
		back = (ImageView) findViewById(R.id.iv_back);
		title = (TextView) findViewById(R.id.title);
		logistics = (WebView) findViewById(R.id.logistics);
		back.setOnClickListener(this);
		title.setText("物流查询");
	}

	/**
	 * 查看物流
	 * @param order_code
	 */
	private void getLogistics(String order_code) {
		try {
			YesOrNo = appContext.getLogistics(ActivityLogistics.this, order_code, strnetworkHelper);

			if (!YesOrNo) { // 如果没联网
				Toast.makeText(ActivityLogistics.this, "请检查网络连接",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			break;
		default:
			break;
		}
	}

	@Override
	public void onErrorHappened(VolleyError error) {
//		Toast.makeText(ActivityLogistics.this, "error"+error,
//				Toast.LENGTH_LONG).show();
	}

	@Override
	public void onDataChanged(String data) {
		OrderExpress orderExpress = null;
		Gson gson = new Gson();
		JSONObject jsonObject=null;
		int code = -1;
		String content = null;
		OrderPayData orderPayData;
		try {
			jsonObject = new JSONObject(data);
			code = jsonObject.getInt("code");
			content = jsonObject.getString("data");
			if (code == 0) {
				
				orderExpress = gson.fromJson(content, OrderExpress.class);
				String url = "http://m.kuaidi100.com/index_all.html?type="+orderExpress.getExpresscode()+"&postid="+orderExpress.getExpressno();
				WebSettings ws = logistics.getSettings();  
		        //是否允许脚本支持  
		        ws.setJavaScriptEnabled(true);  
		        ws.setJavaScriptCanOpenWindowsAutomatically(true);  
		        ws.setSaveFormData(false);  
		        ws.setSavePassword(false);  
		        ws.setAppCacheEnabled(true);  
		        ws.setAppCacheMaxSize(10240);  
//		      ws.setCacheMode(WebSettings.LOAD_NO_CACHE);  
		        //是否允许缩放  
//		      ws.setBuiltInZoomControls(true);  
		        
		        conn(url);
				
			}
			else{
//				byte[] bytes = jsonObject.getString("data").getBytes(); 
//				String newStr = new String(bytes , "UTF-8"); 
//				Toast.makeText(ActivityLogistics.this, "操作:" + newStr,
//						Toast.LENGTH_LONG).show();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		/*Toast.makeText(ActivityLogistics.this, "返回"+data,
				Toast.LENGTH_LONG).show();*/
	}
    /** 
     * 访问url 
     * @param urlStr 
     */  
    private void conn(String urlStr){  
        String url = "";  
        if(urlStr.contains("http://")){  
            url = urlStr;  
        }else{  
            url = "http://"+urlStr;  
        }  
        logistics.loadUrl(url);  
    } 
}
