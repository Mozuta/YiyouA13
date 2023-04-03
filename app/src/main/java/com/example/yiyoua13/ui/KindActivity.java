package com.example.yiyoua13.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yiyoua13.AppFragmentPageAdapter;
import com.example.yiyoua13.R;
import com.example.yiyoua13.ui.Kind.DistanceFragment;
import com.example.yiyoua13.ui.Kind.StarsFragment;
import com.example.yiyoua13.ui.Kind.TotalFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KindActivity extends AppCompatActivity implements View.OnClickListener{
    private ViewPager mViewPager;
    private ImageView back;
    private TextView kind;
    private List<String> mTitleDataList = Arrays.asList("综合排序", "距离排序","好评排序");
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kind);
        back=findViewById(R.id.K_back);
        kind=findViewById(R.id.K_name);
        back.setOnClickListener(this);
        kind.setText(getIntent().getStringExtra("name"));
        mViewPager = (ViewPager) findViewById(R.id.K_viewpager);
        fragmentList=new ArrayList<Fragment>();
        fragmentList.add(new TotalFragment());
        fragmentList.add(new DistanceFragment());
        fragmentList.add(new StarsFragment());
        mViewPager.setAdapter(new AppFragmentPageAdapter(getSupportFragmentManager(),fragmentList));
        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.K_magic_indicator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitleDataList == null ? 0 : mTitleDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
                colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
                colorTransitionPagerTitleView.setTextSize(20);
                //填充
                colorTransitionPagerTitleView.setGravity(View.TEXT_ALIGNMENT_GRAVITY);
                //padding
                colorTransitionPagerTitleView.setText(mTitleDataList.get(index));
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                //padding
                indicator.setMode(LinePagerIndicator.MODE_MATCH_EDGE);
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.K_back:
                finish();
                break;
        }
    }
}