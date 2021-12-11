package com.example.faceinterview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.faceinterview.fragment.ChatFragment;
import com.example.faceinterview.fragment.CommunityFragment;
import com.example.faceinterview.fragment.HomeFragment;
import com.example.faceinterview.fragment.MineFragment;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private HomeFragment homeFragment;
    private CommunityFragment communityFragment;
    private ChatFragment chatFragment;
    private MineFragment mineFragment;
    private Fragment currentFragment;

    private RelativeLayout homeRl;
    private RelativeLayout communityRl;
    private RelativeLayout chatRl;
    private RelativeLayout mineRl;

    private ImageView homeIv;
    private ImageView communityIv;
    private ImageView chatIv;
    private ImageView mineIv;

    private TextView homeTv;
    private TextView communityTv;
    private TextView chatTv;
    private TextView mineTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initTab();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_home: // 知道
                homeSelected();
                break;
            case R.id.rl_community: // 我想知道
                communitySelected();
                break;
            case R.id.rl_chat: // 我的
                chatSelected();
                break;
            case R.id.rl_mine: // 我的
                mineSelected();
                break;
            default:
                break;
        }
    }

    /**
     * 初始化
     */
    private void initView() {
        homeRl = findViewById(R.id.rl_home);
        communityRl = findViewById(R.id.rl_community);
        chatRl = findViewById(R.id.rl_chat);
        mineRl = findViewById(R.id.rl_mine);

        homeRl.setOnClickListener(this);
        communityRl.setOnClickListener(this);
        chatRl.setOnClickListener(this);
        mineRl.setOnClickListener(this);

        homeIv = findViewById(R.id.iv_home);
        communityIv = findViewById(R.id.iv_community);
        chatIv = findViewById(R.id.iv_chat);
        mineIv = findViewById(R.id.iv_mine);

        homeTv = findViewById(R.id.tv_home);
        communityTv = findViewById(R.id.tv_community);
        chatTv = findViewById(R.id.tv_chat);
        mineTv = findViewById(R.id.tv_mine);
    }

    /**
     * 初始化底部导航栏
     */
    private void initTab() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }

        if (!homeFragment.isAdded()) {
            //提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_layout, homeFragment).commit();

            currentFragment = homeFragment;

            //更新UI
            tabSelected(1);
        }
    }

    /**
     * 更新底部导航栏
     * @param index
     */
    private void tabSelected(int index) {
        if (index == 1){
            homeIv.setImageResource(R.mipmap.first_yes);
            homeTv.setTextColor(getResources().getColor(R.color.blue));
        } else {
            homeIv.setImageResource(R.mipmap.first_no);
            homeTv.setTextColor(getResources().getColor(R.color.black));
        }

        if (index == 2){
            communityIv.setImageResource(R.mipmap.community_yes);
            communityTv.setTextColor(getResources().getColor(R.color.blue));
        } else {
            communityIv.setImageResource(R.mipmap.community_no);
            communityTv.setTextColor(getResources().getColor(R.color.black));
        }

        if (index == 3){
            chatIv.setImageResource(R.mipmap.chat_yes);
            chatTv.setTextColor(getResources().getColor(R.color.blue));
        } else {
            chatIv.setImageResource(R.mipmap.chat_no);
            chatTv.setTextColor(getResources().getColor(R.color.black));
        }

        if (index == 4){
            mineIv.setImageResource(R.mipmap.mine_yes);
            mineTv.setTextColor(getResources().getColor(R.color.blue));
        } else {
            mineIv.setImageResource(R.mipmap.mine_no);
            mineTv.setTextColor(getResources().getColor(R.color.black));
        }
    }

    /**
     * 点击首页
     */
    private void homeSelected() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), homeFragment);
        tabSelected(1);
    }

    /**
     * 点击社区
     */
    private void communitySelected() {
        if (communityFragment == null) {
            communityFragment = new CommunityFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), communityFragment);
        tabSelected(2);
    }

    /**
     * 点击聊天
     */
    private void chatSelected() {
        if(chatFragment == null) {
            chatFragment = new ChatFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), chatFragment);
        tabSelected(3);
    }

    /**
     * 点击我的
     */
    private void mineSelected() {
        if(mineFragment == null) {
            mineFragment = new MineFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), mineFragment);
        tabSelected(4);
    }

    /**
     * 添加或者显示碎片
     *
     * @param transaction
     * @param fragment
     */
    private void addOrShowFragment(FragmentTransaction transaction,
                                   Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment)
                    .add(R.id.content_layout, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }

        currentFragment = fragment;
    }


}