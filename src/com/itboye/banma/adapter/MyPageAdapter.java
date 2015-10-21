package com.itboye.banma.adapter;

import java.util.ArrayList;
import java.util.List;

import com.itboye.banma.R;
import com.itboye.banma.util.BaseViewHolder;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class MyPageAdapter extends PagerAdapter{
	private List<View> mImages;
	private int[] mImageIds;
	
	public MyPageAdapter(List<View> mImages, int[] mImageIds){
		this.mImageIds = mImageIds;
		this.mImages = mImages;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mImages.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub
		return view == object;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mImages.get(position) );
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View convertView = mImages.get(position);
		ImageView imageView = BaseViewHolder.get(convertView,
				R.id.imageView);
		imageView.setImageResource(mImageIds[position]);
		//imageView.setScaleType(ScaleType.CENTER_CROP);
		
		
		container.addView(convertView);
		
		return convertView;
	}
}
