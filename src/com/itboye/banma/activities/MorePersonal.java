package com.itboye.banma.activities;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.BreakIterator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.itboye.banma.R;
import com.itboye.banma.api.ApiClient;
import com.itboye.banma.api.StrUIDataListener;
import com.itboye.banma.api.StrVolleyInterface;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
import com.itboye.banma.util.ChoosePictureDialog;
import com.itboye.banma.util.CircleImg;
import com.itboye.banma.util.NetUtil;
import com.itboye.banma.utils.BitmapCache;
import com.itboye.banma.welcome.WelcomeActivity;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.weixin.controller.UMWXHandler;

public class MorePersonal extends Activity implements OnClickListener,StrUIDataListener{
	private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果
	private static final int FROM_NEWPHONE=4;//从newphone取得的结果
	private static final int FROM_NICK=0;//从nick中获得的
	static RequestQueue mSingleQueue;
	private AppContext appContext;
	
	private StrVolleyInterface networkHelper;
	private TextView tvUserName;//用户昵称
	private TextView tvPhoneNumber;//用户手机号
	private TextView tvRenZhen;//实名认证
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
	private RelativeLayout rlPersonMain;//总布局
	private RelativeLayout rlWeiXin;//微信
	private RelativeLayout rlShengFenZheng;//绑定身份证
	private int FLAG=0;//区分返回请求数据 =1,表示微信请求的
	
	
	 public static final String APP_ID = "wx0d259d7e9716d3dd";//微信
	 public static final String AppSecret = "94124fb74284c8dae6f188c7e269a5a0";//微信
	
