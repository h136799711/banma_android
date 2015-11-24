package com.itboye.banma.fragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itboye.banma.R;
import com.itboye.banma.activities.HomePageActivity;
import com.itboye.banma.adapter.OrderListAdapter;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
import com.itboye.banma.entity.OrderDetailListItem;
import com.itboye.banma.fragment.CenterFragment.ChangeItemListener;
import com.itboye.banma.view.PullToRefreshListView;
import com.itboye.banma.view.PullToRefreshListView.OnRefreshListener;

import android.support.v4.app.Fragment;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class OrderStateFragment extends Fragment implements StrUIDataListener ,OnClickListener{
	private View chatView;
	private AppContext appContext;
	private StrVolleyInterface networkHelper;
	private ImageView retry_img;
	private LinearLayout wait_ll;
	private LinearLayout loading_ll;
	private LinearLayout orderListLayout;
	private LinearLayout ll_cart;
	private int pageNo;
	private int pageSize;
	private boolean YesOrNo; // 网络是否连接
	private List<OrderDetailListItem> orderList = new ArrayList<OrderDetailListItem>();
	private ListView listView;
	private ListView fresh_list;
	private OrderListAdapter adapter;
	private int state;  //区分订单状态:1：代付款 2：代发货  3：待收货  4：待评价
	private Button goShop;
	private StateGoShoppingListener goShoppingListener;
	
	public OrderStateFragment(int state){
		this.state = state;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		appContext = (AppContext) getActivity().getApplication();
		networkHelper = new StrVolleyInterface(getActivity());
		networkHelper.setStrUIDataListener(OrderStateFragment.this);
		
		
	}
	/**
	 * 加载数据
	 */
	public void load_data() {
		try {
			
			YesOrNo = appContext.getAllOrder(getActivity(), pageNo, pageSize,
					state, networkHelper);
			if(!YesOrNo){
				ll_cart.setVisibility(View.GONE);
				wait_ll.setVisibility(View.VISIBLE);
				retry_img.setVisibility(View.VISIBLE);
				loading_ll.setVisibility(View.GONE);
				orderListLayout.setVisibility(View.GONE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void showListView(List<OrderDetailListItem> orderList) {
		if(orderList != null && orderList.size() >0){
			adapter = new OrderListAdapter(getActivity(), orderList, appContext);
			listView.setAdapter(adapter);
			ll_cart.setVisibility(View.GONE);
			wait_ll.setVisibility(View.GONE);
			retry_img.setVisibility(View.GONE);
			loading_ll.setVisibility(View.GONE);
			orderListLayout.setVisibility(View.VISIBLE);
		}else{
			ll_cart.setVisibility(View.VISIBLE);
			wait_ll.setVisibility(View.GONE);
			retry_img.setVisibility(View.GONE);
			loading_ll.setVisibility(View.GONE);
			orderListLayout.setVisibility(View.GONE);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		pageNo = 0;
		pageSize = Constant.PAGE_SIZE;
		if(orderList != null){
			orderList.clear();
		}
		super.onCreateView(inflater, container, savedInstanceState);
		chatView = inflater.inflate(R.layout.fragment_quanbu, container, false);
		goShop=(Button)chatView.findViewById(R.id.btn_quguangguang);
		goShop.setOnClickListener(this);
		fresh_list = (ListView) chatView.findViewById(R.id.fresh_list);
		fresh_list.setVisibility(View.GONE);
		listView = (ListView) chatView.findViewById(R.id.list);
		listView.setVisibility(View.VISIBLE);
		/*
		 * demandAdapter = new DemandListAdapter(getActivity(), messagelist);
		 * listView.setAdapter(demandAdapter);
		 */
		ll_cart = (LinearLayout) chatView.findViewById(R.id.ll_cart);
		wait_ll = (LinearLayout) chatView.findViewById(R.id.wait_ll);
		loading_ll = (LinearLayout) chatView.findViewById(R.id.loading_ll);
		retry_img = (ImageView) chatView.findViewById(R.id.retry_img);
		orderListLayout = (LinearLayout) chatView.findViewById(R.id.lv);
		wait_ll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (retry_img.getVisibility() == View.VISIBLE) {
					ll_cart.setVisibility(View.GONE);
					wait_ll.setVisibility(View.VISIBLE);
					retry_img.setVisibility(View.GONE);
					loading_ll.setVisibility(View.VISIBLE);
					orderListLayout.setVisibility(View.GONE);
					pageNo = 0;
					orderList.clear();
					load_data();
				}
			}
		});
		ll_cart.setVisibility(View.GONE);
		wait_ll.setVisibility(View.VISIBLE);
		retry_img.setVisibility(View.GONE);
		loading_ll.setVisibility(View.VISIBLE);
		orderListLayout.setVisibility(View.GONE);
		load_data();
		return chatView;
	}
	
	@Override
	public void onErrorHappened(VolleyError error) {
		Toast.makeText(getActivity(), "返回错误信息：" + error,
				Toast.LENGTH_LONG).show();
		ll_cart.setVisibility(View.GONE);
		wait_ll.setVisibility(View.VISIBLE);
		retry_img.setVisibility(View.VISIBLE);
		loading_ll.setVisibility(View.GONE);
		orderListLayout.setVisibility(View.GONE);
	}
	@Override
	public void onDataChanged(String data) {
		Gson gson = new Gson();
		/*Toast.makeText(getActivity(), "AllOrder返回数据：" + data, Toast.LENGTH_LONG)
				.show();*/
		JSONObject jsonObject = null;
		String content = null;
		String listStr;
		int code = -1;
		JSONObject jsonObject2 = null;

		try {
			jsonObject = new JSONObject(data);
			code = jsonObject.getInt("code");
			content = jsonObject.getString("data");
			
			if (code == 0) {
				//System.out.println(data);
				/*Toast.makeText(getActivity(), "AllOrder成功", Toast.LENGTH_LONG)
						.show();*/
				if(content.length() < 5){
					ll_cart.setVisibility(View.VISIBLE);
					wait_ll.setVisibility(View.GONE);
					retry_img.setVisibility(View.GONE);
					loading_ll.setVisibility(View.GONE);
					orderListLayout.setVisibility(View.GONE);
					return;
				}
				jsonObject2 = new JSONObject(content);

				listStr = jsonObject2.getString("list");
				Map<String, OrderDetailListItem> retMap = gson.fromJson(listStr,  
		                new TypeToken<Map<String, OrderDetailListItem>>() {  
		                }.getType());  
				System.out.println(retMap);
				//map转化为List
				Iterator it = retMap.keySet().iterator(); 
				while (it.hasNext()) {
					String key = it.next().toString();
					orderList.add(retMap.get(key)); 
					/*String key = it.next().toString();
					switch (state) {
					case Constant.DAIFUKUAN:  //代付款
						if(!retMap.get(key).getOrder_status().equals(Constant.ORDER_CANCEL+"") 
							&& retMap.get(key).getPay_status().equals(Constant.ORDER_TOBE_PAID+"")
							&& retMap.get(key).getOrder_status().equals(Constant.ORDER_TOBE_CONFIRMED+"") ){
							orderList.add(retMap.get(key)); 
						}
					break;
					case Constant.DAIFAHUO:  //代发货
						if(retMap.get(key).getOrder_status().equals(Constant.ORDER_TOBE_SHIPPED+"")) 
						{
							orderList.add(retMap.get(key)); 
						}
					break;
					case Constant.DAISHOUHUO:  //代收货
						if(retMap.get(key).getOrder_status().equals(Constant.ORDER_SHIPPED+"")) 
						{
							orderList.add(retMap.get(key)); 
						}
						break;
					case Constant.DAIPINGJIA:  //代评价
						
						break;

					default:
						break;
					}

*/
				}
				showListView(orderList);
				
				
			} else {
				ll_cart.setVisibility(View.GONE);
				wait_ll.setVisibility(View.VISIBLE);
				retry_img.setVisibility(View.VISIBLE);
				loading_ll.setVisibility(View.GONE);
				orderListLayout.setVisibility(View.GONE);
			}

		} catch (JSONException e1) {
			e1.printStackTrace();
			ll_cart.setVisibility(View.GONE);
			wait_ll.setVisibility(View.VISIBLE);
			retry_img.setVisibility(View.VISIBLE);
			loading_ll.setVisibility(View.GONE);
			orderListLayout.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_quguangguang:
//			Intent intent=new Intent(getActivity(),HomePageActivity.class);
//			intent.putExtra("position", 2);
//			startActivity(intent);
			getActivity().finish();
			getActivity().overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			//goShoppingListener.onStateChanged(2);
			break;

		default:
			break;
		}
	}
	
	/* @Override  
	    public void onAttach(Activity activity)   
	    {  
	        super.onAttach(activity);  
	        goShoppingListener = (GoShoppingListener) activity;   
	    } */ 
	
	 public interface StateGoShoppingListener{  
	      public void onStateChanged(int position);  
	  }  

}
