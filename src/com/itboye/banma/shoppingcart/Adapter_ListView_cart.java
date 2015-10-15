package com.itboye.banma.shoppingcart;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.itboye.banma.R;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.utils.BitmapCache;

public class Adapter_ListView_cart extends BaseAdapter  {
	private Context context;
	private ImageLoader imageLoader;
	private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
	private onCheckedChanged listener;
	private onAddChanged addListener;
	private onReduceChanged reduceListener;

	public Adapter_ListView_cart(Context context, ArrayList<HashMap<String, Object>> arrayList) {
		this.imageLoader= new ImageLoader(AppContext.getHttpQueues(),
				new BitmapCache());
		this.context = context;
		this.arrayList = arrayList;
	}

	public Adapter_ListView_cart(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return (arrayList != null && arrayList.size() == 0) ? 0: arrayList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, View currentView, ViewGroup arg2) {
		HolderView holderView = null;
		if (currentView == null) {
			holderView = new HolderView();
			currentView = LayoutInflater.from(context).inflate(R.layout.adapter_listview_cart, null);
			holderView.tv_name=(TextView)currentView.findViewById(R.id.tv_name);
			holderView.tv_num = (TextView) currentView.findViewById(R.id.tv_num);
			holderView.tv_price=(TextView)currentView.findViewById(R.id.tv_price);
			holderView.iv_xiala=(ImageView)currentView.findViewById(R.id.iv_xiala);
			holderView.tv_type_color = (TextView) currentView.findViewById(R.id.tv_type_color);
			holderView.iv_icon=(ImageView)currentView.findViewById(R.id.iv_icon_url);
			holderView.tv_pop_num=(TextView)currentView.findViewById(R.id.tv_pop_num);
			holderView.tv_pop_red=(TextView)currentView.findViewById(R.id.tv_pop_reduce);
			holderView.tv_pop_add=(TextView)currentView.findViewById(R.id.tv_pop_add);
			holderView.tv_guige=(TextView)currentView.findViewById(R.id.tv_guige);
			holderView.cb_choice = (CheckBox) currentView.findViewById(R.id.cb_choice);
			currentView.setTag(holderView);
		} else {
			holderView = (HolderView) currentView.getTag();
		}
		if (arrayList.size() != 0) {
			holderView.tv_num.setText("x" + arrayList.get(position).get("count"));
			holderView.tv_type_color.setText(arrayList.get(position).get("sku_desc")+"");
			holderView.tv_price.setText(arrayList.get(position).get("price")+"");
			holderView.tv_name.setText(arrayList.get(position).get("name")+"");	
//			ImageLoader imageLoader = new ImageLoader(AppContext.getHttpQueues(),
//					new BitmapCache());
//			ImageListener listener1 = ImageLoader.getImageListener(holderView.iv_icon,
//					R.drawable.image_loading, R.drawable.image_load_fail);
//			imageLoader.get(arrayList.get(position).get("icon_url").toString(), listener1, 0, 0);
//			if (arrayList.get(position).get("icon_url")!=null) {
//		
//				holderView.iv_icon.setDefaultImageResId(R.drawable.ic_launcher);  
//				holderView.iv_icon.setErrorImageResId(R.drawable.ic_launcher);  
//				holderView.iv_icon.setImageUrl(arrayList.get(position).get("icon_url").toString(), imageLoader);  
//		}
			holderView.cb_choice.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean choice) {
					listener.getChoiceData(position, choice);
				}
			});
			holderView.tv_guige.setText(arrayList.get(position).get("sku_desc")+"");
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
		}
		return currentView;
	}

	public class HolderView { 
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
	public interface  onReduceChanged {
		public void reduceCount(int position);
	}

	public interface onCheckedChanged{
		
		public void getChoiceData(int position,boolean isChoice);
	}
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
