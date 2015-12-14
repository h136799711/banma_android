package com.itboye.banma.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.itboye.banma.R;
import com.itboye.banma.adapter.MyGridAdapter;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.app.Constant;
import com.itboye.banma.entity.ProductDetail.Sku_info;
import com.itboye.banma.entity.SkuInfo;
import com.itboye.banma.entity.SkuStandard;
import com.itboye.banma.util.BaseViewHolder;
import com.itboye.banma.utils.BitmapCache;
import com.itboye.banma.utils.ChooseStandardInterface;
import com.itboye.banma.utils.ChooseStandardListener;
import com.itboye.banma.utils.OrderBitmapCache;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.itboye.banma.R;
import com.itboye.banma.app.Constant;

@SuppressLint("CommitPrefEdits")
public class BabyPopWindow implements OnDismissListener, OnClickListener,
		ChooseStandardListener {
	private TextView pop_add, pop_reduce, pop_num, pop_ok;
	private LinearLayout oneLayout, twoLayout, threeLayout;
	private TextView tex_one, tex_two, tex_three;
	private ImageView pow_pic;
	private TextView pow_price;
	private TextView pow_ori_price;
	private TextView pow_quantity;
	private TextView standardView;
	private ImageView pop_del;
	private PopupWindow popupWindow;
	private LinearLayout sku_info_list_layout;
	private OnItemClickListener listener;
	private final int ADDORREDUCE = 1;
	private static Context context;
	private String str_color = "";
	private String str_type = "";
	private List<SkuInfo> skuInfo;
	private List<Sku_info> sku_info;
	List<SkuStandard> skuList;
	private String url;
	private Double price;
	private String name;
	private Double ori_price;
	private int quantity;
	private View view;
	private int skuPosition = -100; // 记录所选规格在列表位置
	private int sku_id = -1; // 记录所选规格ID
	private ChooseStandardInterface ch;
	String standard = null;
	String standardStr = null;
	int has_sku;

	public BabyPopWindow(Context context, List<Sku_info> sku_info, String name,
			List<SkuInfo> skuInfo, String url, Double price,
			Double ori_price, String has_sku, int quantity, List<SkuStandard> skuList) {
		this.context = context;
		this.skuInfo = skuInfo;
		this.sku_info = sku_info;
		this.skuList = skuList;
		this.name = name;
		this.url = url;
		this.price = price;
		this.ori_price = ori_price;
		this.quantity = quantity;
		this.has_sku = Integer.parseInt(has_sku);
		ch = new ChooseStandardInterface();
		ch.setChooseStandardListener(this);
		view = LayoutInflater.from(context).inflate(R.layout.adapter_popwindow,
				null);

		pow_pic = (ImageView) view.findViewById(R.id.pow_pic);
		pow_price = (TextView) view.findViewById(R.id.pow_price);
		pow_ori_price = (TextView) view.findViewById(R.id.ori_price);
		pow_quantity = (TextView) view.findViewById(R.id.pow_stock);
		pop_add = (TextView) view.findViewById(R.id.pop_add);
		pop_reduce = (TextView) view.findViewById(R.id.pop_reduce);
		pop_num = (TextView) view.findViewById(R.id.pop_num);
		pop_ok = (TextView) view.findViewById(R.id.pop_ok);
		pop_del = (ImageView) view.findViewById(R.id.pop_del);
		standardView = (TextView) view.findViewById(R.id.standard);
		sku_info_list_layout = (LinearLayout) view.findViewById(R.id.sku_info_list_layout);

		ImageLoader imageLoader = new ImageLoader(AppContext.getHttpQueues(),
				new OrderBitmapCache());
		ImageListener listener = ImageLoader.getImageListener(pow_pic,
				R.drawable.loading_image_baby, R.drawable.loading_image_baby);
		imageLoader.get(url, listener, 150, 150);
		pow_price.setText("￥" + price);
		pow_ori_price.setText("￥" + ori_price);
		pow_ori_price.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		pow_quantity.setText("库存" + quantity +"件");
	
		if (skuInfo != null) {
			int n = skuInfo.size();
			for(int k=0; k<n; k++){   //去除为空的规格
				if(skuInfo.get(k).getValue_list().size() == 0){
					skuInfo.remove(k);
					k--;
					n--;
				}
			}
			Constant.SKU_ALLNUM = skuInfo.size();
		} else {
			Constant.SKU_ALLNUM = 0;
		}
		
		
		if(Constant.SKU_ALLNUM == 0){
			init();
		}else{
			standardView.setText("请选择规格参数");
			for(int k=0; k<Constant.SKU_ALLNUM; k++){
				LayoutInflater inflater= LayoutInflater.from(context);
				View convertView = inflater.inflate(R.layout.sku_info_item,null);
				TextView sku_tex = BaseViewHolder.get(convertView, R.id.sku_tex);
				MyGridView sku_info_gridview = BaseViewHolder.get(convertView, R.id.sku_info_gridview);
				MyGridAdapter adapter = new MyGridAdapter(context, null, skuInfo.get(k), k, ch);
				getItemMaxWhith(sku_info_gridview, adapter);
				sku_info_gridview.setAdapter(adapter);
				sku_tex.setText(skuInfo.get(k).getName());
				sku_info_list_layout.addView(convertView);
				
			}
			init();
		}
		
	}
	
	/**
	 * 获取adapter的最大item宽度
	 * @param gridView
	 * @param adapter
	 */
	public static void getItemMaxWhith(MyGridView gridView, MyGridAdapter adapter) {    
	       // 固定列宽，有多少列  
	       
	       int maxwidth = 0;  
	       int width = 0;
	       WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

			int win_width = wm.getDefaultDisplay().getWidth();
			int win_height = wm.getDefaultDisplay().getHeight();
	       // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，  
	       // listAdapter.getCount()小于等于8时计算两次高度相加  
	       for (int i = 0; i < adapter.getCount(); i++) {  
	        // 获取listview的每一个item  
	           View listItem = adapter.getView(i, null, gridView);  
	           listItem.measure(0, 0);  
	           // 获取item的宽度  
	          
	           width = listItem.getMeasuredWidth();
	           if(maxwidth < width){
	        	   maxwidth = width;
	           }
	       }  
	       int num = win_width / (maxwidth+20);
	  
	       // 获取listview的布局参数  
	       ViewGroup.LayoutParams params = gridView.getLayoutParams();  
	       // 设置高度  
	       params.width = (maxwidth+20)*num;  
	       // 设置margin  
	       ((MarginLayoutParams) params).setMargins(10, 5, 10, 5);  
	       // 设置参数  
	       gridView.setLayoutParams(params); 
	       gridView.setNumColumns(num);
	   }  

	private void init() {
		pop_add.setOnClickListener(this);
		pop_reduce.setOnClickListener(this);
		pop_ok.setOnClickListener(this);
		pop_del.setOnClickListener(this);

		popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		popupWindow.setAnimationStyle(R.style.popWindow_anim_style);
		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		popupWindow.setOnDismissListener(this);

	}

	public interface OnItemClickListener {
		/**
		 * 购买点击监听
		 * 
		 * @param skuStandard
		 */
		public void onClickOKPop(SkuStandard skuStandard);

		public void onClickDissmissPop();
	}

	/** 绑定监听 */
	public void setOnItemClickListener(OnItemClickListener listener) {
		this.listener = listener;
	}

	// popWindow销毁方法
	@Override
	public void onDismiss() {

	}

	/** 初始化popupWindow */
	public void showAsDropDown(View parent) {
		popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
		popupWindow.setFocusable(false);
		popupWindow.setOutsideTouchable(false);
		popupWindow.update();
	}

	/** 关闭popupWindow */
	public void dissmiss() {
		listener.onClickDissmissPop();

		popupWindow.dismiss();
	}

	public boolean isOrNot() {
		return popupWindow.isShowing();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.pop_add:
			if (Integer.valueOf(pop_num.getText().toString()) < quantity) {

				String num_add = Integer.valueOf(pop_num.getText().toString())
						+ ADDORREDUCE + "";
				pop_num.setText(num_add);
			} else {
				Toast.makeText(context, "不能超过"+quantity, Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.pop_reduce:
			if (!pop_num.getText().toString().equals("1")) {
				String num_reduce = Integer.valueOf(pop_num.getText()
						.toString()) - ADDORREDUCE + "";
				pop_num.setText(num_reduce);
			} else {
				Toast.makeText(context, "已经是最小数量", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.pop_del:
			// listener.onClickOKPop();
			dissmiss();

			break;
		case R.id.pop_ok:
			if(has_sku == 0){
				if(Integer.valueOf(pop_num.getText().toString()) > quantity){
					Toast.makeText(context, "购买数量过多,请减少数量", Toast.LENGTH_SHORT).show();
				}else{

					SkuStandard sku = new SkuStandard();
					sku.setNum(pop_num.getText().toString());
					sku.setName(name);
					listener.onClickOKPop(sku);
				}
			}else if(has_sku == 1){
				if(skuInfo == null){
					Toast.makeText(context, "服务器信息不全,请稍后再试", Toast.LENGTH_SHORT).show();
				}else if (sku_id == -1) {
					if(skuPosition == -1){
						Toast.makeText(context, "库存为0,请重选规格", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(context, "请选择规格", Toast.LENGTH_SHORT).show();
					}
				
				}else {
					if(skuList.get(skuPosition).getQuantity() <= 0){
						Toast.makeText(context, "库存为0,请重选规格", Toast.LENGTH_SHORT).show();
					}else{
						if(Integer.valueOf(pop_num.getText().toString()) > skuList.get(skuPosition).getQuantity()){
							Toast.makeText(context, "购买数量过多,请减少数量", Toast.LENGTH_SHORT).show();
						}else{
							skuList.get(skuPosition).setNum(pop_num.getText().toString());
							skuList.get(skuPosition).setName(name);
							skuList.get(skuPosition).setSku(standardStr);
							listener.onClickOKPop(skuList.get(skuPosition));

							dissmiss();
						}
					}

				}
			}
			break;

		default:

			break;
		}
	}

	/** 保存数据 */
	private void setSaveData() {
		SharedPreferences sp = context.getSharedPreferences("SAVE_CART",
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt("ArrayCart_size", Constant.arrayList_cart.size());
		for (int i = 0; i < Constant.arrayList_cart.size(); i++) {
			editor.remove("ArrayCart_type_" + i);
			editor.remove("ArrayCart_color_" + i);
			editor.remove("ArrayCart_num_" + i);
			editor.putString("ArrayCart_type_" + i, Constant.arrayList_cart
					.get(i).get("type").toString());
			editor.putString("ArrayCart_color_" + i, Constant.arrayList_cart
					.get(i).get("color").toString());
			editor.putString("ArrayCart_num_" + i,
					Constant.arrayList_cart.get(i).get("num").toString());

		}

	}

	@Override
	public void changeview() {
		standard = null;
		standardStr = null;
		for (int j = 0; j < skuInfo.size(); j++) {
			if (standard == null) {
				standard = Constant.SKU_INFO[j];
				standardStr = Constant.SKU_INFOSTR[j];
			} else {
				standard = standard + Constant.SKU_INFO[j];
				standardStr = standardStr + Constant.SKU_INFOSTR[j];
			}

		};
		skuPosition = -1;
		for (int k = 0; k < skuList.size(); k++) {
			if (skuList.get(k).getSku_id().equals(standard)) {
				skuPosition = k;
				sku_id = skuList.get(k).getId();

				break;
			}
		}
		standardView.setText(standardStr);
		if(skuPosition == -1){
			pow_quantity.setText("库存0");
			pow_price.setText("￥" + skuList.get(0).getPrice());
			pow_ori_price.setText("￥" + skuList.get(0).getOri_price());
			pow_ori_price.getPaint().setFlags(
					Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		}else{
			pow_quantity.setText("库存" + skuList.get(skuPosition).getQuantity());
			pow_price.setText("￥" + skuList.get(skuPosition).getPrice());
			pow_ori_price.setText("￥" + skuList.get(skuPosition).getOri_price());
			pow_ori_price.getPaint().setFlags(
					Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		}
		
	}

}
