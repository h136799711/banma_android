package com.itboye.banma.wxapi;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.itboye.banma.R;
import com.itboye.banma.activities.LoginActivity;
import com.itboye.banma.api.ApiClient;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
import com.itboye.banma.entity.User;
import com.itboye.banma.fragment.CenterFragment;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Space;
import android.widget.Toast;


public class WXEntryActivity extends Activity  implements IWXAPIEventHandler{
	private IWXAPI api;  
	private Context context = WXEntryActivity.this;
	private AppContext appContext;
	private StrVolleyInterface networkHelper;
	private Gson  gson=new Gson();
	private SharedPreferences sp;
	
	private void handleIntent(Intent paramIntent) {
		api = WXAPIFactory.createWXAPI(this,Constant.APP_ID, true);  
		api.handleIntent(paramIntent, this);
		}
	@Override
	protected void onCreate(Bundle savedInstanceState) {

	// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		handleIntent(getIntent());
		appContext = (AppContext) getApplication();
		networkHelper = new StrVolleyInterface(this);
		sp = getSharedPreferences(Constant.MY_PREFERENCES, 0);
	//	networkHelper.setStrUIDataListener(this);
	}
	
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		setIntent(intent);
		handleIntent(intent);
		}

	@Override
	public void onReq(BaseReq arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResp(BaseResp resp) {
		// TODO Auto-generated method stub
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			Toast.makeText(context, "授权成功", Toast.LENGTH_LONG).show();
			 String code=((SendAuth.Resp)resp).code;
		//	 System.out.println(code+"code");
//			  SharedPreferences sp = this.getSharedPreferences(Constant.MY_PREFERENCES, 0);  
//		        //使用Editor接口修改SharedPreferences中的值并提交。  
//		     //   Editor editor = sp.edit();  
//		    //    editor.putString(Constant.WEIXIN_CODE, code);
//		     //   editor.commit();
////		       System.out.println(code+"code");
//		//	 ApiClient.wxLogin(WXEntryActivity.this, code, networkHelper);
//		       Editor edit = sp.edit();
//		       edit.putBoolean("iscode", true);
//		       edit.commit();
		       AppContext.setCode(code);
			  System.out.println( AppContext.getCode()+"code1");
			  
		break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			Toast.makeText(context, "用户拒绝", Toast.LENGTH_LONG).show();
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			Toast.makeText(context, "用户取消", Toast.LENGTH_LONG).show();
			break;
		}
		  finish();
		}
/*	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
		appContext.setLogin(false);
		Toast.makeText(WXEntryActivity.this, "登陆发生异常", Toast.LENGTH_LONG).show();
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
			AppContext.setHeadurl(user.getHead());
			AppContext.setNickname(user.getNickname());
			System.out.println(appContext.getPassword());
			Log.v("用户id", user.getId()+"");
//		    String use = etName.getText().toString();   
//		    String pas = etPassword.getText().toString(); 
	        SharedPreferences sp = this.getSharedPreferences(Constant.MY_PREFERENCES, 0);  
	        //使用Editor接口修改SharedPreferences中的值并提交。  
	        Editor editor = sp.edit();  
	        editor.putString(Constant.MY_ACCOUNT, user.getUsername());  
	        editor.putString(Constant.MY_PASSWORD,user.getPassword());  
	        editor.putBoolean(Constant.IS_LOGIN, true);
	        editor.commit();
//	        Intent  intent=getIntent();
//	        intent.putExtra("nickname", user.getNickname());
//	        intent.putExtra("headurl", user.getHead());
//	        setResult(100, intent);
//	        sp.getString(Constant.MY_ACCOUNT, "");
	        System.out.println(data.toString());
	        finish();
	        startActivity(new Intent(WXEntryActivity.this,CenterFragment.class));
	        overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
		} else {
			appContext.setLogin(false);
			System.out.println("code=" + content.toString());
		}
	}*/
		
}