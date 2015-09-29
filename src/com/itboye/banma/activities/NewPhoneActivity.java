package com.itboye.banma.activities;

import com.android.volley.VolleyError;
import com.itboye.banma.R;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NewPhoneActivity  extends Activity implements OnClickListener,StrUIDataListener{
	private StrVolleyInterface networkHelper;
	private EditText etOldNumber;//当前手机号
	private EditText etOldPass;//当前密码
	private EditText etNewNumber;//新的手机号
	private EditText etCheckCode;//验证码
	private Button btnGetCode;//获取验证码按钮
	private Button btnSub;//确认修改
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newphone);networkHelper = new StrVolleyInterface(this);
		networkHelper.setStrUIDataListener(this);
		
		initId();
	}

	private void initId() {
		// TODO Auto-generated method stub
		etOldNumber=(EditText)findViewById(R.id.et_old_phone_number);
		if (getIntent().getStringExtra("oldPboneNumber")!=null) {
			etOldNumber.setText(getIntent().getStringExtra("oldPboneNumber"));
		}
		etOldPass=(EditText)findViewById(R.id.et_old_pass);
		etNewNumber=(EditText)findViewById(R.id.et_new_phone_number);
		etCheckCode=(EditText)findViewById(R.id.et_check_code);
		btnGetCode=(Button)findViewById(R.id.btn_getcheckcode);
		btnSub=(Button)findViewById(R.id.btn_sub);
	}

	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDataChanged(String data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_getcheckcode:
			
			break;
		case R.id.btn_sub:
			
			break;
		case R.id.tv_back:
			finish();
			break;

		default:
			break;
		}
	}
}
