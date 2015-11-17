package com.itboye.banma.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.animation.AnimatorSet.Builder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itboye.banma.R;
import com.itboye.banma.activities.ActivityLogistics;
import com.itboye.banma.activities.AddAddressActivity;
import com.itboye.banma.activities.ConfirmOrdersActivity;
import com.itboye.banma.activities.LoginActivity;
import com.itboye.banma.activities.OrderDetailActivity;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
import com.itboye.banma.entity.OrderDetailListItem;
import com.itboye.banma.entity.OrderItem;
import com.itboye.banma.entity.OrderPayData;
import com.itboye.banma.entity.SkuStandard;
import com.itboye.banma.entity.User;
import com.itboye.banma.entity.ProductDetail.Sku_info;
import com.itboye.banma.payalipay.PayAlipay;
import com.itboye.banma.util.BaseViewHolder;
import com.itboye.banma.utils.BitmapCache;
import com.itboye.banma.view.MyLinearLayout;
import com.itboye.banma.view.MyListView;

public class OrderListAdapter extends BaseAdapter implements StrUIDataListener{
	private final int ZHUFU_ORDER = 1;
	private final int QUXIAO_ORDER = 2;
	private final int QUERENSHOUHUO_ORDER = 3;
	
	private Context context;
	private AppContext appContext;
	private StrVolleyInterface strnetworkHelper;
	private Boolean YesOrNo; // 是否连接网络
	private int state = -1;
	private ProgressDialog dialog;
	private Gson gson = new Gson();
	private PayAlipay payAlipay;
	private List<OrderDetailListItem> orderList = new ArrayList<OrderDetailListItem>();
	private int location;

	public OrderListAdapter(Context context, List<OrderDetailListItem> orderList) {
		this.context = context;
		this.orderList = orderList;
		appContext = (AppContext) ((Activity) context).getApplication();
		strnetworkHelper = new StrVolleyInterface(context);
		strnetworkHelper.setStrUIDataListener(this);
		dialog = new ProgressDialog(context);
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
		
		List<OrderItem> data = null;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.order_listitem,
					parent, false);
		}
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
		all_price.setText("￥"+order.getPrice());
		
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
				int orderId = Integer.valueOf(order.getId());
				intent.putExtra("id", orderId);
				context.startActivity(intent);
				((Activity) context).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			}
		});

		switch (Integer.parseInt(order.getOrder_status())) {
		case Constant.ORDER_CANCEL:		//取消或交易关闭
			status.setText("["+Constant.getOrderStatus(Constant.ORDER_CANCEL)+"]");  //[交易关闭]
			confirm_one.setVisibility(View.GONE);
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
				status.setText("[待付款]");  //[待付款]
				confirm_one.setVisibility(View.VISIBLE);
				confirm_two.setVisibility(View.GONE);
				confirm_one.setText("发起支付");
				confirm_two.setText("取消订单");
				confirm_one.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast.makeText(context, "发起支付", Toast.LENGTH_LONG).show();
						ordersPay(order.getId());
					
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
						getLogistics(order.getOrder_code());
					}
				});
				confirm_two.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						location = position;
						Toast.makeText(context, "货已发，待收货", Toast.LENGTH_LONG).show();
						ordersSureorder(order.getOrder_code());
					}

				});
			}
			break;
			
		case Constant.ORDER_RECEIPT_OF_GOODS:		//已收货
			if(Integer.parseInt(order.getPay_status()) == Constant.ORDER_PAID ){ //已支付
				status.setText("["+Constant.getOrderStatus(Constant.ORDER_RECEIPT_OF_GOODS)+"]");  //[已收货]
				confirm_one.setVisibility(View.GONE);
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
	
	/**
	 * 发起支付
	 * @param id
	 */
	private void ordersPay(String id) {
		dialog.setMessage("正在发起支付...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
		try {
			state = ZHUFU_ORDER;
			YesOrNo = appContext.ordersPay(context, id, strnetworkHelper);

			if (!YesOrNo) { // 如果没联网
				Toast.makeText(context, "请检查网络连接",
						Toast.LENGTH_SHORT).show();
				state = -1;
				dialog.dismiss();
			}
		} catch (Exception e) {
			state = -1;
			dialog.dismiss();
			e.printStackTrace();
		}
	}
	
	/**
	 * 确认收货
	 * @param order_code
	 */
	private void ordersSureorder(String order_code) {
		dialog.setMessage("正在确认收货...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
		try {
			state = QUERENSHOUHUO_ORDER;
			YesOrNo = appContext.ordersSureorder(context, order_code, strnetworkHelper);

			if (!YesOrNo) { // 如果没联网
				Toast.makeText(context, "请检查网络连接",
						Toast.LENGTH_SHORT).show();
				state = -1;
				dialog.dismiss();
			}
		} catch (Exception e) {
			state = -1;
			dialog.dismiss();
			e.printStackTrace();
		}
	}
	
	/**
	 * 查看物流
	 * @param order_code
	 */
	private void getLogistics(String order_code) {
		Intent intent = new Intent(context, ActivityLogistics.class);
		intent.putExtra("order_code", order_code);
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	@Override
	public void onErrorHappened(VolleyError error) {
		dialog.dismiss();
		Toast.makeText(context, "服务器异常，稍后再试",Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDataChanged(String data) {
		dialog.dismiss();
		JSONObject jsonObject=null;
		int code = -1;
		String content = null;
		OrderPayData orderPayData;
		if(state == ZHUFU_ORDER){
			try {
				jsonObject = new JSONObject(data);
				code = jsonObject.getInt("code");
				content = jsonObject.getString("data");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			if (code == 0) {
				payAlipay = new PayAlipay(context);
				orderPayData = gson.fromJson(content, OrderPayData.class);
				payAlipay.pay(null, orderPayData);
			}
			else{
				Toast.makeText(context, "服务器异常，稍后再试"+data,Toast.LENGTH_SHORT).show();
			}
		}
		else if(state == QUERENSHOUHUO_ORDER){
			try {
				jsonObject = new JSONObject(data);
				code = jsonObject.getInt("code");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			if (code == 0) {
				
				orderList.remove(location);
				notifyDataSetChanged();
				
			}else{
				Toast.makeText(context, "服务器异常，稍后再试",Toast.LENGTH_SHORT).show();
			}
		}
	}
}

