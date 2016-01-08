package com.itboye.banma.activities;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.itboye.banma.R;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityReturnBaby extends Activity implements OnClickListener, StrUIDataListener{

	TextView title;
	ImageView iv_back;
	TextView sales_return;
	TextView refund;
	LinearLayout money_layout;
	TextView money_txt;
	EditText money_edit;
	EditText cause_edt;
	Button submit;
	
	private int code = -1;	//1表示退款，0表示退货
	private Boolean YesOrNo; // 是否连接网络
	private StrVolleyInterface strnetworkHelper;
	private AppContext appContext;
	Double order_price;   //订单价格
	String order_code;		//订单编号
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_return_order);
		initData();
		initView();
	}

	private void initData() {
		Intent intent = getIntent();
		code = intent.getIntExtra("code", 0);	//1表示退款，0表示退货
		order_code = intent.getStringExtra("order_code");
		order_price = Double.valueOf(intent.getStringExtra("order_price"));
		appContext = (AppContext) getApplication();
		strnetworkHelper = new StrVolleyInterface(ActivityReturnBaby.this);
		strnetworkHelper.setStrUIDataListener(ActivityReturnBaby.this);
	}

	private void initView() {
		title = (TextView) findViewById(R.id.title);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		sales_return = (TextView) findViewById(R.id.sales_return);
		refund = (TextView) findViewById(R.id.refund);
		money_layout = (LinearLayout) findViewById(R.id.money_layout);
		money_txt = (TextView) findViewById(R.id.money_txt);
		money_edit = (EditText) findViewById(R.id.money_edit);
		cause_edt = (EditText) findViewById(R.id.cause_edt);
		submit = (Button) findViewById(R.id.submit);
		
		iv_back.setOnClickListener(this);
		submit.setOnClickListener(this);
		
		if(code == 0){
			title.setText("退货业务");
			refund.setVisibility(View.GONE);
			money_txt.setVisibility(View.GONE);
			money_layout.setVisibility(View.GONE);
		}else if(code == 1){
			title.setText("退款业务");
			money_txt.setText("退款（不能超过+"+order_price+")");
			sales_return.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.iv_back:
			// 返回
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			break;
		case R.id.submit:
			
			if(code == 0){  //退货业务
				
			}else if(code == 1){  //退款业务
				
				try {
					YesOrNo = appContext.refund(ActivityReturnBaby.this, money_edit.getText().toString(),
							order_code, cause_edt.getText().toString(), strnetworkHelper);
					if (!YesOrNo) { // 如果没联网
						Toast.makeText(ActivityReturnBaby.this, "请检查网络连接", Toast.LENGTH_SHORT)
								.show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			break;
		}
	}

	@Override
	public void onErrorHappened(VolleyError error) {
		Toast.makeText(this, "访问出错"+error, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDataChanged(String data) {
		Toast.makeText(this, "数据："+data, Toast.LENGTH_SHORT).show();
		JSONObject jsondata;
		try {
			jsondata = new JSONObject(data);

			int code = jsondata.getInt("code");
			String content = jsondata.getString("data");
			if (code == 0) {
				
			}else{
				byte[] bytes = content.getBytes();
				String newStr = null;
				try {
					newStr = new String(bytes , "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} 
				Toast.makeText(ActivityReturnBaby.this, "异常:"+newStr,
						Toast.LENGTH_SHORT).show();
			}
		}catch (JSONException e) {
	
			e.printStackTrace();
			Toast.makeText(ActivityReturnBaby.this, "加载失败",
					Toast.LENGTH_SHORT).show();
		}
	}

}
