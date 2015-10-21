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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itboye.banma.adapter.OrderListAdapter;
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
	private Double priceAll;
	private int numAll;
	private ImageView top_back;
	private TextView top_title;
	private ListView orderListView;
	/*private TextView pop_add;
	private TextView pop_reduce;
	private TextView pop_num;*/
	private TextView all_num;
	private TextView all_price;
	private TextView order_all_price;
	private TextView adr_name;
	private TextView adr_phone;
	private TextView adr_address;
	private LinearLayout select_adress_layout;
	private MailingAdress address = null;
	private Boolean YesOrNo; // 是否连接网络
	private StrVolleyInterface strnetworkHelper;
	private List<SkuStandard> list = new ArrayList<SkuStandard>();
	private int state = -1;
	private Button btn_regisited;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_order);
		initData();
		initView();
	}

	@SuppressWarnings("unchecked")
	private void initData() {
		appContext = (AppContext) getApplication();
		strnetworkHelper = new StrVolleyInterface(ConfirmOrderActivity.this);
		strnetworkHelper.setStrUIDataListener(ConfirmOrderActivity.this);
		Intent intent = getIntent();
		list = (List<SkuStandard>) intent.getSerializableExtra("SkuStandardList");
		/*state = intent.getIntExtra("state", -1);
		if(state == 0){
			//表示是购买页面传过来的，显示数量添加按钮
		}*/
		priceAll = 0.0;
		numAll = 0;
		for(int i=0; i<list.size(); i++){
			priceAll += list.get(i).getPrice() * Integer.valueOf(list.get(i).getNum());
			numAll += Integer.parseInt(list.get(i).getNum());
		}
		
	}

	private void initView() {
		btn_regisited=(Button)findViewById(R.id.registered);
		select_adress_layout = (LinearLayout) findViewById(R.id.select_adress_layout);
		orderListView = (ListView) findViewById(R.id.order_list);
		top_back = (ImageView) findViewById(R.id.iv_back);
		top_title = (TextView) findViewById(R.id.title);
		adr_name = (TextView) findViewById(R.id.adr_name);
		adr_phone = (TextView) findViewById(R.id.adr_phone);
		adr_address = (TextView) findViewById(R.id.adr_address);
		/*pop_add = (TextView) findViewById(R.id.pop_add);
		pop_reduce = (TextView) findViewById(R.id.pop_reduce);
		pop_num = (TextView) findViewById(R.id.pop_num);*/
		all_num = (TextView) findViewById(R.id.all_num);
		all_price = (TextView) findViewById(R.id.all_price);
		order_all_price = (TextView) findViewById(R.id.order_all_price);
		select_adress_layout.setOnClickListener(this);
		top_back.setOnClickListener(this);
		/*pop_add.setOnClickListener(this);
		pop_reduce.setOnClickListener(this);*/
		
		OrderListAdapter adapter = new OrderListAdapter(ConfirmOrderActivity.this, list);
		orderListView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(orderListView);
		
		/*pop_num.setText(""+numAll);*/
		all_num.setText(""+numAll);
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
	
	/*
	 * 动态设置ListView组建的高度
	 * 
	 * */
	public void setListViewHeightBasedOnChildren(ListView listView) {
	      
	      ListAdapter listAdapter = listView.getAdapter();
	      
	      if (listAdapter == null) {
	      
	       return;
	      
	      }
	      
	      int totalHeight = 0;
	      
	      for (int i = 0; i < listAdapter.getCount(); i++) {
	      
	       View listItem = listAdapter.getView(i, null, listView);
	      
	       listItem.measure(0, 0);
	      
	       totalHeight += listItem.getMeasuredHeight();
	      
	      }
	      
	      ViewGroup.LayoutParams params = listView.getLayoutParams();
	      
	      params.height = totalHeight
	      
	        + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
	      
	      // params.height += 5;// if without this statement,the listview will be
	      
	      // a
	      
	      // little short
	      
	      // listView.getDividerHeight()获取子项间分隔符占用的高度
	      
	      // params.height最后得到整个ListView完整显示需要的高度
	      
	      listView.setLayoutParams(params);
	      
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
			if(address == null){
				intent.putExtra("addressId", -1);
			}else{
				intent.putExtra("addressId", address.getId());
			}
			
			startActivityForResult(intent, 2000);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		case R.id.registered:
			//完成订单提交，成功后启动支付宝付款
			
			break;
		/*case R.id.pop_add:
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
			break;*/
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
				if (addresslist.size() > 0) {
					// showListView(addresslist);
					address = addresslist.get(0);
					adr_name.setText(address.getContactname());
					adr_phone.setText(address.getMobile());
					adr_address.setText(address.getProvince()
							+ address.getCity() + address.getArea()
							+ address.getDetailinfo());

				}
				else {
					Toast.makeText(ConfirmOrderActivity.this, "请添加地址",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent = new Intent(this, SelectOrderAddressActivity.class);
					intent.putExtra("addressId", -1);
					startActivityForResult(intent, 2000);
					overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
				}

			} else {
				Toast.makeText(ConfirmOrderActivity.this, "地址加载异常",
						Toast.LENGTH_SHORT).show();
			}

		} catch (JSONException e) {
			e.printStackTrace();
			Toast.makeText(ConfirmOrderActivity.this, "地址加载失败",
					Toast.LENGTH_SHORT).show();
		}
	}
}
