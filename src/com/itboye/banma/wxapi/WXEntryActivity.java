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

	@Override
	public void onResp(BaseResp resp) {
		// TODO Auto-generated method stub
		//Toast.makeText(context, "onResp", Toast.LENGTH_SHORT).show();
		super.onResp(resp);   //下面登錄成功 分享不行

		//Toast.makeText(context, "super.onResp(resp);", Toast.LENGTH_SHORT).show();
		try {
			switch (resp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				//Toast.makeText(context, "授权成功", Toast.LENGTH_SHORT).show();

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







