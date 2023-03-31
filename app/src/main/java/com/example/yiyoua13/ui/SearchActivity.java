package com.example.yiyoua13.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.yiyoua13.PlaceAdapter;
import com.example.yiyoua13.R;
import com.example.yiyoua13.variousclass.Place;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends Activity implements View.OnClickListener{
    private SearchView searchView;
    private ImageButton jdbtn1;
    private ImageButton jdbtn2;
    private ImageButton jdbtn3;
    private ImageButton jdbtn4;
    private ImageButton jdbtn5;
    private ImageButton jdbtn6;
    private ImageButton jdbtn7;
    private ImageButton jdbtn8;
    private Spinner spinner;
    private List<Place> placeList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
    }
    public void init(){
        searchView = findViewById(R.id.searchView);
        searchView.setIconified(false);//设置searchView处于展开状态
        searchView.onActionViewExpanded();// 当展开无输入内容的时候，没有关闭的图标
        searchView.setSubmitButtonEnabled(true);//显示提交按钮
        searchView.setQueryHint("请输入搜索内容");//设置提示信息
        searchView.clearFocus();//不获取焦点
        spinner = findViewById(R.id.spinner);
        jdbtn1 = (ImageButton) findViewById(R.id.imageButton1);
        jdbtn2 = (ImageButton) findViewById(R.id.imageButton2);
        jdbtn3 = (ImageButton) findViewById(R.id.imageButton3);
        jdbtn4 = (ImageButton) findViewById(R.id.imageButton4);
        jdbtn5 = (ImageButton) findViewById(R.id.imageButton5);
        jdbtn6 = (ImageButton) findViewById(R.id.imageButton6);
        jdbtn7 = (ImageButton) findViewById(R.id.imageButton7);
        jdbtn8 = (ImageButton) findViewById(R.id.imageButton8);
        jdbtn1.setOnClickListener(this);
        jdbtn2.setOnClickListener(this);
        jdbtn3.setOnClickListener(this);
        jdbtn4.setOnClickListener(this);
        jdbtn5.setOnClickListener(this);
        jdbtn6.setOnClickListener(this);
        jdbtn7.setOnClickListener(this);
        jdbtn8.setOnClickListener(this);
        //
        placeList.add(new Place("星海大桥",R.drawable.hog));
        placeList.add(new Place("达达利亚",R.drawable.lwr));
        placeList.add(new Place("默认图片",R.drawable.ic_launcher_background));
        placeList.add(new Place("还是默认",R.drawable.ic_launcher_background));
        placeList.add(new Place("缺省默认",R.drawable.ic_launcher_background));
        placeList.add(new Place("缺省默认",R.drawable.ic_launcher_background));
        placeList.add(new Place("缺省默认",R.drawable.ic_launcher_background));
        placeList.add(new Place("缺省默认",R.drawable.ic_launcher_background));
        placeList.add(new Place("缺省默认",R.drawable.ic_launcher_background));
        placeList.add(new Place("缺省默认",R.drawable.ic_launcher_background));
        placeList.add(new Place("缺省默认",R.drawable.ic_launcher_background));
        placeList.add(new Place("缺省默认",R.drawable.ic_launcher_background));
        placeList.add(new Place("缺省默认",R.drawable.ic_launcher_background));
        placeList.add(new Place("缺省默认",R.drawable.ic_launcher_background));
        placeList.add(new Place("缺省默认",R.drawable.ic_launcher_background));
        placeList.add(new Place("缺省默认",R.drawable.ic_launcher_background));
        PlaceAdapter adapter = new PlaceAdapter(placeList,this);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //搜索栏显示选择的内容
                searchView.setQuery(placeList.get(i).getName(),false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageButton1:
                searchView.setQuery("1",false);
                break;
            case R.id.imageButton2:
                searchView.setQuery("2",false);
                break;
            case R.id.imageButton3:
                searchView.setQuery("3",false);
                break;
            case R.id.imageButton4:
                searchView.setQuery("4",false);
                break;
            case R.id.imageButton5:
                searchView.setQuery("5",false);
                break;
            case R.id.imageButton6:
                searchView.setQuery("6",false);
                break;
            case R.id.imageButton7:
                searchView.setQuery("7",false);
                break;
            case R.id.imageButton8:
                searchView.setQuery("8",false);
                break;
        }

    }
}