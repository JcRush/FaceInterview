package com.example.faceinterview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.MobSDK;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.wrapper.TokenVerifyResult;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etPhone;
    private EditText etCode;
    private Button btnSendCode;
    private Button btnLogin;
    private TimeCount time;
    private String phoneNumber;

    EventHandler eventHandler = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            // TODO 此处为子线程！不可直接处理UI线程！处理后续操作需传到主线程中操作！
            if (result == SMSSDK.RESULT_COMPLETE) {
                //成功回调
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交短信、语音验证码成功
                    toMainActivity();
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取短信验证码成功
                } else if (event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {
                    //获取语音验证码成功
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                }else if (event == SMSSDK.EVENT_GET_VERIFY_TOKEN_CODE) {
                    //本机验证获取token成功
                    TokenVerifyResult tokenVerifyResult = (TokenVerifyResult) data;
                }else if (event == SMSSDK.EVENT_VERIFY_LOGIN) {
                    //本机验证登陆成功
                }
            } else if (result == SMSSDK.RESULT_ERROR) {
                //失败回调
            } else {
                Toast.makeText(LoginActivity.this, getResources().
                                getString(R.string.code_error), Toast.LENGTH_SHORT).show();
                //其他失败回调
                ((Throwable) data).printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        MobSDK.submitPolicyGrantResult(true, null);
        //注册短信回调
        SMSSDK.registerEventHandler(eventHandler);

        //构造CountDownTimer对象
        time =  new  TimeCount( 60000 ,  1000 );
        //启动短信验证sdk
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.send_code:
                sendCode();
                if(!phoneNumber.isEmpty()) {
                    time.start();
                }
                break;
            case R.id.login:
                String code = etCode.getText().toString();
                SMSSDK.submitVerificationCode("86", phoneNumber, code);
                break;
            default:
                break;
        }
    }

    private void initView() {
        etPhone = findViewById(R.id.phone_number);
        etCode = findViewById(R.id.code);
        btnSendCode = findViewById(R.id.send_code);
        btnSendCode.setOnClickListener(this);
        btnLogin = findViewById(R.id.login);
        btnLogin.setOnClickListener(this);
        btnLogin.setEnabled(false);

        etCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                updateLoginUi();
                return false;
            }
        });
    }

    public void updateLoginUi() {
        btnLogin.setEnabled(true);
        btnLogin.setAlpha(1);
        btnLogin.setTextColor(getResources().getColor(R.color.white));
    }

    private void sendCode() {
        phoneNumber = etPhone.getText().toString();
        if (phoneNumber.isEmpty()) {
            Toast.makeText(LoginActivity.this, getResources().getString(R.string.phone_error),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        SMSSDK.getVerificationCode("86", phoneNumber);
    }

    private void toMainActivity() {
        SharedPreferences preferences = this.getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("login", true);
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    protected void onDestroy() {//销毁
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    /* 定义一个倒计时的内部类 */
    class  TimeCount  extends CountDownTimer {
        public  TimeCount( long  millisInFuture,  long  countDownInterval) {
            super (millisInFuture, countDownInterval); //参数依次为总时长,和计时的时间间隔
        }
        @Override
        public  void  onFinish() { //计时完毕时触发
            btnSendCode.setAlpha(1f);
            btnSendCode.setText( "重新验证" );
            btnSendCode.setTextColor(getResources().getColor(R.color.white));
            btnSendCode.setClickable( true );
        }
        @Override
        public  void  onTick( long  millisUntilFinished){ //计时过程显示
            btnSendCode.setAlpha(0.2f);
            btnSendCode.setTextColor(getResources().getColor(R.color.black));
            btnSendCode.setClickable( false );
            btnSendCode.setText(millisUntilFinished / 1000 + "秒" );
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}

