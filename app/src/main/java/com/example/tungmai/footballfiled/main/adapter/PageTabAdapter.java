package com.example.tungmai.footballfiled.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.tungmai.footballfiled.main.fragment.Tab1;
import com.example.tungmai.footballfiled.main.fragment.Tab2;
import com.example.tungmai.footballfiled.main.fragment.Tab3;
import com.example.tungmai.footballfiled.main.fragment.Tab4;

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
            case 3:
                Tab4 tab4 = new Tab4();
                return tab4;
        }
        return null;
    }

    @Override
    public int getCount() {
        return countTabs;
    }
}
