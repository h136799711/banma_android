
package com.itboye.banma.wxapi;

import android.os.Bundle;

import com.itboye.banma.R;
import com.itboye.banma.app.Constant;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

public class WXEntryActivity extends WXCallbackActivity implements IWXAPIEventHandler{
	private IWXAPI api;  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        // TODO Auto-generated method stub  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_login);  
        api = WXAPIFactory.createWXAPI(this,Constant.APP_ID, false);  
        api.handleIntent(getIntent(), this);
    }
      
    @Override  
    public void onReq(BaseReq arg0) {  
        // TODO Auto-generated method stub  
          
    }  
  
    @Override  
    public void onResp(BaseResp resp) {  
        Bundle bundle = new Bundle();  
        switch (resp.errCode) {  
        case BaseResp.ErrCode.ERR_OK:  
//      可用以下两种方法获得code  
//      resp.toBundle(bundle);  
//      Resp sp = new Resp(bundle);  
//      String code = sp.code;<span style="white-space:pre">  
//      或者  
        String code = ((SendAuth.Resp)  resp).code;  
            //上面的code就是接入指南里要拿到的code  
              System.out.println("成功");
            break;  
            
        case BaseResp.ErrCode.ERR_AUTH_DENIED:  
//          可用以下两种方法获得code  
//          resp.toBundle(bundle);  
//          Resp sp = new Resp(bundle);  
//          String code = sp.code;<span style="white-space:pre">  
//          或者  
            String code1 = ((SendAuth.Resp)  resp).code;  
                //上面的code就是接入指南里要拿到的code  
                  System.out.println("拒绝");
                break;  
                
        case BaseResp.ErrCode.ERR_USER_CANCEL:  
//          可用以下两种方法获得code  
//          resp.toBundle(bundle);  
//          Resp sp = new Resp(bundle);  
//          String code = sp.code;<span style="white-space:pre">  
//          或者  
            String code11 = ((SendAuth.Resp)  resp).code;  
                //上面的code就是接入指南里要拿到的code  
                  System.out.println("成功");
                break;  
  
        default:  
            break;  
        }  
          
    }  
}
