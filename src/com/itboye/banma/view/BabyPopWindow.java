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
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
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
	private MyGridView gridviewOne;
	private MyGridView gridviewTwo;
	private MyGridView gridviewThree;
	private PopupWindow popupWindow;
	private OnItemClickListener listener;
	private final int ADDORREDUCE = 1;
	private Context context;
	private String str_color = "";
	private String str_type = "";
	private Map<String, SkuInfo> skuInfo;
	private List<Sku_info> sku_info;
	List<SkuStandard> skuList;
	private String url;
	private Double price;
	private String name;
	private Double ori_price;
	private int quantity;
	private View view;
	private int skuPosition = 0; // 记录所选规格在列表位置
	private int sku_id = -1; // 记录所选规格ID
	private ChooseStandardInterface ch;

	public BabyPopWindow(Context context, List<Sku_info> sku_info, String name,
			Map<String, SkuInfo> skuInfo, String url, Double price,
			Double ori_price, int quantity, List<SkuStandard> skuList) {
		this.context = context;
		this.skuInfo = skuInfo;
		this.sku_info = sku_info;
		this.skuList = skuList;
		this.name = name;
		this.url = url;
		this.price = price;
		this.ori_price = ori_price;
		this.quantity = quantity;
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
		tex_one = (TextView) view.findViewById(R.id.tex_one);
		tex_two = (TextView) view.findViewById(R.id.tex_two);
		tex_three = (TextView) view.findViewById(R.id.tex_three);
		standardView = (TextView) view.findViewById(R.id.standard);

		ImageLoader imageLoader = new ImageLoader(AppContext.getHttpQueues(),
				new BitmapCache());
		ImageListener listener = ImageLoader.getImageListener(pow_pic,
				R.drawable.image_loading, R.drawable.image_load_fail);
		imageLoader.get(url, listener, 100, 100);
		pow_price.setText("￥" + price);
		pow_ori_price.setText("￥" + ori_price);
		pow_ori_price.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		pow_quantity.setText("库存" + quantity);

		if (skuInfo != null) {
			Constant.SKU_ALLNUM = skuInfo.size();
		} else {
			Constant.SKU_ALLNUM = 0;
		}
		switch (Constant.SKU_ALLNUM) {
		case 0:
			init();
			break;
		case 1:

			oneLayout = (LinearLayout) view.findViewById(R.id.one_layout);
			twoLayout = (LinearLayout) view.findViewById(R.id.two_layout);
			threeLayout = (LinearLayout) view.findViewById(R.id.three_layout);
			oneLayout.setVisibility(View.VISIBLE);
			twoLayout.setVisibility(View.GONE);
			threeLayout.setVisibility(View.GONE);
			gridviewOne = (MyGridView) view.findViewById(R.id.gridview_one);
			gridviewOne.setAdapter(new MyGridAdapter(context, sku_info.get(0),
					skuInfo.get(sku_info.get(0).getId()), 0, ch));
			tex_one.setText(skuInfo.get(sku_info.get(0).getId()).getName());
			init();
			break;
		case 2:
			oneLayout = (LinearLayout) view.findViewById(R.id.one_layout);
			twoLayout = (LinearLayout) view.findViewById(R.id.two_layout);
			threeLayout = (LinearLayout) view.findViewById(R.id.three_layout);
			oneLayout.setVisibility(View.VISIBLE);
			twoLayout.setVisibility(View.VISIBLE);
			threeLayout.setVisibility(View.GONE);
			gridviewOne = (MyGridView) view.findViewById(R.id.gridview_one);
			gridviewTwo = (MyGridView) view.findViewById(R.id.gridview_two);
			gridviewOne.setAdapter(new MyGridAdapter(context, sku_info.get(0),
					skuInfo.get(sku_info.get(0).getId()), 0, ch));
			gridviewTwo.setAdapter(new MyGridAdapter(context, sku_info.get(1),
					skuInfo.get(sku_info.get(1).getId()), 1, ch));
			tex_one.setText(skuInfo.get(sku_info.get(0).getId()).getName());
			tex_two.setText(skuInfo.get(sku_info.get(1).getId()).getName());
			init();

			break;
		case 3:
			oneLayout = (LinearLayout) view.findViewById(R.id.one_layout);
			twoLayout = (LinearLayout) view.findViewById(R.id.two_layout);
			threeLayout = (LinearLayout) view.findViewById(R.id.three_layout);
			oneLayout.setVisibility(View.VISIBLE);
			twoLayout.setVisibility(View.VISIBLE);
			threeLayout.setVisibility(View.VISIBLE);
			gridviewOne = (MyGridView) view.findViewById(R.id.gridview_one);
			gridviewTwo = (MyGridView) view.findViewById(R.id.gridview_two);
			gridviewThree = (MyGridView) view.findViewById(R.id.gridview_three);
			gridviewOne.setAdapter(new MyGridAdapter(context, sku_info.get(0),
					skuInfo.get(sku_info.get(0).getId()), 0, ch));
			gridviewTwo.setAdapter(new MyGridAdapter(context, sku_info.get(1),
					skuInfo.get(sku_info.get(1).getId()), 1, ch));
			gridviewThree.setAdapter(new MyGridAdapter(context,
					sku_info.get(2), skuInfo.get(sku_info.get(2).getId()), 2,
					ch));
			tex_one.setText(skuInfo.get(sku_info.get(0).getId()).getName());
			tex_two.setText(skuInfo.get(sku_info.get(1).getId()).getName());
			tex_three.setText(skuInfo.get(sku_info.get(2).getId()).getName());
			init();
			break;

		default:
			init();
			break;
		}
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
			if (!pop_num.getText().toString().equals("50")) {

				String num_add = Integer.valueOf(pop_num.getText().toString())
						+ ADDORREDUCE + "";
				pop_num.setText(num_add);
			} else {
				Toast.makeText(context, "不能超过50", Toast.LENGTH_SHORT).show();
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

			if (sku_id == -1) {
				Toast.makeText(context, "请选择规格", Toast.LENGTH_SHORT).show();
			} else {
				skuList.get(skuPosition).setNum(pop_num.getText().toString());
				skuList.get(skuPosition).setName(name);
				listener.onClickOKPop(skuList.get(skuPosition));

				dissmiss();

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
		String standard = null;
		for (int j = 0; j < skuInfo.size(); j++) {
			if (standard == null) {
				standard = Constant.SKU_INFO[j];
			} else {
				standard = standard + Constant.SKU_INFO[j];
			}

		}
		;
		for (int k = 0; k < skuList.size(); k++) {
			if (skuList.get(k).getSku_id().equals(standard)) {
				skuPosition = k;
				sku_id = skuList.get(k).getId();

				break;
			}
		}
		standardView.setText(skuList.get(skuPosition).getSku());
		pow_quantity.setText("库存" + skuList.get(skuPosition).getQuantity());
		pow_price.setText("￥" + skuList.get(skuPosition).getPrice());
		pow_ori_price.setText("￥" + skuList.get(skuPosition).getOri_price());
		pow_ori_price.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
	}

}
