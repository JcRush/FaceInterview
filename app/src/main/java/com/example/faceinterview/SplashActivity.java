package com.example.faceinterview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

public class SplashActivity extends AppCompatActivity {

    //跳过按钮
    private Button btnSkip;
    private final Handler handler = new Handler();
    private Runnable runnableToLogin = new Runnable() {
        @Override
        public void run() {
            toMainActivity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
        initEvent();

        //延迟4秒
        handler.postDelayed(runnableToLogin,4000);

    }

    //初始化组件
    public void initView(){
        btnSkip = findViewById(R.id.splash_btn_skip);
    }

    //监听事件
    public void initEvent() {
        btnSkip.setOnClickListener(v -> {
            //防止LoginActivity被打开两次
            handler.removeCallbacks(runnableToLogin);
            toMainActivity();
        });
    }
    /**
     * 跳转到登录界面
     */
    private void toMainActivity(){
        Intent intent;
        if(isLogin()){
            intent = new Intent(this, MainActivity.class);
        }else {
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }

    /**
     * 读取SP判断是否已经登陆
     */
    private boolean isLogin(){
        SharedPreferences preferences = this.getSharedPreferences("login", MODE_PRIVATE);
        boolean login = preferences.getBoolean("login", false);
        return login;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //防止内存泄漏
        handler.removeCallbacks(runnableToLogin);
    }

}