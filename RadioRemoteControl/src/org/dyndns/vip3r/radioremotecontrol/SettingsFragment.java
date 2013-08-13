package org.dyndns.vip3r.radioremotecontrol;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class SettingsFragment extends PreferenceFragment {
	
	public static final String KEY_SAME_URL = "same_url";
	public static final String KEY_RASPBERRY_URL = "raspberry_url";
	public static final String KEY_WEBIOPI_URL = "webiopi_url";
	public static final String KEY_MJPGSTREAM_URL = "mjpgstream_url";
	public static final String KEY_WEBIOPI_PORT = "webiopi_port";
	public static final String KEY_MJPGSTREAM_PORT = "mjpgstream_port";
	
	@Override 
	public void onCreate(Bundle savedInstanceState) { 
	super.onCreate(savedInstanceState); 
	addPreferencesFromResource(R.xml.preferences); 
	}
}
