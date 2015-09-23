package com.itboye.banma.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itboye.banma.R;
import com.itboye.banma.adapter.MailingAddressAdapter;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.entity.MailingAdress;

public class MailingAddressActivity extends Activity implements
		StrUIDataListener, OnClickListener {
	private AppContext appContext;
	private ImageView back;
	private TextView title;
	private ListView address_list;
	private Button add_address;
	private LinearLayout list_Layout;
	private LinearLayout wait_ll;
	private LinearLayout loading_ll;
	private ImageView retry_img;
	private MailingAddressAdapter adapter;
	private Intent intent;
	private Boolean YesOrNo; // 是否连接网络
	private StrVolleyInterface strnetworkHelper;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mailing_address);
		appContext = (AppContext) getApplication();
		strnetworkHelper = new StrVolleyInterface(MailingAddressActivity.this);
		strnetworkHelper.setStrUIDataListener(MailingAddressActivity.this);
		init();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
		}
		return false;
	}

	private void init() {
		list_Layout = (LinearLayout) findViewById(R.id.list_Layout);
		// 旋转等待页
		wait_ll = (LinearLayout) findViewById(R.id.wait_ll);
		retry_img = (ImageView) findViewById(R.id.retry_img);
		loading_ll = (LinearLayout) findViewById(R.id.loading_ll);
		wait_ll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (retry_img.getVisibility() == View.VISIBLE) {
					wait_ll.setVisibility(View.VISIBLE);
					retry_img.setVisibility(View.GONE);
					loading_ll.setVisibility(View.VISIBLE);
					list_Layout.setVisibility(View.GONE);
					load_data();
					
				}
			}
		});

		back = (ImageView) findViewById(R.id.iv_back);
		title = (TextView) findViewById(R.id.title);
		address_list = (ListView) findViewById(R.id.address_list);
		add_address = (Button) findViewById(R.id.add_address);
		title.setText(R.string.manage_address);
		back.setOnClickListener(this);
		add_address.setOnClickListener(this);

		wait_ll.setVisibility(View.VISIBLE);
		retry_img.setVisibility(View.GONE);
		loading_ll.setVisibility(View.VISIBLE);
		list_Layout.setVisibility(View.GONE);
		
		load_data();

	}

	/**
	 * 加载数据
	 */
	public void load_data() {
		try {
			YesOrNo = appContext.getAddressList(MailingAddressActivity.this,
					strnetworkHelper);
			if(!YesOrNo){   //如果没联网
				wait_ll.setVisibility(View.VISIBLE);
				retry_img.setVisibility(View.VISIBLE);
				loading_ll.setVisibility(View.GONE);
				list_Layout.setVisibility(View.GONE);
				Toast.makeText(MailingAddressActivity.this, "请检查网络连接", Toast.LENGTH_LONG)
				.show();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_address:
			intent = new Intent(this, AddAddressActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		case R.id.iv_back:
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			break;
		default:
			break;
		}
	}

	@Override
	public void onErrorHappened(VolleyError error) {
		Toast.makeText(MailingAddressActivity.this, "加载失败", Toast.LENGTH_LONG)
				.show();
		wait_ll.setVisibility(View.VISIBLE);
		retry_img.setVisibility(View.VISIBLE);
		loading_ll.setVisibility(View.GONE);
		list_Layout.setVisibility(View.GONE);
	}

	@Override
	public void onDataChanged(String data) {
		Gson gson = new Gson();
		List<MailingAdress> list = new ArrayList<MailingAdress>();
		JSONObject jsondata;
		try {
			jsondata = new JSONObject(data);

			int code = jsondata.getInt("code");
			if (code == 0) {
				wait_ll.setVisibility(View.GONE);
				retry_img.setVisibility(View.GONE);
				loading_ll.setVisibility(View.GONE);
				list_Layout.setVisibility(View.VISIBLE);
				String addressData = jsondata.getString("data");
				list = gson.fromJson(addressData,
						new TypeToken<List<MailingAdress>>() {
						}.getType());
				adapter = new MailingAddressAdapter(
						MailingAddressActivity.this, list);
				address_list.setAdapter(adapter);
			} else {
				Toast.makeText(MailingAddressActivity.this, "加载失败",
						Toast.LENGTH_LONG).show();
				wait_ll.setVisibility(View.VISIBLE);
				retry_img.setVisibility(View.VISIBLE);
				loading_ll.setVisibility(View.GONE);
				list_Layout.setVisibility(View.GONE);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
