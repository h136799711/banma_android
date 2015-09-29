package com.itboye.banma.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.itboye.banma.R;

public class NickName extends Activity {
	private TextView tvNickName;//昵称
	private Button btnXiuGai;//修改按钮
	private TextView tvTitle;//top中textview
	private ImageView ivBack;
	//private ImageView ivBack;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nickname);
		initId();
		ivBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		btnXiuGai.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=getIntent();
				intent.putExtra("nickName", tvNickName.getText());
				NickName.this.setResult(0,intent);
				finish();
			}
		});
	}
	private void initId() {
		// TODO Auto-generated method stub
		ivBack=(ImageView)findViewById(R.id.iv_back);
		tvTitle=(TextView)findViewById(R.id.title);
		tvTitle.setText("昵称修改");
		tvNickName=(TextView)findViewById(R.id.tv_nickname);
		btnXiuGai=(Button)findViewById(R.id.btn_xiugai);
	}

}
