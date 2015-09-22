package com.itboye.banma.activities;

import com.itboye.banma.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CenterActivity extends Activity implements OnClickListener{
	ImageButton itnBackCenter;//
	ImageView ivPersonheadFail;
	TextView tvCheckList;//选择按钮
	ImageView ivBack;//返回按钮
	private RelativeLayout mailing_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);
       initId(this);
       ivPersonheadFail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(CenterActivity.this,LoginActivity.class));
				
			}
	});
    }
	private void initId(CenterActivity centerActivity) {
		// TODO Auto-generated method stub
		ivBack=(ImageView)findViewById(R.id.iv_back);
		ivPersonheadFail=(ImageView)findViewById(R.id.iv_personheadfail);
		tvCheckList=(TextView)findViewById(R.id.tv_check_list);
		
		mailing_address = (RelativeLayout) findViewById(R.id.address);
		mailing_address.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.address:
			Intent intent = new Intent(this, MailingAddressActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right,
					R.anim.out_to_left);
			break;

		default:
			break;
		}
	}
    
}
