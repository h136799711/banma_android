package com.itboye.banma.fragment;

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
import com.itboye.banma.utils.BitmapCache;
import com.itboye.banma.utils.BitmapCacheHomageImage;
import com.itboye.banma.view.DrawableChangeView;
import com.itboye.banma.view.FancyCoverFlow;

import android.R.integer;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
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
	private Drawable[] drawables;
	private Animation mHiddenAction;
	private Animation mShowAction;
	BitmapCacheHomageImage bitmapCache = new BitmapCacheHomageImage();
	Boolean YesOrNo;
	int state;
	int one_bk=0, two_bk=0;  //保证前两个背景已经加载好的标志，因为界面显示必须用到前两个背景
	private String nextBck;   //传到首页暂时充当背景图片的地址，根据这个地址可以再缓存中找到图片

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
		mHiddenAction = new AlphaAnimation(1.0f, 1.0f);
		mShowAction = new AlphaAnimation(0.0f, 1.0f);
		mHiddenAction.setDuration(1000);
		mShowAction.setDuration(1000);
		initView();
		return chatView;
	}

	private void initView() {
		Intent intent = getActivity().getIntent();
		nextBck = intent.getStringExtra("nextBck");
		// 旋转等待页
				wait_ll = (LinearLayout) chatView.findViewById(R.id.wait_ll);
				retry_img = (ImageView) chatView.findViewById(R.id.retry_img);
				loading_ll = (LinearLayout) chatView.findViewById(R.id.loading_ll);
				frame_layout = (FrameLayout) chatView.findViewById(R.id.frame_layout);
				if(bitmapCache.getBitmap(nextBck) != null){
					Drawable drawable = new BitmapDrawable(bitmapCache.getBitmap(nextBck));
					wait_ll.setBackgroundDrawable(drawable);
				}
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
		
		wait_ll.setVisibility(View.VISIBLE);
		retry_img.setVisibility(View.GONE);
		loading_ll.setVisibility(View.GONE);
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
					darwableView.setPosition(arg0);
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
//		Toast.makeText(getActivity(), "onErrorHappened" + error.toString(), Toast.LENGTH_SHORT)
//		.show();
		wait_ll.setVisibility(View.VISIBLE);
		retry_img.setVisibility(View.VISIBLE);
		loading_ll.setVisibility(View.GONE);
		frame_layout.setVisibility(View.GONE);
	}

	@Override
	public void onDataChanged(String data) {
		state += 1;
		Gson gson = new Gson();
		productlist = new ArrayList<ProductItem>();
		JSONObject jsondata;
		try {
			jsondata = new JSONObject(data);

			int code = jsondata.getInt("code");
			if (code == 0) {
				/*Toast.makeText(getActivity(), "code=1" + data.toString(), Toast.LENGTH_SHORT)
				.show();*/
				appContext.setImg(Constant.URL+"/Api/Picture/index?id=");
				String producData = jsondata.getString("data");
				jsondata = new JSONObject(producData);
				String producList = jsondata.getString("list");
				productlist = gson.fromJson(producList,
						new TypeToken<List<ProductItem>>() {
						}.getType());
				if (productlist != null) {
			
					drawables = new Drawable[productlist.size()+2];
					//初始化，避免有些加载慢而引起的异常
					for(int i=0; i<productlist.size()+2; i++){
						drawables[i] = getActivity().getResources().getDrawable(R.drawable.back001);
					}
					for(int k=0; k<productlist.size(); k++){
						final int pos = k;
						if(bitmapCache.getBitmap(productlist.get(k).getImg_post_bg()) != null){
							Drawable drawable = new BitmapDrawable(bitmapCache.getBitmap(productlist.get(k).getImg_post_bg()));
							drawables[pos] = drawable;
							if(pos == 0){
								one_bk = 1;
							}else if(pos == 1){
								two_bk = 1;
							}
						}else{
							ImageRequest imageRequest = new ImageRequest(  
									productlist.get(k).getImg_post_bg(),  
									new Response.Listener<Bitmap>() {  
										@Override  
										public void onResponse(Bitmap response) {  
											Drawable drawable = new BitmapDrawable(response);
											drawables[pos] = drawable;
											if(pos == 0){
												one_bk = 1;
											}else if(pos == 1){
												two_bk = 1;
											}
										}  
									}, 0, 0, Config.RGB_565, new Response.ErrorListener() {  
										@Override  
										public void onErrorResponse(VolleyError error) {  
											// imageView.setImageResource(R.drawable.default_image);  
										}  
									});
							imageRequest.setTag("imageRequest");
							AppContext.getHttpQueues().add(imageRequest);
							AppContext.getHttpQueues().start();
						}
					}
					
					Thread thread=new Thread(new Runnable()  
			        {  
			            @Override  
			            public void run()  
			            {   
			            	
			                Message message=new Message();  
			                message.what=1;
			                
			               /* one_bk = 1 ; two_bk = 1;
			                mHandler.sendMessage(message);*/
			                
			                while(true){
			                	if(productlist.size()<=1){
			                		mHandler.sendMessage(message); 
			                		break;
			                	}else
								if(one_bk == 1 && two_bk == 1){
									mHandler.sendMessage(message);  
									break;
								}
							}
			                
			            }  
			        });  
			        thread.start();  
					
					
				}

			} else {
				/*Toast.makeText(getActivity(), "code=0" + data.toString(), Toast.LENGTH_SHORT)
				.show();*/
				if(state<=2){
					/*Toast.makeText(getActivity(), "code=00" + data.toString(), Toast.LENGTH_SHORT)
					.show();*/
					initData();
				}
				else{
//					Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_LONG).show();
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
	public Handler mHandler=new Handler()  
    {  
        public void handleMessage(Message msg)  
        {  
            switch(msg.what)  
            {  
            case 1:  
            	showListView(productlist);
            	wait_ll.startAnimation(mHiddenAction);
				wait_ll.setVisibility(View.GONE);
				retry_img.setVisibility(View.GONE);
				loading_ll.setVisibility(View.GONE);
				frame_layout.startAnimation(mShowAction);
				frame_layout.setVisibility(View.VISIBLE); 
                break;  
            default:  
                break;        
            }  
            super.handleMessage(msg);  
        }  
    };  

}
