package com.itboye.banma.adapter;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.itboye.banma.R;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.utils.BitmapCache;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class BabyDetailAdapter extends BaseAdapter {
	String[] imgsurl;
	private Context mContext;
	
	public BabyDetailAdapter(Context context, String[] imgs){
		imgsurl = imgs;
		mContext= context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imgsurl.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.detail_list_item, parent, false);
		}
		ImageLoader imageLoader = new ImageLoader(AppContext.getHttpQueues(), new BitmapCache()); 
		NetworkImageView detailImage = (NetworkImageView) convertView.findViewById(R.id.pic_detail);
		//detailImage.setDefaultImageResId(R.drawable.image_loading);  //加载中显示的图片
		detailImage.setErrorImageResId(R.drawable.image_load_fail);  //加载失败显示的图片
		detailImage.setImageUrl(imgsurl[position], imageLoader);
		return convertView;
	}

}
