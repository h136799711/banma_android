package com.itboye.banma.fragment;

import android.os.Bundle;

public class BabyMultiPageFragment extends MultiPageFragment{
	
	private BabyDetailFragment detailFragment;
	private BabyParameterFragment parameterFragment;
	private BabyCommentFragment commentFragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		detailFragment = new BabyDetailFragment();
		parameterFragment = new BabyParameterFragment();
		commentFragment = new BabyCommentFragment();
		mFragmentList.add(detailFragment);
		mFragmentList.add(parameterFragment);
		mFragmentList.add(commentFragment);
		
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        
        one_title.setText("图文详情");
    	two_title.setText("产品参数");
    	three_title.setText("累计评价");
        
    }

}
