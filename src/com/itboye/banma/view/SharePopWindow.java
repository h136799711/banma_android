package com.itboye.banma.view;

import com.itboye.banma.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

public class SharePopWindow implements OnClickListener{

	private Context context;
	private View view;
	private LinearLayout weixin;
	private LinearLayout circle_friends;
	private LinearLayout sina_microblog;
	private LinearLayout qq_friend;
	private PopupWindow popupWindow;
	LinearLayout all_choice_layout;
	
	public SharePopWindow(Context context){
		this.context = context;
		view = LayoutInflater.from(context).inflate(R.layout.pop_share,
				null);
		weixin = (LinearLayout) view.findViewById(R.id.weixin);
		circle_friends = (LinearLayout) view.findViewById(R.id.circle_friends);
		sina_microblog = (LinearLayout) view.findViewById(R.id.sina_microblog);
		qq_friend = (LinearLayout) view.findViewById(R.id.qq_friend);
		init();
	}
	
	private void init() {
		weixin.setOnClickListener(this);
		circle_friends.setOnClickListener(this);
		sina_microblog.setOnClickListener(this);
		qq_friend.setOnClickListener(this);
		popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		popupWindow.setAnimationStyle(R.style.popWindow_anim_style);
		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		
	}
	
	/** 初始化popupWindow */
	public void showAsDropDown(View parent, LinearLayout all_choice_layout) {
		this.all_choice_layout = all_choice_layout;
		popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.update();
		//添加pop窗口关闭事件  view.setVisibility(View.GONE);
		popupWindow.setOnDismissListener(new poponDismissListener());
	}
	/** 关闭popupWindow */
	public void dissmiss() {
		popupWindow.dismiss();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.weixin:
			Toast.makeText(context, "weixin", Toast.LENGTH_SHORT).show();
			break;
		case R.id.circle_friends:
			Toast.makeText(context, "circle_friends", Toast.LENGTH_SHORT).show();
			break;
		case R.id.sina_microblog:
			Toast.makeText(context, "sina_microblog", Toast.LENGTH_SHORT).show();
			break;
		case R.id.qq_friend:
			Toast.makeText(context, "qq_friend", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
	/** 
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来 
     * @author cg 
     * 
     */  
    public class poponDismissListener implements PopupWindow.OnDismissListener{  
        @Override  
        public void onDismiss() {  
        	all_choice_layout.setVisibility(View.GONE);    
        }  
          
    } 

}
