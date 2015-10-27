package com.itboye.banma.activities;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.itboye.banma.R;
import com.itboye.banma.adapter.OrderListItemAdapter;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.entity.OrderDetail;
import com.itboye.banma.view.BabyPopWindow.OnItemClickListener;
import com.itboye.banma.view.MyListView;

public class OrderDetailActivity extends Activity implements OnClickListener, StrUIDataListener {
	private AppContext appContext;
	private Boolean YesOrNo; // 是否连接网络
	private ImageView back;
	private TextView title;
	private StrVolleyInterface strnetworkHelper;
	private LinearLayout wait_ll;
	private LinearLayout loading_ll;
	private ImageView retry_img;
	private ScrollView scrollView;
	private LinearLayout bottom;
	private TextView adr_name;
	private TextView adr_phone;
	private TextView adr_address;
	private TextView order_code;
	private TextView order_price;
	private MyListView order_list;
	private TextView order_code_text;
	private TextView createtime_text;
	private LinearLayout order_flex;
	private ImageView img_flex;
	
	private int id;			//order的id
	private OrderDetail orderDetail;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail);
		appContext = (AppContext) getApplication();
		initView();
		init();
	}
	private void initView() {
		scrollView = (ScrollView) findViewById(R.id.scrollView);
		bottom = (LinearLayout) findViewById(R.id.bottom);
		// 旋转等待页
		wait_ll = (LinearLayout) findViewById(R.id.wait_ll);
		retry_img = (ImageView) findViewById(R.id.retry_img);
		loading_ll = (LinearLayout) findViewById(R.id.loading_ll);
		wait_ll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (retry_img.getVisibility() == View.VISIBLE) {			
					iniData();
				}
			}
		});
		back = (ImageView) findViewById(R.id.iv_back);
		title = (TextView) findViewById(R.id.title);
		order_flex = (LinearLayout) findViewById(R.id.order_flex);
		img_flex = (ImageView) findViewById(R.id.img_flex);
		adr_name = (TextView) findViewById(R.id.adr_name);
		adr_phone = (TextView) findViewById(R.id.adr_phone);
		adr_address = (TextView) findViewById(R.id.adr_address);
		order_code = (TextView) findViewById(R.id.order_code);
		order_price = (TextView) findViewById(R.id.order_price);
		order_list = (MyListView) findViewById(R.id.order_list);
		order_code_text = (TextView) findViewById(R.id.order_code_text);
		createtime_text = (TextView) findViewById(R.id.createtime_text);
		order_flex.setOnClickListener(this);
		back.setOnClickListener(this);
		title.setText("订单详情");
		//初始化
		wait_ll.setVisibility(View.VISIBLE);
		retry_img.setVisibility(View.VISIBLE);
		loading_ll.setVisibility(View.GONE);
		scrollView.setVisibility(View.GONE);
		bottom.setVisibility(View.GONE);
	}
	
	private void init() {
		Intent intent = getIntent();
		id = intent.getIntExtra("id", 0);
		strnetworkHelper = new StrVolleyInterface(OrderDetailActivity.this);
		strnetworkHelper.setStrUIDataListener(OrderDetailActivity.this);
		iniData();
	}
	
	protected void iniData() {
		wait_ll.setVisibility(View.VISIBLE);
		retry_img.setVisibility(View.GONE);
		loading_ll.setVisibility(View.VISIBLE);
		scrollView.setVisibility(View.GONE);
		bottom.setVisibility(View.GONE);

		try {
			YesOrNo = appContext.getOrderDetail(OrderDetailActivity.this, id,
					strnetworkHelper);
			if (!YesOrNo) { // 如果没联网
				Toast.makeText(OrderDetailActivity.this, "请检查网络连接", Toast.LENGTH_SHORT)
						.show();
				wait_ll.setVisibility(View.VISIBLE);
				retry_img.setVisibility(View.VISIBLE);
				loading_ll.setVisibility(View.GONE);
				scrollView.setVisibility(View.GONE);
				bottom.setVisibility(View.GONE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(OrderDetailActivity.this, "网络访问错误", Toast.LENGTH_SHORT)
			.show();
			wait_ll.setVisibility(View.VISIBLE);
			retry_img.setVisibility(View.VISIBLE);
			loading_ll.setVisibility(View.GONE);
			scrollView.setVisibility(View.GONE);
			bottom.setVisibility(View.GONE);
		}
	}
	@Override
	public void onErrorHappened(VolleyError error) {
		Toast.makeText(OrderDetailActivity.this, "订单详情数据返回失败"+error, Toast.LENGTH_SHORT)
		.show();
	}
	@Override
	public void onDataChanged(String data) {
		Gson gson = new Gson();
		String content = null;
		JSONObject jsonObject = null;
		int code = -1;
		/*Toast.makeText(OrderDetailActivity.this, "订单详情数据返回成功"+data, Toast.LENGTH_SHORT)
		.show();*/
		try {
			jsonObject = new JSONObject(data);
			code = jsonObject.getInt("code");
			content = jsonObject.getString("data");
			if (code == 0) {
				System.out.println(data);
				orderDetail = gson.fromJson(content, OrderDetail.class);
				showDataView();
				wait_ll.setVisibility(View.GONE);
				retry_img.setVisibility(View.GONE);
				loading_ll.setVisibility(View.GONE);
				scrollView.setVisibility(View.VISIBLE);
				bottom.setVisibility(View.VISIBLE);
			}else{
				wait_ll.setVisibility(View.VISIBLE);
				retry_img.setVisibility(View.VISIBLE);
				loading_ll.setVisibility(View.GONE);
				scrollView.setVisibility(View.GONE);
				bottom.setVisibility(View.GONE);
			}
		}catch (JSONException e1) {
			e1.printStackTrace();
			wait_ll.setVisibility(View.VISIBLE);
			retry_img.setVisibility(View.VISIBLE);
			loading_ll.setVisibility(View.GONE);
			scrollView.setVisibility(View.GONE);
			bottom.setVisibility(View.GONE);
		}
	
	}
	/**
	 * 获取到的数据展示出来
	 */
	private void showDataView() {
		adr_name.setText(orderDetail.getContactname());
		adr_phone.setText(orderDetail.getMobile());
		adr_address.setText(orderDetail.getCountry()+" "+orderDetail.getProvince()
				+" "+orderDetail.getCity()+" "+orderDetail.getArea()+" "+orderDetail.getDetailinfo());
		order_code.setText(orderDetail.getOrder_code());
		order_price.setText("￥"+orderDetail.getPrice());
		order_code_text.setText(orderDetail.getOrder_code());
		createtime_text.setText(orderDetail.getCreatetime());
		OrderListItemAdapter adapter = new OrderListItemAdapter(OrderDetailActivity.this, orderDetail.getItems());
		order_list.setAdapter(adapter);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.order_flex:
			if (order_list.getVisibility() == View.VISIBLE) {
				order_list.setVisibility(View.GONE);
				img_flex.setBackgroundResource(R.drawable.arrow_up);
			} else if (order_list.getVisibility() == View.GONE) {
				order_list.setVisibility(View.VISIBLE);
				img_flex.setBackgroundResource(R.drawable.arrow_down);
			}
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
		}
		return false;
	}
	
}
