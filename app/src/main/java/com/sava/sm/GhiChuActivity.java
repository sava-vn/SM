package com.sava.sm;


import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.sava.sm.adapter.TabGhiChuAdapter;
import com.sava.sm.extras.SlidingTabLayout;

public class GhiChuActivity extends AppCompatActivity {
    private Toolbar mtoolbar;
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghichu);
        initToobar();
        initTabs();
    }

    private void initToobar() {
        mtoolbar = findViewById(R.id.tb_note);
        mtoolbar.setTitle("Ghi chú");
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initTabs() {
        mViewPager = findViewById(R.id.vp_tabs);
        mViewPager.setAdapter(new TabGhiChuAdapter(getSupportFragmentManager(), this));
        mSlidingTabLayout = findViewById(R.id.stl_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.primary));
        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.accent));
        mSlidingTabLayout.setCustomTabView(R.layout.tab_view_ghichu, R.id.tv_tab);
        mSlidingTabLayout.setViewPager(mViewPager);
    }
}
