package com.itboye.banma.activities;

import java.util.ArrayList;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.itboye.banma.R;
import com.itboye.banma.adapter.MyFragmentPagerAdapter;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
import com.itboye.banma.fragment.OrderAllFragment;
import com.itboye.banma.fragment.OrderStateFragment;
import com.itboye.banma.utils.BitmapCache;

public class BabyOrderActivity extends FragmentActivity implements OnClickListener{
	public static int AnimationTimeSeconds=100;	
   private Resources resources;
    private ViewPager mPager;
    private ArrayList<Fragment> fragmentsList;
    private ImageView ivBottomLine;
    private TextView tv_quanbu, tv_daishou,tv_daifa,tv_daifu,tv_daiping;

    private int currIndex = 0;
    private int bottomLineWidth;
    private int offset = 0;
    private int position_one;
    public final static int num = 5; 
    private AppContext appContext;
    private Fragment daifa,daishou,daifu,daiping,quanbu;
    private TextView title;
    private ImageView iv_back;
    private int orderState;  //订单状态

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_baby_order);
		appContext = (AppContext) getApplication();
		title=(TextView)findViewById(R.id.title);
		title.setText("全部订单");
		iv_back=(ImageView)findViewById(R.id.iv_back);
		iv_back.setVisibility(View.VISIBLE);
		iv_back.setOnClickListener(this);
		Intent intent = getIntent();
		orderState = intent.getIntExtra("orderState", 0);
		
		resources = getResources();
        InitTextView();
        InitWidth();
        InitViewPager();
        /*TranslateAnimation animation = new TranslateAnimation(position_one, position_one, 0, 0);
        tv_quanbu.setTextColor(resources.getColor(R.color.red));
        animation.setFillAfter(true);
        animation.setDuration(AnimationTimeSeconds);
        ivBottomLine.startAnimation(animation);*/
    }
	private void InitTextView() {

		
		  tv_quanbu = (TextView) findViewById(R.id.tv_quanbu);
		  tv_daifu = (TextView) findViewById(R.id.tv_daifu);
		  tv_daifa = (TextView) findViewById(R.id.tv_daifa);
		  tv_daishou = (TextView) findViewById(R.id.tv_daishou);
		  tv_daiping=(TextView) findViewById(R.id.tv_daiping);

		  tv_quanbu.setOnClickListener(new MyOnClickListener(0));
		  tv_daifu.setOnClickListener(new MyOnClickListener(1));
		  tv_daifa.setOnClickListener(new MyOnClickListener(2));
		  tv_daishou.setOnClickListener(new MyOnClickListener(3));
		  tv_daiping.setOnClickListener(new MyOnClickListener(4) );
	    }

	    private void InitViewPager() {
	        mPager = (ViewPager) findViewById(R.id.vPager);
	        fragmentsList = new ArrayList<Fragment>();

	        daifa = new OrderStateFragment(Constant.STATE_DAIFAHUO);
	        daishou=new OrderStateFragment(Constant.STATE_DAISHOUHUO);
	        daifu=new OrderStateFragment(Constant.STATE_DAIFUKUAN);
	        quanbu=new OrderAllFragment();
	        daiping=new OrderStateFragment(Constant.STATE_YISHOUHUO);

	        fragmentsList.add(quanbu);
	        fragmentsList.add(daifu);
	        fragmentsList.add(daifa);
	        fragmentsList.add(daishou);
	        fragmentsList.add(daiping);

	        mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentsList));
	        changTitle(orderState);
	        currIndex = orderState;
	        mPager.setCurrentItem (orderState); 
	        MyOnPageChangeListener myOnPageChangeListener = new MyOnPageChangeListener();
	        mPager.setOnPageChangeListener(myOnPageChangeListener);
	        //mPager.setCurrentItem(0);
	        mPager.setOffscreenPageLimit(0);
	        
	        
	    }

	    private void InitWidth() {
	        ivBottomLine = (ImageView) findViewById(R.id.iv_bottom_line);
	        bottomLineWidth = ivBottomLine.getLayoutParams().width;
	        DisplayMetrics dm = new DisplayMetrics();
	        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
	        int screenW = dm.widthPixels;
	        position_one = (int) ((screenW / num - bottomLineWidth)/2);
	        offset = (int) (screenW / num);
	      //  offset = avg + position_one;
	    }

	    public class MyOnClickListener implements View.OnClickListener {
	        private int index = 0;

	        public MyOnClickListener(int i) {
	            index = i;
	        }

	        @Override
	        public void onClick(View v) {
	            mPager.setCurrentItem(index);
	        }
	    };

	    public class MyOnPageChangeListener implements OnPageChangeListener {

	        @Override
	        public void onPageSelected(int arg0) {
	        	changTitle(arg0);
	           currIndex = arg0;
	           
	        }

	        @Override
	        public void onPageScrolled(int arg0, float arg1, int arg2) {
	        }

	        @Override
	        public void onPageScrollStateChanged(int arg0) {
	        }
	    }
	    
	    public void changTitle(int arg0){
	    	Animation animation = null;
            
            switch (arg0) {
            case 0:	 
                animation = new TranslateAnimation(currIndex*offset+position_one,arg0*offset+position_one, 0, 0);
                tv_quanbu.setTextColor(resources.getColor(R.color.red));
                tv_daishou.setTextColor(resources.getColor(R.color.grey));
                tv_daifa.setTextColor(resources.getColor(R.color.grey));
                tv_daifu.setTextColor(resources.getColor(R.color.grey));
                tv_daiping.setTextColor(resources.getColor(R.color.grey));
                break;
            case 1:
            	animation = new TranslateAnimation(currIndex*offset+position_one, arg0*offset+position_one, 0, 0);
            	tv_quanbu.setTextColor(resources.getColor(R.color.grey));
                tv_daifu.setTextColor(resources.getColor(R.color.red));
                tv_daifa.setTextColor(resources.getColor(R.color.grey));
                tv_daishou.setTextColor(resources.getColor(R.color.grey));
                tv_daiping.setTextColor(resources.getColor(R.color.grey));
                break;
            case 2:
            	animation = new TranslateAnimation(currIndex*offset+position_one, arg0*offset+position_one, 0, 0);
            	tv_quanbu.setTextColor(resources.getColor(R.color.grey));
                tv_daishou.setTextColor(resources.getColor(R.color.grey));
                tv_daifa.setTextColor(resources.getColor(R.color.red));
                tv_daifu.setTextColor(resources.getColor(R.color.grey));
                tv_daiping.setTextColor(resources.getColor(R.color.grey));
                break;
            case 3:
            	animation = new TranslateAnimation(currIndex*offset+position_one, arg0*offset+position_one, 0, 0);
            	tv_quanbu.setTextColor(resources.getColor(R.color.grey));
                tv_daifu.setTextColor(resources.getColor(R.color.grey));
                tv_daifa.setTextColor(resources.getColor(R.color.grey));
                tv_daishou.setTextColor(resources.getColor(R.color.red));
                tv_daiping.setTextColor(resources.getColor(R.color.grey));
                break;
            case 4:
            	animation = new TranslateAnimation(currIndex*offset+position_one, arg0*offset+position_one, 0, 0);
            	tv_quanbu.setTextColor(resources.getColor(R.color.grey));
                tv_daishou.setTextColor(resources.getColor(R.color.grey));
                tv_daifa.setTextColor(resources.getColor(R.color.grey));
                tv_daifu.setTextColor(resources.getColor(R.color.grey));
                tv_daiping.setTextColor(resources.getColor(R.color.red));
                break;
            }
            animation.setFillAfter(true);
	        animation.setDuration(AnimationTimeSeconds);
	        ivBottomLine.startAnimation(animation);
	    }

	    @Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				finish();
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
			}
			return false;
		}
	    
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.title:
				
				break;
				
			case R.id.iv_back:
				// 返回
				finish();
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
				break;

			default:
				break;
			}
		}
}
