package com.itboye.banma.adapter;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
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
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.itboye.banma.R;
import com.itboye.banma.activities.BabyActivity;
import com.itboye.banma.activities.OrderDetailActivity;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.entity.OrderItem;
import com.itboye.banma.entity.SkuStandard;
import com.itboye.banma.util.BaseViewHolder;
import com.itboye.banma.utils.BitmapCache;
import com.itboye.banma.utils.OrderBitmapCache;

public class OrderListItemAdapter  extends BaseAdapter {
	private Context context;
	private List<OrderItem> list = new ArrayList<OrderItem>();
	private ImageLoader imageLoader;

	public OrderListItemAdapter(Context context, List<OrderItem> list) {
		this.context = context;
		this.list = list;
		imageLoader = new ImageLoader(AppContext.getHttpQueues(),
				new OrderBitmapCache());
	}

	@Override
	public int getCount() {
		return list.size();
	}

	public void onDateChang(List<OrderItem> list) {
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
		final OrderItem order = list.get(position);
		ViewHolder holder;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.confirm_order_item,
					parent, false);
			holder = new ViewHolder();
			holder.order_pic = (ImageView) view.findViewById(R.id.order_pic);
			holder.order_name = (TextView) view.findViewById(R.id.order_name);
			holder.order_standard = (TextView) view.findViewById(R.id.order_standard);
			holder.order_price = (TextView) view.findViewById(R.id.order_price);
			holder.order_number = (TextView) view.findViewById(R.id.order_number);
			holder.line = view.findViewById(R.id.line);
			holder.listener = ImageLoader.getImageListener(holder.order_pic,
					0, R.drawable.loading_image_baby);
			view.setTag(holder);
		}else{
			holder = (ViewHolder) view.getTag();
		}

		//String urlimg = AppContext.getImg()+order.getImg_url();
		imageLoader.get(order.getImg_url(), holder.listener, 150, 150);
		
		holder.order_name.setText(order.getName());
		if(order.getSku_desc()==null || order.getSku_desc().length()<=0){
			holder.order_standard.setText("无规格参数");
		}else{
			holder.order_standard.setText(order.getSku_desc());
		}
		holder.order_price.setText("￥" + order.getPrice());
		// order_number.setText("×"+skuStandard.getNum());
		holder.order_number.setText("×"+order.getCount());
		int num = getCount()-1;
		if(position >= num){
			holder.line.setVisibility(View.GONE);
		}else{
			holder.line.setVisibility(View.VISIBLE);
		}
		
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, BabyActivity.class);
				intent.putExtra("PID", Integer.valueOf(order.getP_id()));
				context.startActivity(intent);
				((Activity) context).overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			}
		});
		
		return view;
	}
	
	private static class ViewHolder {
		ImageView order_pic;
		TextView order_name;
		TextView order_standard;
		TextView order_price;
		TextView order_number;
		View line;
		ImageListener listener;
	}
	
	/**
	 * 刷新列表
	 * 
	 * @param newList
	 */
	public void notifyDataSetChanged(List<OrderItem> newList) {
		this.list = newList;
		notifyDataSetChanged();
	}
}
