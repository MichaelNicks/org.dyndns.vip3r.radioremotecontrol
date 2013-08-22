package org.dyndns.vip3r.radioremotecontrol;


import java.io.IOException;

import org.dyndns.vip3r.radioremotecontrol.preferences.NetworkPreferenceFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class RemoteView extends SurfaceView implements SurfaceHolder.Callback {

	private Context context;
	private Paint pJoystick;
	private Paint pJoypad;
	private Paint pMjpeg;
	private int height;
	private int width;
	private float eventX;
	private float eventY;
	private Joystick joy1;
	private Joystick joy2;
	private RemoteControlThread remoteThread;
	private MjpegInputStream mjpegIn;
	private boolean streamAvaliable = false;

	String mjpgstream_url;
	String mjpgstream_port;
//	private SensorActivity sensors;
    
	public void setSize() {
		height = getHeight();
		width = getWidth();
	}

	
	
	public RemoteView(Context context) {
		super(context);
		this.context = context;
		getHolder().addCallback(this);
		setFocusable(true);
		loadPreferences();
		//remoteThread = new RemoteControlThread(getHolder(), this, context);
		
		//Log.d("CV", "test");
		new Thread(new Runnable() {
			public void run() {
				mjpegIn = MjpegInputStream.read(mjpgstream_url +":"+ mjpgstream_port +"/?action=stream");
				//mjpegIn = MjpegInputStream.read("http://rasp-cam.fritz.box:8080/?action=stream");
				if(mjpegIn != null){
					streamAvaliable = true;
					Log.d("CV", "test");
				}
			}
			
		}).start();
		
		
//		sensors = new SensorActivity(this);
		
		pJoystick = new Paint();
		pJoypad = new Paint();
		pMjpeg = new Paint();
		pJoystick.setColor(Color.BLACK);
		pJoypad.setColor(Color.GREEN);
		remoteThread = new RemoteControlThread(getHolder(), this, context);
	}

	private void loadPreferences() {
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		if(sharedPref.getBoolean(NetworkPreferenceFragment.KEY_SAME_URL, true) == true)
		{
			mjpgstream_url = sharedPref.getString(NetworkPreferenceFragment.KEY_RASPBERRY_URL, "http://192.168.0.1");
		}
		else
		{
			mjpgstream_url = sharedPref.getString(NetworkPreferenceFragment.KEY_MJPGSTREAM_URL, "http://192.168.0.1");
		}
		mjpgstream_port = sharedPref.getString(NetworkPreferenceFragment.KEY_MJPGSTREAM_PORT, "8080");
		Log.d("CV", mjpgstream_url);
		Log.d("CV", mjpgstream_port);
		
		
	/*	SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
		webiopi_url = sharedPreferences.getString("webiopi_url", "-");
		webiopi_port = sharedPreferences.getInt("webiopi_port", 80);
		mjpgstream_url = sharedPreferences.getString("mjpegstream_url", "-");
		mjpgstream_port = sharedPreferences.getInt("mjpgstream_port", 8080);*/
	}
	
	protected void doDraw(Canvas canvas) throws IOException {

		canvas.drawColor(Color.GRAY);
		Rect mJpegDestRect = new Rect(0,0,width,height);
		if(streamAvaliable == true){
			Bitmap mjpeg = mjpegIn.readMjpegFrame();
			canvas.drawBitmap(mjpeg, null, mJpegDestRect, pMjpeg);
			
		}
			
		canvas.drawCircle(joy1.getX0(), joy1.getY0(), joy1.getRadius(), pJoypad);
		canvas.drawCircle(joy1.getX(), joy1.getY(), joy1.getRadius() / 10,
				pJoystick);

		canvas.drawCircle(joy2.getX0(), joy2.getY0(), joy2.getRadius(), pJoypad);
		canvas.drawCircle(joy2.getX(), joy2.getY(), joy2.getRadius() / 10,
				pJoystick);
		postInvalidate();

	}

	private int getIndex(MotionEvent event) {
		int idx = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
		return idx;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction() & MotionEvent.ACTION_MASK;
		eventX = 0;
		eventY = 0;

		switch (action) {
		case MotionEvent.ACTION_DOWN: {
			int id = event.getPointerId(getIndex(event));

			eventX = event.getX(id);
			eventY = event.getY(id);

			joy1.checkJoyDown(eventX, eventY, id);
			joy2.checkJoyDown(eventX, eventY, id);

//			Log.d("CV", "Pointer down [" + id + "] X=" + eventX + ", Y="
//					+ eventY + ", ID=" + joy1.getID() + ", ID=" + joy2.getID());
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			int touchCounter = event.getPointerCount();
			for (int t = 0; t < touchCounter; t++) {
				eventX = event.getX(t);
				eventY = event.getY(t);

				int id = event.getPointerId(t);

				joy1.checkJoyMove(eventX, eventY, id);
				joy2.checkJoyMove(eventX, eventY, id);

			}
			break;
		}
		case MotionEvent.ACTION_POINTER_DOWN: {
			int id = event.getPointerId(getIndex(event));
			eventX = event.getX(id);
			eventY = event.getY(id);
			joy1.checkJoyDown(eventX, eventY, id);
			joy2.checkJoyDown(eventX, eventY, id);

			break;
		}
		case MotionEvent.ACTION_POINTER_UP: {
			int id = event.getPointerId(getIndex(event));
			joy1.checkJoyUp(id);
			joy2.checkJoyUp(id);

			break;
		}
		case MotionEvent.ACTION_UP: {
			int id = event.getPointerId(getIndex(event));

			joy1.checkJoyUp(id);
			joy2.checkJoyUp(id);

			break;
		}
		}
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		setSize();
		
		
		Log.d("CV", height + " " + width);
		joy1 = new Joystick(10, height - height / 3 - 10, width, height);
		joy2 = new Joystick(width - (height / 3) - 10,
				height - height / 3 - 10, width, height);
		remoteThread.setJoystick(joy1, joy2);
		remoteThread.setRunnable(true);
		remoteThread.start();

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		remoteThread.setRunnable(false);

		while (retry) {

			try {

				remoteThread.join();
				retry = false;

			} catch (InterruptedException ie) {

				// Try again and again and again
			}

			break;
		}

		remoteThread = null;

	}
	
	

}
