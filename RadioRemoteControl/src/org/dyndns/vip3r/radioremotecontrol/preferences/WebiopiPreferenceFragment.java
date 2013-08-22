package org.dyndns.vip3r.radioremotecontrol.preferences;

import org.dyndns.vip3r.radioremotecontrol.R;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class WebiopiPreferenceFragment extends PreferenceFragment{
	@Override
	 public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	     addPreferencesFromResource(R.xml.preference_webiopi);
	 }
}
