package com.example.yiyoua13;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class FragAct extends AppCompatActivity {
    private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_frag);
        //setupWindowAnimations();
        bt=(Button)findViewById(R.id.button_t);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FragAct.this,bottomnavi.class);
                //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(FragAct.this).toBundle());
                FragAct.this.startActivity(intent);
            }
        });
    }
    private void setupWindowAnimations() {
        Fade fade = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.activity_fade);
        getWindow().setEnterTransition(fade);
    }
}