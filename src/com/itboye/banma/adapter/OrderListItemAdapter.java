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

public class OrderListItemAdapter  extends BaseAdapter {
	private Context context;
	private List<OrderItem> list = new ArrayList<OrderItem>();

	public OrderListItemAdapter(Context context, List<OrderItem> list) {
		this.context = context;
		this.list = list;
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
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.confirm_order_item,
					parent, false);
			ImageView order_pic = BaseViewHolder.get(view, R.id.order_pic);
			ImageListener listener = ImageLoader.getImageListener(order_pic,
					0, R.drawable.image_load_fail);
			ImageLoader imageLoader = new ImageLoader(AppContext.getHttpQueues(),
					new BitmapCache());
			//String urlimg = AppContext.getImg()+order.getImg_url();
			imageLoader.get(order.getImg_url(), listener, 80, 80);
		}
		
		TextView order_name = BaseViewHolder.get(view, R.id.order_name);
		TextView order_standard = BaseViewHolder.get(view, R.id.order_standard);
		TextView order_price = BaseViewHolder.get(view, R.id.order_price);
		TextView order_number = BaseViewHolder.get(view, R.id.order_number);
		View line = BaseViewHolder.get(view, R.id.line);
		
		
		
		order_name.setText(order.getName());
		if(order.getSku_desc()==null || order.getSku_desc().length()<=0){
			order_standard.setText("无规格参数");
		}else{
			order_standard.setText(order.getSku_desc());
		}
		order_price.setText("￥" + order.getPrice());
		// order_number.setText("×"+skuStandard.getNum());
		order_number.setText("×"+order.getCount());
		int num = getCount()-1;
		if(position >= num){
			line.setVisibility(View.GONE);
		}else{
			line.setVisibility(View.VISIBLE);
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
}
