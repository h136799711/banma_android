package com.itboye.banma.activities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.drawable;
import android.R.integer;
import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.nfc.NfcAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itboye.banma.R;
import com.itboye.banma.adapter.BabyDetailAdapter;
import com.itboye.banma.adapter.ViewPagerFragmentAdapter;
import com.itboye.banma.api.ApiClient;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
import com.itboye.banma.entity.ProductDetail;
import com.itboye.banma.entity.ProductDetail.Sku_info;
import com.itboye.banma.entity.SkuStandard;
import com.itboye.banma.fragment.BabyDetailFragment;
import com.itboye.banma.fragment.BabyParameterFragment;
import com.itboye.banma.shoppingcart.ShoppingCartActivity;
import com.itboye.banma.utils.BitmapCache;
import com.itboye.banma.view.BabyPopWindow;
import com.itboye.banma.view.MyListView;
import com.itboye.banma.view.BabyPopWindow.OnItemClickListener;
import com.itboye.banma.view.HackyViewPager;
import com.itboye.banma.view.SharePopWindow;
import com.itboye.banma.view.ViewPagerScroller;
import com.itboye.banma.view.ViewPagerScroller.OnScrollListener;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.social.UMPlatformData;
import com.umeng.analytics.social.UMPlatformData.GENDER;
import com.umeng.analytics.social.UMPlatformData.UMedia;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
//import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

