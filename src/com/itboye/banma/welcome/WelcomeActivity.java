package com.itboye.banma.welcome;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itboye.banma.R;
import com.itboye.banma.activities.HomePageActivity;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
import com.itboye.banma.entity.ProductItem;
import com.itboye.banma.service.TokenIntentService;
import com.itboye.banma.utils.BitmapCache;
import com.itboye.banma.utils.BitmapCacheHomageImage;
import com.itboye.banma.utils.SharedConfig;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class WelcomeActivity extends Activity implements StrUIDataListener, OnPageChangeListener,
		OnClickListener {
	private final int GETTOKEN = 0;   //获取Token
	private final int BABY = 1;   //获取商品
	private View view;
	private Context context;
	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;
	private Button startButton,enter;
	private LinearLayout indicatorLayout;
	private ArrayList<View> views;
	private ImageView[] indicators = null;
	private int[] images;
	private AppContext appContext;
	private StrVolleyInterface networkHelper;
	private Animation animation;
	private int net_state = -1;
	boolean finish_a = false;
	Intent intent;
	private String nextBck;   //传到首页暂时充当背景图片的地址，根据这个地址可以再缓存中找到图片

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = View.inflate(this, R.layout.activity_welcome, null);
		setContentView(view);
		context = this;
		
		appContext = (AppContext) getApplication();
		networkHelper = new StrVolleyInterface(this);
		networkHelper.setStrUIDataListener(this);
		
		
		//友盟启动
				MobclickAgent.updateOnlineConfig( this );
				//加密处理
				AnalyticsConfig.enableEncrypt(true);
				
				MobclickAgent.setDebugMode( true );
				
				//开启推送服务
				PushAgent mPushAgent = PushAgent.getInstance(getApplicationContext());
				mPushAgent.enable();
				//统计应用启动次数
				PushAgent.getInstance(getApplicationContext()).onAppStart();
				//获取测试设备的Device Token。
				String device_token = UmengRegistrar.getRegistrationId(this);
				System.out.println(device_token+"设备");
		
		images = new int[] { R.drawable.welcome_01};
		initView();
		initAnimation();

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
	       
	

	private void initView() {
		
		net_state = GETTOKEN;  
		//请求token
				if (appContext.isNetworkConnected()==false) {
					Toast.makeText(WelcomeActivity.this, "请检查网络是否连接", Toast.LENGTH_LONG).show();
				}
				try {
					appContext.getToken(WelcomeActivity.this,
							"client_credentials", "by559a8de1c325c1",
							"aedd16f80c192661016eebe3ac35a6e7",networkHelper);
				} catch (Exception e) {
//					Toast.makeText(WelcomeActivity.this, "访问异常" + e,
//							Toast.LENGTH_LONG).show();
					e.printStackTrace();
					Log.v("获取token异常",e+"" );
				}
		
		viewPager = (ViewPager) view.findViewById(R.id.viewpage);
		startButton = (Button) view.findViewById(R.id.start_Button);
		startButton.setOnClickListener(WelcomeActivity.this);
		enter=(Button)view.findViewById(R.id.enter_app);
		enter.setOnClickListener(WelcomeActivity.this);
		indicatorLayout = (LinearLayout) view.findViewById(R.id.indicator);
		views = new ArrayList<View>();
		indicators = new ImageView[images.length]; 
		for (int i = 0; i < images.length; i++) {
			ImageView imageView = new ImageView(context);
			imageView.setBackgroundResource(images[i]);
			views.add(imageView);
			indicators[i] = new ImageView(context);
			indicators[i].setBackgroundResource(R.drawable.indicators_default);
			if (i == 0) {
				indicators[i].setBackgroundResource(R.drawable.indicators_now);
			}
			indicatorLayout.addView(indicators[i]);
		}
		pagerAdapter = new BasePagerAdapter(views);
		viewPager.setAdapter(pagerAdapter); 
		viewPager.setOnPageChangeListener(WelcomeActivity.this);
	}
	
	private void initAnimation() {
		animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
		view.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
			
						/*if (first) {
							intent = new Intent(AppStartActivity.this,
									WelcomeActivity.class);
						} else {
							intent = new Intent(AppStartActivity.this,
									HomePageActivity.class);
						}*/
						if(finish_a == false){
							finish_a = true;
							intent = new Intent(WelcomeActivity.this,
								HomePageActivity.class);
							intent.putExtra("nextBck", nextBck);
							startActivity(intent);
							overridePendingTransition(R.anim.s_in_from_right,
								R.anim.s_out_to_left);
							WelcomeActivity.this.finish();
						}
					}
				}, 2000);
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.start_Button) {
			new SharedConfig(this);
			SharedPreferences shared = SharedConfig.GetConfig();
			Editor editor = shared.edit();
			editor.putBoolean("First", false);
			editor.commit();
			
			if(finish_a == false){
				finish_a = true;
				intent = new Intent(WelcomeActivity.this,
					HomePageActivity.class);
				intent.putExtra("nextBck", nextBck);
				startActivity(intent);
				overridePendingTransition(R.anim.s_in_from_right,
					R.anim.s_out_to_left);
				WelcomeActivity.this.finish();
			}
			
		}
		if (v.getId() == R.id.enter_app) {
			new SharedConfig(this);
			SharedPreferences shared = SharedConfig.GetConfig();
			Editor editor = shared.edit();
			editor.putBoolean("First", false);
			editor.commit();
			
			if(finish_a == false){
				finish_a = true;
				intent = new Intent(WelcomeActivity.this,
					HomePageActivity.class);
				intent.putExtra("nextBck", nextBck);
				startActivity(intent);
//				overridePendingTransition(R.anim.s_in_from_right,
//					R.anim.s_out_to_left);
				WelcomeActivity.this.finish();
			}
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		if (arg0 == indicators.length - 1) {
			startButton.setVisibility(View.VISIBLE);
//			enter.setVisibility(View.GONE);
			
		} else {
			startButton.setVisibility(View.GONE);
//			enter.setVisibility(View.VISIBLE);
		}
		for (int i = 0; i < indicators.length; i++) {
			indicators[i]
					.setBackgroundResource(R.drawable.indicators_default);
			if (arg0 == i) {
				indicators[arg0].setBackgroundResource(R.drawable.indicators_now);
			}
		}
	}

	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
