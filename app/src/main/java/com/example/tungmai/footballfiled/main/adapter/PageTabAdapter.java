package com.example.tungmai.footballfiled.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.tungmai.footballfiled.main.tablayout.Tab1;
import com.example.tungmai.footballfiled.main.tablayout.Tab2;
import com.example.tungmai.footballfiled.main.tablayout.Tab3;

/**
 * Created by tungmai on 08/01/2017.
 */

public class PageTabAdapter extends FragmentStatePagerAdapter {
    private int countTabs;


    public PageTabAdapter(FragmentManager fm, int countTabs) {
        super(fm);
        this.countTabs = countTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Tab1 tab1 = new Tab1();
                return tab1;
            case 1:
                Tab2 tab2 = new Tab2();
                return tab2;
            case 2:
                Tab3 tab3 = new Tab3();
                return tab3;
        }
        return null;
    }

    @Override
    public int getCount() {
        return countTabs;
    }
}
