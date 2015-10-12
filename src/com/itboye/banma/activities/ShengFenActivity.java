package com.itboye.banma.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.itboye.banma.R;
import com.itboye.banma.api.ApiClient;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;

public class ShengFenActivity extends Activity implements OnClickListener,StrUIDataListener {
	
	private AppContext appContext;
	private SharedPreferences sp;
	private StrVolleyInterface networkHelper;
	private EditText etTrueName;//真实姓名
	private EditText etShenfenNumber;//身份证号
	private Button btnSubShenfen;//提交
	private ImageView ivBack;
	private TextView tvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shengfen);
        appContext = (AppContext) getApplication();
		networkHelper = new StrVolleyInterface(this);
		networkHelper.setStrUIDataListener(this);
       initId(this);
       btnSubShenfen.setOnClickListener(this);
       ivBack.setOnClickListener(this);
    }
	private void initId(ShengFenActivity shengFenActivity) {
		// TODO Auto-generated method stub
		tvTitle=(TextView)findViewById(R.id.title);
		tvTitle.setText("身份证信息填写");
		ivBack=(ImageView)findViewById(R.id.iv_back);
		etShenfenNumber=(EditText)findViewById(R.id.et_shenfen_number);
		etTrueName=(EditText)findViewById(R.id.et_true_name);
		btnSubShenfen=(Button)findViewById(R.id.btn_sub_shenfen);
	}
	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
		
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
			Toast.makeText(ShengFenActivity.this, content, Toast.LENGTH_LONG).show();
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
		}
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_sub_shenfen:
			ApiClient.modifyPersonal(ShengFenActivity.this, appContext.getLoginUid()+"","" , "",etTrueName.getText().toString(), 
					etShenfenNumber.getText().toString(), "", "", "", "", "", networkHelper);
			break;
		case R.id.iv_back:
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			break;

		default:
			break;
		}
	}
}
