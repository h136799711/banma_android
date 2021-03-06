package com.itboye.banma.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.itboye.banma.R;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.entity.SkuStandard;
import com.itboye.banma.util.BaseViewHolder;
import com.itboye.banma.utils.BitmapCache;

public class ConfirmOrderListAdapter  extends BaseAdapter {
	private Context context;
	private List<SkuStandard> list = new ArrayList<SkuStandard>();

	public ConfirmOrderListAdapter(Context context, List<SkuStandard> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	public void onDateChang(List<SkuStandard> list) {
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
	public View getView(final int position, View view, ViewGroup parent) {
		final SkuStandard order = list.get(position);
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.confirm_order_item,
					parent, false);
		}
		ImageView order_pic = BaseViewHolder.get(view, R.id.order_pic);
		TextView order_name = BaseViewHolder.get(view, R.id.order_name);
		TextView order_standard = BaseViewHolder.get(view, R.id.order_standard);
		TextView order_price = BaseViewHolder.get(view, R.id.order_price);
		TextView order_number = BaseViewHolder.get(view, R.id.order_number);
		View line = BaseViewHolder.get(view, R.id.line);
		ImageLoader imageLoader = new ImageLoader(AppContext.getHttpQueues(),
				new BitmapCache());
		ImageListener listener = ImageLoader.getImageListener(order_pic,
				R.drawable.loading_image_baby, R.drawable.loading_image_baby);
		imageLoader.get(order.getIcon_url(), listener, 0, 0);
		order_name.setText(order.getName());
		if(order.getSku()==null || order.getSku().isEmpty() || order.getSku().length()<=0 ){
			order_standard.setText("暂无规格说明");
		}else{
			order_standard.setText(order.getSku());
		}
		order_price.setText("￥" + order.getPrice());
		// order_number.setText("×"+skuStandard.getNum());
		order_number.setText("×"+order.getNum());
		int num = getCount()-1;
		if(position >= num){
			line.setVisibility(View.GONE);
		}else{
			line.setVisibility(View.VISIBLE);
		}
		return view;
	}
}
