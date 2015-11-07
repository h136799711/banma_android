package com.itboye.banma.activities;

import com.android.volley.VolleyError;
import com.itboye.banma.R;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;

import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FindFragment extends Fragment implements OnClickListener ,StrUIDataListener {
	private AppContext appContext;
	private StrVolleyInterface networkHelper;
	private View productGridView;
	private TextView title;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		networkHelper = new StrVolleyInterface(getActivity());
		networkHelper.setStrUIDataListener(this);
		appContext = (AppContext) getActivity().getApplication();
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		productGridView = inflater.inflate(R.layout.fragment_find, container, false);

		initView();
		return productGridView;
	}


	private void initView() {
		// TODO Auto-generated method stub
		title=(TextView)productGridView.findViewById(R.id.title);
		title.setText("发现");
	}


	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDataChanged(String data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}

}
