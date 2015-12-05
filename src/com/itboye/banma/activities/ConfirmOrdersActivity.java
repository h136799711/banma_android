package com.itboye.banma.activities;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
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
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.itboye.banma.adapter.ConfirmOrderListAdapter;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
import com.itboye.banma.entity.MailingAdress;
import com.itboye.banma.entity.OrderPayData;
import com.itboye.banma.entity.SkuStandard;
import com.itboye.banma.payalipay.PayAlipay;
import com.itboye.banma.utils.BitmapCache;
import com.umeng.analytics.MobclickAgent;

public class ConfirmOrdersActivity extends Activity implements OnClickListener,
StrUIDataListener {
	private LinearLayout ll_youhui;
	private LinearLayout ll_beizhu;
	
	private final int ADDORREDUCE = 1;
	private final int ADDRESS = 1;
	private final int ORDER = 2;
	private AppContext appContext; 
	private Double priceAll;
	private Double tax_moneyAll;
	private int numAll;
	private ImageView top_back;
	private TextView top_title;
	private ListView orderListView;
	private TextView all_num;
	private TextView all_price;
	private TextView order_all_price;
	private LinearLayout order_flex;
	private ImageView img_flex;
	private TextView adr_name;
	private TextView adr_phone;
	private TextView adr_address;
	private TextView discount_code;
	private TextView privilege_money;
	private TextView tax_money;
	private LinearLayout select_adress_layout;
	private LinearLayout add_adress_layout;
	private MailingAdress address = null;
	private Boolean YesOrNo; // 是否连接网络
	private StrVolleyInterface strnetworkHelper;
	private List<SkuStandard> list = new ArrayList<SkuStandard>();
	private int state = -1;
	private Button confirm;
	private PayAlipay payAlipay;
	private ProgressDialog dialog;
	private String cart_ids;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_order);
		initData();
		initView();
	}

	@SuppressWarnings("unchecked")
	private void initData() {
		dialog = new ProgressDialog(ConfirmOrdersActivity.this);
		dialog.setMessage("加载...");
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.show();
		appContext = (AppContext) getApplication();
		strnetworkHelper = new StrVolleyInterface(ConfirmOrdersActivity.this);
		strnetworkHelper.setStrUIDataListener(ConfirmOrdersActivity.this);
		Intent intent = getIntent();
		list = (List<SkuStandard>) intent
				.getSerializableExtra("SkuStandardList");

		priceAll = 0.0;
		numAll = 0;
		tax_moneyAll = 0.0;
		for (int i = 0; i < list.size(); i++) {
			priceAll += list.get(i).getPrice()
					* Integer.valueOf(list.get(i).getNum());
			numAll += Integer.parseInt(list.get(i).getNum());
			tax_moneyAll += list.get(i).getPrice()
					* Integer.valueOf(list.get(i).getNum())
					*Double.valueOf(list.get(i).getTaxrate());
			if(i==0){
				cart_ids = ""+list.get(i).getId();
			}else{
				cart_ids += ","+list.get(i).getId();
			}
		}
		if(priceAll >= 500){      //大于500元收取关税
			priceAll += tax_moneyAll;
		}else{
			tax_moneyAll = 0.0;
		}
	}

	private void initView() {
		confirm = (Button) findViewById(R.id.confirm);
		select_adress_layout = (LinearLayout) findViewById(R.id.select_adress_layout);
		add_adress_layout = (LinearLayout) findViewById(R.id.add_adress_layout);
		order_flex = (LinearLayout) findViewById(R.id.order_flex);
		img_flex = (ImageView) findViewById(R.id.img_flex);
		orderListView = (ListView) findViewById(R.id.order_list);
		top_back = (ImageView) findViewById(R.id.iv_back);
		top_title = (TextView) findViewById(R.id.title);
		adr_name = (TextView) findViewById(R.id.adr_name);
		tax_money = (TextView) findViewById(R.id.tax_money);
		adr_phone = (TextView) findViewById(R.id.adr_phone);
		adr_address = (TextView) findViewById(R.id.adr_address);
		discount_code = (TextView) findViewById(R.id.discount_code);
		privilege_money = (TextView) findViewById(R.id.privilege_money);
		ll_beizhu=(LinearLayout)findViewById(R.id.ll_beizhu);
		ll_beizhu.setOnClickListener(this);
		ll_youhui=(LinearLayout)findViewById(R.id.ll_youhui);
		ll_youhui.setOnClickListener(this);
		/*
		 * pop_add = (TextView) findViewById(R.id.pop_add); pop_reduce =
		 * (TextView) findViewById(R.id.pop_reduce); pop_num = (TextView)
		 * findViewById(R.id.pop_num);
		 */
		adr_address = (TextView) findViewById(R.id.adr_address); 
		/*pop_add = (TextView) findViewById(R.id.pop_add);
		pop_reduce = (TextView) findViewById(R.id.pop_reduce);
		pop_num = (TextView) findViewById(R.id.pop_num);*/
		all_num = (TextView) findViewById(R.id.all_num);
		all_price = (TextView) findViewById(R.id.all_price);
		order_all_price = (TextView) findViewById(R.id.order_all_price);
		select_adress_layout.setOnClickListener(this);
		add_adress_layout.setOnClickListener(this);
		top_back.setOnClickListener(this);
		order_flex.setOnClickListener(this);
		confirm.setOnClickListener(this);
	
		ConfirmOrderListAdapter adapter = new ConfirmOrderListAdapter(
				ConfirmOrdersActivity.this, list);
		orderListView.setAdapter(adapter);
		setListViewHeightBasedOnChildren(orderListView);

		/* pop_num.setText(""+numAll); */
		all_num.setText("" + numAll);
		all_price.setText("￥" + priceAll);
		DecimalFormat df=new DecimalFormat(".##");
		Double discount_price = (Double) (priceAll * Double.valueOf(appContext.getDiscount_ratio()));
		discount_code.setText(appContext.getIdcode());
		privilege_money.setText("￥"+df.format(discount_price));
		//all_price.setText("￥"+(Double.valueOf(priceAll) - discount_price));
		
		order_all_price.setText("￥"+df.format((Double.valueOf(priceAll) - discount_price)));
		
		//order_all_price.setText("￥" + priceAll);
		top_title.setText(string.confirm_order);
		tax_money.setText("￥" + df.format(tax_moneyAll));

		load_data();
	}

	/**
	 * 加载数据
	 */
	public void load_data() {
		try {
			state = ADDRESS;
			YesOrNo = appContext.getAddressList(ConfirmOrdersActivity.this,
					strnetworkHelper);

			if (!YesOrNo) { // 如果没联网
				Toast.makeText(ConfirmOrdersActivity.this, "请检查网络连接",
						Toast.LENGTH_SHORT).show();
				state = -1;
			}
		} catch (Exception e) {
			state = -1;
			e.printStackTrace();
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

		}else if(requestCode == 2100 && resultCode == 1001){
		
			load_data();
		}else if(requestCode==1005) {
			//在这里处理优惠的问题
			//处理方法，比较store_id,计算折扣
			if (data!=null) {
				String  discount=data.getStringExtra("discount_ratio");
				String store_id=data.getStringExtra("store_id");
				String  idcode=data.getStringExtra("idcode");
				Double discount_price = (Double) (priceAll * Double.valueOf(discount));
				discount_code.setText(idcode);
				privilege_money.setText("￥"+discount_price);
				//all_price.setText("￥"+(Double.valueOf(priceAll) - discount_price));
				order_all_price.setText("￥"+(Double.valueOf(priceAll) - discount_price));
				
			}
		
		}

	}

	/*
	 * 动态设置ListView组建的高度
	 */
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
		System.out.println("高度"+totalHeight+"高度");
		Log.v("height", totalHeight+"");
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
		Intent intent;
		switch (v.getId()) {
		case R.id.ll_youhui:
			intent = new Intent(this, YouHuiActivity .class);
			startActivityForResult(intent,1005);
			break;
			
		case R.id.ll_beizhu:
			startActivity(new Intent(ConfirmOrdersActivity.this,SuggestActivity.class));
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		case R.id.iv_back:
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			break;
		case R.id.select_adress_layout:
			//用户有收货地址
			intent = new Intent();
			intent = new Intent(this, SelectOrderAddressActivity.class);
			intent.putExtra("addressId", address.getId());
			startActivityForResult(intent, 2000);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);


			break;
		case R.id.add_adress_layout:
			intent = new Intent();
			intent = new Intent(this, AddAddressActivity.class);
			intent.putExtra("add_state", Constant.CONORDER_ADDADR);
			startActivityForResult(intent, 2100);
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		case R.id.confirm:
			// 添加订单
			ordersAdd(appContext.getLoginUid(), cart_ids, discount_code.getText().toString(), null,address.getId(),2,1);
			dialog.setMessage("提交订单...");
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.show();
			
			break;
		case R.id.order_flex:
			if (orderListView.getVisibility() == View.VISIBLE) {
				orderListView.setVisibility(View.GONE);
				img_flex.setBackgroundResource(R.drawable.arrow_up);
			} else if (orderListView.getVisibility() == View.GONE) {
				orderListView.setVisibility(View.VISIBLE);
				img_flex.setBackgroundResource(R.drawable.arrow_down);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 添加订单
	 * 
	 * @return
	 */
	public Boolean ordersAdd(int uid, String cart_ids, String idcode, String note,
			int addr_id, int from, int payType) {
		try {
			state = ORDER;
			YesOrNo = appContext.ordersAdd(ConfirmOrdersActivity.this, uid,
					cart_ids, idcode, note, addr_id, from, payType,
					strnetworkHelper);

			if (!YesOrNo) { // 如果没联网
				Toast.makeText(ConfirmOrdersActivity.this, "请检查网络连接",
						Toast.LENGTH_SHORT).show();
				state = -1;
			}
		} catch (Exception e) {
			state = -1;
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void onErrorHappened(VolleyError error) {
		state = -1;
		Toast.makeText(ConfirmOrdersActivity.this, "加载失败"+error, Toast.LENGTH_SHORT)
		.show();
		dialog.dismiss();
	}

	@Override
	public void onDataChanged(String data) {
		Gson gson = new Gson();
		List<MailingAdress> addresslist = new ArrayList<MailingAdress>();
		JSONObject jsondata;
		try {
			jsondata = new JSONObject(data);

			int code = jsondata.getInt("code");

			if (state == ADDRESS) { // 地址的请求相应
				dialog.dismiss();
				if (code == 0) {
					String addressData = jsondata.getString("data");
					addresslist = gson.fromJson(addressData,
							new TypeToken<List<MailingAdress>>() {
					}.getType());
					if (addresslist.size() > 0) {
						// showListView(addresslist);
						add_adress_layout.setVisibility(View.GONE);
						select_adress_layout.setVisibility(View.VISIBLE);
						address = addresslist.get(0);
						adr_name.setText(address.getContactname());
						adr_phone.setText(address.getMobile());
						adr_address.setText(address.getProvince()
								+ address.getCity() + address.getArea()
								+ address.getDetailinfo());

					} else {
						Toast.makeText(ConfirmOrdersActivity.this, "请添加地址",
								Toast.LENGTH_SHORT).show();
						add_adress_layout.setVisibility(View.VISIBLE);
						select_adress_layout.setVisibility(View.GONE);

						/*Intent intent = new Intent();
						intent = new Intent(this,
								SelectOrderAddressActivity.class);
						intent.putExtra("addressId", -1);
						startActivityForResult(intent, 2000);
						overridePendingTransition(R.anim.in_from_right,
								R.anim.out_to_left);*/
					}

				} else {
					Toast.makeText(ConfirmOrdersActivity.this, "地址加载异常",
							Toast.LENGTH_SHORT).show();
				}
				state = -1;
			} else if (state == ORDER) {
				String content = jsondata.getString("data");
				if (code == 0) {
					OrderPayData orderPayData;
					/*Toast.makeText(ConfirmOrdersActivity.this, "订单添加成功"+data,
							Toast.LENGTH_SHORT).show();*/
					orderPayData = gson.fromJson(content, OrderPayData.class);
					// 完成订单提交，成功后启动支付宝付款
					dialog.dismiss();
					payAlipay = new PayAlipay(ConfirmOrdersActivity.this);
					payAlipay.pay(null, orderPayData);
						
					
				}else{
					byte[] bytes = content.getBytes();
					String newStr = null;
					try {
						newStr = new String(bytes , "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} 
					Toast.makeText(ConfirmOrdersActivity.this, "订单添加异常:"+newStr,
							Toast.LENGTH_SHORT).show();
					dialog.dismiss();
				}
				state = -1;
			}

		} catch (JSONException e) {
			state = -1;
			e.printStackTrace();
			Toast.makeText(ConfirmOrdersActivity.this, "地址加载失败",
					Toast.LENGTH_SHORT).show();
		}
	}
}
