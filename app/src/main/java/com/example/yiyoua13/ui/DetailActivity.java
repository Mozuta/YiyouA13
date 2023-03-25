package com.example.yiyoua13.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.yiyoua13.R;

public class DetailActivity extends AppCompatActivity {
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
    }
    public void init(){
        title = findViewById(R.id.namein);
        title.setText(getIntent().getStringExtra("person_data"));

    }
}