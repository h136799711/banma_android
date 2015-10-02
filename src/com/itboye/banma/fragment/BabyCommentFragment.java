package com.itboye.banma.fragment;

import com.itboye.banma.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BabyCommentFragment extends Fragment{
	private View chatView;
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        chatView = inflater.inflate(R.layout.fragment_baby_comment, container,false);
        return chatView;    
    }

}
