package com.itboye.banma.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollViewPager extends ViewPager {
	private boolean isPagingEnabled = true;
	 
    public NoScrollViewPager(Context context) {
        super(context);
    }
 
    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
 
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return  false;
    }
 
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }
 
    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }
}