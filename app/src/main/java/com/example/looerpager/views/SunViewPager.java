package com.example.looerpager.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class SunViewPager extends ViewPager {
    private static final String TAG = "SunViewPager";
    //默认切换时间
    public static final int DEFAULT_SWITCH_TIME=2000;
    private long delayTime = DEFAULT_SWITCH_TIME;
    private OnPagerItemClickListener mItemClickListener = null;

    public SunViewPager(@NonNull Context context) {
        this(context, null);
    }

    private boolean isClick = false;
    private float downX;
    private float downY;
    private long downTime;

    public SunViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getX();
                        downY = event.getY();
                        downTime = System.currentTimeMillis();
                        stopLooper();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        float dx = Math.abs(event.getX() - downX);
                        float dy = Math.abs(event.getY() - downY);
                        long dTime = System.currentTimeMillis() - downTime;
                        isClick = dy <= 5 && dx <= 5 && dTime <= 1000;
                        if (isClick&&mItemClickListener!=null)
                        {
                            mItemClickListener.onItemClick(getCurrentItem());
                        }
                        Log.d(TAG, "is click -->" + isClick);
                        startLooper();
                        break;

                }
                return false;
            }
        });
    }
    public void setPagerItemClickListener(OnPagerItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public interface OnPagerItemClickListener {
        void onItemClick(int position);
    }

    //外部传入DelayTime
    public void setDelayTime(long delayTime) {
        this.delayTime = delayTime;
    }

    //轮播循环
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "onAttachedToWindow...");
        startLooper();
    }

    private void startLooper() {
        removeCallbacks(mTask);
        postDelayed(mTask, delayTime);
    }

    private Runnable mTask = new Runnable() {
        @Override
        public void run() {
            int currentItem = getCurrentItem();
            currentItem++;
            setCurrentItem(currentItem);
            postDelayed(this, delayTime);
        }
    };

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetachedFromWindow...");
        stopLooper();
    }

    private void stopLooper() {
        removeCallbacks(mTask);
    }
}
