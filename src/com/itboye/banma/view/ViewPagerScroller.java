package com.itboye.banma.view;
import java.lang.reflect.Field;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ScrollView;

 
/**
 * ViewPager 滚动速度设置
 * 
 * @author Tercel
 * 
 */
public class ViewPagerScroller extends ScrollView {  
  
    private GestureDetector mGestureDetector; 
    private OnScrollListener onScrollListener; 
    /** 
     * 主要是用在用户手指离开MyScrollView，MyScrollView还在继续滑动，我们用来保存Y的距离，然后做比较 
     */  
    private int lastScrollY; 
  
 // 滑动距离及坐标  
    private float xDistance, yDistance, xLast, yLast; 
  
    public ViewPagerScroller(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
    }  
  
    public ViewPagerScroller(Context context) {  
        super(context);  
    }  
  
    public ViewPagerScroller(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }   
  
    @Override  
    public boolean onInterceptTouchEvent(MotionEvent ev) {  
    	 switch (ev.getAction()) {  
         case MotionEvent.ACTION_DOWN:  
             xDistance = yDistance = 0f;  
             xLast = ev.getX();  
             yLast = ev.getY();  
             break;  
         case MotionEvent.ACTION_MOVE:  
             final float curX = ev.getX();  
             final float curY = ev.getY();  

             xDistance += Math.abs(curX - xLast);  
             yDistance += Math.abs(curY - yLast);  
             xLast = curX;  
             yLast = curY;  

             if(xDistance > yDistance){  
                 return false;  
             }    
     }  

     return super.onInterceptTouchEvent(ev);    
    }  
    /** 
     * 设置滚动接口 
     * @param onScrollListener 
     */  
    public void setOnScrollListener(OnScrollListener onScrollListener) {  
        this.onScrollListener = onScrollListener;  
    } 
    /** 
     * 用于用户手指离开MyScrollView的时候获取MyScrollView滚动的Y距离，然后回调给onScroll方法中 
     */  
    private Handler handler = new Handler() {  
  
        public void handleMessage(android.os.Message msg) {  
            int scrollY = ViewPagerScroller.this.getScrollY();  
              
            //此时的距离和记录下的距离不相等，在隔5毫秒给handler发送消息  
            if(lastScrollY != scrollY){  
                lastScrollY = scrollY;  
                handler.sendMessageDelayed(handler.obtainMessage(), 5);    
            }  
            if(onScrollListener != null){  
                onScrollListener.onScroll(scrollY);  
            }  
              
        };  
  
    }; 
    /** 
     * 重写onTouchEvent， 当用户的手在MyScrollView上面的时候， 
     * 直接将MyScrollView滑动的Y方向距离回调给onScroll方法中，当用户抬起手的时候， 
     * MyScrollView可能还在滑动，所以当用户抬起手我们隔5毫秒给handler发送消息，在handler处理 
     * MyScrollView滑动的距离 
     */  
    @Override  
    public boolean onTouchEvent(MotionEvent ev) {  
        if(onScrollListener != null){  
            onScrollListener.onScroll(lastScrollY = this.getScrollY());  
        }  
        switch(ev.getAction()){  
        case MotionEvent.ACTION_UP:  
             handler.sendMessageDelayed(handler.obtainMessage(), 10);    
            break;  
        }  
        return super.onTouchEvent(ev);  
    }  
    /** 
     *  
     * 滚动的回调接口 
     *  
     * @author xiaanming 
     * 
     */  
    public interface OnScrollListener{  
        /** 
         * 回调方法， 返回MyScrollView滑动的Y方向距离 
         * @param scrollY 
         *              、 
         */  
        public void onScroll(int scrollY);  
    }  
}  