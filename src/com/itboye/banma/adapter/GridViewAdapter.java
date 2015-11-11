package com.itboye.banma.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.itboye.banma.R;
import com.itboye.banma.activities.BabyActivity;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.entity.ProductItem;
import com.itboye.banma.util.BaseViewHolder;
import com.itboye.banma.utils.BitmapCache;

public class GridViewAdapter extends BaseAdapter {

	private Context context;
	private List<ProductItem> list = new ArrayList<ProductItem>();

	public GridViewAdapter(Context context, List<ProductItem> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	public void onDateChang(List<ProductItem> list) {
		this.list = list;
		this.notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}
	
	@Override
	public boolean isEnabled(int position) {
		return false;
	}
	
	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		final ProductItem order = list.get(position);
		System.out.println(order+"商品");
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.gridview_item,
					parent, false);
		}
		ImageView order_pic = BaseViewHolder.get(view, R.id.iv_find);
		TextView order_standard = BaseViewHolder.get(view, R.id.tv_find_des);
		TextView order_price = BaseViewHolder.get(view, R.id.tv_find_price);
		ImageLoader imageLoader = new ImageLoader(AppContext.getHttpQueues(),
				new BitmapCache());
		ImageListener listener = ImageLoader.getImageListener(order_pic,
				R.drawable.image_loading, R.drawable.image_load_fail);
		imageLoader.get(order.getMain_img(), listener);
		order_standard.setText(order.getName());
		order_price.setText("￥" + order.getPrice());
		// order_number.setText("×"+skuStandard.getNum());
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, BabyActivity.class);
				intent.putExtra("PID", list.get(position).getId());
				context.startActivity(intent);
				((Activity) context).overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			}
		});
		
		return view;
	}
}
