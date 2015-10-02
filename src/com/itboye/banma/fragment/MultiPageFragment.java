package com.itboye.banma.fragment;

import java.util.ArrayList;
import java.util.List;

import com.itboye.banma.R;
import com.itboye.banma.adapter.MyPagerAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MultiPageFragment  extends Fragment {
	private View chatView;
	private ViewPager viewPager;
	protected TextView one_title;
	protected TextView two_title;
	protected TextView three_title;
	private MyPagerAdapter myAdapter;
	protected List<Fragment> mFragmentList = new ArrayList<Fragment>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		chatView = inflater.inflate(R.layout.baby_multi_page, container, false);
		return chatView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();

	}

	private void initView() {
		one_title = (TextView) chatView.findViewById(R.id.one_title);
		two_title = (TextView) chatView.findViewById(R.id.two_title);
		three_title = (TextView) chatView.findViewById(R.id.three_title);
		myAdapter = new MyPagerAdapter(getChildFragmentManager(), mFragmentList);
		viewPager = (ViewPager) chatView.findViewById(R.id.viewPager);
		viewPager.setAdapter(myAdapter);
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				changTextColor(arg0);
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		one_title.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				viewPager.setCurrentItem(0);
			}
		});

		two_title.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				viewPager.setCurrentItem(1);
			}
		});

		three_title.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				viewPager.setCurrentItem(2);
			}
		});

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
			one_title.setBackgroundColor(this.getResources().getColor(R.color.red));
			break;
		case 1:
			two_title.setTextColor(this.getResources().getColor(
					R.color.white));
			two_title.setBackgroundColor(this.getResources().getColor(R.color.red));
			break;
		case 2:
			three_title.setTextColor(this.getResources().getColor(
					R.color.white));
			three_title.setBackgroundColor(this.getResources().getColor(R.color.red));
			break;
		}
		
	}


}
