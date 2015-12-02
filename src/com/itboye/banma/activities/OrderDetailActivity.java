package com.itboye.banma.activities;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import android.widget.Button;
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
import com.itboye.banma.app.Constant;
import com.itboye.banma.entity.OrderDetail;
import com.itboye.banma.view.BabyPopWindow.OnItemClickListener;
import com.itboye.banma.view.MyListView;
import com.umeng.analytics.MobclickAgent;

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
	private TextView order_price;
	private TextView order_all_price;
	private MyListView order_list;
	private TextView store_name;
	private TextView order_code_text;
	private TextView createtime_text;
	private TextView discount_money;
	private TextView weight;
	private TextView post_price;
	private TextView tariff;
	private LinearLayout order_flex;
	private ImageView img_flex;
	private TextView order_state;
	private Button confirm;
	
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
		order_price = (TextView) findViewById(R.id.order_price);
		order_list = (MyListView) findViewById(R.id.order_list);
		store_name = (TextView) findViewById(R.id.store_name);
		order_code_text = (TextView) findViewById(R.id.order_code_text);
		createtime_text = (TextView) findViewById(R.id.createtime_text);
		order_state =  (TextView) findViewById(R.id.order_state);
		order_all_price =  (TextView) findViewById(R.id.order_all_price);
		discount_money = (TextView) findViewById(R.id.discount_money);
		weight = (TextView) findViewById(R.id.weight);
		post_price = (TextView) findViewById(R.id.post_price);
		tariff = (TextView) findViewById(R.id.tariff);
		confirm = (Button) findViewById(R.id.confirm);
		confirm.setOnClickListener(this);
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
				byte[] bytes = jsonObject.getString("data").getBytes(); 
				String newStr = new String(bytes , "UTF-8"); 
				Toast.makeText(OrderDetailActivity.this, ""+newStr, Toast.LENGTH_SHORT)
				.show();
			}
		}catch (Exception e1) {
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
		store_name.setText(orderDetail.getStore_name());
		order_price.setText("￥"+orderDetail.getPrice());
		order_code_text.setText(orderDetail.getOrder_code());
		//时间戳转化为时间格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = sdf.format(new Date(Long.valueOf(orderDetail.getCreatetime())*1000));
		createtime_text.setText(date);
		post_price.setText("￥"+orderDetail.getPost_price());
		tariff.setText("￥"+orderDetail.getTax_amount());
		double weight_kg = 0.0;
		for(int i=0; i<orderDetail.getItems().size(); i++){
			weight_kg += orderDetail.getItems().get(i).getWeight() * Integer.valueOf(orderDetail.getItems().get(i).getCount());
		}
		weight.setText(weight_kg/1000 +"kg");
		OrderListItemAdapter adapter = new OrderListItemAdapter(OrderDetailActivity.this, orderDetail.getItems());
		order_list.setAdapter(adapter);
		order_all_price.setText("￥"+orderDetail.getPrice());
		discount_money.setText("￥"+orderDetail.getDiscount_money()); 
		switch (Integer.parseInt(orderDetail.getOrder_status())) {
		case Constant.ORDER_CANCEL:		//取消或交易关闭
			order_state.setText("["+Constant.getOrderStatus(Constant.ORDER_CANCEL)+"状态]");  //[交易关闭]
			break;
			
		case Constant.ORDER_TOBE_CONFIRMED:		//待确认状态
			if(Integer.parseInt(orderDetail.getPay_status()) == Constant.ORDER_PAID ){ //已支付
				order_state.setText("["+Constant.getOrderStatus(Constant.ORDER_TOBE_CONFIRMED)+"状态]");  //[交易关闭]
				
			}else if(Integer.parseInt(orderDetail.getPay_status()) == Constant.ORDER_TOBE_PAID ){ //待支付
				order_state.setText("[待付款]");  //[待付款]
			}
			break;
		
		case Constant.ORDER_TOBE_SHIPPED:		//待发货状态
			if(Integer.parseInt(orderDetail.getPay_status()) == Constant.ORDER_PAID ){ //已支付
				order_state.setText("["+Constant.getOrderStatus(Constant.ORDER_TOBE_SHIPPED)+"状态]");  //[代发货]
			}
			break;
			
		case Constant.ORDER_SHIPPED:		//已发货
			if(Integer.parseInt(orderDetail.getPay_status()) == Constant.ORDER_PAID ){ //已支付
				order_state.setText("["+Constant.getOrderStatus(Constant.ORDER_SHIPPED)+"状态]");  //[待收货]
				confirm.setVisibility(View.VISIBLE);
			}
			break;
			
		case Constant.ORDER_RECEIPT_OF_GOODS:		//已收货
			if(Integer.parseInt(orderDetail.getPay_status()) == Constant.ORDER_PAID ){ //已支付
				order_state.setText("["+Constant.getOrderStatus(Constant.ORDER_RECEIPT_OF_GOODS)+"状态]");  //[已收货]
				
			}
			break;
		default:
			break;
		}
		
		
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
		case R.id.confirm:
	
			Intent intent = new Intent(OrderDetailActivity.this, ActivityLogistics.class);
			intent.putExtra("order_code", orderDetail.getOrder_code());
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

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
