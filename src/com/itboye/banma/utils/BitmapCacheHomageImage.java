package com.itboye.banma.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class BitmapCacheHomageImage implements ImageCache {

	private static LruCache<String, Bitmap> mCache;

	public BitmapCacheHomageImage() {
		if (mCache == null) {
			// 获取到可用内存的最大值，使用内存超出这个值会引起OutOfMemory异常。  
		    // LruCache通过构造函数传入缓存值，以KB为单位。  
		    int maxMemory = (int) (Runtime.getRuntime().maxMemory());  
		    // 使用最大可用内存值的1/5作为缓存的大小。  
		    int cacheSize = maxMemory / 5;  
			//int maxSize = 10 * 1024 * 1024;
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
		if(url == null){
			return null;
		}
			
		return mCache.get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		mCache.put(url, bitmap);
	}

}