package com.itboye.banma.activities;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.itboye.banma.R;
import com.itboye.banma.R.string;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itboye.banma.app.AppContext;
import com.itboye.banma.entity.SkuStandard;
import com.itboye.banma.utils.BitmapCache;

public class ConfirmOrderActivity extends Activity implements OnClickListener{
	private final int ADDORREDUCE = 1;
	private SkuStandard skuStandard;
	private String main_img;
	private String name;
	private Double price;
	private Double priceAll;
	private ImageView top_back;
	private TextView top_title;
	private ImageView order_pic;
	private TextView order_name;
	private TextView order_standard;
	private TextView order_price;
	private TextView order_number;
	private TextView pop_add;
	private TextView pop_reduce;
	private TextView pop_num;
	private TextView all_num;
	private TextView all_price;
	private TextView order_all_price;
	
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_order);
		initData();
		initView();
	}

	private void initData() {
		Intent intent = getIntent();
		skuStandard = (SkuStandard) intent.getSerializableExtra("SkuStandard");
		main_img = intent.getStringExtra("main_img");
		name = intent.getStringExtra("name");
		price = intent.getDoubleExtra("price", 100.0);
		priceAll = price * Integer.parseInt(skuStandard.getProduct_code());
		System.out.println("SkuStandard"+skuStandard);
	}

	private void initView() {
		top_back = (ImageView) findViewById(R.id.iv_back);
		top_title = (TextView) findViewById(R.id.title);
		order_pic = (ImageView) findViewById(R.id.order_pic);
		order_name = (TextView) findViewById(R.id.order_name);
		order_standard = (TextView) findViewById(R.id.order_standard);
		order_price = (TextView) findViewById(R.id.order_price);
		order_number = (TextView) findViewById(R.id.order_number);
		pop_add = (TextView) findViewById(R.id.pop_add);
		pop_reduce = (TextView) findViewById(R.id.pop_reduce);
		pop_num = (TextView) findViewById(R.id.pop_num);
		all_num = (TextView) findViewById(R.id.all_num);
		all_price = (TextView) findViewById(R.id.all_price);
		order_all_price = (TextView) findViewById(R.id.order_all_price); 
		top_back.setOnClickListener(this);
		pop_add.setOnClickListener(this);
		pop_reduce.setOnClickListener(this);
		
		ImageLoader imageLoader = new ImageLoader(AppContext.getHttpQueues(), new BitmapCache()); 
		ImageListener listener = ImageLoader.getImageListener(order_pic,  
				R.drawable.image_loading, R.drawable.image_load_fail); 
		imageLoader.get(main_img, listener, 0, 0);
		order_name.setText(name);
		order_standard.setText(skuStandard.getSku());
		order_price.setText(""+price);
		//order_number.setText("×"+skuStandard.getProduct_code());
		pop_num.setText(skuStandard.getProduct_code());
		all_num.setText(skuStandard.getProduct_code());
		all_price.setText(""+priceAll);
		order_all_price.setText(""+priceAll);
		top_title.setText(string.confirm_order);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			break;
		case R.id.pop_add:
			if (!pop_num.getText().toString().equals("50")) {

				String num_add = Integer.valueOf(pop_num.getText().toString())
						+ ADDORREDUCE + "";
				priceAll = Integer.parseInt(num_add) * price;
				skuStandard.setProduct_code(num_add);
				pop_num.setText(num_add);
				all_num.setText(skuStandard.getProduct_code());
				all_price.setText(""+priceAll);
				order_all_price.setText(""+priceAll);
			} else {
				Toast.makeText(ConfirmOrderActivity.this, "不能超过50", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.pop_reduce:
			if (!pop_num.getText().toString().equals("1")) {
				String num_reduce = Integer.valueOf(pop_num.getText()
						.toString()) - ADDORREDUCE + "";
				priceAll = Integer.parseInt(num_reduce) * price;
				skuStandard.setProduct_code(num_reduce);
				pop_num.setText(num_reduce);
				all_num.setText(skuStandard.getProduct_code());
				all_price.setText(""+priceAll);
				order_all_price.setText(""+priceAll);
			} else {
				Toast.makeText(ConfirmOrderActivity.this, "已经是最小数量", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}
}
