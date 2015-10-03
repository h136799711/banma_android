package com.itboye.banma.view;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
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
public class BabyPopWindow implements OnDismissListener, OnClickListener {
	private TextView pop_add, pop_reduce, pop_num, pop_ok;
	private LinearLayout oneLayout, twoLayout;
	private ImageView pop_del;
	private MyGridView gridviewOne;
	private MyGridView gridviewTwo;
	private PopupWindow popupWindow;
	private OnItemClickListener listener;
	private final int ADDORREDUCE = 1;
	private Context context;
	private String str_color = "";
	private String str_type = "";

	public BabyPopWindow(Context context) {
		this.context = context;
		View view = LayoutInflater.from(context).inflate(
				R.layout.adapter_popwindow, null);

		pop_add = (TextView) view.findViewById(R.id.pop_add);
		pop_reduce = (TextView) view.findViewById(R.id.pop_reduce);
		pop_num = (TextView) view.findViewById(R.id.pop_num);
		pop_ok = (TextView) view.findViewById(R.id.pop_ok);
		pop_del = (ImageView) view.findViewById(R.id.pop_del);
		oneLayout = (LinearLayout) view.findViewById(R.id.one_layout);
		twoLayout = (LinearLayout) view.findViewById(R.id.two_layout);
		oneLayout.setVisibility(View.VISIBLE);
		twoLayout.setVisibility(View.VISIBLE);
		gridviewOne = (MyGridView) view.findViewById(R.id.gridview_one);
		gridviewTwo = (MyGridView) view.findViewById(R.id.gridview_two);
		gridviewOne.setAdapter(new MyGridAdapter(context));
		gridviewTwo.setAdapter(new MyGridAdapter(context));

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
		/** 购买点击监听 */
		public void onClickOKPop();
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
		listener.onClickOKPop();
		popupWindow.dismiss();
	}

	public boolean isOrNot() {
		return popupWindow.isShowing();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.pop_add:
			if (!pop_num.getText().toString().equals("750")) {

				String num_add = Integer.valueOf(pop_num.getText().toString())
						+ ADDORREDUCE + "";
				pop_num.setText(num_add);
			} else {
				Toast.makeText(context, "不能超过750", Toast.LENGTH_SHORT).show();
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
			listener.onClickOKPop();
			dissmiss();

			break;
		case R.id.pop_ok:
			listener.onClickOKPop();
			if (str_color.equals("")) {
				Toast.makeText(context, "请选择颜色", Toast.LENGTH_SHORT).show();
			} else if (str_type.equals("")) {
				Toast.makeText(context, "请选择种类", Toast.LENGTH_SHORT).show();
			} else {
				HashMap<String, Object> allHashMap = new HashMap<String, Object>();

				allHashMap.put("color", str_color);
				allHashMap.put("type", str_type);
				allHashMap.put("num", pop_num.getText().toString());
				allHashMap.put("id", Constant.arrayList_cart_id += 1);

				Constant.arrayList_cart.add(allHashMap);
				setSaveData();
				dissmiss();

			}
			break;

		default:
			break;
		}
		/** 保存数据 */
		private void setSaveData() {
			SharedPreferences sp = context.getSharedPreferences("SAVE_CART",
					Context.MODE_PRIVATE);
			Editor editor = sp.edit();
		editor.putInt("ArrayCart_size", Constant.arrayList_cart.size());
		for (int i = 0; i < Constant.arrayList_cart.size(); i++) {
			editor.remove("ArrayCart_type_"+i);
			editor.remove("ArrayCart_color_"+i);
			editor.remove("ArrayCart_num_"+i);
			editor.putString("ArrayCart_type_"+i, Constant.arrayList_cart.get(i).get("type").toString());
			editor.putString("ArrayCart_color_"+i, Constant.arrayList_cart.get(i).get("color").toString());
			editor.putString("ArrayCart_num_"+i, Constant.arrayList_cart.get(i).get("num").toString());
					
		}

	}

}
