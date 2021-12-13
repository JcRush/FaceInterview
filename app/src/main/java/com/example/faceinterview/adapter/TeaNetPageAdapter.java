package com.example.faceinterview.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class TeaNetPageAdapter extends PagerAdapter {
    private ArrayList<ImageView> viewpage_imageList;

    public TeaNetPageAdapter(ArrayList<ImageView>  viewpage_imageList) {

        this.viewpage_imageList = viewpage_imageList;

    }
    // 需要实现以下四个方法

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        // 判断view和Object对应是否有关联关系
        if (view == object) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // 获得相应位置上的view； container view的容器，其实就是viewpage自身,
        // position: viewpager上的位置
        // 给container添加内容
        container.addView(viewpage_imageList.get(position % viewpage_imageList.size()));

        return viewpage_imageList.get(position % viewpage_imageList.size());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 销毁对应位置上的Object
        // super.destroyItem(container, position, object);
        container.removeView((View) object);
        object = null;
    }
}
