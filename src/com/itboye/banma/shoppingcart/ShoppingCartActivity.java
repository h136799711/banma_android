package com.itboye.banma.shoppingcart;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.itboye.banma.R;
import com.itboye.banma.activities.CenterActivity;
import com.itboye.banma.activities.ConfirmOrderActivity;
import com.itboye.banma.api.ApiClient;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface; 
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
import com.itboye.banma.entity.CartList;
import com.itboye.banma.shoppingcart.Adapter_ListView_cart.onAddChanged;
import com.itboye.banma.shoppingcart.Adapter_ListView_cart.onCheckedChanged;
import com.itboye.banma.shoppingcart.Adapter_ListView_cart.onReduceChanged;
import com.itboye.banma.view.MyListView;

public class ShoppingCartActivity  extends Activity implements StrUIDataListener,onCheckedChanged,
OnClickListener,onAddChanged,onReduceChanged{
	private AppContext appContext;
	private StrVolleyInterface networkHelper;
	
	private ImageView ivBack;//返回按钮
	private TextView tv_goShop, tv_cart_Allprice, tv_cart_buy_Ordel;
	private LinearLayout ll_cart;
	private MyListView listView_cart;
	private CheckBox cb_cart_all;
	private Adapter_ListView_cart adapter;
	private String str_del = "结算(0)";
	private TextView tv_title_right;//编辑按钮
	private boolean[] is_choice;
	private CartList[] cartList;//多页查询购物车实体
	private int EditState=1;//标示编辑状态,1标示编辑，2标示完成
	private int RequestState=-1;//区分不同的请求返回 1.购物车删除。2.购物车修改。3.购物车分页查询
												//4.单个查询。5.数量查询6.批量添加接口
	private boolean Is_Internet;//是否联网
	private int AllCount=0;//购物车总价格
	private  ArrayList<HashMap<String, Object>> arrayList_cart=new ArrayList<HashMap<String,Object>>();

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopcart);
		appContext = (AppContext) getApplication();
		networkHelper = new StrVolleyInterface(this);
		networkHelper.setStrUIDataListener(this);
		initData();
		initView();
		tv_cart_buy_Ordel.setOnClickListener(this);
	}

	private void initData() {
		// TODO Auto-generated method stub
		try {
			RequestState=4;
			//Is_Internet=appContext.getCartListByPage(ShoppingCartActivity.this, appContext.getLoginUid(), networkHelper);
			ApiClient.getCartListByPage(this, appContext.getLoginUid(), networkHelper);
				//Toast.makeText(ShoppingCartActivity.this, "请检查网络是否连接",Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initView() {
		// TODO Auto-generated method stub
		tv_title_right=(TextView)findViewById(R.id.tv_title_right);
		tv_title_right.setOnClickListener(this);
		ivBack=(ImageView)findViewById(R.id.iv_back);
		ivBack.setOnClickListener(this);
		tv_goShop = (TextView) this.findViewById(R.id.tv_goShop);
		tv_cart_Allprice = (TextView) this.findViewById(R.id.tv_cart_Allprice);
		tv_cart_buy_Ordel = (TextView) this.findViewById(R.id.tv_cart_buy_or_del);
		tv_cart_buy_Ordel.setText(str_del);
		cb_cart_all = (CheckBox) this.findViewById(R.id.cb_cart_all);
		ll_cart = (LinearLayout) this.findViewById(R.id.ll_cart);
		listView_cart = (MyListView) this.findViewById(R.id.listView_cart);
	}

	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDataChanged(String data) {
		// TODO Auto-generated method stub
		int code=-1;
		JSONObject jsonObject = null;
		try {
			 jsonObject=new JSONObject(data);
			code=jsonObject.getInt("code");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		switch (RequestState) {
		case 2:
			try {
				if (code==0) {
					Log.v("修改购物车", jsonObject.toString());
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		case 4:
			try {				
				if (code==0) {
					JSONArray jsonArray=new JSONArray(jsonObject.getString("data"));
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject temp=(JSONObject) jsonArray.get(i);
						
					//	Gson gson=new Gson();
					//    cartList[i]=gson.fromJson(temp.toString(), CartList.class);
						
						HashMap< String , Object> hashMap=new HashMap<String, Object>();
						hashMap.put("id", temp.getString("id"));
						hashMap.put("name", temp.getString("name"));
						hashMap.put("count", temp.getInt("count"));
						hashMap.put("price",temp.getString("price"));
						hashMap.put("express", temp.getString("express"));
						hashMap.put("sku_id", temp.getString("sku_id"));
						hashMap.put("psku_id", temp.getString("psku_id"));
						hashMap.put("sku_desc", temp.getString("sku_desc"));
						hashMap.put("icon_url", temp.getString("icon_url"));
						arrayList_cart.add(hashMap);				
					}
					updateView();//数据请求成功时刷新页面
					System.out.println(jsonObject.get("data"));
				}
			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			//	Toast.makeText(this, "添加购物车失败", Toast.LENGTH_SHORT).show();
			}
			break;
		case 1:
			try {				
				if (code==0) {
						Log.v("delete","成功");											
					}
				} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
		
	}

	private void updateView() {
		// TODO Auto-generated method stub
		// 如果购物车中有数据，那么就显示数据，否则显示默认界面
		is_choice=new boolean[arrayList_cart.size()];
		if (arrayList_cart != null && arrayList_cart.size() != 0) {
			adapter = new Adapter_ListView_cart(ShoppingCartActivity.this, arrayList_cart);
			adapter.setOnCheckedChanged(this);
			adapter.setOnAddChanged(this);
			adapter.setOnRedChanged(this);
			listView_cart.setAdapter(adapter);
			ll_cart.setVisibility(View.GONE);
		} else {
			ll_cart.setVisibility(View.VISIBLE);
		}
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
				System.out.println(listView_cart.getChildCount()+"数量");
				if (arg1) {
					// 设置全选
					for (int i = 0; i < arrayList_cart.size(); i++) {
						// 如果选中了全选，那么就将列表的每一行都选中
						((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.cb_choice)).setChecked(true);
						
					}
				} else {
					// 设置全部取消
					for (int i = 0; i < arrayList_cart.size(); i++) {
						// 判断列表每一行是否处于选中状态，如果处于选中状态，则计数+1
						if (((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.cb_choice)).isChecked()) {
							// 计算出列表选中状态的数量
							isChoice_all += 1;
						}
					}
					// 判断列表选中数是否等于列表的总数，如果等于，那么就需要执行全部取消操作
					if (isChoice_all == arrayList_cart.size()) {
						// 如果没有选中了全选，那么就将列表的每一行都不选
						for (int i = 0; i < arrayList_cart.size(); i++) {
							// 列表每一行都取消
							((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.cb_choice)).setChecked(false);
						}
					}
				}
			}
		});


//		listView_cart.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//				Intent intent = new Intent(ShoppingCartActivity.this, CenterActivity.class);
//				startActivity(intent);
//			}
//		});
		
		
		
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
			if (arrayList_cart.size()!=0) {
				AllCount += Float.valueOf(arrayList_cart.get(position).get("count").toString())*
						Float.valueOf(arrayList_cart.get(position).get("price").toString());
			}
		} else {
			if (arrayList_cart.size()!=0) {
				AllCount -= Float.valueOf(arrayList_cart.get(position).get("count").toString())*
						Float.valueOf(arrayList_cart.get(position).get("price").toString());
			}
		}
		// 记录列表处于选中状态的数量
		int num_choice = 0;
		for (int i = 0; i < arrayList_cart.size(); i++) {
			is_choice[i]=false;
		}
		for (int i = 0; i < arrayList_cart.size(); i++) {
			// 判断列表中每一行的选中状态，如果是选中，计数加1
			if (null!=listView_cart.getChildAt(i)&&((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.cb_choice)).isChecked()) {
				// 列表处于选中状态的数量+1
				num_choice += 1;
				is_choice[i]=true;
			}
		}
		// 判断列表中的CheckBox是否全部选择
		if (num_choice == arrayList_cart.size()) {
			// 如果选中的状态数量=列表的总数量，那么就将全选设置为选中
			cb_cart_all.setChecked(true);
		} else {
			// 如果选中的状态数量！=列表的总数量，那么就将全选设置为取消
			cb_cart_all.setChecked(false);
		}

		tv_cart_Allprice.setText("合计：￥"+AllCount+ "");
		System.out.println("选择的位置--->"+position);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId() ){
		case R.id.tv_title_right:
			if (EditState==1) {//当前为1状态被点击了
				for (int i = 0; i < arrayList_cart.size(); i++) {
					is_choice[i]=false;
					((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.cb_choice)).setChecked(false);
				}
				tv_title_right.setText("完成");
				tv_cart_buy_Ordel.setText("删除");
				EditState=2;
				
				for (int i = 0; i < arrayList_cart.size(); i++) {
					((LinearLayout) (listView_cart.getChildAt(i)).findViewById(R.id.ll_add_reduce)).setVisibility(View.VISIBLE);
					((LinearLayout) (listView_cart.getChildAt(i)).findViewById(R.id.ll_cart_detail)).setVisibility(View.GONE);
				}
			}else {//正在显示完成按钮
				EditState=1;
				for (int i = 0; i < arrayList_cart.size(); i++) {
					is_choice[i]=false;
				((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.cb_choice)).setChecked(false);
				}
				is_choice=new boolean[arrayList_cart.size()];
				tv_title_right.setText("编辑");
				tv_cart_buy_Ordel.setText("结算");
				
				for (int i = 0; i < arrayList_cart.size(); i++) {
					((LinearLayout) (listView_cart.getChildAt(i)).findViewById(R.id.ll_add_reduce)).setVisibility(View.GONE);
					((LinearLayout) (listView_cart.getChildAt(i)).findViewById(R.id.ll_cart_detail)).setVisibility(View.VISIBLE);
				}
			}
			break;
		case R.id.iv_back:
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			break;
		case R.id.tv_cart_buy_or_del://点击结算/删除
			boolean[] is_choice_copy=is_choice;
			if (tv_cart_buy_Ordel.getText().toString().equals("删除")) {
				//执行删除操作
				if (arrayList_cart.size()!=0) {
					for (int i = is_choice_copy.length-1; i >=0; i--) {
						if (is_choice_copy[i]) {
							((CheckBox) (listView_cart.getChildAt(i)).findViewById(R.id.cb_choice)).setChecked(false);
							//删除购物车
							ApiClient.deleteCart(ShoppingCartActivity.this,arrayList_cart.get(i).get("id").toString(), networkHelper);
							arrayList_cart.remove(i);
							RequestState=1;
							is_choice_copy=deleteByIndex(is_choice, i);
						}
					}
				}
				
				
				if (arrayList_cart.size()==0) {
					ll_cart.setVisibility(View.VISIBLE);
				}
				
				adapter.notifyDataSetChanged();
				is_choice=new boolean[arrayList_cart.size()];
				System.out.println("此时的长度---->"+is_choice.length);
			}else {
				//执行结算操作
			//	Toast.makeText(getActivity(), "暂时无法结算", Toast.LENGTH_SHORT).show();
				Intent  intent=new Intent(ShoppingCartActivity.this,ConfirmOrderActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			}
		default:
			break;
		}
	}
	
	/**删除数组中的一个元素*/
    public static boolean[] deleteByIndex(boolean[] array, int index) {
    	boolean[] newArray = new boolean[array.length - 1];
        for (int i = 0; i < newArray.length; i++) {
            if (i < index) {
                newArray[i] = array[i];
            } else {
                newArray[i] = array[i + 1];
            }
        }
        return newArray;
    }

	@Override
	public void addCount(int position) {
		// TODO Auto-generated method stub
		int temp=(Integer) arrayList_cart.get(position).get("count");
		arrayList_cart.get(position).put("count", temp+1);
		RequestState=2;
		ApiClient.modifyCart(ShoppingCartActivity.this,arrayList_cart.get(position).get("id")+"", arrayList_cart.get(position).get("count")+"", 
				arrayList_cart.get(position).get("express")+"",
				arrayList_cart.get(position).get("sku_id")+"",arrayList_cart.get(position).get("psku_id")+"",networkHelper);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void reduceCount(int position) {
		// TODO Auto-generated method stub
		int temp=(Integer) arrayList_cart.get(position).get("count");
		if (temp>1) {
			arrayList_cart.get(position).put("count", temp-1);
		}else {
			Toast.makeText(this, "不能再少了", Toast.LENGTH_SHORT).show();;
		}	
		//这里因为开始设计的时候不合理，所以写的比较乱
		RequestState=2;
		ApiClient.modifyCart(ShoppingCartActivity.this,arrayList_cart.get(position).get("id")+"", arrayList_cart.get(position).get("count")+"", 
				arrayList_cart.get(position).get("express")+"",
				arrayList_cart.get(position).get("sku_id")+"",arrayList_cart.get(position).get("psku_id")+"",networkHelper);
		adapter.notifyDataSetChanged();
	}
}
