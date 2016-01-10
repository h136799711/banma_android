package com.itboye.banma.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
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

public class SuggestActivity extends Activity implements StrUIDataListener ,android.view.View.OnClickListener{
	private StrVolleyInterface networkHelper;
	private AppContext appContext;
	private ProgressDialog dialog;
	private  TextView title;
	private  ImageView iv_back;
	private EditText et_suggest;;
	private Button btn_tijiao;
	int beizhuCode;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suggest);
		appContext = (AppContext) getApplication();
		networkHelper = new StrVolleyInterface(this);
		networkHelper.setStrUIDataListener(this);
		initId(this);
		initData();

	}

	private void initData() {
		// TODO Auto-generated method stub

	}

	private void initId(SuggestActivity suggestActivity) {
		// TODO Auto-generated method stub
		Intent intent=getIntent();
		beizhuCode=intent.getIntExtra("beizhuCode", 0); 

		title=(TextView)findViewById(R.id.title);
		title.setText("意见与建议");
		iv_back=(ImageView)findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
		et_suggest=(EditText)findViewById(R.id.et_suggest);
		btn_tijiao=(Button)findViewById(R.id.btn_tijiao);
		btn_tijiao.setOnClickListener(this);

		if (beizhuCode==1) {
			title.setText("备注");
		}
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
			Toast.makeText(SuggestActivity.this, content.toString(), Toast.LENGTH_LONG).show();
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
		}else {
			Toast.makeText(SuggestActivity.this, content.toString(), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.btn_tijiao:
			if (beizhuCode==1) {
				Intent intent=new Intent(SuggestActivity.this,ConfirmOrdersActivity.class);
				 intent.putExtra("beizhu",et_suggest.getText().toString() );
				 setResult(1106, intent);
				 finish();
				 overridePendingTransition(R.anim.push_right_in,
						 R.anim.push_right_out);
			}else {
				ApiClient.suggest(this, et_suggest.getText().toString(), appContext.getLoginUid()+"", networkHelper);
			}
			
			break;
		default:
			break;
		}
	}
}
