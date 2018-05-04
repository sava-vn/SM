package com.sava.sm.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sava.sm.Fragment.Frag.Note.NoteFragment;
import com.sava.sm.Fragment.Frag.Note.ReminderFragment;


public class TabGhiChuAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private String[] titles ={"NOTE","REMINDER"};
    public TabGhiChuAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new NoteFragment();
                break;
            case 1:
                fragment = new ReminderFragment();
                break;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
