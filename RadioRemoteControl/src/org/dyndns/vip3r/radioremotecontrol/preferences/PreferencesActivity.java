package org.dyndns.vip3r.radioremotecontrol.preferences;

import java.util.List;

import org.dyndns.vip3r.radioremotecontrol.R;

import android.preference.PreferenceActivity;

public class PreferencesActivity extends PreferenceActivity {

    /**
     * Populate the activity with the top-level headers.
     */
    @Override
    public void onBuildHeaders(List<Header> target) {
     loadHeadersFromResource(R.xml.pref_headers, target);
    }
}