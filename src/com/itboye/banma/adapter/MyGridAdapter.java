package com.itboye.banma.adapter;

import java.util.ArrayList;
import java.util.List;

import com.itboye.banma.R;
import com.itboye.banma.util.BaseViewHolder;

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

	public String[] img_text = { "型号000", "型号111", "型号222", "型号333", "型号444", "型号555",
			"型号666"};
	public MyGridAdapter(Context mContext) {
		super();
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return img_text.length;
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
	    params.height=width/6;  
	    //params.width =(width-3)/4;
	    //System.out.println("height="+height+"width="+width);
		convertView.setLayoutParams(params);
		
		final TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
		tv.setText(img_text[position]);
		tv.setBackgroundResource(R.drawable.yuanjiao);
		if(position == state){
			tv.setBackgroundResource(R.drawable.yuanjiao_choice);
		}
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				notifyDataSetChanged();
				state = position;
				//tv.setBackgroundResource(R.drawable.yuanjiao_choice);
			}
		});
		
		return convertView;
	}

}
