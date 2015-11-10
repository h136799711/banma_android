package com.itboye.banma.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义view，绘制两层drawable，上层为前一个view的图片，下层为下一个view的图片
 * 每次滑动，将上层图片根据其所占屏幕的比例设置透明度来达到一种简便的效果
 * @author Administrator
 * @date 2013/1/29 17:52
 */
public class DrawableChangeView extends View  {

	private int mPosition;
    private int mPrevPosition;
    private float mDegree;
    
    private Drawable[] mDrawables;
    private Drawable mBack;
    
    public DrawableChangeView(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public DrawableChangeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    public DrawableChangeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setWillNotDraw(false);
    }

    public void setDrawables(Drawable[] drawables) {
        mDrawables = drawables;
        mBack = drawables[1];
    }
    
    public void setPosition(int position) {
        mPosition = position;
    }
    
    public void setDegree(float degree) {
        mDegree = degree;
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        if (mDrawables == null) {
            return;
        }
        int alpha = 255 - (int) (mDegree * 255);
        Drawable fore = mDrawables[mPosition];
        fore.setBounds(0, 0, getWidth(), getHeight());
        mBack.setBounds(0, 0, getWidth(), getHeight());
        if (mPrevPosition != mPosition) {
            if (mPosition != mDrawables.length - 1) {
                mBack = mDrawables[mPosition + 1];
            } else {
                mBack = mDrawables[mPosition];
            }
        }
        
        fore.setAlpha(alpha);
        mBack.setAlpha(255);
        mBack.draw(canvas);
        fore.draw(canvas);
        mPrevPosition = mPosition;
    }
}
