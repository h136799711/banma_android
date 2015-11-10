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
import com.itboye.banma.view.DrawableChangeView;
import com.itboye.banma.view.FancyCoverFlow;

import android.R.integer;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class HomePageFragment extends Fragment implements OnClickListener,
		StrUIDataListener {
	// private AppContext appContext;
	private static final int NUM = 9;
	private View chatView;
	private ViewPager mViewPager;
	private List<View> mImages = new ArrayList<View>();
	private LinearLayout all_top;
	private ImageView back;
	private TextView title;
	private View top_line;
	private ImageView more;
	private MyPageAdapter adapter;
	private AppContext appContext;
	private StrVolleyInterface networkHelper;
	private List<ProductItem> productlist;
	private FancyCoverFlowSampleAdapter flowSampleAdapter;
	private FancyCoverFlow fancyCoverFlow;
	private LinearLayout wait_ll;
	private LinearLayout loading_ll;
	private ImageView retry_img;
	private FrameLayout frame_layout;
	private DrawableChangeView darwableView;
	private Drawable[] drawables = new Drawable[NUM];
	Boolean YesOrNo;
	int state;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		networkHelper = new StrVolleyInterface(getActivity());
		networkHelper.setStrUIDataListener(this);
		appContext = (AppContext) getActivity().getApplication();
		state = 0;
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
		// 旋转等待页
				wait_ll = (LinearLayout) chatView.findViewById(R.id.wait_ll);
				retry_img = (ImageView) chatView.findViewById(R.id.retry_img);
				loading_ll = (LinearLayout) chatView.findViewById(R.id.loading_ll);
				frame_layout = (FrameLayout) chatView.findViewById(R.id.frame_layout);
				wait_ll.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (retry_img.getVisibility() == View.VISIBLE) {
							wait_ll.setVisibility(View.VISIBLE);
							retry_img.setVisibility(View.GONE);
							loading_ll.setVisibility(View.VISIBLE);
							frame_layout.setVisibility(View.GONE);
						
							initData();

						}
					}
				});
		darwableView = (DrawableChangeView) chatView.findViewById(R.id.drawableChangeView);
		mViewPager = (ViewPager) chatView.findViewById(R.id.id_viewpager);
		all_top = (LinearLayout) chatView.findViewById(R.id.all_top);
		top_line = chatView.findViewById(R.id.top_line);
		back = (ImageView) chatView.findViewById(R.id.iv_back);
		title = (TextView) chatView.findViewById(R.id.title);
		more = (ImageView) chatView.findViewById(R.id.more);
		back.setVisibility(View.GONE);
		more.setVisibility(View.VISIBLE);
		top_line.setVisibility(View.GONE);
		title.setText("商品");
		
		wait_ll.setVisibility(View.VISIBLE);
		retry_img.setVisibility(View.GONE);
		loading_ll.setVisibility(View.VISIBLE);
		frame_layout.setVisibility(View.GONE);
		
		initData();
	}

	private void showListView(List<ProductItem> productlist2) {
		// 查找布局文件用LayoutInflater.inflate
		LayoutInflater inflater = getActivity().getLayoutInflater();
		for (int i = 0; i < productlist.size(); i++) {
			View convertView = inflater.inflate(R.layout.view, null);

			mImages.add(convertView);

		}
		/*if (flowSampleAdapter == null) {
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
		}*/

		if (adapter == null) {
			getViews();
			adapter = new MyPageAdapter(getActivity(), mImages, productlist);
			// 添加动画效果
			// mViewPager.setPageTransformer(true, arg1);
			//mViewPager.setPageTransformer(true, new RotateDownTransformer());
			mViewPager.setAdapter(adapter);
			mViewPager.setOffscreenPageLimit(2);
			mViewPager.setAdapter(adapter);
			mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(int arg0) {
					System.out.println("当前现实的view"+arg0);
				}
				
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					//arg1 显示的view前一个view所占屏幕的比例
					//arg2 viewpager的总宽度
					System.out.println("arg0所占屏幕的比例"+arg1+"  arg0"+arg0);
					darwableView.setPosition(arg0 % 6);
					darwableView.setDegree(arg1);
					darwableView.invalidate();
				}
				
				@Override
				public void onPageScrollStateChanged(int arg0) {
					
				}

			});
			darwableView.setDrawables(drawables);
			
		}

	}
	
	private void getViews() {
		
		drawables[0] = getActivity().getResources().getDrawable(R.drawable.back001);
		drawables[1] = getActivity().getResources().getDrawable(R.drawable.back002);
		drawables[2] = getActivity().getResources().getDrawable(R.drawable.back003);
		drawables[3] = getActivity().getResources().getDrawable(R.drawable.back004);
		drawables[4] = getActivity().getResources().getDrawable(R.drawable.back005);
		drawables[5] = getActivity().getResources().getDrawable(R.drawable.back006);
		drawables[6] = getActivity().getResources().getDrawable(R.drawable.back001);
		drawables[7] = getActivity().getResources().getDrawable(R.drawable.back002);
		drawables[8] = getActivity().getResources().getDrawable(R.drawable.back003);
	
	}
	
	/**
	 * 加载商品列表数据
	 */
	private void initData() {
		try {
			YesOrNo = appContext.getProductList(getActivity(), 1,
					Constant.PAGE_SIZE, networkHelper);
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
		wait_ll.setVisibility(View.VISIBLE);
		retry_img.setVisibility(View.VISIBLE);
		loading_ll.setVisibility(View.GONE);
		frame_layout.setVisibility(View.GONE);
	}

	@Override
	public void onDataChanged(String data) {
		/*Toast.makeText(getActivity(), "shuju" + data, Toast.LENGTH_SHORT)
				.show();*/
		state += 1;
		Gson gson = new Gson();
		productlist = new ArrayList<ProductItem>();
		JSONObject jsondata;
		try {
			jsondata = new JSONObject(data);

			int code = jsondata.getInt("code");
			if (code == 0) {
				wait_ll.setVisibility(View.GONE);
				retry_img.setVisibility(View.GONE);
				loading_ll.setVisibility(View.GONE);
				frame_layout.setVisibility(View.VISIBLE);
				
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
				if(state<=1){
				initData();
				}
				else{
					Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_LONG).show();
					wait_ll.setVisibility(View.VISIBLE);
					retry_img.setVisibility(View.VISIBLE);
					loading_ll.setVisibility(View.GONE);
					frame_layout.setVisibility(View.GONE);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {

	}

}
