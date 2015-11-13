package com.itboye.banma.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.itboye.banma.R;
import com.itboye.banma.api.ApiClient;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;

public class YouHuiActivity extends Activity implements StrUIDataListener,android.view.View.OnClickListener{
	private StrVolleyInterface networkHelper;
	private AppContext appContext;
	private ProgressDialog dialog;
	private  TextView title;
	private  ImageView iv_back;
	private EditText et_youhuima;
	private Button btn_yanzheng;
	private TextView tv_list;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_youhui);
		appContext = (AppContext) getApplication();
		networkHelper = new StrVolleyInterface(this);
		networkHelper.setStrUIDataListener(this);
		
		initId();
		initData();
}

	private void initData() {
		// TODO Auto-generated method stub
	}

	private void initId() {
		// TODO Auto-generated method stub
		title=(TextView)findViewById(R.id.title);
		title.setText("使用优惠码");
		iv_back=(ImageView)findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
		btn_yanzheng=(Button) findViewById(R.id.btn_yanzheng);
		btn_yanzheng.setOnClickListener(this);
		et_youhuima=(EditText)findViewById(R.id.et_youhuima);
		tv_list=(TextView) findViewById(R.id.tv_youhui_list);
	}

	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
		
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
			Toast.makeText(YouHuiActivity.this, content.toString(), Toast.LENGTH_LONG).show();
			Intent intent = new Intent();
			intent.putExtra("YOU_HUI", content);
			/*
			 * 调用setResult方法表示我将Intent对象返回给之前的那个Activity，
			 * 这样就可以在onActivityResult方法中得到Intent对象，
			 */
			setResult(1005, intent);
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
		}else {
			Toast.makeText(YouHuiActivity.this, content.toString(), Toast.LENGTH_LONG).show();
		}
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.btn_yanzheng:
			ApiClient.youHuiMa(YouHuiActivity.this, et_youhuima.getText().toString(),networkHelper);
			break;
		default:
			break;
		}
	}
}
