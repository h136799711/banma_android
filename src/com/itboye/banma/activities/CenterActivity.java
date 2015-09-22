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
	ImageView ivBackCenter;//���ذ�ť
	ImageView ivPersonheadFail;//Ĭ��û�е�½ʱ��ͷ��
	TextView tvCheckList;//
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
		ivBackCenter=(ImageView)findViewById(R.id.btn_back);
		ivPersonheadFail=(ImageView)findViewById(R.id.iv_personheadfail);
		tvCheckList=(TextView)findViewById(R.id.tv_check_list);
	}
    
}
