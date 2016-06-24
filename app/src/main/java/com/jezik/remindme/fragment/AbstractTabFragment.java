package com.jezik.remindme.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by Дмитрий on 24.06.2016.
 */
public abstract class AbstractTabFragment extends Fragment {

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
