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
import com.itboye.banma.view.PullToRefreshListView;
import com.itboye.banma.view.PullToRefreshListView.OnRefreshListener;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class OrderAllFragment extends Fragment implements StrUIDataListener,
		OnRefreshListener {
	private View chatView;
	private AppContext appContext;
	private StrVolleyInterface networkHelper;
	private ImageView retry_img;
	private LinearLayout wait_ll;
	private LinearLayout loading_ll;
	private LinearLayout orderListLayout;
	private int pageNo;
	private int pageSize;
	private boolean YesOrNo; // 网络是否连接
	private List<OrderDetailListItem> orderList = new ArrayList<OrderDetailListItem>();
	private PullToRefreshListView listView;
	private OrderListAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		appContext = (AppContext) getActivity().getApplication();
		networkHelper = new StrVolleyInterface(getActivity());
		networkHelper.setStrUIDataListener(OrderAllFragment.this);
		pageNo = 1;
		pageSize = Constant.PAGE_SIZE;
		load_data();
	}

	/**
	 * 加载数据
	 */
	public void load_data() {
		try {
			YesOrNo = appContext.getAllOrder(getActivity(), pageNo, pageSize,
					networkHelper);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void showListView(List<OrderDetailListItem> orderList) {
		if (adapter == null) {
			adapter = new OrderListAdapter(getActivity(), orderList);
			listView.setAdapter(adapter);
		} else {
			adapter.onDateChang(orderList);
		}
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		chatView = inflater.inflate(R.layout.fragment_quanbu, container, false);
		listView = (PullToRefreshListView) chatView.findViewById(R.id.fresh_list);
		listView.setOnRefreshListener(this);
		/*
		 * demandAdapter = new DemandListAdapter(getActivity(), messagelist);
		 * listView.setAdapter(demandAdapter);
		 */
		wait_ll = (LinearLayout) chatView.findViewById(R.id.wait_ll);
		loading_ll = (LinearLayout) chatView.findViewById(R.id.loading_ll);
		retry_img = (ImageView) chatView.findViewById(R.id.retry_img);
		orderListLayout = (LinearLayout) chatView.findViewById(R.id.lv);
		wait_ll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (retry_img.getVisibility() == View.VISIBLE) {
					wait_ll.setVisibility(View.VISIBLE);
					retry_img.setVisibility(View.GONE);
					loading_ll.setVisibility(View.VISIBLE);
					orderListLayout.setVisibility(View.GONE);
					load_data();
				}
			}
		});
		wait_ll.setVisibility(View.VISIBLE);
		retry_img.setVisibility(View.GONE);
		loading_ll.setVisibility(View.VISIBLE);
		orderListLayout.setVisibility(View.GONE);
		return chatView;
	}

	@Override
	public void onRefresh() {

	}

	@Override
	public void onLoad() {

	}

	@Override
	public void onErrorHappened(VolleyError error) {
		Toast.makeText(getActivity(), "AllOrder返回错误信息：" + error,
				Toast.LENGTH_LONG).show();
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
			jsonObject2 = new JSONObject(content);

			if (code == 0) {
				System.out.println(data);
				Toast.makeText(getActivity(), "AllOrder成功", Toast.LENGTH_LONG)
						.show();
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
			       }
				showListView(orderList);

				wait_ll.setVisibility(View.GONE);
				retry_img.setVisibility(View.GONE);
				loading_ll.setVisibility(View.GONE);
				orderListLayout.setVisibility(View.VISIBLE);
				
			} else {
				wait_ll.setVisibility(View.VISIBLE);
				retry_img.setVisibility(View.VISIBLE);
				loading_ll.setVisibility(View.GONE);
				orderListLayout.setVisibility(View.GONE);
			}

		} catch (JSONException e1) {
			e1.printStackTrace();
			wait_ll.setVisibility(View.VISIBLE);
			retry_img.setVisibility(View.VISIBLE);
			loading_ll.setVisibility(View.GONE);
			orderListLayout.setVisibility(View.GONE);
		}
	}

}