//		Toast.makeText(WelcomeActivity.this, "加载失败" + error, Toast.LENGTH_LONG)
//		.show();
		this.startService(new Intent(this,TokenIntentService.class));
		AppContext.setTokenSuccess(false);
		net_state = -1;
		Log.v("获取token",error.toString() );
	}

	@Override
	public void onDataChanged(String data) {
		// TODO Auto-generated method stub
		String access_token = null;
		JSONObject jsonObject = null;
		int code = -1;
		
		try {
			jsonObject=new JSONObject(data);
			code = jsonObject.getInt("code");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		if(net_state == GETTOKEN){
			if (code == 0) {
				try {
					JSONObject	tempdata=(JSONObject) jsonObject.get("data");
					access_token = tempdata.getString("access_token");
					Log.v("获取token",access_token+"1");
					AppContext.setAccess_token(access_token);
					AppContext.setTokenSuccess(true);
					this.startService(new Intent(this,TokenIntentService.class));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				initData();  //现提前加载几个商品，免得进入主页加载过慢
				/*Toast.makeText(WelcomeActivity.this, "获取token成功：" + access_token, Toast.LENGTH_LONG)
			.show();*/
			} else {
				net_state = -1;
				AppContext.setTokenSuccess(false);
				this.startService(new Intent(this,TokenIntentService.class));
				//			Toast.makeText(WelcomeActivity.this, "获取token失败：code=" + code, Toast.LENGTH_LONG)
				//			.show();
			}
		}else if(net_state == BABY){
			Gson gson = new Gson();
			String producData;
			try {
				final BitmapCacheHomageImage bitmapCache = new BitmapCacheHomageImage();
				producData = jsonObject.getString("data");
				JSONObject jsondata = new JSONObject(producData);
				String producList = jsondata.getString("list");
				List<ProductItem> productlist = gson.fromJson(producList,
						new TypeToken<List<ProductItem>>() {
						}.getType());
				if (productlist != null) {
					nextBck = productlist.get(0).getImg_post_bg();
					for(int k=0; k<productlist.size(); k++){
						final String url_bg = productlist.get(k).getImg_post_bg();
						ImageRequest imageRequest_bg = new ImageRequest(  url_bg,
								new Response.Listener<Bitmap>() {  
									@Override  
									public void onResponse(Bitmap response) {  
										bitmapCache.putBitmap(url_bg, response);
									}  
								}, 0, 0, Config.RGB_565, new Response.ErrorListener() {  
									@Override  
									public void onErrorResponse(VolleyError error) {  
										// imageView.setImageResource(R.drawable.default_image);  
									}  
								});
						imageRequest_bg.setTag("imageRequest_bg");
						AppContext.getHttpQueues().add(imageRequest_bg);
						AppContext.getHttpQueues().start();
						final String url_img = productlist.get(k).getImg_post();
						ImageRequest imageRequest_img = new ImageRequest(  url_img,
								new Response.Listener<Bitmap>() {  
									@Override  
									public void onResponse(Bitmap response) {  
										bitmapCache.putBitmap(url_img, response);
									}  
								}, 0, 0, Config.RGB_565, new Response.ErrorListener() {  
									@Override  
									public void onErrorResponse(VolleyError error) {  
										// imageView.setImageResource(R.drawable.default_image);  
									}  
								});
						imageRequest_img.setTag("imageRequest_img");
						AppContext.getHttpQueues().add(imageRequest_img);
						AppContext.getHttpQueues().start();
					}
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * 加载商品列表数据
	 */
	private void initData() {
		net_state = BABY;
		try {
			boolean YesOrNo = appContext.getProductList(WelcomeActivity.this, 1,
					4, "index",networkHelper);
			if (!YesOrNo) { // 如果没联网
				Toast.makeText(WelcomeActivity.this, "请检查网络连接", Toast.LENGTH_SHORT)
						.show();
				net_state = -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			net_state = -1;
		}

	}
}
