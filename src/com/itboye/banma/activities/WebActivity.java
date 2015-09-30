package com.itboye.banma.activities;
/*
 * 主要用于显示webview相关的链接界面
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.itboye.banma.R;

public class WebActivity  extends Activity{
	WebView wvShow;//显示web页面
	ImageView ivBackWeb;//web页面的返回按钮
	ProgressDialog dialog;//显示正在加载
	TextView  tvTitle;//titlebar标示
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        wvShow=(WebView)findViewById(R.id.wv_show);
        ivBackWeb=(ImageView)findViewById(R.id.iv_back_web);
        tvTitle=(TextView)findViewById(R.id.tv_title);
        Intent intent=getIntent();
        String Url=intent.getStringExtra("Url"); 
        if (Url.equals("http://banma.itboye.com/Public/html/about.html")) {
			tvTitle.setText("关于斑马");
		}
        if (Url.equals("http://banma.itboye.com/Public/html/copyright.html")) {
            wvShow.loadUrl(Url);
		}else if (Url.equals( "http://banma.itboye.com/Public/html/about.html")) {
			wvShow.loadUrl(Url);
		}

   
        ivBackWeb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();;//结束当前webview Activity
			}
		});
    }
}
