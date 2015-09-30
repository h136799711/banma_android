package com.itboye.banma.activities;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.internal.Primitives;
import com.itboye.banma.R;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.entity.ProductDetail;
import com.itboye.banma.view.BabyPopWindow;
import com.itboye.banma.view.BabyPopWindow.OnItemClickListener;
import com.itboye.banma.view.HackyViewPager;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BabyActivity extends FragmentActivity implements OnItemClickListener, OnClickListener, StrUIDataListener {

	NfcAdapter nfcAdapter;
	private AppContext appContext;
	private Boolean YesOrNo; // 是否连接网络
	private StrVolleyInterface strnetworkHelper;
	private ProductDetail productDetail;

	private HackyViewPager viewPager;
	private ArrayList<View> allListView;
	private int[] resId = { R.drawable.detail_show_1, R.drawable.detail_show_2, R.drawable.detail_show_3};
	private ListView listView;
	private ImageView iv_baby_collection;
	private BabyPopWindow popWindow;
	private TextView title;
	private ImageView pic_image;
	private LinearLayout all_choice_layout = null;
	boolean isClickBuy = false;
	private static boolean isCollection=false; 
	private int position=0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_babydetail_a);
		appContext = (AppContext) getApplication();
		//是否被收藏
		getSaveCollection();
		initView();
		iniData();
		popWindow = new BabyPopWindow(this);
		popWindow.setOnItemClickListener(this);
	}

	/**
	 * 加载数据
	 */
	private void iniData() {
		strnetworkHelper = new StrVolleyInterface(BabyActivity.this);
		strnetworkHelper.setStrUIDataListener(BabyActivity.this);
		try {
			YesOrNo = appContext.getProductDetail(BabyActivity.this, 2, strnetworkHelper);
			if(!YesOrNo){   //如果没联网
				Toast.makeText(BabyActivity.this, "请检查网络连接", Toast.LENGTH_SHORT)
				.show();
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		
	}

	@SuppressLint("NewApi")
	private void initView() {
		//获取设备NFC
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		if (nfcAdapter == null) {
			Toast.makeText(this, "设备不支持NFC！", Toast.LENGTH_SHORT).show();
		}
		((ImageView) findViewById(R.id.iv_back)).setOnClickListener(this);
		((ImageView) findViewById(R.id.put_in)).setOnClickListener(this);
		((ImageView) findViewById(R.id.buy_now)).setOnClickListener(this);
		title = (TextView) findViewById(R.id.title);
		iv_baby_collection=(ImageView) findViewById(R.id.iv_baby_collection);
		iv_baby_collection.setOnClickListener(this);
		all_choice_layout = (LinearLayout) findViewById(R.id.all_choice_layout);
		listView = (ListView) findViewById(R.id.listView_Detail);
		listView.setFocusable(false);
		listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		/*listView.setAdapter(new Adapter_ListView_detail(this));
		listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Uri uri = Uri.parse("http://yecaoly.taobao.com"); 
				Intent intent = new Intent(Intent.ACTION_VIEW, uri); 
				startActivity(intent);
			}
		});*/
		initViewPager();
		
		if (isCollection) {
			//图标设置为已收藏
			iv_baby_collection.setImageResource(R.drawable.second_2_collection);
		}
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.iv_back:
			//返回
			finish();
			break;
		case R.id.iv_baby_collection:
			//收藏
			if (isCollection) {
				//取消收藏
				cancelCollection();
			}else {
				isCollection=true;
				setSaveCollection();
				//收藏宝贝
				iv_baby_collection.setImageResource(R.drawable.second_2_collection);
				Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.put_in:
			//加入购物车ﳵ
			isClickBuy = false;
			setBackgroundBlack(all_choice_layout, 0);
			popWindow.showAsDropDown(view);
			break;
		case R.id.buy_now:
			//点击购买
			isClickBuy = true;
			setBackgroundBlack(all_choice_layout, 0);
			popWindow.showAsDropDown(view);
			break;
		}
	}
	
	

	private void initViewPager() {

		if (allListView != null) {
			allListView.clear();
			allListView = null;
		}
		allListView = new ArrayList<View>();

		for (int i = 0; i < resId.length; i++) {
			View view = LayoutInflater.from(this).inflate(R.layout.pic_item, null);
			pic_image = (ImageView) view.findViewById(R.id.pic_item);
			pic_image.setImageResource(resId[i]);
			pic_image.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					//��ս���鿴��ͼ����
					/*Intent intent = new Intent(BabyActivity.this, ShowBigPictrue.class);
					intent.putExtra("position", position);
					startActivity(intent);*/
				}
			});
			allListView.add(view);
		}

		viewPager = (HackyViewPager) findViewById(R.id.iv_baby);
		ViewPagerAdapter adapter = new ViewPagerAdapter();
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				position=arg0;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		viewPager.setAdapter(adapter);

	}

	private class ViewPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return allListView.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (View) arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = allListView.get(position);
			container.addView(view);
			return view;
		}

	}

	//购买按钮监听
	@Override
	public void onClickOKPop() {
		setBackgroundBlack(all_choice_layout, 1);

		if (isClickBuy) {
			//跳转到购买页面
			/*Intent intent = new Intent(BabyActivity.this, BuynowActivity.class);
			startActivity(intent);*/
		}else {
			//Toast.makeText(this, "添加购物车", Toast.LENGTH_SHORT).show();
			
		}
	}
	
	/** 把背景变成暗色 */
	public void setBackgroundBlack(View view, int what) {
		switch (what) {
		case 0:
			view.setVisibility(View.VISIBLE);
			break;
		case 1:
			view.setVisibility(View.GONE);
			break;
		}
	}

	/**保存收藏状态*/
	private void setSaveCollection(){
		SharedPreferences sp=getSharedPreferences("SAVECOLLECTION", Context.MODE_PRIVATE);
		Editor editor=sp.edit();
		editor.putBoolean("isCollection", isCollection);
		editor.commit();
	}
	/**获取收藏状态*/
	private void getSaveCollection(){
		SharedPreferences sp=getSharedPreferences("SAVECOLLECTION", Context.MODE_PRIVATE);
		isCollection=sp.getBoolean("isCollection", false);
		
	}
	/**取消收藏*/
	private  void cancelCollection(){
		AlertDialog.Builder dialog=new AlertDialog.Builder(this);
		dialog.setTitle("取消收藏");
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				isCollection=false;
				iv_baby_collection.setImageResource(R.drawable.second_2);
				setSaveCollection();
			}
		});
		dialog.setNegativeButton("取消", null);
		dialog.create().show();
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(popWindow.isOrNot()){
				popWindow.dissmiss();
			}else{
				finish();
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
			}
		}
		return false;
	}

	@Override
	public void onErrorHappened(VolleyError error) {
		Toast.makeText(BabyActivity.this, "网络异常"+error, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDataChanged(String data) {
		int code = -1;
		String detail = null;
		Toast.makeText(BabyActivity.this, "获取成功", Toast.LENGTH_SHORT).show();
		try {
			JSONObject jsonObject = new JSONObject(data);
			code = jsonObject.getInt("code");
			detail = jsonObject.getString("data");
			if(code == 0){
				Gson gson = new Gson();
				productDetail = gson.fromJson(detail, ProductDetail.class);
				title.setText(productDetail.getName());
				System.out.println("data*****="+detail);
				System.out.println("商品详情*****="+productDetail.toString());
			}
			
			
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

}
