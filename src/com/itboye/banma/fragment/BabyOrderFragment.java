package com.itboye.banma.fragment;

import java.util.ArrayList;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.itboye.banma.R;
import com.itboye.banma.adapter.MyFragmentPagerAdapter;

public class BabyOrderFragment extends Fragment implements OnClickListener{
	private View view;
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
    Fragment daifa,daishou,daifu,daiping,quanbu;
//	DaiShouFragemt daishou;
//	DaiPingFragment daiping;
//	QuanBuFragment quanbu;
//	DaiFuFragment daifu;
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_baby_order, container,false);
        resources = getResources();
        InitTextView(view);
        InitWidth(view);
        InitViewPager(view);
        TranslateAnimation animation = new TranslateAnimation(position_one, position_one, 0, 0);
        tv_quanbu.setTextColor(resources.getColor(R.color.red));
        animation.setFillAfter(true);
        animation.setDuration(AnimationTimeSeconds);
        ivBottomLine.startAnimation(animation);
        return view;    
    }
	private void InitTextView(View parentView) {
		  tv_quanbu = (TextView) parentView.findViewById(R.id.tv_quanbu);
		  tv_daifu = (TextView) parentView.findViewById(R.id.tv_daifu);
		  tv_daifa = (TextView) parentView.findViewById(R.id.tv_daifa);
		  tv_daishou = (TextView) parentView.findViewById(R.id.tv_daishou);
		  tv_daiping=(TextView) parentView.findViewById(R.id.tv_daiping);

		  tv_quanbu.setOnClickListener(new MyOnClickListener(0));
		  tv_daifu.setOnClickListener(new MyOnClickListener(1));
		  tv_daifa.setOnClickListener(new MyOnClickListener(2));
		  tv_daishou.setOnClickListener(new MyOnClickListener(3));
		  tv_daiping.setOnClickListener(new MyOnClickListener(4) );
	    }

	    private void InitViewPager(View parentView) {
	        mPager = (ViewPager) parentView.findViewById(R.id.vPager);
	        fragmentsList = new ArrayList<Fragment>();

	        daifa = new OrderDaiFaFragment();
	        daishou=new OrderDaiShouFragment();
	        daifu=new OrderDaiFuFragment();
	        quanbu=new OrderAllFragment();
	        daiping=new OrderDaiPingFragment();

	        fragmentsList.add(quanbu);
	        fragmentsList.add(daifu);
	        fragmentsList.add(daifa);
	        fragmentsList.add(daishou);
	        fragmentsList.add(daiping);

	        mPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentsList));
	        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	        //mPager.setCurrentItem(0);
	        mPager.setOffscreenPageLimit(0);
	        
	    }

	    private void InitWidth(View parentView) {
	        ivBottomLine = (ImageView) parentView.findViewById(R.id.iv_bottom_line);
	        bottomLineWidth = ivBottomLine.getLayoutParams().width;
	        DisplayMetrics dm = new DisplayMetrics();
	        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
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
	           currIndex = arg0;
	           animation.setFillAfter(true);
	           animation.setDuration(AnimationTimeSeconds);
	           ivBottomLine.startAnimation(animation);
	        }

	        @Override
	        public void onPageScrolled(int arg0, float arg1, int arg2) {
	        }

	        @Override
	        public void onPageScrollStateChanged(int arg0) {
	        }
	    }

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.title:
				
				break;

			default:
				break;
			}
		}
}
