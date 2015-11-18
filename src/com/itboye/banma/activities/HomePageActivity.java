package com.itboye.banma.activities;

import java.util.ArrayList;
import java.util.List;

import com.itboye.banma.R;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.fragment.BabyOrderFragment;
import com.itboye.banma.fragment.BabyParameterFragment;
import com.itboye.banma.fragment.CenterFragment;
import com.itboye.banma.fragment.CenterFragment.ChangeItemListener;
import com.itboye.banma.fragment.FindFragment;
import com.itboye.banma.fragment.HomePageFragment;
import com.itboye.banma.fragment.OrderAllFragment;
import com.itboye.banma.fragment.OrderAllFragment.AllGoShoppingListener;
import com.itboye.banma.fragment.OrderStateFragment.GoShoppingListener;
import com.umeng.analytics.MobclickAgent;

import android.R.integer;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomePageActivity extends FragmentActivity  implements OnClickListener,
ChangeItemListener,GoShoppingListener,AllGoShoppingListener{
	private ViewPager mViewPager;
	private List<Fragment> mTabs = new ArrayList<Fragment>();
	private FragmentPagerAdapter mAdapter;
	private HomePageFragment homePageFragment;
	private BabyOrderFragment babyCommentFragment;
	private CenterFragment centerFragment;
	private FindFragment findFragment;
	private List<LinearLayout> mTabIndicator = new ArrayList<LinearLayout>();
	private List<ImageView> imageViewlist = new ArrayList<ImageView>();
	private List<TextView> textViewlist = new ArrayList<TextView>();
	private int[] ic = {R.drawable.banma_home_normal, R.drawable.banma_order_normal,R.drawable.faxian_un, R.drawable.banma_mine_normal};
	private int[] ic_sel = {R.drawable.banma_home_select, R.drawable.banma_order_select,R.drawable.faxian ,R.drawable.banma_mine_select};
	private AppContext appContext;
	private long exitTime = 0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homepage);
		appContext = (AppContext) getApplication();
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        initView();
        mViewPager.setOffscreenPageLimit(3);
		mViewPager.setAdapter(mAdapter);
    }

	private void initView() {
		
		homePageFragment = new HomePageFragment();
		babyCommentFragment = new BabyOrderFragment();
		centerFragment = new CenterFragment();
		findFragment=new FindFragment();
		mTabs.add(homePageFragment);
		mTabs.add(babyCommentFragment);
		mTabs.add(findFragment);
		mTabs.add(centerFragment);
		
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
		{

			@Override
			public int getCount()
			{
				return mTabs.size();
			}

			@Override
			public Fragment getItem(int arg0)
			{
				return mTabs.get(arg0);
			}
		};

		initTabIndicator();

	}
	
	//友盟统计
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		}
		public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		}
	
	private void initTabIndicator()
	{
		LinearLayout one = (LinearLayout) findViewById(R.id.tab_one);
		LinearLayout two = (LinearLayout) findViewById(R.id.tab_two);
		LinearLayout three = (LinearLayout) findViewById(R.id.tab_three);
		LinearLayout four = (LinearLayout) findViewById(R.id.tab_four);
		ImageView ima_one = (ImageView) findViewById(R.id.ic_tab_one);
		ImageView ima_two = (ImageView) findViewById(R.id.ic_tab_two);
		ImageView ima_three = (ImageView) findViewById(R.id.ic_tab_three);
		ImageView ima_four = (ImageView) findViewById(R.id.ic_tab_four);
		TextView tex_one = (TextView) findViewById(R.id.tex_tab_one);
		TextView tex_two = (TextView) findViewById(R.id.tex_tab_two);
		TextView tex_three = (TextView) findViewById(R.id.tex_tab_three);
		TextView tex_four = (TextView) findViewById(R.id.tex_tab_four);

		mTabIndicator.add(one);
		mTabIndicator.add(two);
		mTabIndicator.add(three);
		mTabIndicator.add(four);
		
		imageViewlist.add(ima_one);
		imageViewlist.add(ima_two);
		imageViewlist.add(ima_three);
		imageViewlist.add(ima_four);
		
		textViewlist.add(tex_one);
		textViewlist.add(tex_two);
		textViewlist.add(tex_three);
		textViewlist.add(tex_four);

		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);

	}

	@Override
	public void onClick(View v)
	{
		
		switch (v.getId())
		{
		case R.id.tab_one:
			changTabColor(0);
			mViewPager.setCurrentItem(0, false);
			break;
		case R.id.tab_two:
			if(appContext.isLogin()){
				changTabColor(1);
				mViewPager.setCurrentItem(1, false);
	
			} else{
				startActivityForResult(new Intent(HomePageActivity.this,LoginActivity.class),100);
				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			}

			break;
		case R.id.tab_three:
			changTabColor(2);
			mViewPager.setCurrentItem(2, false);
			break;
		case R.id.tab_four:
			changTabColor(3);
			mViewPager.setCurrentItem(3, false);
			break;
		}

	}
	/**
	 * 改变Tab颜色
	 * @param arg0
	 */
	private void changTabColor(int arg0) {
		for(int k=0; k<4; k++){
			imageViewlist.get(k).setImageResource(ic[k]);
			textViewlist.get(k).setTextColor(this.getResources().getColor(R.color.tab_text));
		}
		imageViewlist.get(arg0).setImageResource(ic_sel[arg0]);
		textViewlist.get(arg0).setTextColor(this.getResources().getColor(R.color.tab_text_sel));
		
	}

	@Override
	public void onItemChanged(int position) {
		// TODO Auto-generated method stub
		changTabColor(position);
		mViewPager.setCurrentItem(position,false);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
	            exitTime = System.currentTimeMillis();   
	        } else {
	            finish();
	            System.exit(0);
	        }
	        return true;   
	    }
	    return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onChanged(int position ) {
		// TODO Auto-generated method stub
		changTabColor(position);
		mViewPager.setCurrentItem(position,false);
	}
}