package com.itboye.banma.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.itboye.banma.R;
import com.itboye.banma.app.Constant;
import com.itboye.banma.entity.ProductDetail.Sku_info;
import com.itboye.banma.entity.SkuInfo;
import com.itboye.banma.entity.SkuInfo.MapValue;
import com.itboye.banma.util.BaseViewHolder;
import com.itboye.banma.utils.ChooseStandardInterface;

import android.R.integer;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyGridAdapter extends BaseAdapter {
	private Context mContext;
	private int state = -1;
	private int i; //区分第几个Grid
	private SkuInfo skuInfo;
	private Map<String, MapValue> value;
	private Sku_info sku_info;
	private ChooseStandardInterface ch;
	/*public String[] img_text = { "型号000", "型号111", "型号222", "型号333", "型号444", "型号555",
			"型号666"};*/
	public MyGridAdapter(Context mContext, Sku_info sku_info, SkuInfo skuInfo, int i, ChooseStandardInterface ch) {
		super();
		this.mContext = mContext;
		this.skuInfo = skuInfo;
		this.sku_info = sku_info;
		this.i = i;
		this.ch = ch;
		value = skuInfo.getValue();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return value.size();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.grid_item, parent, false);
		}
		
		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		
		LayoutParams params = convertView.getLayoutParams();  
	    params.height=width/8;  
	    //params.width =(width-3)/4;
	    //System.out.println("height="+height+"width="+width);
		convertView.setLayoutParams(params);
		
		final TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
		tv.setText(value.get(sku_info.getVid()[position]).getValue());
		//tv.setText(position+"");
		tv.setBackgroundResource(R.drawable.yuanjiao);
		if(position == state){
			tv.setBackgroundResource(R.drawable.yuanjiao_choice);
		}
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				notifyDataSetChanged();
				state = position;
				Constant.SKU_INFO[i] = skuInfo.getKey() + ":" + value.get(sku_info.getVid()[position]).getKey()+";";
				//tv.setBackgroundResource(R.drawable.yuanjiao_choice);
				Constant.SKU_NUM[i] = 1;
				int sum = 0;
				for(int k=0; k<Constant.SKU_ALLNUM; k++){
					sum = sum + Constant.SKU_NUM[k];
				}
				if(sum >= Constant.SKU_ALLNUM){
					ch.changeview();
				}
			}
		});
		
		return convertView;
	}

}
