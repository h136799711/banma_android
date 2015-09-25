package com.itboye.banma.util;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.itboye.banma.R;

public class ChoosePictureDialog {

	@SuppressLint("InflateParams")
	public static Dialog getDialog(Context  context){
		  LayoutInflater inflater = LayoutInflater.from(context);  
	        View v = inflater.inflate(R.layout.dialog_picture, null);// 得到加载view  
	        LinearLayout layout = (LinearLayout) v.findViewById(R.id.ll_dialog);// 加载布局          
	        // main.xml中的ImageView    
	        Dialog   loadingDialog = new Dialog(context, R.style.picture_dialog);// 创建自定义样式dialog  
	        
	        loadingDialog.setCancelable(true);// 可以用“返回键”取消  
	        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(  
	                LinearLayout.LayoutParams.MATCH_PARENT,  
	                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局  
	        return loadingDialog;  
		
	}
}
