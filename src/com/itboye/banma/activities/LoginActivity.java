package com.itboye.banma.activities;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.google.gson.Gson;
import com.itboye.banma.R;
import com.itboye.banma.activities.RegistActivity.CloseReceiver;
import com.itboye.banma.api.ApiClient;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
import com.itboye.banma.entity.User;
import com.itboye.banma.util.FileUtil;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.social.UMSocialService;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.UMQQSsoHandler;
public class LoginActivity extends Activity implements StrUIDataListener,OnClickListener {
	TextView tvRegist;//注册view
	Button btnLogin;//登陆按钮
	EditText etName;//用户账号，一般为手机号
	EditText etPassword;//用户密码/明文
   TextView tvForget;//忘记密码
   TextView tvQuXiao;//取消
   private int LoginWay;//判断登陆方式
    private ImageView ivWeixin,iv_xinlang,iv_taobao,iv_qq;//微信登陆
	private AppContext appContext;
	private StrVolleyInterface networkHelper;
	private Gson gson = new Gson();
	private ProgressDialog dialog;
//    UMSocialService mController;
	private IWXAPI api;  
    private SharedPreferences sp ;
	private Intent intent;
	private CloseReceiver closeReceiver;
	private com.umeng.socialize.controller.UMSocialService mController;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		mController = UMServiceFactory.getUMSocialService("com.umeng.login");
		
		api = WXAPIFactory.createWXAPI(this,Constant.APP_ID, true);  
		api.registerApp(Constant.APP_ID);
		//添加qq平台
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "1104887406",
                "7mxqFi07TN8QD1ZR");
		qqSsoHandler.addToSocialSDK();
		
		// 添加微信平台
