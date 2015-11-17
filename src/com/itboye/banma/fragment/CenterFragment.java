package com.itboye.banma.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.itboye.banma.R;
import com.itboye.banma.activities.BabyActivity;
import com.itboye.banma.activities.LoginActivity;
import com.itboye.banma.activities.MailingAddressActivity;
import com.itboye.banma.activities.MorePersonal;
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
	private  ChangeItemListener changeItemListener;

	
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
             if (appContext.isLogin()) {
            	 changeItemListener.onItemChanged(1);
             		}			
             else {
     			Toast.makeText(getActivity(), "请先登录",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.iv_personheadfail://点击成功头像 跳转
		if (!appContext.isLogin()) {
				startActivityForResult(new Intent(getActivity(),LoginActivity.class),100);
				getActivity().overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			}
			break;
			
		case R.id.rl_more:
				startActivity(new Intent(getActivity(),MorePersonal.class));
				getActivity().overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
				break;
			
		case R.id.iv_share:
			if(appContext.isLogin()){
			Intent intent2=new Intent(getActivity(),WebActivity.class);
			intent2.putExtra("Url", "MingPian");
			startActivity(intent2);
			getActivity().overridePendingTransition(R.anim.in_from_right,
					R.anim.out_to_left);
			}else{
				Toast.makeText(getActivity(), "请先登录",
						Toast.LENGTH_SHORT).show();
			}
			break;
			
		case R.id.tv_yongjin:
			
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
							AppContext.setHasHead(true);
					try {
						ImageListener listener = ImageLoader.getImageListener(ivPersonheadFail,R.drawable.person_head, R.drawable.person_head);  
						  imageLoader.get(data.getStringExtra("headurl"), listener);
						     tvPersonnamefail.setText(data.getStringExtra("nickname"));
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
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (appContext.isLogin()==true) {
			if (AppContext.hasHead==true) {
				ImageLoader imageLoader = new ImageLoader(AppContext.getHttpQueues(),
						new BitmapCache());
				try {
					ImageListener listener = ImageLoader.getImageListener(ivPersonheadFail,R.drawable.person_head, R.drawable.person_head);  
					  imageLoader.get(AppContext.getHeadurl(), listener);
					}catch (Exception e) {
					// TODO: handle exception
							e.printStackTrace();
				}	
			}
			   tvPersonnamefail.setText(AppContext.getNickname());
		
		}else {
			ivPersonheadFail.setImageResource(R.drawable.person_head);
			tvPersonnamefail.setText("登陆/注册");
		}
		
	}
	//这里必不可少， /** Fragment第一次附属于Activity时调用,在onCreate之前调用 */  
	 @Override  
	    public void onAttach(Activity activity)   
	    {  
	        super.onAttach(activity);  
	        changeItemListener = (ChangeItemListener) activity;   
	    }  
	
	 public interface ChangeItemListener{  
	      public void onItemChanged(int postion);  
	  }  
}
