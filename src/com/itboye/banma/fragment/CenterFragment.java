package com.itboye.banma.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.itboye.banma.R;
import com.itboye.banma.activities.BabyActivity;
import com.itboye.banma.activities.LoginActivity;
import com.itboye.banma.activities.MailingAddressActivity;
import com.itboye.banma.activities.MorePersonal;
import com.itboye.banma.activities.OrderActivity;
import com.itboye.banma.activities.WebActivity;
import com.itboye.banma.api.ApiClient;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
import com.itboye.banma.util.CircleImg;
import com.itboye.banma.utils.BitmapCache;

public class CenterFragment extends Fragment implements OnClickListener{
	private View chatView;
	private CircleImg ivPersonheadFail;//未登录头头像
	private CircleImg ivPersonhead;//登陆的头像
	TextView tvCheckList;//选择按钮
	//ImageView ivBack;//返回按钮
	TextView tvPersonnamefail;//未登录提示
	private TextView tvYongJin;//我的返佣
	private ImageView ivShare;//扫描二维码
	private LinearLayout rlMoney;//我的佣金
	private LinearLayout mailing_address;//地址管理
	private LinearLayout morePersonal;//更多个人相关
	private LinearLayout order_goods;
	private AppContext appContext;
	private SharedPreferences sp;
	private StrVolleyInterface networkHelper;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		chatView = inflater.inflate(R.layout.activity_center, container, false);
		appContext = (AppContext) getActivity().getApplication();
		networkHelper = new StrVolleyInterface(getActivity());
	//	networkHelper.setStrUIDataListener(this);
		initId();
		return chatView;
	}
	
	private void initId() {
		// TODO Auto-generated method stub
	//	ivPersonhead=(ImageView)findViewById(R.id.iv_personhead);
       // SharedPreferences sp = this.getSharedPreferences(Constant.MY_PREFERENCES, 0);  
      // String userId= sp.getString("MY_USERID", "");
		String userId=appContext.getLoginUid()+"";
//		ivBack=(ImageView)chatView.findViewById(R.id.iv_back);
		ivPersonheadFail=(CircleImg)chatView.findViewById(R.id.iv_personheadfail);
		ivPersonheadFail.setDefaultImageResId(R.drawable.person_head);
		tvPersonnamefail=(TextView)chatView.findViewById(R.id.tv_personnamefail);
//		sp = getActivity().getSharedPreferences(Constant.MY_PREFERENCES, getActivity().MODE_PRIVATE);
		
//		if (appContext.isLogin()) {
//			String number=sp.getString(Constant.MY_ACCOUNT, "");
//			String psw=sp.getString(Constant.MY_PASSWORD, "");
//			ApiClient.Login(getActivity(), number, psw, networkHelper);//请求用户数据
//			ImageLoader imageLoader = new ImageLoader(AppContext.getHttpQueues(),
//					new BitmapCache());
//			try {
//			      ivPersonheadFail.setErrorImageResId(R.drawable.person_head); // 加载失败显示的图片
//			       ivPersonheadFail.setDefaultImageResId(R.drawable.person_head);
//				  ivPersonheadFail.setImageUrl(sp.getString(Constant.MY_HEAD_URL, ""), imageLoader);
//				}catch (Exception e) {
//				// TODO: handle exception
//					e.printStackTrace();
//			    	System.out.println("头像加载失败");
//			}		
//		}
		tvYongJin=(TextView)chatView.findViewById(R.id.tv_yongjin);
		ivShare=(ImageView)chatView.findViewById(R.id.iv_share);
	   rlMoney=(LinearLayout)chatView.findViewById(R.id.rl_money);
		//ivBack=(ImageView)chatView.findViewById(R.id.iv_back);
		tvCheckList=(TextView)chatView.findViewById(R.id.tv_check_list);
		order_goods = (LinearLayout) chatView.findViewById(R.id.order_goods);
		mailing_address = (LinearLayout) chatView.findViewById(R.id.address);
		morePersonal=(LinearLayout)chatView.findViewById(R.id.rl_more);
		rlMoney.setOnClickListener(this);
		order_goods.setOnClickListener(this);
		mailing_address.setOnClickListener(this);
		ivPersonheadFail.setOnClickListener(this);
		//ivBack.setOnClickListener(this);
		morePersonal.setOnClickListener(this);
		ivShare.setOnClickListener(this);
		tvYongJin.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_money:
			if (appContext.isLogin()) {
				Intent intent = new Intent(getActivity(), OrderActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			}else{
				Toast.makeText(getActivity(), "请先登录",
						Toast.LENGTH_LONG).show();
			}
			break;
			
		case R.id.address:
			if(appContext.isLogin()){   //判断登陆
				Intent intent = new Intent(getActivity(), MailingAddressActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			}else{
				Toast.makeText(getActivity(), "请先登录",
						Toast.LENGTH_LONG).show();
			}
			break;
			
		case R.id.order_goods:
			Intent intent = new Intent(getActivity(), BabyActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.in_from_right,
					R.anim.out_to_left);
			
			break;
		case R.id.iv_personheadfail://点击成功头像 跳转
		if (!appContext.isLogin()) {
				startActivityForResult(new Intent(getActivity(),LoginActivity.class),100);
				getActivity().overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			}
			break;
			
		/*case R.id.iv_back:
			getActivity().finish();
			getActivity().overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			break;*/
		case R.id.rl_more:
				startActivity(new Intent(getActivity(),MorePersonal.class));
				getActivity().overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
				break;
			
//	case R.id.rl_money:
//		startActivity(new Intent(getActivity(),ShoppingCartActivity.class));
//			overridePendingTransition(R.anim.in_from_right,
//				R.anim.out_to_left);
//		break;
			
		case R.id.iv_share:
			if(appContext.isLogin()){
			Intent intent2=new Intent(getActivity(),WebActivity.class);
			intent2.putExtra("Url", "MingPian");
			startActivity(intent2);
			getActivity().overridePendingTransition(R.anim.in_from_right,
					R.anim.out_to_left);
			}else{
				Toast.makeText(getActivity(), "请先登录",
						Toast.LENGTH_LONG).show();
			}
			break;
			
		case R.id.tv_yongjin:
			if (appContext.isLogin()) {
				Intent intent3=new Intent(getActivity(),WebActivity.class);
				intent3.putExtra("Url", "FanYong");
				startActivity(intent3);
				getActivity().overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			}else{
				Toast.makeText(getActivity(), "请先登录",
						Toast.LENGTH_LONG).show();
			}
			break;
			
		default:
			break;
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==100) {
			if (data!=null) {
				if (appContext.isLogin()) {
					ImageLoader imageLoader = new ImageLoader(AppContext.getHttpQueues(),
							new BitmapCache());
					try {
					      ivPersonheadFail.setErrorImageResId(R.drawable.person_head); // 加载失败显示的图片
					       ivPersonheadFail.setDefaultImageResId(R.drawable.person_head);
					      ivPersonheadFail.setImageUrl(AppContext.getHeadurl(), imageLoader);
					     tvPersonnamefail.setText(AppContext.getNickname());
						}catch (Exception e) {
						// TODO: handle exception
							e.printStackTrace();
					}		
				}else {
					ivPersonheadFail.setImageResource(R.drawable.person_head);
					tvPersonnamefail.setText("登陆/注册");
				}
    		}
    	}
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (appContext.isLogin()) {
			ImageLoader imageLoader = new ImageLoader(AppContext.getHttpQueues(),
					new BitmapCache());
			try {
			      ivPersonheadFail.setErrorImageResId(R.drawable.person_head); // 加载失败显示的图片
			       ivPersonheadFail.setDefaultImageResId(R.drawable.person_head);
			      ivPersonheadFail.setImageUrl(AppContext.getHeadurl(), imageLoader);
			      tvPersonnamefail.setText(AppContext.getNickname());
				}catch (Exception e) {
				// TODO: handle exception
					ivPersonheadFail.setImageResource(R.drawable.person_head);
					e.printStackTrace();
			}		
		}else {
			ivPersonheadFail.setImageResource(R.drawable.person_head);
			tvPersonnamefail.setText("登陆/注册");
		}
	}
//	@Override
//	public void onErrorHappened(VolleyError error) {
//		// TODO Auto-generated method stub
//		
//	}
//	@Override
//	public void onDataChanged(String data) {
//		// TODO Auto-generated method stub
//		JSONObject jsonObject=null;
//		String content=null;
//		int code = -1;
//		JSONObject jsonObject2 = null;
//
//		try {
//			jsonObject = new JSONObject(data);
//			code = jsonObject.getInt("code");
//			content=jsonObject.getString("data");
//			jsonObject2=new JSONObject(content);
//		    nickname=jsonObject2.getString("nickname");
//		} catch (JSONException e1) {
//			e1.printStackTrace();
//		}
//		if (code == 0) {
//				if (appContext.isLogin()) {
//					tvPersonnamefail.setText(nickname);
//				}
//	     	}
//		}  
}
