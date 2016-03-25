package com.itboye.banma.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.itboye.banma.R;
import com.itboye.banma.activities.BabyActivity;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.entity.ProductItem;
import com.itboye.banma.util.BaseViewHolder;
import com.itboye.banma.utils.BitmapCache;
import com.itboye.banma.utils.BitmapCacheHomageImage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyPageAdapter extends PagerAdapter{
	private List<View> mImages;
	private int[] mImageIds;
	private List<ProductItem> productlist;
	private Context mContext;
	BitmapCacheHomageImage bitmapCache = new BitmapCacheHomageImage();
	
	public MyPageAdapter(List<View> mImages, int[] mImageIds){
		this.mImageIds = mImageIds;
		this.mImages = mImages;
	}
	
	public MyPageAdapter(Context context, List<View> mImages, List<ProductItem> productlist) {
		this.productlist = productlist;
		this.mImages = mImages;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return productlist.size();
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return POSITION_NONE;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub
		return view == object;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mImages.get(position) );
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		View convertView = mImages.get(position);
		final LinearLayout imageView_layout = BaseViewHolder.get(convertView,
				R.id.imageView_layout);
		NetworkImageView imageView = BaseViewHolder.get(convertView,
				R.id.imageView);
		/*TextView name = BaseViewHolder.get(convertView,
				R.id.name);*/
		TextView price = BaseViewHolder.get(convertView,
				R.id.price);
		/*name.setText(productlist.get(position).getName());*/
		//price.setText("￥"+productlist.get(position).getPrice());
		ImageLoader imageLoader = new ImageLoader(AppContext.getHttpQueues(), bitmapCache); 
		imageView.setDefaultImageResId(R.drawable.loading_image_shouye);  //加载中显示的图片
		imageView.setErrorImageResId(R.drawable.loading_image_shouye);  //加载失败显示的图片
		imageView.setImageUrl(productlist.get(position).getImg_post(), imageLoader);

		//imageView.setImageUrl("http://banma.itboye.com/index.php/Api/Picture/index?id=0", imageLoader);

		/*ImageRequest imageRequest = new ImageRequest(  
		        "http://developer.android.com/images/home/aw_dac.png",  
		        new Response.Listener<Bitmap>() {  
		            @Override  
		            public void onResponse(Bitmap response) {  
		            	imageView_layout.set(response);  
		            }  
		        }, 0, 0, Config.RGB_565, new Response.ErrorListener() {  
		            @Override  
		            public void onErrorResponse(VolleyError error) {  
		                imageView.setImageResource(R.drawable.default_image);  
		            }  
		        });
		*/
		
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, BabyActivity.class);
				intent.putExtra("PID", productlist.get(position).getId());
				mContext.startActivity(intent);
				((Activity) mContext).overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
			}
		});
		
		//imageView.setImageResource(mImageIds[position]);
		//imageView.setScaleType(ScaleType.CENTER_CROP);
		
		
		container.addView(convertView);
		
		return convertView;
	}
}
