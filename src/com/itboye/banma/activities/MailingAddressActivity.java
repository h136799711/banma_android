package com.itboye.banma.activities;

import java.util.ArrayList;
import java.util.List;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.itboye.banma.R;
import com.itboye.banma.adapter.MailingAddressAdapter;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.entity.MailingAdress;

public class MailingAddressActivity extends Activity implements StrUIDataListener, OnClickListener {
	private AppContext appContext;
	private ImageView back;
	private TextView title;
	private ListView address_list;
	private Button add_address;
	private MailingAddressAdapter adapter;
	private Intent intent;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mailing_address);
		appContext = (AppContext) getApplication();
		init();
	}
	
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event)  
    {  
        if (keyCode == KeyEvent.KEYCODE_BACK )  
        {  
        	finish();
			overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
        }
        return false;
    }

	private void init() {
		back = (ImageView) findViewById(R.id.iv_back);
		title = (TextView) findViewById(R.id.title);
		address_list = (ListView) findViewById(R.id.address_list);
		add_address = (Button) findViewById(R.id.add_address);
		title.setText(R.string.manage_address);
		back.setOnClickListener(this);
		add_address.setOnClickListener(this);
		
		List<MailingAdress> list = new ArrayList<MailingAdress>();
		MailingAdress data1 = new MailingAdress("中国", "浙江", "杭州市", "江干区", "下沙高教园", "高健", "18358495623", null, "259651");
		MailingAdress data2 = new MailingAdress("中国", "河南", "郑州", "江的区", "河南师范大学", "高健", "18851236969", null, "289524");
		MailingAdress data3 = new MailingAdress("中国", "山西", "西安市", "临潼区", "西安科技技大学", "何洁", "15236212191", null, "244557");
		list.add(data1);
		list.add(data2);
		list.add(data3);
		adapter = new MailingAddressAdapter(MailingAddressActivity.this, list);
		address_list.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_address:
			intent = new Intent(this, AddAddressActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right,
					R.anim.out_to_left);
			break;
		case R.id.iv_back:
			finish();
			overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
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
		
	}

	
}
