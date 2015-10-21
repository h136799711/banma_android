package com.itboye.banma.fragment;

import java.util.ArrayList;
import java.util.List;

import com.itboye.banma.R;
import com.itboye.banma.adapter.FancyCoverFlowSampleAdapter;
import com.itboye.banma.adapter.MyPageAdapter;
import com.itboye.banma.adapter.RotateDownTransformer;
import com.itboye.banma.view.FancyCoverFlow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class HomePageFragment  extends Fragment{
	//private AppContext appContext;
	private View chatView;
	private ViewPager mViewPager;
	private int[] mImageIds = new int[]{R.drawable.guide_image1, R.drawable.guide_image2, R.drawable.guide_image3,
			R.drawable.picture_1, R.drawable.picture_2, R.drawable.picture_3, R.drawable.picture_4, R.drawable.picture_5 };
	private List<View> mImages = new ArrayList<View>();
	private MyPageAdapter adapter; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//appContext = (AppContext) getActivity().getApplication();
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
		final FancyCoverFlow fancyCoverFlow = (FancyCoverFlow) chatView.findViewById(R.id.fancyCoverFlow);
        fancyCoverFlow.setAdapter(new FancyCoverFlowSampleAdapter());
		//fancyCoverFlow.setCallbackDuringFling(false);  //设置为false是当滑动结束后更新item
		//fancyCoverFlow.setSelection(5);			//设置默认
        fancyCoverFlow.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				mViewPager.setCurrentItem(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
        	
		});
        
		//查找布局文件用LayoutInflater.inflate
        LayoutInflater inflater = getActivity().getLayoutInflater();
        for(int i=0; i<mImageIds.length; i++){
        	View convertView = inflater.inflate(R.layout.view, null);
            
    		mImages.add(convertView);
    		
        }
		adapter = new MyPageAdapter(mImages, mImageIds);
		mViewPager = (ViewPager) chatView.findViewById(R.id.id_viewpager);
		//添加动画效果
		//mViewPager.setPageTransformer(true, arg1);
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
