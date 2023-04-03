package com.example.yiyoua13;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.Window;

import com.example.yiyoua13.ui.InterestActivity;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class MainActivity extends Activity {

    @SuppressLint("HandlerLeak")
    private final Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            //Intent intent=new Intent(String.valueOf(ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle()));//实际是页面跳转
            Intent intent= new Intent(MainActivity.this, FragAct.class);
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
        /*EasyPermissions.requestPermissions(
                new PermissionRequest.Builder(this, RC_CAMERA_AND_LOCATION, perms)
                        .setRationale(R.string.camera_and_location_rationale)
                        .setPositiveButtonText(R.string.rationale_ask_ok)
                        .setNegativeButtonText(R.string.rationale_ask_cancel)
                        .setTheme(R.style.my_fancy_style)
                        .build());*/
        setupWindowAnimations();
        handler.sendEmptyMessageDelayed(0,1000);//4000毫秒


    }
}