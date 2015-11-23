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
import com.itboye.banma.adapter.OrderListAdapter;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
import com.itboye.banma.entity.OrderDetailListItem;
import com.itboye.banma.fragment.OrderStateFragment.GoShoppingListener;
import com.itboye.banma.view.PullToRefreshListView;
import com.itboye.banma.view.PullToRefreshListView.OnRefreshListener;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class OrderAllFragment extends Fragment implements StrUIDataListener,OnClickListener,
		OnRefreshListener {
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
	private int upOrdowm = -1; //刷新状态 0下拉刷新  1上拉刷新
	private List<OrderDetailListItem> orderList = new ArrayList<OrderDetailListItem>();
	private PullToRefreshListView listView;
	private OrderListAdapter adapter;
	private AllGoShoppingListener goShoppingListener;
	private Button goshop;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		appContext = (AppContext) getActivity().getApplication();
		networkHelper = new StrVolleyInterface(getActivity());
		networkHelper.setStrUIDataListener(OrderAllFragment.this);
		
	}

	/**
	 * 加载数据
	 */
	public void load_data() {
		
		try {
			YesOrNo = appContext.getAllOrder(getActivity(), pageNo, pageSize,
					 Constant.STATE_ALL, networkHelper);
			if(!YesOrNo){
				upOrdowm = -1;
				ll_cart.setVisibility(View.GONE);
				wait_ll.setVisibility(View.VISIBLE);
				retry_img.setVisibility(View.VISIBLE);
				loading_ll.setVisibility(View.GONE);
				orderListLayout.setVisibility(View.GONE);
			}
		} catch (Exception e) {
			upOrdowm = -1;
			e.printStackTrace();
		}
	}
	
	private void showListView(List<OrderDetailListItem> orderList) {
		
		if(orderList != null && orderList.size() >0){

			if (adapter == null) {
				adapter = new OrderListAdapter(getActivity(), orderList);
				listView.setAdapter(adapter);
			} else {
				adapter.onDateChang(orderList);
			}
			if(upOrdowm == 0){
				listView.onRefreshComplete();
				listView.setSelection(0);
			}
			upOrdowm = -1;
			ll_cart.setVisibility(View.GONE);
			wait_ll.setVisibility(View.GONE);
			retry_img.setVisibility(View.GONE);
			loading_ll.setVisibility(View.GONE);
			orderListLayout.setVisibility(View.VISIBLE);
		}else{
			upOrdowm = -1;
			ll_cart.setVisibility(View.VISIBLE);
			wait_ll.setVisibility(View.GONE);
			retry_img.setVisibility(View.GONE);
			loading_ll.setVisibility(View.GONE);
			orderListLayout.setVisibility(View.GONE);
		}
		
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		pageNo = 1;
		pageSize = Constant.PAGE_SIZE;
		adapter = null;
		if(orderList != null){
			orderList.clear();
		}
		super.onCreateView(inflater, container, savedInstanceState);
		chatView = inflater.inflate(R.layout.fragment_quanbu, container, false);
		goshop=(Button) chatView.findViewById(R.id.btn_quguangguang);
		goshop.setOnClickListener(this);
		listView = (PullToRefreshListView) chatView.findViewById(R.id.fresh_list);
		listView.setOnRefreshListener(this);
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
					upOrdowm = -1;
					ll_cart.setVisibility(View.GONE);
					wait_ll.setVisibility(View.VISIBLE);
					retry_img.setVisibility(View.GONE);
					loading_ll.setVisibility(View.VISIBLE);
					orderListLayout.setVisibility(View.GONE);
					pageNo = 1;
					orderList.clear();
					load_data();
				}
			}
		});
		upOrdowm = -1;
		ll_cart.setVisibility(View.GONE);
		wait_ll.setVisibility(View.VISIBLE);
		retry_img.setVisibility(View.GONE);
		loading_ll.setVisibility(View.VISIBLE);
		orderListLayout.setVisibility(View.GONE);
		load_data();
		return chatView;
	}

	@Override
	public void onRefresh() {
		upOrdowm = 0;  //0表示顶部刷新
		pageNo = 1;
		pageSize = Constant.PAGE_SIZE;
		//orderList.clear();
		load_data();
		
	}

	@Override
	public void onLoad() {
		if(upOrdowm == 0){
			listView.hideBottom();
		}else{
			upOrdowm = 1;  //1表示底部刷新
			pageNo += 1;
			load_data();
		}
		
	}

	@Override
	public void onErrorHappened(VolleyError error) {
		Toast.makeText(getActivity(), "返回错误信息：" + error,
				Toast.LENGTH_LONG).show();
		upOrdowm = -1;
		ll_cart.setVisibility(View.GONE);
		wait_ll.setVisibility(View.VISIBLE);
		retry_img.setVisibility(View.VISIBLE);
		loading_ll.setVisibility(View.GONE);
		orderListLayout.setVisibility(View.GONE);
	}

	@Override
	public void onDataChanged(String data) {
		Gson gson = new Gson();
		
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
					if(upOrdowm == 1){
						listView.onLoadNone();
					}else{
						ll_cart.setVisibility(View.VISIBLE);
						wait_ll.setVisibility(View.GONE);
						retry_img.setVisibility(View.GONE);
						loading_ll.setVisibility(View.GONE);
						orderListLayout.setVisibility(View.GONE);
					}
					return;
				}
				jsonObject2 = new JSONObject(content);

				listStr = jsonObject2.getString("list");
				Map<String, OrderDetailListItem> retMap = gson.fromJson(listStr,  
		                new TypeToken<Map<String, OrderDetailListItem>>() {  
		                }.getType());  
				System.out.println(retMap);
				if(upOrdowm == 0){
					orderList.clear();
				}
				//map转化为List
				Iterator it = retMap.keySet().iterator(); 
				if(it.hasNext()){
					listView.loadComplete();
				}else{
					listView.onLoadNone();
				}
				while (it.hasNext()) {  
			           String key = it.next().toString();  
			           orderList.add(retMap.get(key));  
			       }
				/*Toast.makeText(getActivity(), "AllOrder返回数据：orderList"+orderList.size(), Toast.LENGTH_LONG)
				.show();*/
	
				showListView(orderList);
				
			} else {
				upOrdowm = -1;
				ll_cart.setVisibility(View.GONE);
				wait_ll.setVisibility(View.VISIBLE);
				retry_img.setVisibility(View.VISIBLE);
				loading_ll.setVisibility(View.GONE);
				orderListLayout.setVisibility(View.GONE);
			}

		} catch (JSONException e1) {
			e1.printStackTrace();
			upOrdowm = -1;
			ll_cart.setVisibility(View.GONE);
			wait_ll.setVisibility(View.VISIBLE);
			retry_img.setVisibility(View.VISIBLE);
			loading_ll.setVisibility(View.GONE);
			orderListLayout.setVisibility(View.GONE);
		}
	}

	 /*@Override  
	    public void onAttach(Activity activity)   
	    {  
	        super.onAttach(activity);  
	        goShoppingListener = (AllGoShoppingListener) activity;   
	    }  */
	
	 public interface AllGoShoppingListener{  
	      public void onChanged(int position);  
	  }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_quguangguang:
			goShoppingListener.onChanged(2);
			break;

		default:
			break;
		}
	}  

	
}
