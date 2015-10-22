/*
 * Copyright 2013 David Schreiber
 *           2013 John Paul Nalog
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.itboye.banma.adapter;
import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.itboye.banma.R;
import com.itboye.banma.activities.BabyActivity;
import com.itboye.banma.app.AppContext;
import com.itboye.banma.entity.ProductItem;
import com.itboye.banma.utils.BitmapCache;
import com.itboye.banma.view.FancyCoverFlow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FancyCoverFlowSampleAdapter extends FancyCoverFlowAdapter {
    private Context mContext;
    List<ProductItem> productlist;
   
    public FancyCoverFlowSampleAdapter(Context context, List<ProductItem> productlist) {
		this.mContext = context;
		this.productlist = productlist;
	}

	@Override
    public int getCount() {
        return productlist.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getCoverFlowItem(int i, View reuseableView, ViewGroup viewGroup) {
    	ImageView imageView = null;

        if (reuseableView != null) {
            imageView = (ImageView) reuseableView;
        } else {
            imageView = new ImageView(viewGroup.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setLayoutParams(new FancyCoverFlow.LayoutParams(140, 190));

        }
        ImageLoader imageLoader = new ImageLoader(AppContext.getHttpQueues(), new BitmapCache()); 
		ImageListener listener = ImageLoader.getImageListener(imageView,  
				R.drawable.image_loading, R.drawable.image_load_fail); 
		
		imageLoader.get(productlist.get(i).getMain_img(), listener, 280, 380);

       /* ImageLoader imageLoader = new ImageLoader(AppContext.getHttpQueues(), new BitmapCache()); 
		imageView.setDefaultImageResId(R.drawable.image_loading);  //加载中显示的图片
		imageView.setErrorImageResId(R.drawable.image_load_fail);  //加载失败显示的图片
		imageView.setImageUrl(productlist.get(i).getMain_img(), imageLoader);*/
        //imageView.setImageResource(this.getItem(i));
        
        return imageView;
    }

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}
}
