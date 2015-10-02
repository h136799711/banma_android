package com.itboye.banma.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.Gson;
import com.google.gson.internal.Primitives;
import com.itboye.banma.R;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.entity.ProductDetail;
import com.itboye.banma.utils.BitmapCache;
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
import android.graphics.Paint;
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
import android.widget.Button;
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
	private ListView listView;
	private ImageView iv_baby_collection;
	private BabyPopWindow popWindow;
	private TextView title;
	private TextView baby_name;
	private TextView ori_price;
	private TextView price;
	private TextView freight;
	private TextView customs_duties;
	private TextView sales_area;
	private NetworkImageView pic_image;
	private Button put_in;
	private Button buy_now;
	private LinearLayout wait_ll;
	private LinearLayout loading_ll;
	private ImageView retry_img;
	private LinearLayout baby_detail;
	private LinearLayout button_lay;
	private LinearLayout all_choice_layout = null;
	boolean isClickBuy = false;
	private int position=0;
	private ImageView[] indicators = null;
	private LinearLayout indicatorLayout;
	private String[] imageList;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_babydetail_a);
		appContext = (AppContext) getApplication();
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
			YesOrNo = appContext.getProductDetail(BabyActivity.this, 1, strnetworkHelper);
			if(!YesOrNo){   //如果没联网
				Toast.makeText(BabyActivity.this, "请检查网络连接", Toast.LENGTH_SHORT)
				.show();
				wait_ll.setVisibility(View.VISIBLE);
				retry_img.setVisibility(View.VISIBLE);
				loading_ll.setVisibility(View.GONE);
				baby_detail.setVisibility(View.GONE);
				button_lay.setVisibility(View.GONE);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		
	}

	@SuppressLint("NewApi")
	private void initView() {
		baby_detail = (LinearLayout) findViewById(R.id.baby_detail);
		button_lay = (LinearLayout) findViewById(R.id.button_lay);
		// 旋转等待页
				wait_ll = (LinearLayout) findViewById(R.id.wait_ll);
				retry_img = (ImageView) findViewById(R.id.retry_img);
				loading_ll = (LinearLayout) findViewById(R.id.loading_ll);
				wait_ll.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (retry_img.getVisibility() == View.VISIBLE) {
							wait_ll.setVisibility(View.VISIBLE);
							retry_img.setVisibility(View.GONE);
							loading_ll.setVisibility(View.VISIBLE);
							baby_detail.setVisibility(View.GONE);
							button_lay.setVisibility(View.GONE);
							iniData();
							
						}
					}
				});
		((ImageView) findViewById(R.id.iv_back)).setOnClickListener(this);
		put_in = (Button) findViewById(R.id.put_in);
		put_in.setOnClickListener(this);
		buy_now = (Button) findViewById(R.id.buy_now);
		buy_now.setOnClickListener(this);
		title = (TextView) findViewById(R.id.title);
		baby_name = (TextView) findViewById(R.id.baby_name);
		ori_price = (TextView) findViewById(R.id.ori_price);
		price = (TextView) findViewById(R.id.price);
		freight = (TextView) findViewById(R.id.freight);
		customs_duties = (TextView) findViewById(R.id.customs_duties);
		sales_area = (TextView) findViewById(R.id.sales_area);
		all_choice_layout = (LinearLayout) findViewById(R.id.all_choice_layout);
		/*listView = (ListView) findViewById(R.id.listView_Detail);
		listView.setFocusable(false);
		listView.setSelector(new ColorDrawable(Color.TRANSPARENT));*/
		title.setText("商品详情");
		/*listView.setAdapter(new Adapter_ListView_detail(this));
		listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Uri uri = Uri.parse("http://yecaoly.taobao.com"); 
				Intent intent = new Intent(Intent.ACTION_VIEW, uri); 
				startActivity(intent);
			}
		});*/
		
		
		wait_ll.setVisibility(View.VISIBLE);
		retry_img.setVisibility(View.GONE);
		loading_ll.setVisibility(View.VISIBLE);
		baby_detail.setVisibility(View.GONE);
		button_lay.setVisibility(View.GONE);
		
		
		//initViewPager();
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.iv_back:
			//返回
			finish();
			break;
		case R.id.put_in:
			//加入购物ﳵ
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
		ImageLoader imageLoader = new ImageLoader(AppContext.getHttpQueues(), new BitmapCache()); 
		
		if (allListView != null) {
			allListView.clear();
			allListView = null;
		}
		indicatorLayout = (LinearLayout) findViewById(R.id.indicator);
		allListView = new ArrayList<View>();
		indicators = new ImageView[imageList.length];
		
		
		
		for (int i = 0; i < imageList.length; i++) {
		
			View view = LayoutInflater.from(this).inflate(R.layout.pic_item, null);
			pic_image = (NetworkImageView) view.findViewById(R.id.pic_item);
			//pic_image.setDefaultImageResId(R.drawable.base_map);  //加载中显示的图片
			pic_image.setErrorImageResId(R.drawable.image_load_fail);  //加载失败显示的图片
			pic_image.setImageUrl(imageList[i], imageLoader);
			
			/*ImageListener listener = ImageLoader.getImageListener(pic_image,  
			        0, R.drawable.e); 
			imageLoader.get(imageList[i], listener, 150, 150);*/
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
			indicators[i] = new ImageView(BabyActivity.this);
			indicators[i].setBackgroundResource(R.drawable.indicators_default);
			if (i == 0) {
				indicators[i].setBackgroundResource(R.drawable.indicators_now);
			}
			indicatorLayout.addView(indicators[i]);

		}

		viewPager = (HackyViewPager) findViewById(R.id.iv_baby);
		ViewPagerAdapter adapter = new ViewPagerAdapter();
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				position=arg0;
				for (int i = 0; i < indicators.length; i++) {
					indicators[i]
							.setBackgroundResource(R.drawable.indicators_default);
					if (arg0 == i) {
						indicators[arg0].setBackgroundResource(R.drawable.indicators_now);
					}
				}
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
		wait_ll.setVisibility(View.VISIBLE);
		retry_img.setVisibility(View.VISIBLE);
		loading_ll.setVisibility(View.GONE);
		baby_detail.setVisibility(View.GONE);
		button_lay.setVisibility(View.GONE);
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
			System.out.println("data*****="+detail);
			if(code == 0){
				Gson gson = new Gson();
				productDetail = gson.fromJson(detail, ProductDetail.class);
				imageList=productDetail.getImg().split(",");
				
				updatePages();
				
				System.out.println("商品详情*****="+productDetail.toString());
			}
			
			
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 请求数据成功 修改页面
	 */
	private void updatePages() {
		initViewPager();
		baby_name.setText(productDetail.getName());
		ori_price.setText("￥"+productDetail.getOri_price());
		price.setText("￥"+productDetail.getPrice());
		ori_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
		//是否包邮
		if(productDetail.getAttrext_ispostfree()==1){
			freight.setText("免运费");
		}else{
			freight.setText("￥"+productDetail.getExpress());
		}
		customs_duties.setText("免关税");
		sales_area.setText(productDetail.getLoc_province()+productDetail.getLoc_city());
		
		wait_ll.setVisibility(View.GONE);
		retry_img.setVisibility(View.GONE);
		loading_ll.setVisibility(View.GONE);
		baby_detail.setVisibility(View.VISIBLE);
		button_lay.setVisibility(View.VISIBLE);
	}

}
