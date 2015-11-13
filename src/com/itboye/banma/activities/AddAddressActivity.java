package com.itboye.banma.activities;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.umeng.analytics.MobclickAgent;

public class AddAddressActivity extends Activity implements StrUIDataListener,
		OnClickListener {
	public static final int SAVE_ADDRESS = 1;
	public static final int UPDATE_ADDRESS = 2;
	public static final int DELETE_ADDRESS = 3;
	public static final int SET_DEFAULT_ADDERSS = 4;
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
	private EditText id_card;
	private LinearLayout remove;
	private View default_address_line;
	private LinearLayout default_address_layout;
	private CheckBox default_address;
	private View kong;
	private Button save;
	private MailingAdress address;
	private StrVolleyInterface strnetworkHelper;
	private ProgressDialog dialog;
	private int netState = 0;
	private int add_state;  //接收确认订单跳转过来的传参

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

	
	private void init() {
		// 向三级menu添加地区数据
		dBhelper = new DBhelper(this);
		provinceList = dBhelper.getProvince();

		Intent intentdata = this.getIntent();
		address = (MailingAdress) intentdata.getSerializableExtra("address");
		add_state = intentdata.getIntExtra("add_state", 0);
		back = (ImageView) findViewById(R.id.iv_back);
		title = (TextView) findViewById(R.id.title);
		more = (ImageView) findViewById(R.id.more);
		view = findViewById(R.id.view);
		name = (EditText) findViewById(R.id.add_name);
		telephone = (EditText) findViewById(R.id.telephone);
		downtown = (TextView) findViewById(R.id.downtown);
		detailed_address = (EditText) findViewById(R.id.detailed_address);
		postcode = (EditText) findViewById(R.id.postcode);
		id_card =  (EditText) findViewById(R.id.id_card);
		default_address_line = findViewById(R.id.default_address_line);
		default_address_layout = (LinearLayout) findViewById(R.id.default_address_layout);
		default_address = (CheckBox) findViewById(R.id.default_address);
		remove = (LinearLayout) findViewById(R.id.remove);
		kong = findViewById(R.id.kong);
		save = (Button) findViewById(R.id.add_address);

		more.setVisibility(View.GONE);
		back.setOnClickListener(this);
		save.setOnClickListener(this);
		downtown.setOnClickListener(this);
		remove.setOnClickListener(this);

		if (address != null) {
			DataStore.AREA_PROVINCE = new Area(address.getProvinceid(),
					address.getProvince(), null);
			DataStore.AREA_CITY = new Area(address.getCityid(),
					address.getCity(), address.getProvinceid());
			DataStore.AREA_DISTRICT = new Area(address.getAreaid(),
					address.getArea(), address.getCityid());
			name.setText(address.getContactname());
			telephone.setText(address.getMobile());
			downtown.setText(DataStore.AREA_PROVINCE.getName() + " "
					+ DataStore.AREA_CITY.getName() + " "
					+ DataStore.AREA_DISTRICT.getName());
			detailed_address.setText(address.getDetailinfo());
			postcode.setText(address.getPostal_code());
			title.setText(R.string.alter_address);
			id_card.setText(address.getId_card());
			default_address_line.setVisibility(View.VISIBLE);
			default_address_layout.setVisibility(View.VISIBLE);
			remove.setVisibility(View.VISIBLE);
			kong.setVisibility(View.VISIBLE);
		} else {
			DataStore.AREA_PROVINCE = new Area("110000", "北京市 ", null);
			DataStore.AREA_CITY = new Area("110100", "市辖区 ", "110000");
			DataStore.AREA_DISTRICT = new Area("110101", "东城区 ", "110100");
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
			InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			im.hideSoftInputFromWindow(getCurrentFocus()
					.getApplicationWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
			showPopMenu();
			break;
		case R.id.add_address:
			dialog.setMessage("正在保存...");
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.show();

			if (address == null) { // 保存收货地址

				try {
					saveAdress(DataStore.AREA_PROVINCE, DataStore.AREA_CITY,
							DataStore.AREA_DISTRICT, detailed_address.getText()
									.toString(), name.getText().toString(),
							telephone.getText().toString(), postcode.getText()
									.toString(), id_card.getText().toString());
				} catch (Exception e) {
					e.printStackTrace();
					dialog.dismiss();
					Toast.makeText(AddAddressActivity.this, "访问异常",
							Toast.LENGTH_LONG).show();
				}

			} else { // 修改收货地址
				if (default_address.isChecked() == true) {

					try {
						setDefaultAddress(address.getId());
					} catch (Exception e) {
						e.printStackTrace();
						netState = 0;
					}
				}

				try {
					updateAdress(DataStore.AREA_PROVINCE, DataStore.AREA_CITY,
							DataStore.AREA_DISTRICT, detailed_address.getText()
									.toString(), name.getText().toString(),
							telephone.getText().toString(), postcode.getText()
									.toString(), address.getId(),id_card.getText().toString());
				} catch (Exception e) {
					e.printStackTrace();
					dialog.dismiss();
					Toast.makeText(AddAddressActivity.this, "访问异常" + e,
							Toast.LENGTH_LONG).show();
				}

			}

			break;

		case R.id.remove:
			showSexDialog(R.string.delete_address_or_not);
			break;
		default:
			break;
		}
	}

	private void showSexDialog(int deleteAddressOrNot) {
		final AlertDialog dlg = new AlertDialog.Builder(this).create();
		dlg.show();
		Window window = dlg.getWindow();
		// *** 主要就是在这里实现这种效果的.
		// 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
		window.setContentView(R.layout.alert_dialog);
		TextView title = (TextView) window.findViewById(R.id.title);
		TextView alert_message = (TextView) window
				.findViewById(R.id.alert_message);
		Button btn_cancel = (Button) window.findViewById(R.id.btn_cancel);
		Button btn_ok = (Button) window.findViewById(R.id.btn_ok);
		alert_message.setText(getString(deleteAddressOrNot));
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dlg.cancel();
			}
		});
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.setMessage("正在删除...");
				dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				dlg.cancel();
				dialog.show();
				try {
					deleteAdress(address.getId());
				} catch (Exception e) {
					e.printStackTrace();
					dialog.dismiss();
					Toast.makeText(AddAddressActivity.this, "删除失败",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	/**
	 * 设置默认收货地址
	 * 
	 * @param id
	 * @throws Exception
	 */
	private void setDefaultAddress(int id) throws Exception {
		netState = SET_DEFAULT_ADDERSS;
		Boolean stateBoolean = appContext.setDefaultAddress(
				AddAddressActivity.this, id, strnetworkHelper);
		if (stateBoolean == false) {
			dialog.dismiss();
			netState = 0;
			Toast.makeText(AddAddressActivity.this, "请检查网络连接",
					Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 删除收货地址
	 * 
	 * @param id
	 * @throws Exception
	 */
	private void deleteAdress(int id) throws Exception {
		Boolean stateBoolean = appContext.deleteAdress(AddAddressActivity.this,
				id, strnetworkHelper);
		if (stateBoolean == false) {
			dialog.dismiss();
			Toast.makeText(AddAddressActivity.this, "请检查网络连接",
					Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 修改收货地址
	 * 
	 * @return
	 * @throws Exception
	 */
	private void updateAdress(Area province, Area city, Area area,
			String detailinfo, String contactname, String mobile,
			String postal_code, int id, String id_card) throws Exception {

		Boolean stateBoolean = appContext.updateAdress(AddAddressActivity.this,
				province, city, area, detailinfo, contactname, mobile,
				postal_code, id, id_card, strnetworkHelper);
		if (stateBoolean == false) {
			dialog.dismiss();
			Toast.makeText(AddAddressActivity.this, "请检查网络连接",
					Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 保存收货地址
	 * 
	 * @return
	 * @throws Exception
	 */
	private void saveAdress(Area province, Area city, Area area,
			String detailinfo, String contactname, String mobile,
			String postal_code, String id_card) throws Exception {
		Boolean stateBoolean = appContext.saveAdress(AddAddressActivity.this,
				province, city, area, detailinfo, contactname, mobile,
				postal_code, id_card, strnetworkHelper);
		if (stateBoolean == false) {
			dialog.dismiss();
			Toast.makeText(AddAddressActivity.this, "请检查网络连接",
					Toast.LENGTH_LONG).show();
		}

	}

	private void showPopMenu() {
		if (cascadingMenuPopWindow == null) {
			cascadingMenuPopWindow = new CascadingMenuPopWindow(
					getApplicationContext(), provinceList);
			cascadingMenuPopWindow
					.setAnimationStyle(R.style.popWindow_anim_style);
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
		Toast.makeText(AddAddressActivity.this, "访问异常" + error,
				Toast.LENGTH_LONG).show();
	}

	@Override
	public void onDataChanged(String data) {
		dialog.dismiss();
		try {
			JSONObject jsondata = new JSONObject(data);
			int code = jsondata.getInt("code");
			if (code == 0) {
				if (netState == SET_DEFAULT_ADDERSS) {
					Toast.makeText(AddAddressActivity.this, "默认地址设置成功" + data,
							Toast.LENGTH_SHORT).show();
					netState = 0;
				} else {
					Toast.makeText(AddAddressActivity.this, "操作成功" + data,
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.putExtra("result", 0);
					/*
					 * 调用setResult方法表示我将Intent对象返回给之前的那个Activity，
					 * 这样就可以在onActivityResult方法中得到Intent对象，
					 */
					setResult(1001, intent);
					finish();
					overridePendingTransition(R.anim.push_right_in,
							R.anim.push_right_out);
				}
			} else {
				try {
					byte[] bytes = jsondata.getString("data").getBytes(); 
					String newStr = new String(bytes , "UTF-8"); 
					Toast.makeText(AddAddressActivity.this, "操作失败:" + newStr,
							Toast.LENGTH_LONG).show();
				} catch (UnsupportedEncodingException e) {
					
					e.printStackTrace();
				}
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
			downtown.setText(DataStore.AREA_PROVINCE.getName() + " "
					+ DataStore.AREA_CITY.getName() + " "
					+ DataStore.AREA_DISTRICT.getName());
			// 记得提交数据后清空DataStore.AREA_ONE AREA_TWO ..
		}

	}

}
