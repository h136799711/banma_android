package com.itboye.banma.activities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.itboye.banma.R;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
import com.itboye.banma.util.ChoosePictureDialog;
import com.itboye.banma.utils.MultiPartStringRequest;
import com.itboye.banma.welcome.WelcomeActivity;

public class MorePersonal extends Activity implements OnClickListener{
	private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果
	private static final int FROM_NEWPHONE=4;//从newphone取得的结果
	private static final int FROM_NICK=0;//从nick中获得的
	static RequestQueue mSingleQueue;
	
	private AppContext appContext;
	private TextView tvUserName;//用户昵称
	private TextView tvPhoneNumber;//用户手机号
	//private RelativeLayout rlPersonalMian;//该界面的总布局控制
	private ImageView ivBack;//返回按钮
	private TextView tvWeiXin;//微信
	private RelativeLayout rlUserName;//用户名布局
	private RelativeLayout rlWelcome;//欢迎页
	private RelativeLayout rlTiaoKuan;//条款界面
	private RelativeLayout rlAboutBanMa;//关于斑马
	private RelativeLayout rlCallServer;//呼叫客服
	private RelativeLayout rlPhoneNumber;//用户手机号布局
	private TextView tvTelephone;//拨打电话
	private Button btExit;//退出登陆按钮
	
