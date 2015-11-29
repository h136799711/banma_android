package com.itboye.banma.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.toolbox.ImageLoader.ImageCache;
/**
 * 保存订单图片的缓存
 * @author Administrator
 *
 */
public class OrderBitmapCache implements ImageCache {

	private static LruCache<String, Bitmap> mCache;

	public OrderBitmapCache() {
		if (mCache == null) {
			// 获取到可用内存的最大值，使用内存超出这个值会引起OutOfMemory异常。  
		    // LruCache通过构造函数传入缓存值，以KB为单位。  
		    int maxMemory = (int) (Runtime.getRuntime().maxMemory());  
		    // 使用最大可用内存值的1/8作为缓存的大小。  
		    int cacheSize = maxMemory / 10;  
			//int cacheSize = 10 * 1024 * 1024;
			mCache = new LruCache<String, Bitmap>(cacheSize) {
				@Override
				protected int sizeOf(String key, Bitmap bitmap) {
					return bitmap.getRowBytes() * bitmap.getHeight();
				}
			};
		}
	}

	@Override
	public Bitmap getBitmap(String url) {
		return mCache.get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		mCache.put(url, bitmap);
		Log.i(getClass().getSimpleName(), "cacheSize/maxSize:" + mCache.size() + "/" + mCache.maxSize());
	
	}

}