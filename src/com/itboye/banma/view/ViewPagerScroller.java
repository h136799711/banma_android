package com.itboye.banma.view;
import java.lang.reflect.Field;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.animation.Interpolator;
import android.widget.ScrollView;
import android.widget.Scroller;
 
/**
 * ViewPager 滚动速度设置
 * 
 * @author Tercel
 * 
 */
public class ViewPagerScroller extends ScrollView {  
  
    private GestureDetector mGestureDetector;  
  
    public ViewPagerScroller(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
        init();  
    }  
  
    public ViewPagerScroller(Context context) {  
        super(context);  
        init();  
    }  
  
    public ViewPagerScroller(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        init();  
    }  
  
    private void init() {  
        mGestureDetector = new GestureDetector(getContext(),
                new YScrollDetector());  
        setFadingEdgeLength(0);  
    }  
  
    @Override  
    public boolean onInterceptTouchEvent(MotionEvent ev) {  
        return super.onInterceptTouchEvent(ev)  
                && mGestureDetector.onTouchEvent(ev);  
    }  
  
    private class YScrollDetector extends SimpleOnGestureListener {  
        @Override  
        public boolean onScroll(MotionEvent e1, MotionEvent e2,  
                float distanceX, float distanceY) {  
              
            if (Math.abs(distanceY) >= Math.abs(distanceX)) {  
                return true;  
            }  
            return false;  
        }  
    }  
}  