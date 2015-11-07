package com.itboye.banma.wxapi;
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
import android.os.Bundle;
import android.widget.Toast;


public class WXEntryActivity extends Activity  implements IWXAPIEventHandler{
	private IWXAPI api;  
	private Context context = WXEntryActivity.this;
	
	private void handleIntent(Intent paramIntent) {
		api = WXAPIFactory.createWXAPI(this,Constant.APP_ID, true);  
		api.handleIntent(paramIntent, this);
		}
	@Override
	protected void onCreate(Bundle savedInstanceState) {

	// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		handleIntent(getIntent());
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
			 Constant.WEIXIN_CODE=code;
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