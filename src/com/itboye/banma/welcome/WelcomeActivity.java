package com.itboye.banma.welcome;

import java.util.ArrayList;

import com.itboye.banma.R;
import com.itboye.banma.utils.SharedConfig;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class WelcomeActivity extends Activity implements OnPageChangeListener,
		OnClickListener {
	private Context context;
	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;
	private Button startButton;
	private LinearLayout indicatorLayout;
	private ArrayList<View> views;
	private ImageView[] indicators = null;
	private int[] images;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		context = this;
		images = new int[] { R.drawable.welcome_01, R.drawable.welcome_02,
				R.drawable.welcome_03 };
		initView();

	}

	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.viewpage);
		startButton = (Button) findViewById(R.id.start_Button);
		startButton.setOnClickListener(this);
		indicatorLayout = (LinearLayout) findViewById(R.id.indicator);
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
		viewPager.setOnPageChangeListener(this);
	}
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.start_Button) {
			new SharedConfig(this);
			SharedPreferences shared = SharedConfig.GetConfig();
			Editor editor = shared.edit();
			editor.putBoolean("First", false);
			editor.commit();
			
			startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			this.finish();
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
		} else {
			startButton.setVisibility(View.INVISIBLE);
		}
		for (int i = 0; i < indicators.length; i++) {
			indicators[i]
					.setBackgroundResource(R.drawable.indicators_default);
			if (arg0 == i) {
				indicators[arg0].setBackgroundResource(R.drawable.indicators_now);
			}
		}
	}
}
