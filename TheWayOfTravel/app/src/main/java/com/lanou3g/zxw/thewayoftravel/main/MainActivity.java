package com.lanou3g.zxw.thewayoftravel.main;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.lanou3g.zxw.thewayoftravel.R;
import com.lanou3g.zxw.thewayoftravel.base.BaseActivity;
import com.lanou3g.zxw.thewayoftravel.strategy.StrategyFragment;
import com.lanou3g.zxw.thewayoftravel.toolkit.ToolkitFragment;
import com.lanou3g.zxw.thewayoftravel.travelnotes.TravelFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {
    private TabLayout mainTabLayout;
    private ViewPager mainViewPager;
    private MainPagerAdapter mainPagerAdapter;
    private List<Fragment> fragments;

    @Override
    protected void initView() {
        mainTabLayout = bindView(R.id.main_tablayout);
        mainViewPager = bindView(R.id.main_viewpager);

    }

    @Override
    protected void initData() {
        fragments = new ArrayList<>();
        fragments.add(new TravelFragment());
        fragments.add(new StrategyFragment());
        fragments.add(new ToolkitFragment());
        //MainActivity的viewPager+tablayout
        //加载适配器
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mainPagerAdapter.setFragments(fragments);
        mainViewPager.setAdapter(mainPagerAdapter);
        mainTabLayout.setupWithViewPager(mainViewPager);
        //设定颜色
        int ColorBlue = getResources().getColor(R.color.colorSubject);
        mainTabLayout.setTabTextColors(Color.LTGRAY,ColorBlue);
        mainTabLayout.setSelectedTabIndicatorColor(ColorBlue);
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }
}
