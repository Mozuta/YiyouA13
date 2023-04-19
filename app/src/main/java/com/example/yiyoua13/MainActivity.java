package com.example.yiyoua13;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Window;

import com.example.yiyoua13.ui.InterestActivity;
import com.example.yiyoua13.ui.Url_Request;
import com.example.yiyoua13.utils.StatusBarUtil;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class MainActivity extends Activity {
    private static final int DELAY_TIME = 1000;
    private SharedPreferences sp;
    String user_id,user_token;

    @SuppressLint("HandlerLeak")
    private final Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            //Intent intent=new Intent(String.valueOf(ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle()));//实际是页面跳转
            Intent intent= new Intent(MainActivity.this, bottomnavi.class);
            MainActivity.this.startActivity(intent);
            //startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());

            finishAfterTransition();
            //finish();//销毁welcome页面。
            super.handleMessage(msg);

        }
    };



    private void setupWindowAnimations() {
        Fade fade = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.activity_fade);
        Slide slide = (Slide) TransitionInflater.from(this).inflateTransition(R.transition.activity_slide);
        getWindow().setExitTransition(fade);
        getWindow().setReenterTransition(slide);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this,0x55000000);
        }
        sp = this.getSharedPreferences("Login", MODE_PRIVATE);
        //user_token=TOKEN
        user_token=sp.getString("TOKEN","");
        user_id=sp.getString("USER_ID","");
        if(user_token.equals("")){

        }else{
            Url_Request.sendRequestUserMe(Url_Request.getUrl_head()+"/user/me",user_token, MainActivity.this, new Url_Request.OnIconResponseListener() {
                @Override
                public void onBeanResponse(Object bean) {
                    Log.e("获取用户信息","userme");
                }
            });
        }

        Url_Request.sendRequestUserCheck(Url_Request.getUrl_head() + "/user/check", user_token, new Url_Request.OnIconResponseListener() {
            @Override
            public void onBeanResponse(Object bean) {
                Boolean isLogin = (Boolean) bean;
                if (isLogin) {

                } else {
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putString("TOKEN","");
                    editor.putString("USER_ID","");
                    editor.apply();
                }
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 延时操作结束后，使用 Intent 跳转到应用的主界面
                Intent intent = new Intent(MainActivity.this, bottomnavi.class);
                startActivity(intent);
                finish();
            }
        }, DELAY_TIME);


    }
}