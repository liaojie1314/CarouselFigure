package com.example.looerpager;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.looerpager.views.PagerItem;
import com.example.looerpager.views.SunLooperPager;
import java.util.ArrayList;
import java.util.List;

public class SuperMainActivity extends AppCompatActivity {

    private SunLooperPager mLooperPager;
    private List<PagerItem> mData = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_main);
        initData();
        initView();
        initEvent();
    }

    private void initEvent() {
        if (mLooperPager != null) {
            mLooperPager.setOnItemClickListener(new SunLooperPager.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(SuperMainActivity.this, "点击了第" + (position + 1) + "个item", Toast.LENGTH_SHORT).show();
                    //todo:根据交互业务实现具体逻辑
                }
            });
        }
    }

    private void initData() {
        mData.add(new PagerItem("第一张图片", R.mipmap.pic1));
        mData.add(new PagerItem("第2张图片", R.mipmap.pic2));
        mData.add(new PagerItem("第三张图片", R.mipmap.pic3));
        mData.add(new PagerItem("第4张图片", R.mipmap.pic4));
    }

    private SunLooperPager.InnerAdapter mInnerAdapter = new SunLooperPager.InnerAdapter() {
        @Override
        protected int getDataSize() {
            return mData.size();
        }

        @Override
        protected View getSunView(ViewGroup container, int position) {
            ImageView iv = new ImageView(container.getContext());
            iv.setImageResource(mData.get(position).getPicResId());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            return iv;
        }
    };

    private void initView() {
        mLooperPager = this.findViewById(R.id.sun_looper_pager);
        mLooperPager.setData(mInnerAdapter, new SunLooperPager.BindTitleListener() {
            @Override
            public String getTitle(int position) {
                return mData.get(position).getTitle();
            }
        });
    }
}
