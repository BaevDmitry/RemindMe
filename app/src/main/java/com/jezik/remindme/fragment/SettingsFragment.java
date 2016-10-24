package com.jezik.remindme.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import com.jezik.remindme.R;

/**
 * Created by Дмитрий on 24.10.2016.
 */

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }
}
