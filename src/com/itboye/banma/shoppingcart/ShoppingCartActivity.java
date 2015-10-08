package com.itboye.banma.shoppingcart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.android.volley.VolleyError;
import com.itboye.banma.R;
import com.itboye.banma.activities.CenterActivity;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface; 
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
import com.itboye.banma.shoppingcart.Adapter_ListView_cart.onCheckedChanged;

public class ShoppingCartActivity  extends Activity implements StrUIDataListener,onCheckedChanged,OnClickListener{
	private AppContext appContext;
	private StrVolleyInterface networkHelper;
	
	private ImageView ivBack;//返回按钮
	private TextView tv_goShop, tv_cart_Allprice, tv_cart_buy_Ordel;
	private LinearLayout ll_cart;
	private ListView listView_cart;
	private CheckBox cb_cart_all;
	private Adapter_ListView_cart adapter;
	private String str_del = "结算(0)";
	private boolean[] is_choice;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopcart);
		is_choice=new boolean[Constant.arrayList_cart.size()];
		initView();
		appContext = (AppContext) getApplication();
		networkHelper = new StrVolleyInterface(this);
		networkHelper.setStrUIDataListener(this);
	}

	private void initView() {
		// TODO Auto-generated method stub
		ivBack=(ImageView)findViewById(R.id.iv_back);
		ivBack.setOnClickListener(this);
		tv_goShop = (TextView) this.findViewById(R.id.tv_goShop);
		tv_cart_Allprice = (TextView) this.findViewById(R.id.tv_cart_Allprice);
		tv_cart_buy_Ordel = (TextView) this.findViewById(R.id.tv_cart_buy_or_del);
		tv_cart_buy_Ordel.setText(str_del);
		cb_cart_all = (CheckBox) this.findViewById(R.id.cb_cart_all);

		  cb_cart_all.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				/*
				 * 判断一：（全选按钮选中）全选按钮是否选择，如果选择，那么列表每一行都选中
				 * 判断二：（全选按钮取消）当取消全选按钮时，会有两种情况
				 * ，第一：主动点击全选按钮，此时直接取消列表所有的选中状态，第二：取消列表某一行，导致全选取消，此时列表其他行仍然是选中
				 * 
				 * 判断二的分析：（主动取消）判断列表每一行的选中状态，如果全部都是选中状态，那么（列表选中数=列表总数），此时属于主动取消，
				 * 则取消所有行的选中状态，反之（被动取消）则不响应
				 */

				// 记录列表每一行的选中状态数量
				int isChoice_all = 0;
				if (arg1) {
					// 设置全选
					for (int i = 0; i < Constant.arrayList_cart.size(); i++) {
						// 如果选中了全选，那么就将列表的每一行都选中
						((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.cb_choice)).setChecked(true);
					}
				} else {
					// 设置全部取消
					for (int i = 0; i < Constant.arrayList_cart.size(); i++) {
						// 判断列表每一行是否处于选中状态，如果处于选中状态，则计数+1
						if (((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.cb_choice)).isChecked()) {
							// 计算出列表选中状态的数量
							isChoice_all += 1;
						}
					}
					// 判断列表选中数是否等于列表的总数，如果等于，那么就需要执行全部取消操作
					if (isChoice_all == Constant.arrayList_cart.size()) {
						// 如果没有选中了全选，那么就将列表的每一行都不选
						for (int i = 0; i < Constant.arrayList_cart.size(); i++) {
							// 列表每一行都取消
							((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.cb_choice)).setChecked(false);
						}
					}
				}
			}
		});
		
		

		ll_cart = (LinearLayout) this.findViewById(R.id.ll_cart);
		listView_cart = (ListView) this.findViewById(R.id.listView_cart);
		// 如果购物车中有数据，那么就显示数据，否则显示默认界面
		if (Constant.arrayList_cart != null && Constant.arrayList_cart.size() != 0) {
			adapter = new Adapter_ListView_cart(ShoppingCartActivity.this, Constant.arrayList_cart);
			adapter.setOnCheckedChanged(this);
			listView_cart.setAdapter(adapter);
			ll_cart.setVisibility(View.GONE);
		} else {
			ll_cart.setVisibility(View.VISIBLE);
		}

		listView_cart.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(ShoppingCartActivity.this, CenterActivity.class);
				startActivity(intent);
			}
		});
		
		
		
		tv_cart_buy_Ordel.setOnClickListener(this);
		tv_goShop.setOnClickListener(this);
	}


	/*@Override
	public void onAttach(Activity activity) {
		btnCallListener = (IBtnCallListener) activity;
		super.onAttach(activity);
	}*/

	/*@Override
	public void transferMsg() {
		// 这里响应在FragmentActivity中的控件交互
		System.out.println("由Activity中传送来的消息");
	}*/

	/** adapter的回调函数，当点击CheckBox的时候传递点击位置和checkBox的状态 */
	@Override
	public void getChoiceData(int position, boolean isChoice) {
		//得到点击的哪一行
		if (isChoice) {
			if (Constant.arrayList_cart.size()!=0) {
				//49表示商品的价格，这里偷了下懒，没有去动态获取商品价格
				Constant.Allprice_cart += Float.valueOf(Constant.arrayList_cart.get(position).get("num").toString())*49;
			}
		} else {
			if (Constant.arrayList_cart.size()!=0) {
				Constant.Allprice_cart -= Float.valueOf(Constant.arrayList_cart.get(position).get("num").toString())*49;
			}
		}
		// 记录列表处于选中状态的数量
		int num_choice = 0;
		for (int i = 0; i < Constant.arrayList_cart.size(); i++) {
			// 判断列表中每一行的选中状态，如果是选中，计数加1
			if (null!=listView_cart.getChildAt(i)&&((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.cb_choice)).isChecked()) {
				// 列表处于选中状态的数量+1
				num_choice += 1;
				is_choice[i]=true;
			}
		}
		// 判断列表中的CheckBox是否全部选择
		if (num_choice == Constant.arrayList_cart.size()) {
			// 如果选中的状态数量=列表的总数量，那么就将全选设置为选中
			cb_cart_all.setChecked(true);
		} else {
			// 如果选中的状态数量！=列表的总数量，那么就将全选设置为取消
			cb_cart_all.setChecked(false);
		}

		tv_cart_Allprice.setText("合计：￥"+Constant.Allprice_cart + "");

		System.out.println("选择的位置--->"+position);
		
	}

	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDataChanged(String data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId() ){
		case R.id.iv_back:
			finish();
			break;

		default:
			break;
		}
	}
}
