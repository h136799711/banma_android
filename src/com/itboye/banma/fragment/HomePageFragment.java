package com.itboye.banma.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itboye.banma.R;
import com.itboye.banma.activities.MailingAddressActivity;
import com.itboye.banma.adapter.FancyCoverFlowSampleAdapter;
import com.itboye.banma.adapter.MyPageAdapter;
import com.itboye.banma.adapter.RotateDownTransformer;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
import com.itboye.banma.entity.MailingAdress;
import com.itboye.banma.entity.ProductItem;
import com.itboye.banma.view.FancyCoverFlow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class HomePageFragment extends Fragment implements OnClickListener,
		StrUIDataListener {
	// private AppContext appContext;
	private View chatView;
	private ViewPager mViewPager;
	private List<View> mImages = new ArrayList<View>();
	private ImageView back;
	private TextView title;
	private ImageView more;
	private MyPageAdapter adapter;
	private AppContext appContext;
	private StrVolleyInterface networkHelper;
	private List<ProductItem> productlist;
	private FancyCoverFlowSampleAdapter flowSampleAdapter;
	private FancyCoverFlow fancyCoverFlow;
	Boolean YesOrNo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		networkHelper = new StrVolleyInterface(getActivity());
		networkHelper.setStrUIDataListener(this);
		appContext = (AppContext) getActivity().getApplication();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		chatView = inflater.inflate(R.layout.home_page, container, false);

		initView();
		return chatView;
	}

	private void initView() {
		back = (ImageView) chatView.findViewById(R.id.iv_back);
		title = (TextView) chatView.findViewById(R.id.title);
		more = (ImageView) chatView.findViewById(R.id.more);
		back.setVisibility(View.GONE);
		more.setVisibility(View.GONE);
		title.setText("商品");
		initData();
	}

	private void showListView(List<ProductItem> productlist2) {
		// 查找布局文件用LayoutInflater.inflate
		LayoutInflater inflater = getActivity().getLayoutInflater();
		for (int i = 0; i < productlist.size(); i++) {
			View convertView = inflater.inflate(R.layout.view, null);

			mImages.add(convertView);

		}
		if (flowSampleAdapter == null) {
			fancyCoverFlow = (FancyCoverFlow) chatView
					.findViewById(R.id.fancyCoverFlow);
			flowSampleAdapter = new FancyCoverFlowSampleAdapter(getActivity(), productlist);
			fancyCoverFlow.setAdapter(flowSampleAdapter);
			// fancyCoverFlow.setCallbackDuringFling(false);
			// //设置为false是当滑动结束后更新item
			// fancyCoverFlow.setSelection(5); //设置默认
			fancyCoverFlow
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> parent,
								View view, int position, long id) {
							mViewPager.setCurrentItem(position);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}

					});
		}

		if (adapter == null) {
			adapter = new MyPageAdapter(getActivity(), mImages, productlist);
			mViewPager = (ViewPager) chatView.findViewById(R.id.id_viewpager);
			// 添加动画效果
			// mViewPager.setPageTransformer(true, arg1);
			mViewPager.setPageTransformer(true, new RotateDownTransformer());
			mViewPager.setAdapter(adapter);
			mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(int position) {

					fancyCoverFlow.setSelection(position, true);
				}

				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub

				}

			});
		}

	}

	/**
	 * 加载商品列表数据
	 */
	private void initData() {
		try {
			/*YesOrNo = appContext.getProductList(getActivity(), 1,
					Constant.PAGE_SIZE, networkHelper);*/
			if (!YesOrNo) { // 如果没联网
				/*
				 * wait_ll.setVisibility(View.VISIBLE);
				 * retry_img.setVisibility(View.VISIBLE);
				 * loading_ll.setVisibility(View.GONE);
				 * list_Layout.setVisibility(View.GONE);
				 */
				Toast.makeText(getActivity(), "请检查网络连接", Toast.LENGTH_SHORT)
						.show();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Override
	public void onErrorHappened(VolleyError error) {

	}

	@Override
	public void onDataChanged(String data) {
		/*Toast.makeText(getActivity(), "shuju" + data, Toast.LENGTH_SHORT)
				.show();*/
		Gson gson = new Gson();
		productlist = new ArrayList<ProductItem>();
		JSONObject jsondata;
		try {
			jsondata = new JSONObject(data);

			int code = jsondata.getInt("code");
			if (code == 0) {
				/*
				 * wait_ll.setVisibility(View.GONE);
				 * retry_img.setVisibility(View.GONE);
				 * loading_ll.setVisibility(View.GONE);
				 * list_Layout.setVisibility(View.VISIBLE);
				 */
				String producData = jsondata.getString("data");
				jsondata = new JSONObject(producData);
				String producList = jsondata.getString("list");
				productlist = gson.fromJson(producList,
						new TypeToken<List<ProductItem>>() {
						}.getType());
				if (productlist != null) {
					showListView(productlist);
				}

			} else {
				/*Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_LONG).show();*/
				initData();
				/*
				 * wait_ll.setVisibility(View.VISIBLE);
				 * retry_img.setVisibility(View.VISIBLE);
				 * loading_ll.setVisibility(View.GONE);
				 * list_Layout.setVisibility(View.GONE);
				 */
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {

	}

}
