package com.example.looerpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ViewPager mViewPager;
    private List<Integer> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        mData.add(R.mipmap.pic1);
        mData.add(R.mipmap.pic2);
        mData.add(R.mipmap.pic3);
        mData.add(R.mipmap.pic4);
        mPagerAdapter.notifyDataSetChanged();
        //设置中间位置
        mViewPager.setCurrentItem(Integer.MAX_VALUE / 2 + 1);
    }

    private void initView() {
        mViewPager = this.findViewById(R.id.view_pager);
        mViewPager.setAdapter(mPagerAdapter);
    }

    private PagerAdapter mPagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
//            Log.d(TAG, "Integer.MAX_VALUE-->"+Integer.MAX_VALUE);
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View item = LayoutInflater.from(container.getContext()).inflate(R.layout.item_pager, container, false);
            ImageView iv = item.findViewById(R.id.cover);
            //设置数据
            int realPosition = position % mData.size();
            iv.setImageResource(mData.get(realPosition));
            if (iv.getParent() instanceof ViewGroup) {
                ((ViewGroup) iv.getParent()).removeView(iv);
            }
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    };
}