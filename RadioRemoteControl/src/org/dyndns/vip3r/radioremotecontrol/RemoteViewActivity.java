package org.dyndns.vip3r.radioremotecontrol;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public class RemoteViewActivity extends Activity{

	String webiopi_url;
	int webiopi_port;
	String mjpgstream_url;
	int mjpgstream_port;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(new RemoteView(this));    
	}

	
	
}
