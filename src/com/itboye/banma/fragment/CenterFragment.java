package com.itboye.banma.fragment;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.itboye.banma.R;
import com.itboye.banma.activities.LoginActivity;
import com.itboye.banma.activities.MailingAddressActivity;
import com.itboye.banma.activities.MorePersonal;
import com.itboye.banma.activities.WebActivity;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
import com.itboye.banma.util.CircleImg;
import com.itboye.banma.util.FileUtil;

public class CenterFragment extends Fragment implements OnClickListener{
	private View chatView;
	private CircleImg ivPersonheadFail;//鏈櫥褰曞ご澶村儚
	private CircleImg ivPersonhead;//鐧婚檰鐨勫ご鍍�
	TextView tvCheckList;//閫夋嫨鎸夐挳
	//ImageView ivBack;//杩斿洖鎸夐挳
	TextView tvPersonnamefail;//鏈櫥褰曟彁绀�
	private TextView tvYongJin;//鎴戠殑杩斾剑
	private ImageView ivShare;//鎵弿浜岀淮鐮�
	private LinearLayout rlMoney;//鎴戠殑浣ｉ噾
	private LinearLayout mailing_address;//鍦板潃绠＄悊
	private LinearLayout morePersonal;//鏇村涓汉鐩稿叧
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
				Toast.makeText(getActivity(), "璇峰厛鐧诲綍",
						Toast.LENGTH_LONG).show();
			}			
			break;
			
		case R.id.address:
			if(appContext.isLogin()){   //鍒ゆ柇鐧婚檰
				Intent intent = new Intent(getActivity(), MailingAddressActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			}else{
				Toast.makeText(getActivity(), "璇峰厛鐧诲綍",
						Toast.LENGTH_LONG).show();
			}
			break;
			
		case R.id.order_goods:
             if (appContext.isLogin()) {
            	 changeItemListener.onItemChanged(1);
             		}			
             else {
     			Toast.makeText(getActivity(), "璇峰厛鐧诲綍",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.iv_personheadfail://鐐瑰嚮鎴愬姛澶村儚 璺宠浆
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
				Toast.makeText(getActivity(), "璇峰厛鐧诲綍",
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
					//淇濆瓨bitmap鍥剧墖鍒版湰鍦�
					AppContext.setHasHead(true);
					ImageRequest imageRequest = new ImageRequest(  
							data.getStringExtra("headurl"),  
					        new Response.Listener<Bitmap>() {  
					            @Override  
					            public void onResponse(Bitmap response) {  
					                ivPersonheadFail.setImageBitmap(response);  
					                try {
										AppContext.setHeadurl(FileUtil.saveFile(getActivity().getApplicationContext(), 
												getActivity().getApplicationContext().getFilesDir().getCanonicalPath(),
												Constant.IMAGE_FILE_NAME, response));
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
					            }  
					        }, 0, 0, Config.RGB_565, new Response.ErrorListener() {  
					            @Override  
					            public void onErrorResponse(VolleyError error) {  
					                ivPersonheadFail.setImageResource(R.drawable.person_head);  
					            }  
					        });  
					AppContext.queues.add(imageRequest);  
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
				try {
					ivPersonheadFail.setImageBitmap(FileUtil.getLoacalBitmap(AppContext.getHeadurl()));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}else {

				//淇濆瓨bitmap鍥剧墖鍒版湰鍦�
				AppContext.setHasHead(true);
				ImageRequest imageRequest = new ImageRequest(  
						AppContext.getHeadurl(),  
				        new Response.Listener<Bitmap>() {  
				            @Override  
				            public void onResponse(Bitmap response) {  
				                ivPersonheadFail.setImageBitmap(response);  
				                try {
									AppContext.setHeadurl(FileUtil.saveFile(getActivity().getApplicationContext(), 
											getActivity().getApplicationContext().getFilesDir().getCanonicalPath(),
											Constant.IMAGE_FILE_NAME, response));
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				            }  
				        }, 0, 0, Config.RGB_565, new Response.ErrorListener() {  
				            @Override  
				            public void onErrorResponse(VolleyError error) {  
				                ivPersonheadFail.setImageResource(R.drawable.person_head);  
				            }  
				        });  
				AppContext.queues.add(imageRequest);  
			
			}
		   tvPersonnamefail.setText(AppContext.getNickname());
		
		}else {
			ivPersonheadFail.setImageResource(R.drawable.person_head);
			tvPersonnamefail.setText("鐧婚檰/娉ㄥ唽");
		}
		
	}
	//杩欓噷蹇呬笉鍙皯锛� /** Fragment绗竴娆￠檮灞炰簬Activity鏃惰皟鐢�,鍦╫nCreate涔嬪墠璋冪敤 */  
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
