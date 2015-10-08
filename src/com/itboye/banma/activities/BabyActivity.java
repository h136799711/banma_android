package com.itboye.banma.activities;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itboye.banma.R;
import com.itboye.banma.adapter.ViewPagerFragmentAdapter;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
import com.itboye.banma.entity.ProductDetail;
import com.itboye.banma.entity.SkuStandard;
import com.itboye.banma.entity.ProductDetail.Sku_info;
import com.itboye.banma.fragment.BabyCommentFragment;
import com.itboye.banma.fragment.BabyDetailFragment;
import com.itboye.banma.fragment.BabyParameterFragment;
import com.itboye.banma.utils.BitmapCache;
import com.itboye.banma.view.BabyPopWindow;
import com.itboye.banma.view.BabyPopWindow.OnItemClickListener;
import com.itboye.banma.view.HackyViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.nfc.NfcAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

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
	private ViewPager viewPagerPage;
	protected TextView one_title;
	protected TextView two_title;
	protected TextView three_title;
	private ViewPagerFragmentAdapter myAdapter;
	private MyListener listener = new MyListener();
	private BabyDetailFragment detailFragment;
	private BabyParameterFragment parameterFragment;
	private BabyCommentFragment commentFragment;
	private List<Sku_info> sku_info;  //商品类型参数
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_babydetail_a);
		appContext = (AppContext) getApplication();
		initView();
		iniData();
		
	}

	/**
	 * 加载数据
	 */
	private void iniData() {
		for(int i=0; i<Constant.SKU_INFO.length; i++){
			Constant.SKU_INFO[i] = "";
		}
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
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
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
		viewPager.setOffscreenPageLimit(3);  
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
	public void onClickOKPop(SkuStandard skuStandard) {
		setBackgroundBlack(all_choice_layout, 1);
		if (isClickBuy) {
			//跳转到订单确认页面
			Intent intent = new Intent(BabyActivity.this, ConfirmOrderActivity.class);
			intent.putExtra("SkuStandard", skuStandard);
			intent.putExtra("main_img",productDetail.getMain_img());
			intent.putExtra("name",productDetail.getName());
			intent.putExtra("price",productDetail.getPrice());
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right,
					R.anim.out_to_left);
		}else {
			Toast.makeText(this, "添加购物车", Toast.LENGTH_SHORT).show();
			
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
		Gson gson = new Gson();

		int code = -1;
		String detail = null;
		Toast.makeText(BabyActivity.this, "获取成功", Toast.LENGTH_SHORT).show();
		try {
			JSONObject jsonObject = new JSONObject(data);
			code = jsonObject.getInt("code");
			detail = jsonObject.getString("data");
			System.out.println("data*****="+detail);
			if(code == 0){
				
				productDetail = gson.fromJson(detail, ProductDetail.class);
				imageList=productDetail.getImg().split(",");
				sku_info = new ArrayList<ProductDetail.Sku_info>();
				//String ssString = "[{\"id\":\"1\",\"vid\":[\"1\",\"2\",\"3\"]},{\"id\":\"2\",\"vid\":[\"6\"]},{\"id\":\"3\",\"vid\":[\"9\"]}]";
				sku_info = gson.fromJson(productDetail.getSku_info(),new TypeToken<List<Sku_info>>() {
						}.getType());
				
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
		popWindow = new BabyPopWindow(this, sku_info, productDetail.getSkuInfo(), 
				productDetail.getMain_img(), productDetail.getPrice(), productDetail.getQuantity(), productDetail.getSkuList());
		popWindow.setOnItemClickListener(this);
		initDetailPager();
		wait_ll.setVisibility(View.GONE);
		retry_img.setVisibility(View.GONE);
		loading_ll.setVisibility(View.GONE);
		baby_detail.setVisibility(View.VISIBLE);
		button_lay.setVisibility(View.VISIBLE);
	}
	
	private void initDetailPager() {
		one_title = (TextView) findViewById(R.id.one_title);
		two_title = (TextView) findViewById(R.id.two_title);
		three_title = (TextView) findViewById(R.id.three_title);
		myAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager());
		viewPagerPage = (ViewPager) findViewById(R.id.viewPager);
		detailFragment = new BabyDetailFragment(productDetail.getDetail());
		parameterFragment = new BabyParameterFragment();
		commentFragment = new BabyCommentFragment();
		myAdapter.addFragment(detailFragment);
		myAdapter.addFragment(parameterFragment);
		
		myAdapter.addFragment(commentFragment);
		viewPagerPage.setOffscreenPageLimit(3);
		viewPagerPage.setOnPageChangeListener(listener);
		viewPagerPage.setAdapter(myAdapter);
		one_title.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				viewPagerPage.setCurrentItem(0);
			}
		});

		two_title.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				viewPagerPage.setCurrentItem(1);
			}
		});

		three_title.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				viewPagerPage.setCurrentItem(2);
			}
		});
		// 模拟网络请求完成之后重置ViewPager高度
		new myAsyncTask().execute();
		//resetViewPagerHeight(0);
	}

	/**
	 * 重新设置viewPager高度
	 * 
	 * @param position
	 */
	public void resetViewPagerHeight(int position) {
		View child = viewPagerPage.getChildAt(position);
		if (child != null) {
			child.measure(0, 0);
			int h = child.getMeasuredHeight();
			LinearLayout.LayoutParams params = (LayoutParams) viewPagerPage
					.getLayoutParams();
			params.height = h + 250;
			viewPagerPage.setLayoutParams(params);
		}
	}
	
	public class MyListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {

		}

		@Override
		public void onPageSelected(int position) {
			changTextColor(position);
			// 页面切换后重置ViewPager高度
			resetViewPagerHeight(position);
			switch (position) {
			case 0:
				break;
			case 1:
				break;
			case 2:
				break;
			}
		}
	}
	/**
	 * 改变title颜色
	 * 
	 * @param arg0
	 */
	private void changTextColor(int arg0) {
		one_title.setTextColor(this.getResources().getColor(R.color.black));
		two_title.setTextColor(this.getResources().getColor(R.color.black));
		three_title.setTextColor(this.getResources().getColor(R.color.black));
		one_title.setBackgroundColor(this.getResources().getColor(R.color.white));
		two_title.setBackgroundColor(this.getResources().getColor(R.color.white));
		three_title.setBackgroundColor(this.getResources().getColor(R.color.white));
		switch (arg0) {
		case 0:
			one_title.setTextColor(this.getResources().getColor(
					R.color.white));
			one_title.setBackgroundColor(this.getResources().getColor(R.color.slategray));
			break;
		case 1:
			two_title.setTextColor(this.getResources().getColor(
					R.color.white));
			two_title.setBackgroundColor(this.getResources().getColor(R.color.slategray));
			break;
		case 2:
			three_title.setTextColor(this.getResources().getColor(
					R.color.white));
			three_title.setBackgroundColor(this.getResources().getColor(R.color.slategray));
			break;
		}
		
	}
	
	public class myAsyncTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			resetViewPagerHeight(0);
		}

	}

	@Override
	public void onClickDissmissPop() {
		setBackgroundBlack(all_choice_layout, 1);
	}

}