//		mController = UMServiceFactory.getUMSocialService("com.umeng.login");
//		UMWXHandler wxHandler = new UMWXHandler(this,APP_ID,AppSecret);
//		wxHandler.addToSocialSDK();
		
		intent=getIntent();
		closeReceiver = new CloseReceiver();  
		IntentFilter intentFilter = new IntentFilter("KILL_ACTIVITY");  
		registerReceiver(closeReceiver, intentFilter);  
		
		initId(this);
		dialog = new ProgressDialog(LoginActivity.this);
		appContext = (AppContext) getApplication();
		networkHelper = new StrVolleyInterface(this);
		networkHelper.setStrUIDataListener(this);
		
		
		tvRegist.setOnClickListener(new RegistListener());
		btnLogin.setOnClickListener(new LoginListener());
		tvForget.setOnClickListener(forgetListener);
		tvQuXiao.setOnClickListener(quxiaoListener );
		ivWeixin.setOnClickListener(this);
		
	}

	
	//用于结束activity 
	 public class CloseReceiver extends BroadcastReceiver  
	 {   
	        @Override  
	        public void onReceive(Context context, Intent intent)  
	        {  
	         finish();  
	        }
	 }  
	 
	 //友盟统计
		 public void onPause() {
		 super.onPause();
		 MobclickAgent.onPause(this);
		 } 
		 
	 @Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
		 	unregisterReceiver(closeReceiver);
			super.onDestroy();
		}
	
	private void initId(LoginActivity loginActivity) {
		// TODO Auto-generated method stub
		
		SharedPreferences sp = this.getSharedPreferences(Constant.MY_PREFERENCES, 0);  
        String account = sp.getString(Constant.MY_ACCOUNT, "");
        String pass = sp.getString(Constant.MY_PASSWORD, "");  
        Log.v("账号",account);
        Log.v("密码", pass);
      /*  //对密码进行AES解密  
        try{  
            pass = AESEncryptor.decrypt("41227677", pass);  
        }catch(Exception ex){  
            Toast.makeText(this, "获取密码时产生解密错误!", Toast.LENGTH_LONG).show();
            pass = "";  
        }  */
        iv_qq=(ImageView)findViewById(R.id.iv_qq);
        iv_qq.setOnClickListener(this);
        
        iv_taobao=(ImageView)findViewById(R.id.iv_taobao);
        iv_taobao.setOnClickListener(this);
        
        iv_xinlang=(ImageView)findViewById(R.id.iv_xinlang);
        iv_xinlang.setOnClickListener(this);
        
       
        ivWeixin=(ImageView) findViewById(R.id.iv_weixin);
		 tvQuXiao=(TextView)findViewById(R.id.tv_quxiao);
        tvForget=(TextView)findViewById(R.id.tv_forget);
		tvRegist=(TextView)findViewById(R.id.tv_regist);
		btnLogin=(Button)findViewById(R.id.btn_login);
		etName=(EditText)findViewById(R.id.et_name);
		etPassword=(EditText)findViewById(R.id.et_password);
		etName.addTextChangedListener(new TextChange());
		etPassword.addTextChangedListener(new TextChange());
		etName.setText(account);
		etPassword.setText(pass);
	}
	

	
	@Override
	protected void onResume() {
		super.onResume();
//		SharedPreferences sp = this.getSharedPreferences(Constant.MY_PREFERENCES, 0);  
		 MobclickAgent.onResume(this);//友盟统计
		if (!AppContext.getCode().equals("")) {
	    	ApiClient.wxLogin(LoginActivity.this,AppContext.getCode() ,networkHelper);
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_weixin:
			final SendAuth.Req req = new SendAuth.Req();  
			req.scope = "snsapi_userinfo";  
			req.state = "wechat_sdk_demo_test";  
			api.sendReq(req);  
			break;
			
		case R.id.iv_qq:
		    Toast.makeText(LoginActivity.this, "授权开始", Toast.LENGTH_SHORT).show();
			mController.doOauthVerify(getApplicationContext(), SHARE_MEDIA.QQ, new UMAuthListener() {
			    @Override
			    public void onStart(SHARE_MEDIA platform) {
			        Toast.makeText(LoginActivity.this, "授权开始", Toast.LENGTH_SHORT).show();
			    }
			    @Override
			    public void onError(SocializeException e, SHARE_MEDIA platform) {
			        Toast.makeText(LoginActivity.this, "授权错误", Toast.LENGTH_SHORT).show();
			    }
			    @Override
			    public void onComplete(Bundle value, SHARE_MEDIA platform) {
			        Toast.makeText(LoginActivity.this, "授权完成", Toast.LENGTH_SHORT).show();
			        //获取相关授权信息
			        mController.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, new UMDataListener() {
			    @Override
			    public void onStart() {
			        Toast.makeText(LoginActivity.this, "获取平台数据开始...", Toast.LENGTH_SHORT).show();
			    }                                              
			    @Override
			        public void onComplete(int status, Map<String, Object> info) {
			            if(status == 200 && info != null){
			                StringBuilder sb = new StringBuilder();
			                Set<String> keys = info.keySet();
			                for(String key : keys){
			                   sb.append(key+"="+info.get(key).toString()+"\r\n");
			                }
			                Log.d("TestData",sb.toString());
			            }else{
			               Log.d("TestData","发生错误："+status);
			           }
			        }
			   
			});
			    }
			@Override
			public void onCancel(SHARE_MEDIA platform) {
			        Toast.makeText(LoginActivity.this, "授权取消", Toast.LENGTH_SHORT).show();
			    }
			} );
			break;
			
		case R.id.iv_xinlang:
		
			break;
			
		case R.id.iv_taobao:
			
			break;

		default:
			break;
		}
	}
	//各种监听
	
	//取消 返回键
	OnClickListener quxiaoListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
		}
	};
	
	//忘记密码
	OnClickListener forgetListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub 
			Intent intent=new Intent(LoginActivity.this,RegistActivity.class);
			intent.putExtra("forgetFlag", "forget");
			startActivity(intent);
		}
	};
	
	class LoginListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//登陆请求
	        String name = LoginActivity.this.etName.getText().toString();
	        String password = LoginActivity.this.etPassword.getText().toString();
			ApiClient.Login(LoginActivity.this, name, password, networkHelper);
			dialog.setMessage("正在登录...");
	        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        dialog.show();
		}
	}

	
	class RegistListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intentRegist=new Intent(LoginActivity.this,RegistActivity.class);
			LoginActivity.this.startActivity(intentRegist);
		}
	}


	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
		dialog.dismiss();
		appContext.setLogin(false);
		Toast.makeText(LoginActivity.this, "登陆发生异常", Toast.LENGTH_LONG).show();
	}
	@Override
	public void onDataChanged(String data) {
		// TODO Auto-generated method stub
		JSONObject jsonObject=null;
		int code = -1;
		String content = null;
		try {
			jsonObject = new JSONObject(data);
			code = jsonObject.getInt("code");
			content = jsonObject.getString("data");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		if (code == 0) {
			//String userId=jsonObject.getString("data");
			//System.out.println("code=" + data.toString());
			User user = gson.fromJson(content, User.class);
			appContext.setLogin(true);
			appContext.setLoginUid(user.getId());
			appContext.setPassword(user.getPassword());
			AppContext.setCoin(user.getCoin());
			AppContext.setHeadurl(user.getHead());
			AppContext.setNickname(user.getNickname());
			AppContext.setIdcode(user.getIdcode());
		  //  AppContext.setHasHead(true);
		    saveHead(user.getHead());
			//AppContext.setIdcode(user.get);
			System.out.println(appContext.getPassword());
			Log.v("用户id", user.getId()+"");
		    String use = etName.getText().toString();   
		    String pas = etPassword.getText().toString(); 
		    SharedPreferences sp = this.getSharedPreferences(Constant.MY_PREFERENCES, 0);  
		    Editor editor = sp.edit();  
	        editor.putString(Constant.MY_BANGDING, user.getWexin_bind());
	        editor.putString(Constant.MY_HEAD_URL, user.getHead());
	        editor.putString(Constant.MY_IDNUMBER, user.getIdnumber());
	        editor.putString(Constant.MY_SHIMING, user.getStatus());
	        editor.putString(Constant.MY_USER_NICK, user.getNickname());
	        editor.putString(Constant.MY_USERID,user.getId()+"");
	        editor.putString(Constant.MY_ACCOUNT, user.getMobile());
	        editor.putBoolean(Constant.IS_LOGIN, true);
		if (AppContext.getCode()=="") {
			        //使用Editor接口修改SharedPreferences中的值并提交。  
			        editor.putString(Constant.MY_ACCOUNT, use);  
			        editor.putString(Constant.MY_PASSWORD,pas);  
			        editor.putBoolean(Constant.IS_LOGIN, true);
		}else {
		
		        AppContext.setWeixin(true);
		        AppContext.setCode("");
		}
        editor.commit();
	        dialog.dismiss();
	        Intent  intent=getIntent();
	        intent.putExtra("nickname", user.getNickname());
	        intent.putExtra("headurl", user.getHead());
	        setResult(100, intent);
	        sp.getString(Constant.MY_ACCOUNT, "");
	        System.out.println(data.toString());
	        finish();
	        overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
		} else {
			dialog.dismiss();
			appContext.setLogin(false);
			Toast.makeText(LoginActivity.this, "登陆失败，请检查用户名和密码" , Toast.LENGTH_LONG)
			.show();
			System.out.println("code=" + content.toString());
		}
	}
	
	// EditText监听器
    class TextChange implements TextWatcher {

        @Override
        public void afterTextChanged(Editable arg0) {
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before,
                int count) {

            boolean Sign2 = etName.getText().length() > 0;
            boolean Sign3 = etPassword.getText().length() > 0;

            if (Sign2 & Sign3) {
            	btnLogin.setTextColor(0xFFFFFFFF);
            	btnLogin.setEnabled(true);
            }
            // 在layout文件中，对Button的text属性应预先设置默认值，否则刚打开程序的时候Button是无显示的
            else {
            	btnLogin.setTextColor(0xFF808080);
            	btnLogin.setEnabled(false);
            }
        }

    }
    
    
    
//    @Override
//    protected void onRestart() {
//    	// TODO Auto-generated method stub
//    	super.onRestart();
//    	//appContext = (AppContext) getApplication();
//    	System.out.println(AppContext.getCode());
//    	ApiClient.wxLogin(LoginActivity.this,AppContext.getCode() ,networkHelper);
////    	 SharedPreferences sp = this.getSharedPreferences(Constant.MY_PREFERENCES, 0);  
////         String code = sp.getString(Constant.WEIXIN_CODE, "");
////         System.out.println(sp.getString(Constant.WEIXIN_CODE, "")+"执行");
////     	ApiClient.wxLogin(LoginActivity.this,code ,networkHelper);
//    }
//		
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
		}
		return false;
	}
    
    private void saveHead(String url){
    	ImageRequest imageRequest = new ImageRequest(  
				url,  
		        new Response.Listener<Bitmap>() {  
		            @Override  
		            public void onResponse(Bitmap response) {  
		                try {
							AppContext.setHeadurl(FileUtil.saveFile(getApplicationContext(), 
									getApplicationContext().getFilesDir().getCanonicalPath(),
									Constant.IMAGE_FILE_NAME, response));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }  
		        }, 0, 0, Config.RGB_565, new Response.ErrorListener() {  
		            @Override  
		            public void onErrorResponse(VolleyError error) {  
		            }  
		        });  
		AppContext.queues.add(imageRequest);  
    }

}
