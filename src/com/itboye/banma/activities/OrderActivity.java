package com.itboye.banma.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.itboye.banma.R;
import com.itboye.banma.app.AppContext;

public class OrderActivity extends Activity{
	WebView wvShow;//显示web页面
	ImageView ivBackWeb;//web页面的返回按钮
	ProgressBar dialog;//显示正在加载
	TextView  tvTitle;//titlebar标示
	private String url;
	private AppContext appContext;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        appContext = (AppContext) getApplication();
        wvShow=(WebView)findViewById(R.id.wv_show);
        ivBackWeb=(ImageView)findViewById(R.id.iv_back);
        tvTitle=(TextView)findViewById(R.id.title);
        tvTitle.setText("我的订单");
       // dialog=(ProgressBar)findViewById(R.id.progressBar);      
        
        String url="http://banma.itboye.com/index.php/Home/User/order?uid="+appContext.getLoginUid()+""
		        		+ "&access_token="+AppContext.getAccess_token()+"&key="+appContext.getPassword()+"";
        wvShow.loadUrl(url);
        wvShow.setWebViewClient(new WebViewClient(){
        	@Override
        	public void onPageFinished(WebView view, String url) {
        		// TODO Auto-generated method stub
        		super.onPageFinished(view, url);
        		view.setVisibility(View.VISIBLE);
        		System.out.println("进来了");
                //dialog.setVisibility(View.GONE);
        	}
        });
    
        ivBackWeb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();;//结束当前webview Activity
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
			}
		});
    }
}
