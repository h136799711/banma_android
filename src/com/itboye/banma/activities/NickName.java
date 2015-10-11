package com.itboye.banma.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.itboye.banma.R;
import com.itboye.banma.api.ApiClient;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;

public class NickName extends Activity implements StrUIDataListener {
	private TextView tvNickName;//昵称t
	private Button btnXiuGai;//修改按钮
	private TextView tvTitle;//top中textview
	private ImageView ivBack;
	private StrVolleyInterface networkHelper;
	private AppContext appContext;
	private ProgressDialog dialog;
	//private ImageView ivBack;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nickname);
		networkHelper = new StrVolleyInterface(this);
		networkHelper.setStrUIDataListener(this);
		appContext=(AppContext) getApplication();
		dialog = new ProgressDialog(NickName.this);
		initId();
		
		ivBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
			}
		});
		
		btnXiuGai.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ApiClient.modifyPersonal(NickName.this,appContext.getLoginUid()+"", "",tvNickName.getText().toString(), "", "", "", "", "", "", "", networkHelper);
				dialog.setMessage("正在修改个人信息");
		        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				dialog.show();
			}
		});
	}
	private void initId() {
		// TODO Auto-generated method stub
		ivBack=(ImageView)findViewById(R.id.iv_back);
		tvTitle=(TextView)findViewById(R.id.title);
		tvTitle.setText("昵称修改");
		tvNickName=(TextView)findViewById(R.id.tv_nickname);
		Intent intent=getIntent();
		String name=intent.getStringExtra("nickname");
		tvNickName.setText(name);
		btnXiuGai=(Button)findViewById(R.id.btn_xiugai);
	}
	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
		dialog.dismiss();
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
				dialog.dismiss();
				Toast.makeText(NickName.this, content.toString(), Toast.LENGTH_SHORT).show();
				Intent intent=getIntent();
				intent.putExtra("nickName", tvNickName.getText().toString());
				NickName.this.setResult(0,intent);
				finish();
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
	     	}else {
	     		Toast.makeText(NickName.this, "修改失败", Toast.LENGTH_SHORT).show();
			}
		}
}