	/* 头像名称 */
	 private static ProgressDialog pd;// 等待进度圈
     private String imgUrl = "";
	 private static final String IMAGE_FILE_NAME = "head.jpg";// 头像文件名称
	 private String urlpath;			// 图片本地路径
	 private String resultStr = "";	// 服务端返回结果集
	private static final String PHOTO_FILE_NAME = "image.jpg";
	private File tempFile;
	private TextView title;
	//获取头像的dialog
	Dialog choose;
	
//选择上传头像
	RelativeLayout rlHead;
	private CircleImg ivHead;//头像
	private Bitmap bitmap;
	private SharedPreferences sp;
	private IWXAPI api;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_personal);
		//mSingleQueue = Volley.newRequestQueue(this, new MultiPartStack());

		// 添加微信平台
		
		api = WXAPIFactory.createWXAPI(this,Constant.APP_ID, true);  
		api.registerApp(Constant.APP_ID);
		
		
		mSingleQueue=AppContext.queues;
		appContext=(AppContext) getApplication();
		networkHelper = new StrVolleyInterface(this);
		networkHelper.setStrUIDataListener(this);
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
		rlPersonMain.setOnClickListener(this);
		tvRenZhen.setOnClickListener(this);;
		rlShengFenZheng.setOnClickListener(this);
		rlWeiXin.setOnClickListener(this);
	}
	
	
	private void initID(){
		title=(TextView)findViewById(R.id.title);
		title.setText("更多设置");
		imgUrl = Constant.URL+"/File/upload?access_token="+AppContext.getAccess_token()+"";
		rlWeiXin=(RelativeLayout)findViewById(R.id.rl_weixin);
		rlShengFenZheng=(RelativeLayout)findViewById(R.id.rl_shengfenzheng);
		tvRenZhen=(TextView)findViewById(R.id.tv_renzhen);
		rlPersonMain=(RelativeLayout)findViewById(R.id.rl_person_main);
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
		ivHead=(CircleImg)findViewById(R.id.iv_head);
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
		if (appContext.isLogin()&&AppContext.isWeixin==false) {
			String number=sp.getString(Constant.MY_ACCOUNT, "");
			String psw=sp.getString(Constant.MY_PASSWORD, "");
			ApiClient.Login(MorePersonal.this, number, psw, networkHelper);//请求用户数据
																													//请求用户头像数据
			
		}else {
			if (appContext.isLogin()) {
				tvUserName.setText(sp.getString(Constant.MY_USER_NICK, ""));
					ImageLoader imageLoader = new ImageLoader(AppContext.getHttpQueues(),
							new BitmapCache());
					try {
					       ivHead.setErrorImageResId(R.drawable.person_head); // 加载失败显示的图片
							ivHead.setImageUrl(sp.getString(Constant.MY_HEAD_URL, ""), imageLoader);
						//	System.out.println(AppContext.getPathHeadImage());;
						}catch (Exception e) {
						// TODO: handle exception
							e.printStackTrace();
					    	System.out.println("头像加载失败");
					}		
				try {
					String newString=sp.getString(Constant.MY_ACCOUNT,"").substring(0,3)+"****"+sp.getString(Constant.MY_ACCOUNT,"").substring(7, 11);
					tvPhoneNumber.setText(newString);
			
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
					
				if (sp.getString(Constant.WEIXIN_LOGIN, "").equals("0")) {
					tvWeiXin.setText("未绑定");
				}else {
					tvWeiXin.setText("已绑定");
					tvWeiXin.setClickable(false);
				}
				if (sp.getString(Constant.MY_SHIMING, "").equals("0")) {
					tvRenZhen.setText("未认证");
				}else {
					tvRenZhen.setText("已认证");
				}
			}
	}
	}
	
	
	//友盟统计

		public void onPause() {
			super.onPause();
			MobclickAgent.onPause(this);
		}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		 final SharedPreferences sp = MorePersonal.this.getSharedPreferences(Constant.MY_PREFERENCES, 0);  
		switch (v.getId()) {
	
		case R.id.rl_weixin:
			if (appContext.isLogin()) {
				final SendAuth.Req req = new SendAuth.Req();  
				req.scope = "snsapi_userinfo";  
				req.state = "wechat_sdk_demo_test";  
				api.sendReq(req);  
				}
			break;

			
		
		case R.id.rl_head:
			if (appContext.isLogin())
			{
				choose=ChoosePictureDialog.getDialog(MorePersonal.this);
				choose.show();
				choose.getWindow();
			}
			break;
		case R.id.iv_head:
			if (appContext.isLogin())
			{
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
				Intent intent=new Intent(MorePersonal.this,NickName.class);
				intent.putExtra("nickname", tvUserName.getText());
				startActivityForResult(intent,FROM_NICK);
				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			}
			break;
		case R.id.rl_welcome:
			startActivity(new Intent(MorePersonal.this,WelcomeActivity.class));
			overridePendingTransition(R.anim.in_from_right,
					R.anim.out_to_left);
			break;
		case R.id.rl_tiaokuan:
			String url="http://banma.itboye.com/Public/html/copyright.html";
			Intent intent=new Intent(MorePersonal.this,WebActivity.class);
			intent.putExtra("Url", url);
			startActivity(intent);
			overridePendingTransition(R.anim.in_from_right,
					R.anim.out_to_left);
			break;
		case R.id.rl_aboutbanma:
			String url1="http://banma.itboye.com/Public/html/about.html";
			Intent intent1=new Intent(MorePersonal.this,WebActivity.class);
			intent1.putExtra("Url", url1);
			startActivity(intent1);
			overridePendingTransition(R.anim.in_from_right,
					R.anim.out_to_left);
			break;
		case R.id.rl_call_server:
			 Intent phoneIntent = new Intent("android.intent.action.CALL", 
		             Uri.parse("tel:" + tvTelephone.getText().toString())); 
			startActivity(phoneIntent);
			overridePendingTransition(R.anim.in_from_right,
					R.anim.out_to_left);
			break;
		case  R.id.rl_phone_number_:
			if (appContext.isLogin()) {
				Intent newIntent=new Intent(MorePersonal.this,NewPhoneActivity.class);
				newIntent.putExtra("oldPboneNumber", tvPhoneNumber.getText().toString());
				startActivityForResult(newIntent, FROM_NEWPHONE);//请求码
				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			}
		break;
		
		case R.id.btn_exit:
			sp.edit().clear().commit();//清空所有sp中的数据
			appContext.setLogin(false);
			AppContext.setWeixin(false);
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			break;
			
		case R.id.rl_shengfenzheng:
			if (appContext.isLogin()) {
				startActivity(new Intent(MorePersonal.this,ShengFenActivity.class));
				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			}		
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
				AppContext.setNickname(tempdata.getString("nickName"));
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
					// 新线程后台上传服务端
					pd = ProgressDialog.show(this, null, "正在上传图片，请稍候...");
					new Thread(uploadImageRunnable).start();
				}
			} catch (Exception e) {
					e.printStackTrace();
				}
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 使用HttpUrlConnection模拟post表单进行文件
	 * 上传平时很少使用，比较麻烦
	 * 原理是： 分析文件上传的数据格式，然后根据格式构造相应的发送给服务器的字符串。
	 */
	Runnable uploadImageRunnable = new Runnable() {
		@Override
		public void run() {
			
			if(TextUtils.isEmpty(imgUrl)){
				//Toast.makeText(mContext, "还没有设置上传服务器的路径！", Toast.LENGTH_SHORT).show();
				return;
			}
			
			Map<String, String> textParams1 = new HashMap<String, String>();
			Map<String, String> textParams = new HashMap<String, String>();
			Map<String, File> fileparams = new HashMap<String, File>();
			
			try {
				// 创建一个URL对象
				URL url = new URL(imgUrl);
				textParams1=new HashMap<String, String>();
				textParams = new HashMap<String, String>();
				fileparams = new HashMap<String, File>();
				// 要上传的图片文件
				System.out.println(appContext.getLoginUid());
				textParams.put("uid", appContext.getLoginUid()+"");
				textParams1.put("type", "avatar");
				File file = new File(urlpath);
				fileparams.put("image", file);
				// 利用HttpURLConnection对象从网络中获取网页数据
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				// 设置连接超时（记得设置连接超时,如果网络不好,Android系统在超过默认时间会收回资源中断操作）
				conn.setConnectTimeout(5000);
				// 设置允许输出（发送POST请求必须设置允许输出）
				conn.setDoOutput(true);
				// 设置使用POST的方式发送
				conn.setRequestMethod("POST");
				// 设置不使用缓存（容易出现问题）
				conn.setUseCaches(false);
				conn.setRequestProperty("Charset", "UTF-8");//设置编码   
				// 在开始用HttpURLConnection对象的setRequestProperty()设置,就是生成HTML文件头
				conn.setRequestProperty("ser-Agent", "Fiddler");
				// 设置contentType
				conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + NetUtil.BOUNDARY);
				OutputStream os = conn.getOutputStream();
				DataOutputStream ds = new DataOutputStream(os);
				NetUtil.writeStringParams(textParams1, ds);
				NetUtil.writeStringParams(textParams, ds);
				NetUtil.writeFileParams(fileparams, ds);
				NetUtil.paramsEnd(ds);
				// 对文件流操作完,要记得及时关闭
				os.close();
				// 服务器返回的响应吗
				int code = conn.getResponseCode(); // 从Internet获取网页,发送请求,将网页以流的形式读回来
				// 对响应码进行判断
				if (code == 200) {// 返回的响应码200,是成功
					// 得到网络返回的输入流
					InputStream is = conn.getInputStream();
					resultStr = NetUtil.readString(is);
				} else {
					//Toast.makeText(mContext, "请求URL失败！", Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			handler.sendEmptyMessage(0);// 执行耗时的方法之后发送消给handler
		}
	};
	
	Handler handler = new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				pd.dismiss();
				choose.dismiss();
				try {
					// 返回数据示例，根据需求和后台数据灵活处理
					// {"status":"1","statusMessage":"上传成功","imageUrl":"http://120.24.219.49/726287_temphead.jpg"}
					JSONObject jsonObject = new JSONObject(resultStr);
			    	 JSONObject data=null;;
					int code=jsonObject.getInt("code");
					if (code==0) {
						System.out.println(jsonObject.toString());
					    data=new JSONObject(jsonObject.getString("data"));
						// 服务端以字符串“1”作为操作成功标记
					    //暂时这里先使用缓存来存放图片地址，因为登陆时服务器并没有返回图片地址			    	
							ivHead.setImageBitmap(bitmap);
							AppContext.setHeadurl(data.getString("imgurl"));
							System.out.println(jsonObject.toString());
							Toast.makeText(MorePersonal.this, "头像上传成功", Toast.LENGTH_SHORT).show();			
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				break;
				
			default:
				break;
			}
			return false;
		}
	});
	
	private void saveHeadImage(Bitmap bitmap) {
		// TODO Auto-generated method stub
		File file=new File(Environment.getExternalStorageDirectory(),"image.png");
		FileOutputStream out=null;
		try {
			out=new FileOutputStream(file);
			bitmap.compress(CompressFormat.PNG, 100, out);
			out.close();
			urlpath=file.getAbsolutePath();
		//	AppContext.setPathHeadImage(urlpath);
			System.out.println(file.getAbsolutePath());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
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

	@Override
	public void onErrorHappened(VolleyError error) {
		// TODO Auto-generated method stub
		Log.v("注册接口",error.toString());
	    Log.e("LOGIN-ERROR", error.getMessage(), error);
	    byte[] htmlBodyBytes = error.networkResponse.data;
	    Log.e("LOGIN-ERROR", new String(htmlBodyBytes), error);
	}


	@Override
	public void onDataChanged(String data) {
		JSONObject jsonObject=null;
		int code = -1;
		String content = null;
		JSONObject jsonObject2=null;
		if (FLAG==1) {
			FLAG=0;
			try {
				jsonObject=new JSONObject(data);
				code=jsonObject.getInt("code");
				content = jsonObject.getString("data");
				if (code==0) {
					tvWeiXin.setText("已绑定");
					tvWeiXin.setClickable(false);
					Toast.makeText(MorePersonal.this, "绑定微信成功", Toast.LENGTH_SHORT).show();
					System.out.println(content.toString());
				}
				else {
					Toast.makeText(MorePersonal.this, content.toString(), Toast.LENGTH_SHORT).show();
					System.out.println(content.toString());
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Toast.makeText(MorePersonal.this, "绑定失败", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
		}else {
		// TODO Auto-generated method stub
	
		String nickname = null;
		String phoneNumber=null;
		String weiXin=null;
		String renZheng=null;
		String imageUrl=null;
		try {
			FLAG=0;
			jsonObject = new JSONObject(data);
			code = jsonObject.getInt("code");
			content = jsonObject.getString("data");
			jsonObject2=new JSONObject(content);
			nickname=jsonObject2.getString("nickname");
			phoneNumber=jsonObject2.getString("mobile");
			weiXin=jsonObject2.getString("weixin_bind");
			renZheng=jsonObject2.getString("status");
		//	imageUrl=jsonObject2.getString("imgurl");
			
			System.out.println(phoneNumber);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		if (code == 0) {
				Log.v("登陆数据",content);
				if (appContext.isLogin()) {
					tvUserName.setText(nickname);
					if (appContext.isLogin()) {
						ImageLoader imageLoader = new ImageLoader(AppContext.getHttpQueues(),
								new BitmapCache());
						try {
						       ivHead.setErrorImageResId(R.drawable.person_head); // 加载失败显示的图片
								ivHead.setImageUrl(sp.getString(Constant.MY_HEAD_URL, ""), imageLoader);
								ivHead.setImageUrl(AppContext.getHeadurl(), imageLoader);
							//	System.out.println(AppContext.getPathHeadImage());;
								ivHead.setOnClickListener(this);
							}catch (Exception e) {
							// TODO: handle exception
								e.printStackTrace();
						    	System.out.println("头像加载失败");
						}		
					}
					
					String newString=phoneNumber.substring(0,3)+"****"+phoneNumber.substring(7, 11);
					tvPhoneNumber.setText(newString);
					System.out.println(weiXin+"不绑定1");
					if (weiXin.equals("0")) {
						System.out.println(weiXin+"不绑定2");
						tvWeiXin.setText("未绑定");
					}else {
						tvWeiXin.setText("已绑定");
						System.out.println(weiXin+"不绑定3");
						tvWeiXin.setClickable(false);
					}
					if (renZheng.equals("0")) {
						tvRenZhen.setText("未认证");
					}else {
						tvRenZhen.setText("已认证");
					}
				}
	     	}else {
	     		Toast.makeText(MorePersonal.this, "查询数据失败", Toast.LENGTH_SHORT).show();
			}
		}
 	}
	@Override
    protected void onResume() {
    	// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
		if (!AppContext.getCode().equals("")) {
			FLAG=1;
	    	ApiClient.wxBangDing(MorePersonal.this,appContext.getLoginUid()+"",AppContext.getCode(),networkHelper);
	    	AppContext.setCode("");
		}
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
}