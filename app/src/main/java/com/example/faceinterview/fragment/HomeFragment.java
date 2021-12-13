package com.example.faceinterview.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.faceinterview.R;
import com.example.faceinterview.view.CarouselShow;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class HomeFragment extends Fragment {

    private ViewPager ViewPage_Detail;
    private LinearLayout point_detail;
    private View view;
    //轮播图图片资源
    private final int[] viewpage_images = {R.mipmap.banner_1, R.mipmap.banner_2, R.mipmap.banner_3};
    private ArrayList<ImageView> viewpage_imageList;
    //判断是否自动滚动ViewPage
    private boolean isRunning = true;

    public HomeFragment() {
        // Required empty public constructor
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // 执行滑动到下一个页面
            ViewPage_Detail.setCurrentItem(ViewPage_Detail.getCurrentItem() + 1);
            if (isRunning) {
                // 在发一个handler延时
                handler.sendEmptyMessageDelayed(0, 3000);
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Instantiation();
        initImgUi();
    }

    public void Instantiation() {

        ViewPage_Detail = (ViewPager)getActivity().findViewById(R.id.ViewPage_Detail);
        point_detail = (LinearLayout)getActivity().findViewById(R.id.point_detail);

        //初始化图片资源
        viewpage_imageList = new ArrayList<ImageView> ();
        for (int i : viewpage_images) {
            // 初始化图片资源
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(i);
            viewpage_imageList.add(imageView);

            // 添加指示小点
            ImageView point = new ImageView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(25, 25);
            params.rightMargin = 10;
            params.bottomMargin = 15;
            point.setLayoutParams(params);
            point.setBackgroundResource(R.mipmap.no_focus_point);
            if (i == R.mipmap.banner_1) {
                //默认聚焦在第一张
                point.setBackgroundResource(R.mipmap.focus_point);
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
            }

            point_detail.addView(point);
        }

        //首页轮播
        CarouselShow carouselShow = new CarouselShow(getActivity(), viewpage_imageList);
        carouselShow.CarouselShow_Info_Detail(getView());
        handler.sendEmptyMessageDelayed(0, 3000);

    }

    private void initImgUi() {
        //圆形图
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.interviewer1);
        RoundedBitmapDrawable circleDrawable1 = RoundedBitmapDrawableFactory.create(getResources(), BitmapFactory.decodeResource(getResources(), R.mipmap.interviewer1));
        circleDrawable1.getPaint().setAntiAlias(true);
        circleDrawable1.setCornerRadius(Math.min(bitmap1.getWidth(), bitmap1.getHeight()));
        ImageView iv1 = (ImageView)getActivity().findViewById(R.id.iv_interviewer1);
        iv1.setImageDrawable(circleDrawable1);

        //圆形图
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.interviewer2);
        RoundedBitmapDrawable circleDrawable2 = RoundedBitmapDrawableFactory.create(getResources(), BitmapFactory.decodeResource(getResources(),R.mipmap.interviewer2));
        circleDrawable2.getPaint().setAntiAlias(true);
        circleDrawable2.setCornerRadius(Math.min(bitmap2.getWidth(), bitmap2.getHeight()));
        ImageView iv2 = (ImageView)getActivity().findViewById(R.id.iv_interviewer2);
        iv2.setImageDrawable(circleDrawable2);

        //圆形图
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.mipmap.interviewer3);
        RoundedBitmapDrawable circleDrawable3 = RoundedBitmapDrawableFactory.create(getResources(), BitmapFactory.decodeResource(getResources(),R.mipmap.interviewer3));
        circleDrawable3.getPaint().setAntiAlias(true);
        circleDrawable3.setCornerRadius(Math.min(bitmap3.getWidth(), bitmap3.getHeight()));
        ImageView iv3 = (ImageView)getActivity().findViewById(R.id.iv_interviewer3);
        iv3.setImageDrawable(circleDrawable3);

        //加载gif
        ImageView ivClass1 = (ImageView)getActivity().findViewById(R.id.iv_class1);
        String gif1 = "https://media.giphy.com/media/ounv1hey86r5DM6WhP/giphy.gif";
        //Glide.with(getView().getContext()).load(gif1).into(ivClass1);
        ImageView ivClass2 = (ImageView)getActivity().findViewById(R.id.iv_class2);
        String gif2 = "https://media.giphy.com/media/l2JehgpneMlOjaewE/giphy.gif";
        //Glide.with(getView().getContext()).load(gif2).into(ivClass2);
    }
}