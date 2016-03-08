package com.itboye.banma.activities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.JsonArray;
import com.itboye.banma.R;
import com.itboye.banma.api.ApiClient;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;

public class YouHuiActivity extends Activity implements StrUIDataListener,android.view.View.OnClickListener{
	private StrVolleyInterface networkHelper;
	private AppContext appContext;
	private ProgressDialog dialog;
	private  TextView title;
	private  ImageView iv_back;
	private EditText et_youhuima;
	private Button btn_yanzheng;
	private TextView tv_list;
	private TextView tv_youhui_list;
	private  int Request=0;//为1表示请求历史记录
	private String p_ids;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_youhui);
		appContext = (AppContext) getApplication();
		networkHelper = new StrVolleyInterface(this);
		networkHelper.setStrUIDataListener(this);
		
		initId();
		initData();
}

	private void initData() {
		// TODO Auto-generated method stub
		p_ids = getIntent().getStringExtra("p_ids");
		ApiClient.youHuiHistory(this, appContext.getLoginUid()+"", networkHelper);
		Request=1;
	}

	private void initId() {
		// TODO Auto-generated method stub
		title=(TextView)findViewById(R.id.title);
		title.setText("使用优惠码");
		iv_back=(ImageView)findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
		btn_yanzheng=(Button) findViewById(R.id.btn_yanzheng);
		btn_yanzheng.setOnClickListener(this);
		et_youhuima=(EditText)findViewById(R.id.et_youhuima);
		tv_list=(TextView) findViewById(R.id.tv_list);
		tv_youhui_list=(TextView)findViewById(R.id.tv_youhui_list);
	}

	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
		
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public void onDataChanged(String data) {
		// TODO Auto-generated method stub
		JSONObject jsonObject=null;
		int code = -1;
		String content = null;
		try {
			jsonObject = new JSONObject(data);
			code = jsonObject.getInt("code");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		if (Request==1) {
			if (code==0) {
				try {
					content = jsonObject.getString("data");
					JSONArray jsonArray=new JSONArray(content);
					System.out.println(jsonArray.length());
					if (jsonArray.length()>0) {
						String temp="";
						for (int i = 0; i < jsonArray.length()-1; i++) {
							temp+=jsonArray.getJSONObject(i).getString("idcode")+"\n";
						}
						temp+=jsonArray.getJSONObject(jsonArray.length()-1).getString("idcode");
						tv_youhui_list.setText(temp);
						System.out.println(content.toString());
						tv_youhui_list.setVisibility(View.VISIBLE);
					}else {
						tv_youhui_list.setVisibility(View.VISIBLE);
						tv_youhui_list.setTextColor(R.color.lightgray);
						//tv_youhui_list.setTextSize(R.dimen.text_size_14);
						tv_youhui_list.setGravity(Gravity.LEFT);
						LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
								(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
						layoutParams.setMargins(20,30,20,0);//4个参数按顺序分别是左上右下
						tv_youhui_list.setLayoutParams(layoutParams);
						tv_youhui_list.setText("优惠码的使用方法：优惠码为其他已经注册斑马海购并绑定手机的用户的手机号");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else if (Request==2)  {
				try {
					content = jsonObject.getString("data");
					JSONArray jsonArray=new JSONArray(content);
					if (code==0) {
					if (jsonArray.length()>0) {
						String discount="";
						String store_id="";
						for (int i = 0; i < jsonArray.length(); i++) {
							discount=jsonArray.getJSONObject(i).getString("discount_ratio");
							store_id=jsonArray.getJSONObject(i).getString("store_id");
						}
						Intent intent=new Intent(YouHuiActivity.this,ConfirmOrdersActivity.class);
						 intent.putExtra("discount_ratio", discount);
						 intent.putExtra("store_id", store_id);
						 intent.putExtra("idcode", et_youhuima.getText().toString());
						 setResult(1005, intent);
						 finish();
						 overridePendingTransition(R.anim.push_right_in,
								 R.anim.push_right_out);
						 System.out.println(content.toString());
					}
				} else {
					 Toast.makeText(YouHuiActivity.this, content.toString(), Toast.LENGTH_LONG).show();
				}
				}catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.btn_yanzheng:
			Request=2;
			ApiClient.youHuiMa(YouHuiActivity.this, et_youhuima.getText().toString(), p_ids,networkHelper);
			break;
		default:
			break;
		}
	}
}
