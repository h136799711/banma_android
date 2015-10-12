package com.itboye.banma.activities;
/*
 * 主要用于显示webview相关的链接界面
 */
import android.app.Activity;
import android.app.Application;
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
import com.itboye.banma.app.AppContext;

public class WebActivity  extends Activity{
	WebView wvShow;//显示web页面
	ImageView ivBackWeb;//web页面的返回按钮
	ProgressBar dialog;//显示正在加载
	TextView  tvTitle;//titlebar标示
	private String url;
	private AppContext appContext;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        appContext = (AppContext) getApplication();
        wvShow=(WebView)findViewById(R.id.wv_show);
        ivBackWeb=(ImageView)findViewById(R.id.iv_back_web);
        tvTitle=(TextView)findViewById(R.id.tv_title);
        dialog=(ProgressBar)findViewById(R.id.progressBar);
        //dialog.setVisibility(View.VISIBLE);
        Intent intent=getIntent();
      //  String uid=appContext.getLoginUid()+"";
        String reslut=intent.getStringExtra("Url"); 
        if (reslut.equals("http://banma.itboye.com/Public/html/about.html")) {
			tvTitle.setText("关于斑马");
			url="http://banma.itboye.com/Public/html/about.html";
		}
        if (reslut.equals("http://banma.itboye.com/Public/html/copyright.html")) {
			tvTitle.setText("服务条款");
			url="http://banma.itboye.com/Public/html/copyright.html";
		}
        if (reslut.equals("FanYong")) {
			tvTitle.setText("我的返佣");
			 url="http://banma.itboye.com/index.php/Home/User/rebate?uid="+appContext.getLoginUid()+""
		        		+ "&access_token="+AppContext.getAccess_token()+"&key="+appContext.getPassword()+"";
		}
        if (reslut.equals("MingPian")) {
			tvTitle.setText("我的名片");
			 url="http://banma.itboye.com/index.php/Home/User/share?uid="+appContext.getLoginUid()+""
		        		+ "&access_token="+AppContext.getAccess_token()+"&key="+appContext.getPassword()+"";
		}
        if (reslut.equals("Order")) {
			tvTitle.setText("我的订单");
			 url="http://banma.itboye.com/index.php/Home/User/order?uid="+appContext.getLoginUid()+""
		        		+ "&access_token="+AppContext.getAccess_token()+"&key="+appContext.getPassword()+"";
		}
        
        
        wvShow.setHorizontalScrollBarEnabled(false);
        wvShow.setHorizontalScrollbarOverlay(false);  
        wvShow.loadUrl(url);
        wvShow.setWebViewClient(new WebViewClient(){
        	@Override
        	public void onPageFinished(WebView view, String url) {
        		// TODO Auto-generated method stub
        		super.onPageFinished(view, url);
        		view.setVisibility(View.VISIBLE);
                dialog.setVisibility(View.GONE);
        	}
        });
    
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
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
			}
		});
    }
}
