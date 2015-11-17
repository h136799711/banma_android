package com.itboye.banma.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.itboye.banma.R;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.entity.OrderPayData;
import com.itboye.banma.payalipay.PayAlipay;

public class ActivityLogistics  extends Activity implements StrUIDataListener,
OnClickListener {
	private AppContext appContext;
	private StrVolleyInterface strnetworkHelper;
	private Boolean YesOrNo;

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

	}

	@Override
	public void onErrorHappened(VolleyError error) {
		Toast.makeText(ActivityLogistics.this, "error"+error,
				Toast.LENGTH_LONG).show();
	}

	@Override
	public void onDataChanged(String data) {

		JSONObject jsonObject=null;
		int code = -1;
		String content = null;
		OrderPayData orderPayData;
		try {
			jsonObject = new JSONObject(data);
			code = jsonObject.getInt("code");
			content = jsonObject.getString("data");
			if (code == 0) {
				
			}
			else{
				byte[] bytes = jsonObject.getString("data").getBytes(); 
				String newStr = new String(bytes , "UTF-8"); 
				Toast.makeText(ActivityLogistics.this, "操作:" + newStr,
						Toast.LENGTH_LONG).show();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		Toast.makeText(ActivityLogistics.this, "返回"+data,
				Toast.LENGTH_LONG).show();
	}

}
