package com.itboye.banma.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.itboye.banma.R;
import com.itboye.banma.api.ApiClient;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.Constant;
import com.umeng.analytics.MobclickAgent;

public class NewPhoneActivity  extends Activity implements OnClickListener,StrUIDataListener{
	private StrVolleyInterface networkHelper;
	private EditText etOldNumber;//当前手机号
	private EditText etOldPass;//当前密码
	private EditText etNewNumber;//新的手机号
	private EditText etCheckCode;//验证码
	private Button btnGetCode;//获取验证码按钮
	private Button btnSub;//确认修改
	private ImageView ivBack;//返回按钮
	SharedPreferences sp;
	private String requestCode="";
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newphone);
		networkHelper = new StrVolleyInterface(this);
		networkHelper.setStrUIDataListener(this);
		initId();
		sp=  this.getSharedPreferences(Constant.MY_PREFERENCES, 0);  
		btnGetCode.setOnClickListener(this);
		btnSub.setOnClickListener(this);
		ivBack.setOnClickListener(this);
	}

	private void initId() {
		// TODO Auto-generated method stub
		etOldNumber=(EditText)findViewById(R.id.et_old_phone_number);
		if (getIntent().getStringExtra("oldPboneNumber")!=null) {
			etOldNumber.setText(getIntent().getStringExtra("oldPboneNumber"));
		}
		ivBack=(ImageView)findViewById(R.id.iv_back);
		etOldPass=(EditText)findViewById(R.id.et_old_pass);
		etNewNumber=(EditText)findViewById(R.id.et_new_phone_number);
		etCheckCode=(EditText)findViewById(R.id.et_check_code);
		btnGetCode=(Button)findViewById(R.id.btn_getcheckcode);
		btnSub=(Button)findViewById(R.id.btn_sub);
	}


	//友盟统计
		@Override
		protected void onResume() {

			super.onResume();
			MobclickAgent.onResume(this);
		}

		public void onPause() {
			super.onPause();
			MobclickAgent.onPause(this);
		}

	

	@Override
	public void onClick(View v) {
		String mobile=etNewNumber.getText().toString();
		String checdcode=etCheckCode.getText().toString();
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_getcheckcode:
			if (mobile.length()!=11) {
				Toast.makeText(NewPhoneActivity.this, "请输入正确地新手机号", Toast.LENGTH_SHORT).show();
			}else {
				requestCode="CODE";
				ApiClient.getCheckCode(NewPhoneActivity.this, mobile, "4", networkHelper);
			}			
			break;
		case R.id.btn_sub:
		
			if (mobile.length()!=11) {
				Toast.makeText(NewPhoneActivity.this, "请输入正确地新手机号", Toast.LENGTH_SHORT).show();
			}else if (checdcode.length()!=6) {
				Toast.makeText(NewPhoneActivity.this, "请先获取验证码", Toast.LENGTH_SHORT).show();
			} else {
				requestCode="SUB";
				ApiClient.changePhone(this, sp.getString(Constant.MY_USERID, ""), etCheckCode.getText().toString(),
						etNewNumber.getText().toString(), etOldPass.getText().toString(), networkHelper);
			}
		break;
		case R.id.iv_back:
			NewPhoneActivity.this.finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			break;

		default:
			break;
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
			if (requestCode.equals("CODE")){//获取的是验证码
				Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
				System.out.println("获取验证码成功");
			}else if (requestCode.equals("SUB")){
				//保存登陆绑定的手机号
				Editor editor=sp.edit();
				editor.putString(Constant.MY_BANGDING, etNewNumber.getText().toString());
//				editor.putString(Constant.MY_ACCOUNT, etNewNumber.getText().toString());
				editor.commit();
				//修改显示绑定的手机号
				Intent intent=getIntent();
				intent.putExtra("newPhone", etNewNumber.getText().toString());
				Log.v("新手机号", etNewNumber.getText().toString());
				NewPhoneActivity.this.setResult(1,intent);
				Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
				finish();
			}
		}
		else {
			Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
		}
	}
}
