package com.example.faceinterview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.wrapper.TokenVerifyResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void getCode(){
        EventHandler eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                // TODO 此处为子线程！不可直接处理UI线程！处理后续操作需传到主线程中操作！
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //成功回调
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交短信、语音验证码成功
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取短信验证码成功
                    } else if (event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {
                        //获取语音验证码成功
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }else if (event == SMSSDK.EVENT_GET_VERIFY_TOKEN_CODE) {
                        //本机验证获取token成功
                        TokenVerifyResult tokenVerifyResult = (TokenVerifyResult) data;
                        //SMSSDK.login(phoneNum,tokenVerifyResult);
                    }else if (event == SMSSDK.EVENT_VERIFY_LOGIN) {
                        //本机验证登陆成功
                    }
                } else if (result == SMSSDK.RESULT_ERROR) {
                    //失败回调
                } else {
                    //其他失败回调
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }
}