public class BabyActivity extends FragmentActivity implements
		OnItemClickListener, OnClickListener, StrUIDataListener, OnScrollListener {

	private static int BUY_ADDCART = 5;  //购买时添加购物车标志
	private static int BUY_CART_ID = 6; //购物车单个查询接口标志
	
	NfcAdapter nfcAdapter;
	private AppContext appContext;
	private Boolean YesOrNo; // 是否连接网络
	private StrVolleyInterface strnetworkHelper;
	private ProductDetail productDetail;
	private BabyDetailAdapter adapter;
	private HackyViewPager viewPager;
	private ArrayList<View> allListView;
	private ListView listView;
	private ImageView iv_baby_collection;
	private ImageView no_detail;
	private BabyPopWindow popWindow;
	private SharePopWindow sharePopWindow;
	private TextView title;
	private View top_line;
	private TextView baby_name;
	private TextView ori_price;
	private TextView price;
	private TextView total_sales;
	private TextView weight;
	private TextView customs_duties;
	private TextView sales_area;
	private NetworkImageView pic_image;
	private Button put_in;
	private Button buy_now;
	private ImageView share;
	private LinearLayout wait_ll;
	private LinearLayout loading_ll;
	private ImageView retry_img;
	private LinearLayout baby_detail;
	private LinearLayout button_lay;
	private ImageButton ib_more;
	private ImageButton iv_back;
	private LinearLayout all_choice_layout = null;
	boolean isClickBuy = false;
	private int position = 0;
	private ImageView[] indicators = null;
	private LinearLayout indicatorLayout;
	private String[] imageList;
	private ViewPager viewPagerPage;
	private ViewPagerScroller myScrollView;
	private LinearLayout all_top;
	private WebView detail_image;
	private List<Sku_info> sku_info; // 商品类型参数
	private int requestState = 0;// 判断哪个请求返回的结果 1表示加入购物车请求
	private int pid;  //商品ID
    UMSocialService mController;
    private ProgressDialog dialog;
    public String urlShare="http://banma.itboye.com/index.php/Home/Share/index";
    /** 
     * myScrollView与其父类布局的顶部距离 
     */  
    private int myScrollViewTop; 
    //微信
	String appID = "wx0d259d7e9716d3dd";
	String appSecret = "94124fb74284c8dae6f188c7e269a5a0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_babydetail_a);
		appContext = (AppContext) getApplication();
		
	
		
		//设置新浪SSO handler
	//	mController.getConfig().setSsoHandler(new SinaSsoHandler());
		
		//集成微信

		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(this,appID,appSecret);
		wxHandler.addToSocialSDK();
		// 添加微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(this,appID,appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
		
		
		
		
		//集成扣扣分享
		//参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "1104887406",
		                "7mxqFi07TN8QD1ZR");
		qqSsoHandler.addToSocialSDK(); 
		
		//分享扣扣空间
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, "1104887406",
                "7mxqFi07TN8QD1ZR");
        qZoneSsoHandler.addToSocialSDK();
		
		
		initView();
		iniData();

	}

	//友盟统计
		@Override
		protected void onResume() {

			super.onResume();
			MobclickAgent.onResume(this);
		}

		public void onPause() {
			super.onPause();
			MobclickAgent.onPause(this);
		}

	
	/**
	 * 加载数据
	 */
	private void iniData() {
		dialog = new ProgressDialog(BabyActivity.this);
		Intent intent = getIntent();
		pid = intent.getIntExtra("PID", 21);
		
		for (int i = 0; i < Constant.SKU_INFO.length; i++) {
			Constant.SKU_INFO[i] = "";
			Constant.SKU_INFOSTR[i] = "";
		}
		Constant.SKU_ALLNUM = 0;

		//for(int i=0; i<Constant.SKU_NUM.length; i++){
	//		Constant.SKU_NUM[i] = 0;
	//	}

		for(int i=0; i<Constant.SKU_NUM.length; i++){
			Constant.SKU_NUM[i] = 0;
		}

		strnetworkHelper = new StrVolleyInterface(BabyActivity.this);
		strnetworkHelper.setStrUIDataListener(BabyActivity.this);
		try {
			YesOrNo = appContext.getProductDetail(BabyActivity.this, pid,
					strnetworkHelper);
			if (!YesOrNo) { // 如果没联网
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
		myScrollView = (ViewPagerScroller) findViewById(R.id.myScrollView);
		myScrollView.setOnScrollListener(this);
		all_top = (LinearLayout) findViewById(R.id.all_top);
		all_top.setBackgroundDrawable(getResources().getDrawable(R.drawable.top)); 
    	all_top.getBackground().setAlpha(0);//0~255透明度值
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
		ib_more = (ImageButton) findViewById(R.id.more);
		ib_more.setOnClickListener(this);
		ib_more.setVisibility(View.VISIBLE);

		iv_back = (ImageButton) findViewById(R.id.iv_back);
		iv_back.setBackgroundDrawable(getResources().getDrawable(R.drawable.top_babydetail_back));
		//((ImageView) findViewById(R.id.iv_back)).setImageResource(R.drawable.top_babydetail_back);
		iv_back.setOnClickListener(this);
		put_in = (Button) findViewById(R.id.put_in);
		put_in.setOnClickListener(this);
		buy_now = (Button) findViewById(R.id.buy_now);
		buy_now.setOnClickListener(this);
		share = (ImageView) findViewById(R.id.share);
		share.setOnClickListener(this);
		title = (TextView) findViewById(R.id.title);
		top_line = findViewById(R.id.top_line);
		baby_name = (TextView) findViewById(R.id.baby_name);
		ori_price = (TextView) findViewById(R.id.ori_price);
		price = (TextView) findViewById(R.id.price);
		total_sales = (TextView) findViewById(R.id.total_sales);
		weight = (TextView) findViewById(R.id.weight);
		customs_duties = (TextView) findViewById(R.id.customs_duties);
		sales_area = (TextView) findViewById(R.id.sales_area);
		all_choice_layout = (LinearLayout) findViewById(R.id.all_choice_layout);

		/*
		 * listView = (ListView) findViewById(R.id.listView_Detail);
		 * listView.setFocusable(false); listView.setSelector(new
		 * ColorDrawable(Color.TRANSPARENT));
		 */
		top_line.setVisibility(View.GONE);
		title.setVisibility(View.GONE);
		title.setText("商品详情");
		title.setTextColor(Color.WHITE);
		//title.setVisibility(View.GONE);
		/*
		 * listView.setAdapter(new Adapter_ListView_detail(this));
		 * listView.setOnItemClickListener(new
		 * android.widget.AdapterView.OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> arg0, View arg1, int
		 * arg2, long arg3) { Uri uri = Uri.parse("http://yecaoly.taobao.com");
		 * Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		 * startActivity(intent); } });
		 */

		wait_ll.setVisibility(View.VISIBLE);
		retry_img.setVisibility(View.GONE);
		loading_ll.setVisibility(View.VISIBLE);
		baby_detail.setVisibility(View.GONE);
		button_lay.setVisibility(View.GONE);

		// initViewPager();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.iv_back:
			// 返回
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			break;
		case R.id.more:
			// 点击购物车
			if (appContext.isLogin()) {
				startActivity(new Intent(BabyActivity.this,
						ShoppingCartActivity.class));
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

			}else{
				Intent intent = new Intent(BabyActivity.this,
						LoginActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			}
			// if (appContext.isLogin()) {
			
			// }else {
			// Toast.makeText(BabyActivity.this, "请先登录",
			// Toast.LENGTH_LONG).show();
			// }

			break;
		case R.id.put_in:
			// 加入购物ﳵ
			isClickBuy = false;
			setBackgroundBlack(all_choice_layout, 0);
			popWindow.showAsDropDown(view);
			break;
		case R.id.buy_now:
			// 点击购买
			isClickBuy = true;
			setBackgroundBlack(all_choice_layout, 0);
			popWindow.showAsDropDown(view);
			break;

		case R.id.share:
			umengShare();
			break;
		}
	}

	private void umengShare() {

			 // 首先在您的Activity中添加如下成员变量
				mController = UMServiceFactory.getUMSocialService("com.umeng.share");

				//设置腾讯微博SSO handler
				mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
				
		    	mController.setAppWebSite(SHARE_MEDIA.RENREN, "http://www.umeng.com/social");
		    	
		    	mController.getConfig().removePlatform( SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN);
		   
		    	//设置微信好友分享内容
		    	WeiXinShareContent weixinContent = new WeiXinShareContent();
		    	//设置分享文字
		    	weixinContent.setShareContent(productDetail.getName());
		    	//设置title
		    	weixinContent.setTitle("斑马海外购");
		    	//设置分享内容跳转URL
		    	weixinContent.setTargetUrl(urlShare);
		    	//设置分享图片
		    	weixinContent.setShareImage(new UMImage(this, 
	                   productDetail.getMain_img()));
		    	System.out.println(productDetail.getDetail()+urlShare+new UMImage(this, 
		                   productDetail.getMain_img()));
		    	mController.setShareMedia(weixinContent);			
		    
	   
		    	
		    	//设置微信朋友圈分享内容
		    	CircleShareContent circleMedia = new CircleShareContent();
		    	circleMedia.setShareContent(productDetail.getName());
		    	//设置朋友圈title
		    	circleMedia.setTitle("斑马海外购");
		    	circleMedia.setShareImage(new UMImage(this, 
		                   productDetail.getMain_img()));
		    	circleMedia.setTargetUrl(urlShare);
		    	mController.setShareMedia(circleMedia);
		    	
		    	QQShareContent qqShareContent = new QQShareContent();
		    	//设置分享文字
		    	qqShareContent.setShareContent(productDetail.getName());
		    	//设置分享title
		    	qqShareContent.setTitle("斑马海外购");
		    	//设置分享图片
		    	qqShareContent.setShareImage(new UMImage(this, 
		                   productDetail.getMain_img()));
		    	//设置点击分享内容的跳转链接
		    	qqShareContent.setTargetUrl(urlShare);
		    	mController.setShareMedia(qqShareContent);
		    	
		    	QZoneShareContent qzone = new QZoneShareContent();
		    	//设置分享文字
		    	qzone.setShareContent(productDetail.getName());
		    	//设置点击消息的跳转URL
		    	qzone.setTargetUrl(urlShare);
		    	//设置分享内容的标题
		    	qzone.setTitle("斑马海外购");
		    	//设置分享图片
		    	qzone.setShareImage(new UMImage(this, 
		                   productDetail.getMain_img()));
		    	mController.setShareMedia(qzone);
		    	
		    	mController.openShare(BabyActivity.this, false);
	}

	private void initViewPager() {
		ImageLoader imageLoader = new ImageLoader(AppContext.getHttpQueues(),
				new BitmapCache());

		if (allListView != null) {
			allListView.clear();
			allListView = null;
		}
		indicatorLayout = (LinearLayout) findViewById(R.id.indicator);
		allListView = new ArrayList<View>();
		indicators = new ImageView[imageList.length];

		for (int i = 0; i < imageList.length; i++) {

			View view = LayoutInflater.from(this).inflate(R.layout.pic_item,
					null);
			pic_image = (NetworkImageView) view.findViewById(R.id.pic_item);
			// pic_image.setDefaultImageResId(R.drawable.base_map); //加载中显示的图片
			pic_image.setErrorImageResId(R.drawable.image_load_fail); // 加载失败显示的图片
			String url_image = imageList[i];
			if(url_image.equals("false")){
				url_image = "";
			}
			pic_image.setImageUrl(url_image, imageLoader);

			/*
			 * ImageListener listener = ImageLoader.getImageListener(pic_image,
			 * 0, R.drawable.e); imageLoader.get(imageList[i], listener, 150,
			 * 150);
			 */
			pic_image.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// ��ս���鿴��ͼ����
					/*
					 * Intent intent = new Intent(BabyActivity.this,
					 * ShowBigPictrue.class); intent.putExtra("position",
					 * position); startActivity(intent);
					 */
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
		if(allListView == null){
			allListView = new ArrayList<View>();
		}
		ViewPagerAdapter adapter = new ViewPagerAdapter();
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				position = arg0;
				for (int i = 0; i < indicators.length; i++) {
					indicators[i]
							.setBackgroundResource(R.drawable.indicators_default);
					if (arg0 == i) {
						indicators[arg0]
								.setBackgroundResource(R.drawable.indicators_now);
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

	// 购买按钮监听
	@Override
	public void onClickOKPop(SkuStandard skuStandard) {
		setBackgroundBlack(all_choice_layout, 1);
		if (isClickBuy) {
			if (appContext.isLogin()) {
				requestState = BUY_ADDCART;
				addCart(skuStandard);
				

			}else{
				Intent intent = new Intent(BabyActivity.this,
						LoginActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			}

		} else {
			// Gson gson=new Gson();
			// String s=gson.toJson(skuStandard);
			// System.out.println(s);
			// System.out.println(productDetail.getMain_img());
			// System.out.println(productDetail.getName());
			// System.out.println(productDetail.getPrice());

			if (appContext.isLogin()) {

				requestState = 1;
				addCart(skuStandard);
			}else {
				Intent intent = new Intent(BabyActivity.this,
						LoginActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			}

		}
	}
	
	/**
	 * 添加购物车
	 * @param skuStandard
	 */
	public void addCart(SkuStandard skuStandard){
		if (popWindow.isOrNot()) {
			popWindow.dissmiss();
		}
		dialog.setMessage("请稍等...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
		int hasSku = Integer.parseInt(productDetail.getHas_sku());
		if(hasSku == 1){
			// 相应规格里面有数据
			skuStandard.setIcon_url(productDetail.getMain_img());
		}else if(hasSku == 0){
			//规格里面 没有数据，先初始化加入数据
			skuStandard.setOri_price(productDetail.getOri_price());
			skuStandard.setPrice(productDetail.getPrice());
			skuStandard.setQuantity(productDetail.getQuantity());
			skuStandard.setProduct_id(productDetail.getId()+"");
			skuStandard.setIcon_url(productDetail.getMain_img());
		}
		
		try {
			Boolean stateBoolean = appContext.addCart(BabyActivity.this, appContext.getLoginUid()
					+ "", productDetail.getStore_id() + "",
					productDetail.getId() + "", skuStandard.getSku_id()
					+ "", skuStandard.getSku(), skuStandard.getIcon_url(),
					skuStandard.getNum(), productDetail.getName(),
					"0", skuStandard.getPrice()
					+ "", skuStandard.getOri_price() + "",
					skuStandard.getId() + "", productDetail.getWeight(), "0", strnetworkHelper);
			if (stateBoolean == false) {
				//dialog.dismiss();
				Toast.makeText(BabyActivity.this, "请检查网络连接",
						Toast.LENGTH_LONG).show();
				dialog.dismiss();
			}
		} catch (Exception e) {
			e.printStackTrace();
			dialog.dismiss();
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
			if (popWindow.isOrNot()) {
				popWindow.dissmiss();
			} else {
				finish();
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
			}
		}
		return false;
	}

	@Override
	public void onErrorHappened(VolleyError error) {
		dialog.dismiss();
		Toast.makeText(BabyActivity.this, "网络异常" + error, Toast.LENGTH_SHORT).show();
//		.show();
//       Log.e("LOGIN-ERROR", error.getMessage(), error);
//       byte[] htmlBodyBytes = error.networkResponse.data;
//       Log.e("LOGIN-ERROR", new String(htmlBodyBytes), error);
		if (requestState!=1) {
			wait_ll.setVisibility(View.VISIBLE);
			retry_img.setVisibility(View.VISIBLE);
			loading_ll.setVisibility(View.GONE);
			baby_detail.setVisibility(View.GONE);
			button_lay.setVisibility(View.GONE);
		}
		requestState = 0;
	}

	@Override
	public void onDataChanged(String data) {
		if (requestState == 1) {
			dialog.dismiss();
			int code1 = -1;
			requestState = 0;
			try {
				JSONObject jsonObject = new JSONObject(data);
				System.out.println(data.toString() + "成功了");
				code1 = jsonObject.getInt("code");
				if (code1 == 0) {
					Toast.makeText(this, "添加购物车成功", Toast.LENGTH_SHORT).show();
					System.out.println(jsonObject.getString("data") + "成功了");
				}
				else {
					System.out.println(jsonObject.getString("data") + "失败了");
				}
					
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				Toast.makeText(this, "添加购物车失败", Toast.LENGTH_SHORT).show();
			}
		}else if (requestState == BUY_ADDCART) {  //购买时 先添加购物车
			int code1 = -1;
			requestState = 0;
			try {
				JSONObject jsonObject = new JSONObject(data);
				System.out.println(data.toString() + "成功了");
				code1 = jsonObject.getInt("code");
				if (code1 == 0) {
					//cartId为购物车ID
					int cartId = jsonObject.getInt("data");
					requestState = BUY_CART_ID;
					 appContext.getCartById(BabyActivity.this, cartId, strnetworkHelper);
				}
				else {
					Toast.makeText(this, "服务器异常，请稍后再试", Toast.LENGTH_SHORT).show();
					dialog.dismiss();
				}
					
			} catch (Exception e) {
				// TODO: handle exception
				requestState = 0;
				e.printStackTrace();
				Toast.makeText(this, "服务器异常，请稍后再试", Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		}else if (requestState == BUY_CART_ID) {  //得到购物车ID 跳转到订单确认页面
			dialog.dismiss();
			int code1 = -1;
			requestState = 0;
			try {
				JSONObject jsonObject = new JSONObject(data);
				code1 = jsonObject.getInt("code");
				if (code1 == 0) {
					//cartId为购物车ID
					String cartData = jsonObject.getString("data");
					JSONObject cartData_jsonObject = new JSONObject(cartData); 
					
					// 跳转到订单确认页面
					//
					SkuStandard skuStandard = new SkuStandard();
					skuStandard.setId(cartData_jsonObject.getInt("id"));
					skuStandard.setSku_id(cartData_jsonObject.getString("sku_id"));
					skuStandard.setOri_price(cartData_jsonObject.getDouble("ori_price"));
					skuStandard.setPrice(cartData_jsonObject.getDouble("price"));
					skuStandard.setProduct_id(cartData_jsonObject.getString("p_id"));
					skuStandard.setSku(cartData_jsonObject.getString("sku_desc"));
					skuStandard.setName(cartData_jsonObject.getString("name"));
					skuStandard.setNum(cartData_jsonObject.getString("count"));
					skuStandard.setTaxrate(cartData_jsonObject.getString("taxrate"));
					skuStandard.setIcon_url(appContext.getImg()+cartData_jsonObject.getString("icon_url"));
					List<SkuStandard> list = new ArrayList<SkuStandard>();
					list.add(skuStandard);

					Intent intent = new Intent(BabyActivity.this,
							ConfirmOrdersActivity.class);
					intent.putExtra("SkuStandardList", (Serializable)list);

					startActivity(intent);
					finish();
					overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
					
				}
				else {
					Toast.makeText(this, "服务器异常，请稍后再试", Toast.LENGTH_SHORT).show();
				}
					
			} catch (Exception e) {
				// TODO: handle exception
				requestState = 0;
				e.printStackTrace();
				Toast.makeText(this, "服务器异常，请稍后再试", Toast.LENGTH_SHORT).show();
			}
		}
		
		else {
			Gson gson = new Gson();
			int code = -1;
			String detail = null;
//			Toast.makeText(BabyActivity.this, "获取成功", Toast.LENGTH_SHORT)
//					.show();
			try {
				JSONObject jsonObject = new JSONObject(data);
				code = jsonObject.getInt("code");
				detail = jsonObject.getString("data");
				System.out.println("data*****=" + detail);
				System.out.println("data*****=" + data);
				if (code == 0) {

					productDetail = gson.fromJson(detail, ProductDetail.class);
					imageList = productDetail.getImg().split(",");
					sku_info = new ArrayList<ProductDetail.Sku_info>();
					// String ssString =
					// "[{\"id\":\"1\",\"vid\":[\"1\",\"2\",\"3\"]},{\"id\":\"2\",\"vid\":[\"6\"]},{\"id\":\"3\",\"vid\":[\"9\"]}]";
					sku_info = gson.fromJson(productDetail.getSku_info(),
							new TypeToken<List<Sku_info>>() {
							}.getType());

					updatePages();

					System.out.println("商品详情*****=" + productDetail.toString());
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 请求数据成功 修改页面
	 */
	private void updatePages() {
		initViewPager();
		baby_name.setText(productDetail.getName());
		ori_price.setText("￥" + productDetail.getOri_price());
		price.setText("￥" + productDetail.getPrice());
		ori_price.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		total_sales.setText( productDetail.getTotal_sales());
		weight.setText(productDetail.getWeight()/1000+"kg");
		if(productDetail.getTax_rate() != null){
			customs_duties.setText(Double.valueOf(productDetail.getTax_rate())*100+"%");
		}else{
			customs_duties.setText("0");
		}
		
		sales_area.setText(productDetail.getSource());
		/*popWindow = new BabyPopWindow(this, sku_info, productDetail.getName(),
				productDetail.getSkuInfo(), productDetail.getMain_img(),
				productDetail.getPrice(), productDetail.getOri_price(),
				productDetail.getQuantity(), productDetail.getSkuList());*/
		popWindow = new BabyPopWindow(this, sku_info, productDetail.getName(),
				productDetail.getSku_info_list(), productDetail.getMain_img(),
				productDetail.getPrice(), productDetail.getOri_price(),productDetail.getHas_sku(),
				productDetail.getQuantity(), productDetail.getSku_list());
		popWindow.setOnItemClickListener(this);
		sharePopWindow = new SharePopWindow(this);
		
		initDetailPager();
		wait_ll.setVisibility(View.GONE);
		retry_img.setVisibility(View.GONE);
		loading_ll.setVisibility(View.GONE);
		baby_detail.setVisibility(View.VISIBLE);
		button_lay.setVisibility(View.VISIBLE);
	}

	private void initDetailPager() {
		detail_image = (WebView) findViewById(R.id.detail_image);
		if(productDetail.getDetail() != null){
			/*adapter = new BabyDetailAdapter(BabyActivity.this, productDetail.getDetail());
			detail_image.setAdapter(adapter);*/
			String htmlString = "<style type=\"text/css\">img{width:100%;height:auto;}</style><div class=\"img\">"+
					productDetail.getDetail();
			System.out.println(productDetail.getDetail());
			detail_image.loadDataWithBaseURL("about:blank", htmlString, "text/html", "utf-8", null);
			//detail_image.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			WebSettings webSettings = detail_image.getSettings();  
			webSettings.setUseWideViewPort(false);  //任意比例缩放
			webSettings.setLoadWithOverviewMode(false); 
			webSettings.setBuiltInZoomControls(false); // 设置显示缩放按钮  
			webSettings.setSupportZoom(false); // 支持缩放  
			//myScrollView.requestDisallowInterceptTouchEvent(false); 
			/*detail_image.setOnTouchListener(new OnTouchListener() {

			      @Override
			      public boolean onTouch(View v, MotionEvent event) {
			        // TODO Auto-generated method stub
			        if (event.getAction() == MotionEvent.ACTION_UP) 
			        	myScrollView.requestDisallowInterceptTouchEvent(true); 
			              else  
			            	  myScrollView.requestDisallowInterceptTouchEvent(false);

			              return false; 
			      }


			      });
			*/
			
		}else{
			detail_image.setVisibility(View.GONE);
			no_detail = (ImageView) findViewById(R.id.con_image);
			no_detail.setVisibility(View.VISIBLE);
		}
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
	@Override
	public void onClickDissmissPop() {
		setBackgroundBlack(all_choice_layout, 1);
	}
	
	
	@Override 
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    /**使用SSO授权必须添加如下代码 */
	    UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
	    if(ssoHandler != null){
	       ssoHandler.authorizeCallBack(requestCode, resultCode, data);
	    }
	}

	/** 
     * 窗口有焦点的时候，即所有的布局绘制完毕的时候，我们来获取购买布局的高度和myScrollView距离父类布局的顶部位置 
     */  
    @Override    
    public void onWindowFocusChanged(boolean hasFocus) {    
        super.onWindowFocusChanged(hasFocus);    
        if(hasFocus){  
            
            myScrollViewTop = myScrollView.getTop();  
            System.out.println("myScrollViewTop="+myScrollViewTop);
        }  
    }  
    /** 
     * 滚动的回调方法，当滚动的Y距离大于或者等于 购买布局距离父类布局顶部的位置，就显示购买的悬浮框 
     * 当滚动的Y的距离小于 购买布局距离父类布局顶部的位置加上购买布局的高度就移除购买的悬浮框 
     *  
     */  
    @Override  
    public void onScroll(int scrollY) {  
    	System.out.println("scrollY="+scrollY);
        if(scrollY < 200){  
        	all_top.getBackground().setAlpha(0);
        	iv_back.getBackground().setAlpha(255);
        	ib_more.getBackground().setAlpha(255);
        	if(title.getVisibility() == View.VISIBLE){
        		title.setVisibility(View.GONE);
        		iv_back.setBackgroundDrawable(getResources().getDrawable(R.drawable.top_babydetail_back));
        		ib_more.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_add_cart));
        	}
        }else if(scrollY >= 200 && scrollY <= 965){  
        	all_top.getBackground().setAlpha((scrollY - 200)/3);//0~255透明度值  
        	if(scrollY > 750){
        		iv_back.getBackground().setAlpha(scrollY-750);
        		ib_more.getBackground().setAlpha(scrollY-750);
        		if(title.getVisibility() == View.GONE){
        			title.setVisibility(View.VISIBLE);
        			title.setTextColor(Color.argb(scrollY-750, 0, 255, 0)); //文字透明度
        			iv_back.setBackgroundDrawable(getResources().getDrawable(R.drawable.top_bar_back));
        			iv_back.getBackground().setAlpha(0);
        			ib_more.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopcart_white));
        			ib_more.getBackground().setAlpha(0);
        		}
        		title.setTextColor(Color.argb(scrollY-750, 255, 255, 255)); //文字透明度
        	}
        	else if(scrollY <= 750 ){
        		iv_back.getBackground().setAlpha(((750-scrollY)/2)>255? 255 : ((750-scrollY)/2));
        		ib_more.getBackground().setAlpha(((750-scrollY)/2)>255? 255 : ((750-scrollY)/2));
        		if(title.getVisibility() == View.VISIBLE){
        			title.setVisibility(View.GONE);
        			iv_back.setBackgroundDrawable(getResources().getDrawable(R.drawable.top_babydetail_back));
        			iv_back.getBackground().setAlpha(0);
        			ib_more.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_add_cart));
        			ib_more.getBackground().setAlpha(0);
        		}
        	}
        } else if(scrollY > 965){
        	all_top.getBackground().setAlpha(255);//0~255透明度值
        	iv_back.getBackground().setAlpha(255);
        	ib_more.getBackground().setAlpha(255);
        	title.setTextColor(Color.argb(255, 255, 255, 255));
        	if(title.getVisibility() == View.GONE){
        		title.setVisibility(View.VISIBLE);
        		title.setTextColor(Color.argb(255, 255, 255, 255));
        		iv_back.setBackgroundDrawable(getResources().getDrawable(R.drawable.top_babydetail_back));
        		iv_back.getBackground().setAlpha(0);
        		ib_more.setBackgroundDrawable(getResources().getDrawable(R.drawable.shopcart_white));
    			ib_more.getBackground().setAlpha(0);
        	}
        }
    }  
	


}
