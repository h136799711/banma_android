package com.itboye.banma.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.itboye.banma.R;
import com.itboye.banma.adapter.MailingAddressAdapter;
import com.itboye.banma.adapter.RedEnvelope_adapter;
import com.itboye.banma.api.ApiClient;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.entity.RedEnvelope;
import com.itboye.banma.view.MyListView;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ActivityRedEnvelope extends Activity implements StrUIDataListener{
	//private TextView tvNickName;//昵称t
	//private Button btnXiuGai;//修改按钮
	private ListView red_list;
	private TextView tvTitle;//top中textview
	private ImageView ivBack;
	private LinearLayout ll_list;
	private StrVolleyInterface networkHelper;
	private AppContext appContext;
	private LinearLayout ll_dialog;
	private ProgressBar dialog;
	private ArrayList<RedEnvelope> redList;
	private CheckBox hongbao_check;
	private RedEnvelope_adapter adapter;
	private Intent intent;
	private String hongbao;//区分不同activity来的请求红包
	private LinearLayout no_hongbao;
	//private ImageView ivBack;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_redenve);
		networkHelper = new StrVolleyInterface(this);
		networkHelper.setStrUIDataListener(this);
		appContext=(AppContext) getApplication();
		initId();
		initdata();
		
		intent=getIntent();
		hongbao=intent.getStringExtra("HONGBAO");
		
		ivBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
			}
		});
		
	}
	
	private void initdata() {
		// TODO Auto-generated method stub
		ApiClient.redEnvelope(this, 82+"", 0+"", networkHelper);
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

	private void initId() {
		// TODO Auto-generated method stub
		no_hongbao=(LinearLayout)findViewById(R.id.no_hongbao);
		hongbao_check=(CheckBox)findViewById(R.id.hongbao_choice);
		ll_dialog=(LinearLayout)findViewById(R.id.ll_dialog);
		ll_list=(LinearLayout)findViewById(R.id.ll_list);
		dialog=(ProgressBar)findViewById(R.id.progressBar);
		red_list=(ListView) findViewById(R.id.red_list);
		ivBack=(ImageView)findViewById(R.id.iv_back);
		tvTitle=(TextView)findViewById(R.id.title);
		tvTitle.setText("红包");
	}
	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
		ll_dialog.setVisibility(View.GONE);
	}
	@Override
	public void onDataChanged(String data) {
		// TODO Auto-generated method stub
		Gson gson=new Gson();
		redList=new ArrayList<RedEnvelope>();
		JSONObject jsonObject=null;
		JSONObject jsonObject2=null;
		int code = -1;
		String content = null;
		String list=null;
		try {
			jsonObject = new JSONObject(data);
			code = jsonObject.getInt("code");
			System.out.println(jsonObject.toString());
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		if (code==0) {
			try {
				jsonObject2=new JSONObject(jsonObject.getString("data"));
				JSONArray jsonArray=new JSONArray(jsonObject2.getString("list"));
				if (jsonArray.length()>0) {
					RedEnvelope redEnvelope=null;
					for (int i = 0; i < jsonArray.length(); i++) {
						redEnvelope = gson.fromJson(jsonArray.getString(i), RedEnvelope.class);
						redList.add(i, redEnvelope);
					}
					showRedList();
				}else{
					ll_dialog.setVisibility(View.GONE);
					ll_list.setVisibility(View.GONE);
					no_hongbao.setVisibility(View.VISIBLE);
					//showRedList();
				}
				System.out.println(jsonObject2.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				ll_dialog.setVisibility(View.GONE);
				ll_list.setVisibility(View.GONE);
				no_hongbao.setVisibility(View.VISIBLE);
				//showRedList();
				e.printStackTrace();
			}
		}
	}

	private void showRedList() {
		// TODO Auto-generated method stub
		ll_dialog.setVisibility(View.GONE);
		//dialog.setVisibility(View.GONE);
		ll_list.setVisibility(View.VISIBLE);
		if (!redList.isEmpty()) {
			red_list = (ListView) findViewById(R.id.red_list);
			adapter = new RedEnvelope_adapter(
					ActivityRedEnvelope.this, redList,hongbao);
			red_list.setAdapter(adapter);
		}
	}
}