	/* 头像名称 */
	private static final String PHOTO_FILE_NAME = "image.jpg";
	private File tempFile;
	//获取头像的dialog
	Dialog choose;
	
//选择上传头像
	RelativeLayout rlHead;
	private ImageView ivHead;//头像
	private Bitmap bitmap;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_personal);
		//mSingleQueue = Volley.newRequestQueue(this, new MultiPartStack());
		mSingleQueue=AppContext.queues;
		appContext=(AppContext) getApplication();
		initID();
		initData();
		rlHead.setOnClickListener(this);
		ivBack.setOnClickListener(this);
		rlUserName.setOnClickListener(this);
		rlWelcome.setOnClickListener(this);
		rlTiaoKuan.setOnClickListener(this);
		rlAboutBanMa.setOnClickListener(this);
		rlCallServer.setOnClickListener(this);
		tvPhoneNumber.setOnClickListener(this);
		btExit.setOnClickListener(this);
		rlPhoneNumber.setOnClickListener(this);
	}
	
	
	private void initID(){
		tvTelephone=(TextView)findViewById(R.id.tv_telephone);
		rlCallServer=(RelativeLayout)findViewById(R.id.rl_call_server);
		rlAboutBanMa=(RelativeLayout)findViewById(R.id.rl_aboutbanma);
		rlTiaoKuan=(RelativeLayout)findViewById(R.id.rl_tiaokuan);
		rlWelcome=(RelativeLayout)findViewById(R.id.rl_welcome);
		rlUserName=(RelativeLayout)findViewById(R.id.rl_username);
		ivBack=(ImageView)findViewById(R.id.iv_back);
		tvWeiXin=(TextView)findViewById(R.id.tv_bangding);
	//	rlPersonalMian=(RelativeLayout)findViewById(R.id.rl_person_main);
		rlHead=(RelativeLayout)findViewById(R.id.rl_head);
		ivHead=(ImageView)findViewById(R.id.iv_head);
		tvUserName=(TextView)findViewById(R.id.tv_username);
		tvPhoneNumber=(TextView)findViewById(R.id.tv_phone_number);
		btExit=(Button)findViewById(R.id.btn_exit);
		rlPhoneNumber=(RelativeLayout)findViewById(R.id.rl_phone_number_);

//		else {
//			rlHead.setFocusable(false);
//			rlHead.setClickable(false);
//			tvUserName.setClickable(false);
//			tvPhoneNumber.setClickable(false);
//			rlPersonalMian.setClickable(false);
//			tvWeiXin.setClickable(false);
//		}
	}
	
	@SuppressLint("ResourceAsColor")
	private void initData(){
		sp=getSharedPreferences(Constant.MY_PREFERENCES, MODE_PRIVATE);
		if (appContext.isLogin()) {
		/*String number=sp.getString(Constant.MY_ACCOUNT, "");
		if (number!="") {
			//隐藏中间几位
				String newString=number.substring(0,3)+"****"+number.substring(7, 11);
		     	tvPhoneNumber.setText(newString);
		    }*/
			String number=sp.getString(Constant.MY_BANGDING, "");
			if (sp.getString(Constant.MY_BANGDING, "")!="") {
				String newString=number.substring(0,3)+"****"+number.substring(7, 11);
				tvPhoneNumber.setText(newString);
			}else  {
				String oldNumber=sp.getString(Constant.MY_ACCOUNT, "");
				String newString=oldNumber.substring(0,3)+"****"+oldNumber.substring(7, 11);
				tvPhoneNumber.setText(newString);
			}
			if (sp.getString(Constant.WEIXIN, "")!="") {
				//显示已绑定状态
			}else {
				tvWeiXin.setText("未绑定");
				tvWeiXin.setTextColor(R.color.lightblue);
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_head:
			if (appContext.isLogin()) {
				choose=ChoosePictureDialog.getDialog(MorePersonal.this);
				choose.show();
				choose.getWindow();
			}
			break;
		case R.id.iv_back:
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			break;
		case R.id.rl_username:
			if (appContext.isLogin()) {
				startActivityForResult(new Intent(MorePersonal.this,NickName.class),FROM_NICK);
			}
			break;
		case R.id.rl_welcome:
			startActivity(new Intent(MorePersonal.this,WelcomeActivity.class));
			break;
		case R.id.rl_tiaokuan:
			String url="http://banma.itboye.com/Public/html/copyright.html";
			Intent intent=new Intent(MorePersonal.this,WebActivity.class);
			intent.putExtra("Url", url);
			startActivity(intent);
			break;
		case R.id.rl_aboutbanma:
			String url1="http://banma.itboye.com/Public/html/about.html";
			Intent intent1=new Intent(MorePersonal.this,WebActivity.class);
			intent1.putExtra("Url", url1);
			startActivity(intent1);
			break;
		case R.id.rl_call_server:
			 Intent phoneIntent = new Intent("android.intent.action.CALL", 
		             Uri.parse("tel:" + tvTelephone.getText().toString())); 
			startActivity(phoneIntent);
			break;
		case  R.id.rl_phone_number_:
			Intent newIntent=new Intent(MorePersonal.this,NewPhoneActivity.class);
			newIntent.putExtra("oldPboneNumber", tvPhoneNumber.getText().toString());
			startActivityForResult(newIntent, FROM_NEWPHONE);//请求码
		break;
		case R.id.btn_exit:
			sp.edit().clear().commit();//清空所有sp中的数据
			appContext.setLogin(false);
			finish();
			break;
			
		default:
			break;
		}
	}
	
	//cansel监听
	public void cansel(View v){
		choose.dismiss();
	}
	/*
	 * 从相册获取
	 */
	public void gallery(View view) {
		// 激活系统图库，选择一张图片
		System.out.println("从图库中获取");
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
	}

	/*
	 * 从相机获取
	 */
	public void camera(View view) {
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		// 判断存储卡是否可以用，可用进行存储
		if (hasSdcard()) {
			intent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(Environment
							.getExternalStorageDirectory(), PHOTO_FILE_NAME)));
		}
		startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
	}

	@SuppressLint("ShowToast")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PHOTO_REQUEST_GALLERY) {
			if (data != null) {
				Uri uri = data.getData();
				crop(uri);
			}

		} else if (requestCode == PHOTO_REQUEST_CAMERA) {
			if (hasSdcard()) {
				tempFile = new File(Environment.getExternalStorageDirectory(),
						PHOTO_FILE_NAME);
				crop(Uri.fromFile(tempFile));
			} else {
				Toast.makeText(MorePersonal.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG).show();
			}

		} 
		else if(requestCode==FROM_NICK) {
			if (data!=null) {
				Bundle tempdata=data.getExtras();
				String nickName=tempdata.getString("nickName");
				tvUserName.setText(nickName);
			}
		}
		else if(requestCode==FROM_NEWPHONE) {
			if (data!=null) {
				Bundle tempdata=data.getExtras();
				String phone=tempdata.getString("newPhone");
				String newString=phone.substring(0,3)+"****"+phone.substring(7, 11);
		     	tvPhoneNumber.setText(newString);
			}
		}else if (requestCode == PHOTO_REQUEST_CUT) {
			try {
				if (data!=null) {
					bitmap=	 data.getParcelableExtra("data");
					//讲bitmap保存到指定文件中
					saveHeadImage(bitmap);
				 //  tempFile.delete();
				 //上传文件中的文件
				   upLoadImage();
				}
			} catch (Exception e) {
					e.printStackTrace();
				}
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void saveHeadImage(Bitmap bitmap) {
		// TODO Auto-generated method stub
		File file=new File(Environment.getExternalStorageDirectory(),"image.png");
		FileOutputStream out=null;
		try {
			out=new FileOutputStream(file);
			bitmap.compress(CompressFormat.PNG, 100, out);
			out.close();
			String headPath=file.getAbsolutePath();
			AppContext.setPathHeadImage(headPath);
			System.out.println(file.getAbsolutePath());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void upLoadImage() {
		// TODO Auto-generated method stub
		Map<String, File> files = new HashMap<String, File>();
		files.put("avatar", new File("/storage/emulated/0/image.png"));
		ivHead.setImageBitmap(getLoacalBitmap("/storage/emulated/0/image.png"));
		SharedPreferences sp = this.getSharedPreferences(Constant.MY_PREFERENCES, 0);  
		String  id=sp.getString(Constant.MY_USERID, -1+"");
		Map<String, String> params = new HashMap<String, String>();
		System.out.println(id);
		params.put("uid",id);
		params.put("type","avatar");

		String uri =Constant.URL+"/File/upload?access_token="+AppContext.getAccess_token()+"";
	//	addPutUploadFileRequest(
		//		uri,files, params, mResonseListenerString, mErrorListener, null);
	}
	
	public static Bitmap getLoacalBitmap(String url) {
        try {
             FileInputStream fis = new FileInputStream(url);
             return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片        

          } catch (FileNotFoundException e) {
             e.printStackTrace();
             return null;
        }
	}

	/**
	 * 剪切图片
	 */
	private void crop(Uri uri) {
		// 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// 裁剪框的比例，1：1
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputX", 250);
		intent.putExtra("outputY", 250);
		// 图片格式
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);// 取消人脸识别
		intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	private boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}	
	
	
	Listener<JSONObject> mResonseListener = new Listener<JSONObject>() {

		@Override
		public void onResponse(JSONObject response) {
			String data = null;
			try {
				data=response.getString("data");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("返回数据", " on response json" + data.toString());
		}
	};
	Listener<String> mResonseListenerString = new Listener<String>() {
		JSONObject data=null;
		int code=-1;
		@SuppressLint("CommitPrefEdits")
		@Override
		public void onResponse(String response) {
			try {
				data=new JSONObject(response);
				code=data.getInt("code");
				if (code==0) {
					SharedPreferences sp=getSharedPreferences(Constant.MY_PREFERENCES, MODE_PRIVATE);
					Editor editor=sp.edit();
					editor.putBoolean(Constant.MY_HEAD, true);//如果设置成功，改变头像状态
					AppContext.setHasHead(true);//设置静态变量 头像已经添加
					editor.commit();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("String", " on response String" + data.toString());
		}
	};

	ErrorListener mErrorListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			if (error != null) {
				if (error.networkResponse != null)
					Log.i("发生错误", " error " + new String(error.networkResponse.data));
				//如果发生错误，则将头像设为默认的
				ivHead.setImageResource(R.id.iv_head);
			}
		}
	};

	/*public static void addPutUploadFileRequest(final String url,
			final Map<String, File> files, final Map<String, String> params,
			final Listener<String> responseListener, final ErrorListener errorListener,
			final Object tag) {
		if (null == url || null == responseListener) {
			return;
		}

		MultiPartStringRequest multiPartRequest = new MultiPartStringRequest(
				Request.Method.POST, url, responseListener, errorListener) {

			@Override
			public Map<String, File> getFileUploads() {
				return files;
			}

			@Override
			public Map<String, String> getStringUploads() {
				return params;
			}
			
		};

		mSingleQueue.add(multiPartRequest);
	}*/
}