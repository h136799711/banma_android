package com.itboye.banma.adapter;

import java.util.ArrayList;
import java.util.List;

import android.animation.AnimatorSet.Builder;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itboye.banma.R;
import com.itboye.banma.activities.ConfirmOrdersActivity;
import com.itboye.banma.activities.LoginActivity;
import com.itboye.banma.activities.OrderDetailActivity;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
import com.itboye.banma.entity.OrderDetailListItem;
import com.itboye.banma.entity.OrderItem;
import com.itboye.banma.entity.SkuStandard;
import com.itboye.banma.entity.ProductDetail.Sku_info;
import com.itboye.banma.payalipay.PayAlipay;
import com.itboye.banma.util.BaseViewHolder;
import com.itboye.banma.utils.BitmapCache;
import com.itboye.banma.view.MyLinearLayout;
import com.itboye.banma.view.MyListView;

public class OrderListAdapter   extends BaseAdapter {
	private Context context;
	private List<OrderDetailListItem> orderList = new ArrayList<OrderDetailListItem>();

	public OrderListAdapter(Context context, List<OrderDetailListItem> orderList) {
		this.context = context;
		this.orderList = orderList;
	}

	@Override
	public int getCount() {
		return orderList.size();
	}

	public void onDateChang(List<OrderDetailListItem> orderList) {
		this.orderList = orderList;
		this.notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return orderList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		final OrderDetailListItem order = orderList.get(position);
		Gson gson = new Gson();
		final PayAlipay payAlipay;
		List<OrderItem> data = null;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.order_listitem,
					parent, false);
		}
		payAlipay = new PayAlipay(context);
		MyLinearLayout order_click_layout = BaseViewHolder.get(view, R.id.order_click_layout);
		TextView order_code = BaseViewHolder.get(view, R.id.order_code);
		TextView status = BaseViewHolder.get(view, R.id.status);
		MyListView order_list = BaseViewHolder.get(view, R.id.order_list);
		TextView all_price = BaseViewHolder.get(view, R.id.all_price);
		Button confirm_one = BaseViewHolder.get(view, R.id.confirm_one);
		Button confirm_two = BaseViewHolder.get(view, R.id.confirm_two);
		
		order_code.setText(order.getOrder_code());
		status.setText("["+order.getStatus()+Constant.getStatus(Integer.parseInt(order.getStatus()))+"]"+
				"["+order.getOrder_status()+Constant.getOrderStatus(Integer.parseInt(order.getOrder_status()))+"]"+
				"["+order.getPay_status()+Constant.getPayStatus(Integer.parseInt(order.getPay_status()))+"]");
		all_price.setText(order.getPrice());
		
		data = order.get_items();
		if(data != null){
			OrderListItemAdapter adapter = new OrderListItemAdapter(context, data);
			order_list.setAdapter(adapter);
			order_list.setFocusable(false);
			order_list.setFocusableInTouchMode(false);
		}
		
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, OrderDetailActivity.class);
				intent.putExtra("id", 20);
				context.startActivity(intent);
				((Activity) context).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			}
		});

		switch (Integer.parseInt(order.getOrder_status())) {
		case Constant.ORDER_CANCEL:		//取消或交易关闭
			status.setText("["+Constant.getOrderStatus(Constant.ORDER_CANCEL)+"]");  //[交易关闭]
			confirm_one.setVisibility(View.VISIBLE);
			confirm_two.setVisibility(View.GONE);
			confirm_one.setText("取消订单");
			confirm_one.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(context, "取消订单", Toast.LENGTH_LONG).show();
					//取消订单的对话框
					showQuxiaoDialog();
				}
			});
			break;
			
		case Constant.ORDER_TOBE_CONFIRMED:		//待确认状态
			if(Integer.parseInt(order.getPay_status()) == Constant.ORDER_PAID ){ //已支付
				status.setText("["+Constant.getOrderStatus(Constant.ORDER_TOBE_CONFIRMED)+"]");  //[交易关闭]
				confirm_one.setVisibility(View.GONE);
				confirm_two.setVisibility(View.GONE);
			}else if(Integer.parseInt(order.getPay_status()) == Constant.ORDER_TOBE_PAID ){ //待支付
				status.setText("[代付款]");  //[代付款]
				confirm_one.setVisibility(View.VISIBLE);
				confirm_two.setVisibility(View.VISIBLE);
				confirm_one.setText("发起支付");
				confirm_two.setText("取消订单");
				confirm_one.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast.makeText(context, "发起支付", Toast.LENGTH_LONG).show();
						payAlipay.pay(null, order.getPrice());
					}
				});
				confirm_two.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast.makeText(context, "取消订单", Toast.LENGTH_LONG).show();
						//取消订单的对话框
						showQuxiaoDialog();
					}
				});
			}
			break;
		
		case Constant.ORDER_TOBE_SHIPPED:		//待发货状态
			if(Integer.parseInt(order.getPay_status()) == Constant.ORDER_PAID ){ //已支付
				status.setText("["+Constant.getOrderStatus(Constant.ORDER_TOBE_SHIPPED)+"]");  //[代发货]
				confirm_one.setVisibility(View.GONE);
				confirm_two.setVisibility(View.GONE);
			}
			break;
			
		case Constant.ORDER_SHIPPED:		//已发货
			if(Integer.parseInt(order.getPay_status()) == Constant.ORDER_PAID ){ //已支付
				status.setText("["+Constant.getOrderStatus(Constant.ORDER_SHIPPED)+"]");  //[待收货]
				confirm_one.setVisibility(View.VISIBLE);
				confirm_two.setVisibility(View.VISIBLE);
				confirm_one.setText("查看物流");
				confirm_two.setText("确认收货");
				confirm_one.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast.makeText(context, "查看物流", Toast.LENGTH_LONG).show();
					}
				});
				confirm_two.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast.makeText(context, "货已发，待收货", Toast.LENGTH_LONG).show();
					}
				});
			}
			break;
			
		case Constant.ORDER_RECEIPT_OF_GOODS:		//已收货
			if(Integer.parseInt(order.getPay_status()) == Constant.ORDER_PAID ){ //已支付
				status.setText("["+Constant.getOrderStatus(Constant.ORDER_RECEIPT_OF_GOODS)+"]");  //[已收货]
				confirm_one.setVisibility(View.VISIBLE);
				confirm_two.setVisibility(View.GONE);
				confirm_one.setText("评价");
				confirm_one.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast.makeText(context, "待评价", Toast.LENGTH_LONG).show();
					}
				});
			}
			break;
			
			
		
		/*case Constant.ORDER_TOBE_CONFIRMED: //待确认
			if(Integer.parseInt(order.getPay_status()) == Constant.ORDER_TOBE_PAID){ //代付款状态
				status.setText("["+Constant.getPayStatus(Integer.parseInt(order.getPay_status()))+"]");  //[代付款]
				confirm_one.setVisibility(View.VISIBLE);
				confirm_two.setVisibility(View.VISIBLE);
				confirm_one.setText("取消订单");
				confirm_two.setText("去付款");
				confirm_one.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						//取消订单的对话框
						showQuxiaoDialog();
					}

				});
				confirm_two.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						payAlipay.pay(null, order.getPrice());
					}
				});
			}
			break;
		
		case Constant.ORDER_BACK:   	//订单退回
		case Constant.ORDER_CANCEL:		//取消或交易关闭
			status.setText("["+Constant.getOrderStatus(Constant.ORDER_CANCEL)+"]");  //[交易关闭]
			confirm_one.setVisibility(View.VISIBLE);
			confirm_two.setVisibility(View.GONE);
			confirm_one.setText("删除订单");
			confirm_one.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context, OrderDetailActivity.class);
					context.startActivity(intent);
				}
			});
			break;
		case Constant.ORDER_COMPLETED :	//已完成
			confirm_one.setVisibility(View.GONE);
			confirm_two.setVisibility(View.GONE);
			break;
		case Constant.ORDER_TOBE_SHIPPED:	//待发货
			status.setText("["+Constant.getOrderStatus(Constant.ORDER_TOBE_SHIPPED)+"]");  //[待发货]
			confirm_one.setVisibility(View.VISIBLE);
			confirm_two.setVisibility(View.GONE);
			confirm_one.setText("提醒发货");
			confirm_one.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
				}
			});
			break;
		case Constant.ORDER_SHIPPED :	//已发货
		case Constant.ORDER_RECEIPT_OF_GOODS: //已收货
			confirm_one.setVisibility(View.VISIBLE);
			confirm_two.setVisibility(View.VISIBLE);
			confirm_one.setText("申请退款");
			confirm_two.setText("查看物流");
			confirm_one.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context, OrderDetailActivity.class);
					context.startActivity(intent);
				}
			});
			confirm_two.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context, OrderDetailActivity.class);
					context.startActivity(intent);
				}
			});
			break;
		case Constant.ORDER_RESENDS :	//正在退款
		case Constant.ORDER_RETURNED:	//已退货
			confirm_one.setVisibility(View.VISIBLE);
			confirm_two.setVisibility(View.GONE);
			confirm_one.setText("查看退款");
			confirm_one.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
				}
			});
			break;*/
		default:
			break;
		}
		
		return view;
	}
	
	private void showQuxiaoDialog() {
		final String[] cons = new String[]{
				"我不想买了","信息有误，重拍","卖家缺货","其他原因"
		};
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("选择取消订单理由");
		builder.setSingleChoiceItems(cons, 1, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(context, cons[which], Toast.LENGTH_LONG).show();
			}
		});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(context, "确定", Toast.LENGTH_LONG).show();
			}
		});
		
		builder.create().show();
	}
}

