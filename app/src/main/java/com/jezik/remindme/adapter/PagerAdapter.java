package com.jezik.remindme.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jezik.remindme.Constants;
import com.jezik.remindme.R;
import com.jezik.remindme.fragment.BirthdaysFragment;
import com.jezik.remindme.fragment.HistoryFragment;
import com.jezik.remindme.fragment.IdeasFragment;
import com.jezik.remindme.fragment.TodoFragment;

/**
 * Created by Дмитрий on 23.06.2016.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    private String[] tabs;

    public PagerAdapter(Context context, FragmentManager fm) {
        super(fm);

        tabs = new String[] {
                context.getString(R.string.tab_item_history),
                context.getString(R.string.tab_item_ideas),
                context.getString(R.string.tab_item_todo),
                context.getString(R.string.tab_item_birthdays),
        };
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position){
            case Constants.TAB_HISTORY:
                fragment = new HistoryFragment();
                break;
            case Constants.TAB_IDEAS:
                fragment = new IdeasFragment();
                break;
            case Constants.TAB_TODO:
                fragment = new TodoFragment();
                break;
            case Constants.TAB_BIRTHDAYS:
                fragment = new BirthdaysFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }
}
