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
			Toast.makeText(context, "授权成功", Toast.LENGTH_SHORT).show();
			 String code=((SendAuth.Resp)resp).code;
		       AppContext.setCode(code);
//			  Toast.makeText(context, code, Toast.LENGTH_LONG).show();
			  
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
}