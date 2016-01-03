package com.itboye.banma.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.itboye.banma.R;
import com.itboye.banma.activities.BabyActivity;
import com.itboye.banma.entity.RedEnvelope;
import com.itboye.banma.util.TimeToDate;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class RedEnvelope_adapter extends BaseAdapter{
	private Context context;
	private List<RedEnvelope> list = new ArrayList<RedEnvelope>();
	private String hongbao;//区分哪个activity传来的参数

	public RedEnvelope_adapter(Context context, List<RedEnvelope> list,String hongbao) {
		this.context = context;
		this.list = list;
		this.hongbao=hongbao;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	public void onDateChang(List<RedEnvelope> list) {
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
	
	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		final RedEnvelope red = list.get(position);
		ViewHolder holder;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.red_envelope_item,
					parent, false);
			holder = new ViewHolder();
			holder.red_check=(CheckBox)view.findViewById(R.id.hongbao_choice);
			holder.red_title = (ImageView) view.findViewById(R.id.red_title);
			holder.red_jine = (TextView) view.findViewById(R.id.red_jine);
			holder.red_tiaojian = (TextView) view.findViewById(R.id.red_tiaojian);
			holder.red_guoqi = (TextView) view.findViewById(R.id.red_guoqi);
			holder.red_laiyuan = (TextView) view.findViewById(R.id.red_laiyuan);
			holder.red_beizhu = (TextView) view.findViewById(R.id.order_beizhu);
			holder.red_youxiaoqi = (TextView) view.findViewById(R.id.red_youxiaoqi);
			holder.line = view.findViewById(R.id.line);
			view.setTag(holder);
		}else{
			holder = (ViewHolder) view.getTag();
		}
		if (red.use_status.equals("0")) {
			holder.red_title.setImageResource(R.drawable.hongbao_on);
//			if (hongbao.equals("hongbao_centerFragment")) {
//				holder.red_check.setVisibility(View.INVISIBLE);
//			}
			holder.red_check.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(context, BabyActivity.class);
					intent.putExtra("HONGBAO_ID", red.getId());
					intent.putExtra("HONGBAO_CONDITION", red.getUse_condition());
					context.startActivity(intent);
					((Activity) context).finish();
					((Activity) context).overridePendingTransition(R.anim.push_right_in,
							R.anim.push_right_out);
				}
			});
			holder.red_jine.setText("￥"+red.getMoney());
		//	holder.red_jine.setTextColor(R.color.red);
			String time=TimeToDate.isOvertime((int)(System.currentTimeMillis() / 1000)+"",
					red.getExpire_time(),red.use_status);
			if (time.equals("已过期")) {
				
			}
			holder.red_guoqi.setText(time);
			holder.red_youxiaoqi.setText("有效期："+TimeToDate.timeToDate(red.expire_time));
			holder.red_tiaojian.setText("满"+red.use_condition+"元可用");
			holder.red_laiyuan.setText(red.dtree_type_name);
			//holder.red_laiyuan.setTextColor(R.color.sienna);
			holder.red_beizhu.setText(red.getNotes());
		}else if (red.use_status.equals("1")) {
	    	holder.red_check.setVisibility(View.INVISIBLE);
			holder.red_jine.setText("￥"+red.getMoney());
			holder.red_jine.setTextColor(R.color.lightgray);
			holder.red_title.setImageResource(R.drawable.hongbao_off);
			holder.red_guoqi.setText(TimeToDate.isOvertime((int)(System.currentTimeMillis() / 1000)+"",
					red.getExpire_time(),red.use_status));
			holder.red_youxiaoqi.setText("有效期："+TimeToDate.timeToDate(red.expire_time));
			holder.red_tiaojian.setText("满"+red.use_condition+"元可用");
			holder.red_laiyuan.setText(red.dtree_type_name);
			holder.red_laiyuan.setTextColor(R.color.lightgray);
			holder.red_beizhu.setText(red.getNotes());
			//holder.red_beizhu.setTextColor(R.color.lightgray);
		}
		
		return view;
	}
	
	private static class ViewHolder {
		ImageView red_title;
		TextView red_jine;//红包金额
		TextView red_tiaojian;//使用条件
		TextView red_guoqi;//即将过期时间
		TextView red_laiyuan;//红包来源
		TextView red_beizhu;//红包备注
		TextView red_youxiaoqi;//有效期
		CheckBox red_check;//选择
		View line;
	}
	
	/**
	 * 刷新列表
	 * 
	 * @param newList
	 */
	public void notifyDataSetChanged(List<RedEnvelope> newList) {
		this.list = newList;
		notifyDataSetChanged();
	}
}
