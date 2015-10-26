package com.itboye.banma.fragment;

import com.itboye.banma.R;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class OrderDaiPingFragment extends Fragment {
	private 	View view ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view= inflater.inflate(R.layout.fragment_daiping, null);
		return view;
	}

}
