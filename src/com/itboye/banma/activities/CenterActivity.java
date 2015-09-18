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
import android.widget.TextView;

public class CenterActivity extends Activity {
	ImageButton itnBackCenter;//返回按钮
	ImageView ivPersonheadFail;//默认没有登陆时的头像
	TextView tvCheckList;//查看全部订单
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
				
				//添加改变头像逻辑
			}
	});
    }
	private void initId(CenterActivity centerActivity) {
		// TODO Auto-generated method stub
		itnBackCenter=(ImageButton)findViewById(R.id.btn_backcenter);
		ivPersonheadFail=(ImageView)findViewById(R.id.iv_personheadfail);
		tvCheckList=(TextView)findViewById(R.id.tv_check_list);
	}
    
}
