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
import android.widget.Toast;

import org.w3c.dom.Text;

import com.itboye.banma.R;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

public class WebActivity  extends Activity{
	WebView wvShow;//显示web页面
	ImageView ivBackWeb;//web页面的返回按钮
	ProgressBar dialog;//显示正在加载
	TextView  tvTitle;//titlebar标示
	private UMSocialService mController;
	private TextView tvRight;
	private String url;
	private AppContext appContext;
	private String shareTextBig="["+AppContext.getNickname()+"]"+"邀请您加入...";
	private String shareTextSmall="["+AppContext.getNickname()+"]"+"邀请您加入斑马海购";
	private  String urlShare;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        appContext = (AppContext) getApplication();
        urlShare="http://banma.itboye.com/index.php/Home/InviteRegister/index?uid="+appContext.getLoginUid()+"";
      //集成微信

      		// 添加微信平台
      		UMWXHandler wxHandler = new UMWXHandler(this,Constant.APP_ID,Constant.AppSecret);
      		wxHandler.addToSocialSDK();
      		// 添加微信朋友圈
      		UMWXHandler wxCircleHandler = new UMWXHandler(this,Constant.APP_ID,Constant.AppSecret);
      		wxCircleHandler.setToCircle(true);
      		wxCircleHandler.addToSocialSDK();
      		
      		
      		
      		
      		//集成扣扣分享
      		//参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
      		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "1104887406",
      		                "7mxqFi07TN8QD1ZR");
      		qqSsoHandler.addToSocialSDK(); 
      		
      		//分享扣扣空间
      		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, "1104887406",
                      "7mxqFi07TN8QD1ZR");
              qZoneSsoHandler.addToSocialSDK();
        
        tvRight=(TextView)findViewById(R.id.tv_left);
        tvRight.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				umengShare();
			}
		});
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
        if (reslut.equals("Rank")) {
			tvTitle.setText("邀请排行榜");
			tvRight.setVisibility(View.VISIBLE);
			 url="http://banma.itboye.com/index.php/Home/Share/ranking_invite";
		}
//        
//        if (reslut.equals("Invite")) {
//			tvTitle.setText("邀请排行榜");
//			tvRight.setVisibility(View.VISIBLE);
//			 url="http://banma.itboye.com/index.php/Home/Share/ranking_invite";
//		}
        
        
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
	
	//友盟统计
		@Override
		protected void onResume() {

			super.onResume();
			MobclickAgent.onResume(this);
		}

		public void onPause() {
			super.onPause();
			MobclickAgent.onPause(this);
		}
		
		private void umengShare() {

			 // 首先在您的Activity中添加如下成员变量
				mController = UMServiceFactory.getUMSocialService("com.umeng.share");

				//设置腾讯微博SSO handler
				mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
				
		    	mController.setAppWebSite(SHARE_MEDIA.RENREN, "http://www.umeng.com/social");
		    	
		    	mController.getConfig().removePlatform( SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN);
		   
		    	//设置微信好友分享内容
		    	WeiXinShareContent weixinContent = new WeiXinShareContent();
		    	//设置分享文字
		    	weixinContent.setShareContent(shareTextSmall);
		    	//设置title
		    	weixinContent.setTitle("斑马海外购");
		    	//设置分享内容跳转URL
		    	weixinContent.setTargetUrl(urlShare);
		    	//设置分享图片
		    	weixinContent.setShareImage(new UMImage(this,R.drawable.icon));
		    	mController.setShareMedia(weixinContent);			
		    
	   
		    	
		    	//设置微信朋友圈分享内容
		    	CircleShareContent circleMedia = new CircleShareContent();
		    	circleMedia.setShareContent(shareTextSmall);
		    	//设置朋友圈title
		    	circleMedia.setTitle("斑马海外购");
		    	circleMedia.setShareImage(new UMImage(this,R.drawable.icon));
		    	circleMedia.setTargetUrl(urlShare);
		    	mController.setShareMedia(circleMedia);
		    	
		    	QQShareContent qqShareContent = new QQShareContent();
		    	//设置分享文字
		    	qqShareContent.setShareContent(shareTextSmall);
		    	//设置分享title
		    	qqShareContent.setTitle("斑马海外购");
		    	//设置分享图片
		    	qqShareContent.setShareImage(new UMImage(this,R.drawable.icon));
		    	//设置点击分享内容的跳转链接
		    	qqShareContent.setTargetUrl(urlShare);
		    	mController.setShareMedia(qqShareContent);
		    	
		    	QZoneShareContent qzone = new QZoneShareContent();
		    	//设置分享文字
		    	qzone.setShareContent(shareTextSmall);
		    	//设置点击消息的跳转URL
		    	qzone.setTargetUrl(urlShare);
		    	//设置分享内容的标题
		    	qzone.setTitle("斑马海外购");
		    	//设置分享图片
		    	qzone.setShareImage(new UMImage(this,R.drawable.icon));
		    	mController.setShareMedia(qzone);
		    	
		    	mController.openShare(WebActivity.this, false);
	}


}
