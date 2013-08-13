package org.dyndns.vip3r.radioremotecontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class RadioRemoteControlActivity extends Activity implements OnClickListener{

	private Button bStart;
	private Button bSettings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);	
//		setContentView(new RemoteView(this));
	    setContentView(R.layout.radioremotecontrol);
	   /* WebView myWebView = (WebView) findViewById(R.id.webview);
	    myWebView.getSettings().setJavaScriptEnabled(true);
	    myWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN); 
	    myWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
	    myWebView.loadUrl("http://192.168.123.16:8080/javascript_simple.html");
	    */
	    bStart = (Button) findViewById(R.id.bStart);
	    bSettings = (Button) findViewById(R.id.bSettings);
	    bStart.setOnClickListener(this);
	    bSettings.setOnClickListener(this);
	    
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) { 
		switch (item.getItemId()) { 
			case R.id.menu_settings: 
				startSettings(); 
				return true; 
			default: 
				return super.onOptionsItemSelected(item); 
		}
	}
	
	private void startSettings() {
		Intent intent = new Intent();
		intent.setClass(RadioRemoteControlActivity.this, SettingsActivity.class);
		startActivity(intent);
		}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.bStart:
			Intent newStartScreen = new Intent(this, RemoteViewActivity.class );
			startActivity(newStartScreen);
			this.finish();
			break;
		case R.id.bSettings:
			Intent newSettingsScreen = new Intent(this, SettingsActivity.class );
			startActivity(newSettingsScreen);
			this.finish();
			break;
		}
		
	}

	
	

}
