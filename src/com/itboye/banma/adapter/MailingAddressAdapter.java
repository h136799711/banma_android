package com.itboye.banma.adapter;

import java.util.ArrayList;
import java.util.List;

import com.itboye.banma.R;
import com.itboye.banma.activities.AddAddressActivity;
import com.itboye.banma.entity.MailingAdress;
import com.itboye.banma.util.BaseViewHolder;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MailingAddressAdapter extends BaseAdapter {
	private Context context;
	private List<MailingAdress> list = new ArrayList<MailingAdress>();
	private int state; // 状态，0表示地址管理，其他表示订单确认的更换地址的ID

	public MailingAddressAdapter(Context context, List<MailingAdress> list,
			int state) {
		this.context = context;
		this.list = list;
		this.state = state;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	public void onDateChang(List<MailingAdress> list) {
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
		final MailingAdress address = list.get(position);
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.address_item,
					parent, false);
		}
		TextView adr_default = BaseViewHolder.get(view, R.id.adr_default);
		TextView name = BaseViewHolder.get(view, R.id.adr_name);
		TextView phone = BaseViewHolder.get(view, R.id.adr_phone);
		TextView addressDetail = BaseViewHolder.get(view, R.id.adr_address);
		ImageView image = BaseViewHolder.get(view, R.id.right_image);
		ImageView select = BaseViewHolder.get(view, R.id.right_select);
		View nullview = BaseViewHolder.get(view, R.id.view);

		if (address.getDefault_address() == 1) {
			adr_default.setVisibility(View.VISIBLE);
		} else {
			adr_default.setVisibility(View.GONE);
		}

		name.setText(address.getContactname());
		phone.setText(address.getMobile());
		addressDetail.setText(address.getProvince()+ " " + address.getCity()+ " "
				+ address.getArea() + " " + address.getDetailinfo());
		if (state == 0) {
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context,
							AddAddressActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("address", address);
					intent.putExtras(bundle);
					((Activity) context).startActivityForResult(intent, 1000);
					((Activity) context).overridePendingTransition(
							R.anim.in_from_right, R.anim.out_to_left);
				}
			});
		} else {
			image.setVisibility(View.GONE);
			select.setVisibility(View.GONE);
			nullview.setVisibility(View.VISIBLE);
			if (address.getId() == state) {
				nullview.setVisibility(View.GONE);
				select.setVisibility(View.VISIBLE);
			}
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.putExtra("result", 0);
					Bundle bundle = new Bundle();
					bundle.putSerializable("address", address);
					intent.putExtras(bundle);
					/*
					 * 调用setResult方法表示我将Intent对象返回给之前的那个Activity，
					 * 这样就可以在onActivityResult方法中得到Intent对象，
					 */
					((Activity) context).setResult(2001, intent);
					((Activity) context).finish();
					((Activity) context).overridePendingTransition(
							R.anim.push_right_in, R.anim.push_right_out);

				}
			});

		}
		return view;
	}
}
