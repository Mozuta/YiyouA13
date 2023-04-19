package com.example.yiyoua13.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yiyoua13.AppFragmentPageAdapter;
import com.example.yiyoua13.R;
import com.example.yiyoua13.ui.ff.FansFragment;
import com.example.yiyoua13.ui.ff.FocusFragment;
import com.example.yiyoua13.utils.StatusBarUtil;

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

public class FansFollowActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TextView ff_name;
    public List<String> mTitleDataList ;
    private List<Fragment> fragmentList;
    private ImageView ff_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans_follow);
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this,0x55000000);
        }
        mViewPager = (ViewPager) findViewById(R.id.ff_viewpager);
        ff_name=findViewById(R.id.ff_name);
        ff_back=findViewById(R.id.ff_back);
        ff_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        ff_name.setText(name);
        mTitleDataList=new ArrayList<String>();
        mTitleDataList.add("关注 "+intent.getStringExtra("follow_num"));
        mTitleDataList.add("粉丝 "+intent.getStringExtra("fans_num"));
        fragmentList=new ArrayList<Fragment>();
        fragmentList.add(new FocusFragment());
        fragmentList.add(new FansFragment());
        mViewPager.setAdapter(new AppFragmentPageAdapter(getSupportFragmentManager(),fragmentList));


        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        //字体大小



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
}