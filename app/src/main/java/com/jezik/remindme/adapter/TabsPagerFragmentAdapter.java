package com.jezik.remindme.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jezik.remindme.fragment.AbstractTabFragment;
import com.jezik.remindme.fragment.BirthdaysFragment;
import com.jezik.remindme.fragment.HistoryFragment;
import com.jezik.remindme.fragment.IdeasFragment;
import com.jezik.remindme.fragment.TodoFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Дмитрий on 23.06.2016.
 */
public class TabsPagerFragmentAdapter extends FragmentPagerAdapter {

    private Map<Integer, AbstractTabFragment> tabs = new HashMap<>();


    public TabsPagerFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);

        tabs.put(0, HistoryFragment.newInstance(context));
        tabs.put(1, IdeasFragment.newInstance(context));
        tabs.put(2, TodoFragment.newInstance(context));
        tabs.put(3, BirthdaysFragment.newInstance(context));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }
}
