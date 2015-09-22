package com.itboye.banma.adapter;

import java.util.ArrayList;
import java.util.List;

import com.itboye.banma.R;
import com.itboye.banma.activities.AddAddressActivity;
import com.itboye.banma.entity.MailingAdress;
import com.itboye.banma.util.BaseViewHolder;

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

	public MailingAddressAdapter(Context context, List<MailingAdress> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
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
	public View getView(int position, View view, ViewGroup parent) {
		final MailingAdress address = list.get(position);
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.address_item,
					parent, false);
		}
		TextView name = BaseViewHolder.get(view, R.id.adr_name);
		TextView phone = BaseViewHolder.get(view, R.id.adr_phone);
		TextView addressDetail = BaseViewHolder.get(view, R.id.adr_address);

		name.setText(address.getContactname());
		phone.setText(address.getMobile());
		addressDetail.setText(address.getProvince()+address.getCity()+address.getArea()
				+address.getDetailinfo());
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, AddAddressActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("address", address);
				intent.putExtras(bundle);
				context.startActivity(intent);
				((Activity) context).overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			}
		});
		return view;
	}
}
