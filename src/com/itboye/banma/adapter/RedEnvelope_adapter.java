package com.itboye.banma.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.itboye.banma.R;
import com.itboye.banma.activities.BabyActivity;
import com.itboye.banma.activities.ConfirmOrdersActivity;
import com.itboye.banma.activities.YouHuiActivity;
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
import android.widget.Toast;

public class RedEnvelope_adapter extends BaseAdapter{
	private Context context;
	private int skipState;  //记录由哪个页面跳转过来，1表示由订单确认页面跳转至红包页面，选择红包后要返回红包ID
	Double priceAll;
	private List<RedEnvelope> list = new ArrayList<RedEnvelope>();

	public RedEnvelope_adapter(Context context, List<RedEnvelope> list,
			int skipState, Double priceAll) {
		this.context = context;
		this.list = list;
		this.priceAll = priceAll;
		this.skipState = skipState;
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
//			holder.listener = ImageLoader.getImageListener(holder.order_pic,
//					0, R.drawable.loading_image_baby);
			view.setTag(holder);
		}else{
			holder = (ViewHolder) view.getTag();
		}

		if (Integer.valueOf(red.getUse_status())==0) {
			holder.red_title.setImageResource(R.drawable.hongbao_on);
			if(skipState == 1){  //由订单确认页面跳转过来，响应点击事件，并返回红包ID
				view.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						int day = TimeToDate.remianTime((long)(System.currentTimeMillis() / 1000)+"",
								red.getExpire_time());
						if(day < 0){
							Toast.makeText(context, "该红包已过期", Toast.LENGTH_SHORT).show();
						}else if(priceAll >= Double.valueOf(red.getUse_condition())){
							Intent intent=new Intent(context,ConfirmOrdersActivity.class);
							 intent.putExtra("RedEnvelopeID", red.getId());
							 intent.putExtra("RedEnvelopeMony", red.getMoney());
							 ((Activity) context).setResult(1006, intent);
							 ((Activity) context).finish();
							 ((Activity) context).overridePendingTransition(R.anim.push_right_in,
									 R.anim.push_right_out);
						}else{
							Toast.makeText(context, "订单满"+ red.getUse_condition() +"才可用", Toast.LENGTH_SHORT).show();
						}
						/*// TODO Auto-generated method stub
						Intent intent = new Intent(context, BabyActivity.class);
						intent.putExtra("HONGBAO_ID", red.getId());
						context.startActivity(intent);
						((Activity) context).finish();
						((Activity) context).overridePendingTransition(R.anim.push_right_in,
								R.anim.push_right_out);*/
					}
				});
			}
			holder.red_jine.setText("￥"+red.getMoney());
			holder.red_guoqi.setText(TimeToDate.isOvertime((int)(System.currentTimeMillis() / 1000)+"",
					red.getExpire_time(),red.use_status));
			holder.red_youxiaoqi.setText("有效期："+TimeToDate.timeToDate(red.expire_time));
			holder.red_tiaojian.setText("满"+red.getUse_condition()+"元可用");
			holder.red_laiyuan.setText(red.getDtree_type_name());
			holder.red_beizhu.setText(red.getTpl_notes());
		}else if (Integer.valueOf(red.getUse_status())==1) {
	    	holder.red_check.setVisibility(view.GONE);
			holder.red_jine.setText("￥"+red.getMoney());
			holder.red_jine.setTextColor(R.color.lightgray);
			holder.red_title.setImageResource(R.drawable.hongbao_off);
			holder.red_guoqi.setText(TimeToDate.isOvertime((int)(System.currentTimeMillis() / 1000)+"",
					red.getExpire_time(),red.use_status));
			holder.red_youxiaoqi.setText("有效期："+TimeToDate.timeToDate(red.expire_time));
			holder.red_tiaojian.setText("满"+red.getUse_condition()+"元可用");
			holder.red_laiyuan.setText(red.getDtree_type_name());
			holder.red_laiyuan.setTextColor(R.color.lightgray);
			holder.red_beizhu.setText(red.getTpl_notes());
			//holder.red_beizhu.setTextColor(R.color.lightgray);
		}
		
//		if(order.getSku_desc()==null || order.getSku_desc().length()<=0){
//			holder.order_standard.setText("无规格参数");
//		}else{
//			holder.order_standard.setText(order.getSku_desc());
//		}
//		holder.order_price.setText("￥" + order.getPrice());
//		// order_number.setText("×"+skuStandard.getNum());
//		holder.order_number.setText("×"+order.getCount());
//		int num = getCount()-1;
//		if(position >= num){
//			holder.line.setVisibility(View.GONE);
//		}else{
//			holder.line.setVisibility(View.VISIBLE);
//		}
//		
//		view.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(context, BabyActivity.class);
//				intent.putExtra("PID", Integer.valueOf(order.getP_id()));
//				context.startActivity(intent);
//				((Activity) context).overridePendingTransition(R.anim.in_from_right,
//						R.anim.out_to_left);
//			}
//		});
		
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
