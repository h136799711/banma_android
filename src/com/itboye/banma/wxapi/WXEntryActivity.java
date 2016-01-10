package com.itboye.banma.wxapi;
import com.google.gson.Gson;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;


public class WXEntryActivity extends WXCallbackActivity  implements IWXAPIEventHandler{
	private IWXAPI api;  
	private Context context = WXEntryActivity.this;


	/*//有他登錄可以  WXCallbackActivity
	@Override
	protected void onCreate(Bundle savedInstanceState) {

	// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		api = WXAPIFactory.createWXAPI(this, Constant.APP_ID, false);
        api.registerApp(Constant.APP_ID);
		//api = WXAPIFactory.createWXAPI(this,Constant.APP_ID, true);  
        api.handleIntent(getIntent(), this);
	}*/
	//	@Override
	//	public void onNewIntent(Intent intent) {
	//		// TODO Auto-generated method stub
	//		super.onNewIntent(intent);
	//		setIntent(intent);
	//		handleIntent(intent);
	//		}

	//	@Override
	//	public void onReq(BaseReq arg0) {
	//		// TODO Auto-generated method stub
	//		
	//	}

	@Override
	public void onResp(BaseResp resp) {
		// TODO Auto-generated method stub
		//Toast.makeText(context, "onResp", Toast.LENGTH_SHORT).show();
		super.onResp(resp);   //下面登錄成功 分享不行

		//Toast.makeText(context, "super.onResp(resp);", Toast.LENGTH_SHORT).show();
		try {
			switch (resp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				Toast.makeText(context, "授权成功", Toast.LENGTH_SHORT).show();

				String code=((SendAuth.Resp)resp).code;
				AppContext.setCode(code);


				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				Toast.makeText(context, "用户拒绝", Toast.LENGTH_LONG).show();
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				Toast.makeText(context, "用户取消", Toast.LENGTH_LONG).show();
				break;
			default:
				//finish();
				//Toast.makeText(context, "異常", Toast.LENGTH_LONG).show();

			}
		}catch (Exception e) {
			// TODO: handle exception
			//Toast.makeText(context, "Exception", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

		//finish();
	}	
}



























/*package com.itboye.banma.wxapi;
import com.google.gson.Gson;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
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
import android.os.Bundle;
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
//		       switch (resp.getType()) {
//		          case 2://分享的消息类型
//		            Toast.makeText(WXEntryActivity.this, "分享成功了吗", Toast.LENGTH_LONG).show();
//		            break;
//		        }


		break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			Toast.makeText(context, "用户拒绝", Toast.LENGTH_LONG).show();
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			Toast.makeText(context, "用户取消", Toast.LENGTH_LONG).show();
			break;
	   default:
		   finish();

		}
		  finish();
		}	
}*/