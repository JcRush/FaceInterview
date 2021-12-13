package com.example.faceinterview.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;

import com.example.faceinterview.R;
import com.example.faceinterview.adapter.TeaNetPageAdapter;

import java.util.ArrayList;

public class CarouselShow {
    private final Context context;
    private LinearLayout point_group;
    private ArrayList<ImageView> viewpage_imageList;
    protected int lastPosition = 0;
    private ViewPager ViewPage_Detail;
    private LinearLayout point_detail;

    public CarouselShow(Context context, ArrayList<ImageView> viewpage_imageList) {
        this.context = context;
        this.viewpage_imageList = viewpage_imageList;
    }

    /**
     * 当需要多个轮播功能的时候 建立一个类来调用 并实现此方法
     */
    public void CarouselShow_Info_Detail(View view){

        ViewPage_Detail = (ViewPager)view.findViewById(R.id.ViewPage_Detail);
        point_detail = (LinearLayout) view.findViewById(R.id.point_detail);
        ViewPage_Detail.setAdapter(new TeaNetPageAdapter(viewpage_imageList));

        // 设置当前viewPager的位置
        ViewPage_Detail.setCurrentItem(Integer.MAX_VALUE / 2
                - (Integer.MAX_VALUE / 2 % viewpage_imageList.size()));

        ViewPage_Detail.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // 页面切换后调用， position是新的页面位置
                // 实现无限制循环播放
                position %= viewpage_imageList.size();

                // 把当前点设置为true,将上一个点设为false；并设置point_group图标
                point_detail.getChildAt(position).setEnabled(true);
                //设置聚焦时的图标样式
                point_detail.getChildAt(position).setBackgroundResource(R.mipmap.focus_point);
                point_detail.getChildAt(lastPosition).setEnabled(false);
                //上一张恢复原有图标
                point_detail.getChildAt(lastPosition).setBackgroundResource(R.mipmap.no_focus_point);
                lastPosition = position;
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
