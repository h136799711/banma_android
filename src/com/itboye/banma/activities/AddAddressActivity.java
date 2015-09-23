package com.itboye.banma.activities;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.ProgressDialog;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.itboye.banma.R;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.entity.Area;
import com.itboye.banma.entity.MailingAdress;
import com.itboye.banma.utils.CascadingMenuViewOnSelectListener;
import com.itboye.banma.utils.DBhelper;
import com.itboye.banma.utils.DataStore;
import com.itboye.banma.view.CascadingMenuPopWindow;

public class AddAddressActivity extends Activity implements StrUIDataListener,
		OnClickListener {
	private AppContext appContext;
	private ImageView back;
	private TextView title;
	private ImageView more;
	private View view;
	private EditText name;
	private EditText telephone;
	private TextView downtown;
	private EditText detailed_address;
	private EditText postcode;
	private LinearLayout remove;
	private View kong;
	private Button save;
	private MailingAdress address;
	private StrVolleyInterface strnetworkHelper;
	private ProgressDialog dialog;

	private CascadingMenuPopWindow cascadingMenuPopWindow = null;
	private ArrayList<Area> provinceList;
	private DBhelper dBhelper;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_address);
		appContext = (AppContext) getApplication();
		dialog = new ProgressDialog(AddAddressActivity.this);
		strnetworkHelper = new StrVolleyInterface(AddAddressActivity.this);
		strnetworkHelper.setStrUIDataListener(AddAddressActivity.this);
		init();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
		}
		return false;
	}

	private void init() {
		// 向三级menu添加地区数据
		dBhelper = new DBhelper(this);
		provinceList = dBhelper.getProvince();

		Intent intentdata = this.getIntent();
		address = (MailingAdress) intentdata.getSerializableExtra("address");

		back = (ImageView) findViewById(R.id.iv_back);
		title = (TextView) findViewById(R.id.title);
		more = (ImageView) findViewById(R.id.more);
		view = findViewById(R.id.view);
		name = (EditText) findViewById(R.id.add_name);
		telephone = (EditText) findViewById(R.id.telephone);
		downtown = (TextView) findViewById(R.id.downtown);
		detailed_address = (EditText) findViewById(R.id.detailed_address);
		postcode = (EditText) findViewById(R.id.postcode);
		remove = (LinearLayout) findViewById(R.id.remove);
		kong = findViewById(R.id.kong);
		save = (Button) findViewById(R.id.add_address);

		more.setVisibility(View.VISIBLE);
		back.setOnClickListener(this);
		save.setOnClickListener(this);
		downtown.setOnClickListener(this);
		
		if (address != null) {
			name.setText(address.getContactname());
			telephone.setText(address.getMobile());
			downtown.setText(address.getProvince() + " " + address.getCity()
					+ " " + address.getArea());
			detailed_address.setText(address.getDetailinfo());
			postcode.setText(address.getPostal_code());
			title.setText(R.string.alter_address);
			remove.setVisibility(View.VISIBLE);
			kong.setVisibility(View.VISIBLE);
		} else {
			title.setText(R.string.add_address);
			remove.setVisibility(View.GONE);
			kong.setVisibility(View.GONE);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			break;
		case R.id.downtown:
			InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
			im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			showPopMenu();
			break;
		case R.id.add_address:
			dialog.setMessage("正在保存...");
	        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        dialog.show();
	        
			if(address == null){       //保存收货地址
				
				try {
					saveAdress(DataStore.AREA_ONE, DataStore.AREA_TWO, DataStore.AREA_THREE, 
							detailed_address.getText().toString(), name.getText().toString(),
							telephone.getText().toString(), postcode.getText().toString());
				} catch (Exception e) {
					e.printStackTrace();
					dialog.dismiss();
					Toast.makeText(AddAddressActivity.this, "访问异常", Toast.LENGTH_LONG).show();
				}
				
			}else{       //修改收货地址
				
			}
			

			break;
		default:
			break;
		}
	}
	/**
	 * 保存收货地址
	 * @return 
	 * @throws Exception 
	 */
	private void saveAdress(String province, String city, String area, String detailinfo,
			String contactname, String mobile, String postal_code) throws Exception {
		Boolean stateBoolean = appContext.saveAdress(AddAddressActivity.this, province, city, area, detailinfo, 
				contactname, mobile, postal_code, strnetworkHelper);
		if(stateBoolean == false){
			dialog.dismiss();
			Toast.makeText(AddAddressActivity.this, "请检查网络连接", Toast.LENGTH_LONG).show();
		}
		
	}

	private void showPopMenu() {
		if (cascadingMenuPopWindow == null) {
			cascadingMenuPopWindow = new CascadingMenuPopWindow(
					getApplicationContext(), provinceList);
			cascadingMenuPopWindow.setFocusable(true); // 设置PopupWindow可获得焦点
			cascadingMenuPopWindow.setTouchable(true); // 设置PopupWindow可触摸
			cascadingMenuPopWindow.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸
			cascadingMenuPopWindow.setBackgroundDrawable(getResources()
					.getDrawable(R.drawable.boder));//
			cascadingMenuPopWindow
					.setMenuViewOnSelectListener(new NMCascadingMenuViewOnSelectListener());
			cascadingMenuPopWindow.showAsDropDown(view, 1, 1);
		} else if (cascadingMenuPopWindow != null
				&& cascadingMenuPopWindow.isShowing()) {
			cascadingMenuPopWindow.dismiss();
		} else {
			cascadingMenuPopWindow.showAsDropDown(view, 1, 1);
		}
	}

	@Override
	public void onErrorHappened(VolleyError error) {
		dialog.dismiss();
		Toast.makeText(AddAddressActivity.this, "访问异常", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onDataChanged(String data) {
		dialog.dismiss();
		try {
			JSONObject jsondata = new JSONObject(data);
			int code = jsondata.getInt("code");
			if(code ==0){
				Toast.makeText(AddAddressActivity.this, "保存地址成功"+data, Toast.LENGTH_LONG).show();
				finish();
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_right_out);
			}
			else{
				Toast.makeText(AddAddressActivity.this, "保存地址失败"+data, Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	// 级联菜单选择回调接口
	class NMCascadingMenuViewOnSelectListener implements
			CascadingMenuViewOnSelectListener {

		@Override
		public void getValue(Area area) {
			// cascadingMenuFragment = null;
			downtown.setText(DataStore.AREA_ONE + " " + DataStore.AREA_TWO + " "
					+ DataStore.AREA_THREE);
			// 记得提交数据后清空DataStore.AREA_ONE AREA_TWO ..
		}

	}

}
