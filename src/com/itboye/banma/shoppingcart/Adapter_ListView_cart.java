package com.itboye.banma.shoppingcart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.itboye.banma.R;
import com.itboye.banma.activities.BabyActivity;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.entity.ProductItem;
import com.itboye.banma.utils.BitmapCache;
import com.nineoldandroids.view.ViewHelper;

public class Adapter_ListView_cart extends BaseAdapter  {
	private Context context;
	private ImageLoader imageLoader;
	private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
	private onCheckedChanged listener;
	private onAddChanged addListener;
	private onReduceChanged reduceListener;
//	private onGuiGeChanged guiListener;

	public Adapter_ListView_cart(Context context, ArrayList<HashMap<String, Object>> arrayList) {
		this.imageLoader= new ImageLoader(AppContext.getHttpQueues(),
				new BitmapCache());
		this.context = context;
		this.arrayList = arrayList;
	}

//	public Adapter_ListView_cart(Context context) {
//		this.context = context;
//	}
	public void onDateChang( ArrayList<HashMap<String, Object>> arrayList) {
		this.arrayList = arrayList;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		Log.v("Count", (String) ((arrayList != null && arrayList.size() == 0) ? 0: arrayList.size()+""));
		return (arrayList != null && arrayList.size() == 0) ? 0: arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		Log.v("Item", arrayList.get(position)+"");
		return arrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		Log.v("ItemId", position+"");
		return  position;
	}
	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, View currentView, ViewGroup arg2) {
		HolderView holderView=null;
		if (currentView == null) {
			holderView = new HolderView();
			currentView = LayoutInflater.from(context).inflate(R.layout.adapter_listview_cart, null);
			holderView.tv_name=(TextView)currentView.findViewById(R.id.tv_name);
			holderView.tv_num = (TextView) currentView.findViewById(R.id.tv_num);
			holderView.tv_price=(TextView)currentView.findViewById(R.id.tv_price);
			//holderView.iv_xiala=(ImageView)currentView.findViewById(R.id.iv_xiala);
			holderView.tv_type_color = (TextView) currentView.findViewById(R.id.tv_type_color);
			holderView.iv_icon=(ImageView)currentView.findViewById(R.id.iv_icon_url);
			holderView.tv_pop_num=(TextView)currentView.findViewById(R.id.tv_pop_num);
			holderView.tv_pop_red=(TextView)currentView.findViewById(R.id.tv_pop_reduce);
			holderView.tv_pop_add=(TextView)currentView.findViewById(R.id.tv_pop_add);
			holderView.cb_choice = (CheckBox) currentView.findViewById(R.id.cb_choice);
			currentView.setTag(holderView);
	} 	else {
			holderView = (HolderView) currentView.getTag();
		}

			holderView.tv_num.setText("x" + arrayList.get(position).get("count"));
			holderView.tv_type_color.setText(arrayList.get(position).get("sku_desc")+"");
			holderView.tv_price.setText("￥"+arrayList.get(position).get("price"));
			holderView.tv_name.setText(arrayList.get(position).get("name")+"");	
			System.out.println(position+":"+arrayList.get(position).get("count").toString());
			
//			currentView.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					Intent intent = new Intent(context, BabyActivity.class);
//					intent.putExtra("PID",Integer.parseInt(arrayList.get(position).get("p_id").toString()));
//					System.out.println(Integer.parseInt(arrayList.get(position).get("p_id").toString())+"点击跳转");
//					context.startActivity(intent);
//					((Activity) context).overridePendingTransition(R.anim.in_from_right,
//							R.anim.out_to_left);
//				}
//			});
			
			System.out.println(position+":"+arrayList.get(position).get("icon_url").toString());
			ImageListener listener1 = ImageLoader.getImageListener(holderView.iv_icon,
					R.drawable.image_loading, R.drawable.image_load_fail);
			try {
			    //    imageLoader.get(arrayList.get(position).get("icon_url").toString(), listener1,80,85);
			} catch (Exception e) {
				// TODO: handle exception
				holderView.iv_icon.setImageResource(R.drawable.image_load_fail);
			}
		
			holderView.cb_choice.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean choice) {
					listener.getChoiceData(position, choice);
				}
			});
			
			currentView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context, BabyActivity.class);
					intent.putExtra("PID", Integer.parseInt(arrayList.get(position).get("p_id").toString()));
					context.startActivity(intent);
					((Activity) context).overridePendingTransition(R.anim.in_from_right,
							R.anim.out_to_left);
				}
			});
			
			
		//	holderView.tv_guige.setText(arrayList.get(position).get("sku_desc")+"");
			holderView.tv_pop_num.setText( arrayList.get(position).get("count")+"");
			holderView.tv_pop_red.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					reduceListener.reduceCount(position);
				}
			});
			
			holderView.tv_pop_red.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					reduceListener.reduceCount(position);
				}
			});
			holderView.tv_pop_add.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					addListener.addCount(position);
				}
			});
		return currentView;
	}

	public static class HolderView { 
		private ImageView iv_icon;
		private ImageView iv_xiala;
		private TextView tv_name;
		private TextView tv_type_color;
		private TextView tv_price;
		private TextView tv_num;
		private CheckBox cb_choice;
		private TextView tv_pop_add;
		private TextView tv_pop_red;
		private TextView tv_pop_num;
		private TextView tv_guige;
	}
	public interface  onAddChanged {
		public void addCount(int position);
	}
	
//	public interface onGuiGeChanged{
//		public void guiGeChanged(int position);
//	}
	public interface  onReduceChanged {
		public void reduceCount(int position);
	}

	public interface onCheckedChanged{
		
		public void getChoiceData(int position,boolean isChoice);
	}
//	public void setGuiChanged(onGuiGeChanged guige){
//		this.guiListener=guige;
//	}
	public void setOnRedChanged(onReduceChanged reduceChanged){
		this.reduceListener=reduceChanged;
	}
	public void setOnAddChanged(onAddChanged addChanged){
		this.addListener=addChanged;
	}
	
	public void setOnCheckedChanged(onCheckedChanged listener){
		this.listener=listener;
	}
}
