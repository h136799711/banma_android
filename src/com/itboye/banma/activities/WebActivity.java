package com.itboye.banma.activities;
/*
 * 主要用于显示webview相关的链接界面
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.itboye.banma.R;

public class WebActivity  extends Activity{
	WebView wvShow;//显示web页面
	ImageView ivBackWeb;//web页面的返回按钮
	ProgressBar dialog;//显示正在加载
	TextView  tvTitle;//titlebar标示
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        wvShow=(WebView)findViewById(R.id.wv_show);
        ivBackWeb=(ImageView)findViewById(R.id.iv_back_web);
        tvTitle=(TextView)findViewById(R.id.tv_title);
        dialog=(ProgressBar)findViewById(R.id.progressBar);
        //dialog.setVisibility(View.VISIBLE);
        Intent intent=getIntent();
        final String url=intent.getStringExtra("Url"); 
        if (url.equals("http://banma.itboye.com/Public/html/about.html")) {
			tvTitle.setText("关于斑马");
		}
        wvShow.setHorizontalScrollBarEnabled(false);
        wvShow.setHorizontalScrollbarOverlay(false);  
        wvShow.setWebViewClient(new WebViewClient(){
        	@Override
        	public void onPageFinished(WebView view, String url) {
        		// TODO Auto-generated method stub
        		super.onPageFinished(view, url);
        		view.setVisibility(View.VISIBLE);
                dialog.setVisibility(View.GONE);
        	}
        });
        wvShow.loadUrl(url);
//        wvShow.setWebChromeClient(new WebChromeClient(){
//        	@Override
//       	 public void onProgressChanged(WebView view, int newProgress) {
//                // TODO Auto-generated method stub
//                if (newProgress == 100) {
//                    // 网页加载完成
//              	wvShow.setVisibility(View.VISIBLE);
//                dialog.setVisibility(View.GONE);
//               	  //  view.loadUrl(url);
//                } 
//            }
//        });

   
        ivBackWeb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();;//结束当前webview Activity
			}
		});
    }
}
