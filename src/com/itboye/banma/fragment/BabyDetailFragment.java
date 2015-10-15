package com.itboye.banma.fragment;

import com.itboye.banma.R;
import com.itboye.banma.activities.BabyActivity;
import com.itboye.banma.adapter.BabyDetailAdapter;
import com.itboye.banma.view.MyListView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

public class BabyDetailFragment extends Fragment{
	private View chatView;
	private String[] details;
	private MyListView detailListView;
	private BabyDetailAdapter adapter;
	public BabyDetailFragment(String[] detail) {
		details = detail;
	}
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        chatView = inflater.inflate(R.layout.fragment_baby_detail, container,false);
        initView();
        return chatView;    
    }
	private void initView() {
		detailListView = (MyListView) chatView.findViewById(R.id.detail_image_list);
		if(details != null){
			adapter = new BabyDetailAdapter(getActivity(), details);
			detailListView.setAdapter(adapter);
		}else{
			detailListView.setVisibility(View.GONE);
			ImageView imageView = (ImageView) chatView.findViewById(R.id.con_image);
			imageView.setVisibility(View.VISIBLE);
		}
		
	}

}
