package com.itboye.banma.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itboye.banma.R;
import com.itboye.banma.R.string;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.entity.MailingAdress;
import com.itboye.banma.entity.SkuStandard;
import com.itboye.banma.utils.BitmapCache;

public class ConfirmOrderActivity extends Activity implements OnClickListener,
		StrUIDataListener {
	private final int ADDORREDUCE = 1;
	private AppContext appContext;
	private SkuStandard skuStandard;
	private String main_img;
	private String name;
	private Double price;
	private Double priceAll;
	private ImageView top_back;
	private TextView top_title;
	private ImageView order_pic;
	private TextView order_name;
	private TextView order_standard;
	private TextView order_price;
	private TextView order_number;
	private TextView pop_add;
	private TextView pop_reduce;
	private TextView pop_num;
	private TextView all_num;
	private TextView all_price;
	private TextView order_all_price;
	private TextView adr_name;
	private TextView adr_phone;
	private TextView adr_address;
	private LinearLayout select_adress_layout;
	private MailingAdress address;
	private Boolean YesOrNo; // 是否连接网络
	private StrVolleyInterface strnetworkHelper;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_order);
		initData();
		initView();
	}

	private void initData() {
		appContext = (AppContext) getApplication();
		strnetworkHelper = new StrVolleyInterface(ConfirmOrderActivity.this);
		strnetworkHelper.setStrUIDataListener(ConfirmOrderActivity.this);
		Intent intent = getIntent();
		skuStandard = (SkuStandard) intent.getSerializableExtra("SkuStandard");
		main_img = intent.getStringExtra("main_img");
		name = intent.getStringExtra("name");
		// price = intent.getDoubleExtra("price", 100.0);
		price = skuStandard.getPrice();
		priceAll = price * Integer.parseInt(skuStandard.getNum());
		System.out.println("SkuStandard" + skuStandard);
	}

	private void initView() {
		select_adress_layout = (LinearLayout) findViewById(R.id.select_adress_layout);
		top_back = (ImageView) findViewById(R.id.iv_back);
		top_title = (TextView) findViewById(R.id.title);
		adr_name = (TextView) findViewById(R.id.adr_name);
		adr_phone = (TextView) findViewById(R.id.adr_phone);
		adr_address = (TextView) findViewById(R.id.adr_address);
		order_pic = (ImageView) findViewById(R.id.order_pic);
		order_name = (TextView) findViewById(R.id.order_name);
		order_standard = (TextView) findViewById(R.id.order_standard);
		order_price = (TextView) findViewById(R.id.order_price);
		order_number = (TextView) findViewById(R.id.order_number);
		pop_add = (TextView) findViewById(R.id.pop_add);
		pop_reduce = (TextView) findViewById(R.id.pop_reduce);
		pop_num = (TextView) findViewById(R.id.pop_num);
		all_num = (TextView) findViewById(R.id.all_num);
		all_price = (TextView) findViewById(R.id.all_price);
		order_all_price = (TextView) findViewById(R.id.order_all_price);
		select_adress_layout.setOnClickListener(this);
		top_back.setOnClickListener(this);
		pop_add.setOnClickListener(this);
		pop_reduce.setOnClickListener(this);

		ImageLoader imageLoader = new ImageLoader(AppContext.getHttpQueues(),
				new BitmapCache());
		ImageListener listener = ImageLoader.getImageListener(order_pic,
				R.drawable.image_loading, R.drawable.image_load_fail);
		imageLoader.get(main_img, listener, 0, 0);
		order_name.setText(name);
		order_standard.setText(skuStandard.getSku());
		order_price.setText("￥" + skuStandard.getPrice());
		// order_number.setText("×"+skuStandard.getNum());
		pop_num.setText(skuStandard.getNum());
		all_num.setText(skuStandard.getNum());
		all_price.setText("￥" + priceAll);
		order_all_price.setText("￥" + priceAll);
		top_title.setText(string.confirm_order);
		load_data();
	}

	/**
	 * 加载数据
	 */
	public void load_data() {
		try {
			YesOrNo = appContext.getAddressList(ConfirmOrderActivity.this,
					strnetworkHelper);
			if (!YesOrNo) { // 如果没联网
				Toast.makeText(ConfirmOrderActivity.this, "请检查网络连接",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// 刷新当前界面
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 2000 && resultCode == 2001) {
			int result_value = data.getIntExtra("result", 1);
			if (result_value == 0) {
				address = (MailingAdress) data.getSerializableExtra("address");
				adr_name.setText(address.getContactname());
				adr_phone.setText(address.getMobile());
				adr_address.setText(address.getProvince() + address.getCity()
						+ address.getArea() + address.getDetailinfo());
			}

		}

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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			break;
		case R.id.select_adress_layout:
			Intent intent = new Intent();
			intent = new Intent(this, SelectOrderAddressActivity.class);
			intent.putExtra("addressId", address.getId());
			startActivityForResult(intent, 2000);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		case R.id.pop_add:
			if (!pop_num.getText().toString().equals("50")) {

				String num_add = Integer.valueOf(pop_num.getText().toString())
						+ ADDORREDUCE + "";
				priceAll = Integer.parseInt(num_add) * price;
				skuStandard.setNum(num_add);
				pop_num.setText(num_add);
				all_num.setText(skuStandard.getNum());
				all_price.setText("￥" + priceAll);
				order_all_price.setText("￥" + priceAll);
			} else {
				Toast.makeText(ConfirmOrderActivity.this, "不能超过50",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.pop_reduce:
			if (!pop_num.getText().toString().equals("1")) {
				String num_reduce = Integer.valueOf(pop_num.getText()
						.toString()) - ADDORREDUCE + "";
				priceAll = Integer.parseInt(num_reduce) * price;
				skuStandard.setNum(num_reduce);
				pop_num.setText(num_reduce);
				all_num.setText(skuStandard.getNum());
				all_price.setText("￥" + priceAll);
				order_all_price.setText("￥" + priceAll);
			} else {
				Toast.makeText(ConfirmOrderActivity.this, "已经是最小数量",
						Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onErrorHappened(VolleyError error) {
		Toast.makeText(ConfirmOrderActivity.this, "地址加载失败", Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void onDataChanged(String data) {
		Gson gson = new Gson();
		List<MailingAdress> addresslist = new ArrayList<MailingAdress>();
		JSONObject jsondata;
		try {
			jsondata = new JSONObject(data);

			int code = jsondata.getInt("code");
			if (code == 0) {
				String addressData = jsondata.getString("data");
				addresslist = gson.fromJson(addressData,
						new TypeToken<List<MailingAdress>>() {
						}.getType());
				if (addresslist != null) {
					// showListView(addresslist);
					address = addresslist.get(0);
					adr_name.setText(address.getContactname());
					adr_phone.setText(address.getMobile());
					adr_address.setText(address.getProvince()
							+ address.getCity() + address.getArea()
							+ address.getDetailinfo());

				}

			} else {
				Toast.makeText(ConfirmOrderActivity.this, "加载失败",
						Toast.LENGTH_LONG).show();
			}

		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(ConfirmOrderActivity.this, "地址加载失败",
					Toast.LENGTH_SHORT).show();
		}
	}
}
