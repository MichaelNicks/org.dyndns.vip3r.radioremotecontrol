package org.dyndns.vip3r.radioremotecontrol;

import java.io.IOException;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class RemoteControlThread extends Thread {

	private Joystick joy1 = null;
	private Joystick joy2 = null;
	private Servo servo1 = null;
	private Servo servo2 = null;
	private WebIoPiPwm pwm = null;
	private SurfaceHolder holder;
	private RemoteView remoteView;

	private Canvas canvas;

	private boolean run = false;

	public RemoteControlThread(SurfaceHolder holder, RemoteView remoteView) {
		this.remoteView = remoteView;
		this.holder = holder;
	}

	public void setJoystick(Joystick joy1, Joystick joy2) {
		this.joy1 = joy1;
		this.joy2 = joy2;
		servo1 = new Servo(90, joy1.getRadius());
		servo2 = new Servo(90, joy2.getRadius());
		pwm = new WebIoPiPwm("http://192.168.123.16");
	}

	public Canvas getCanvas() {

		if (canvas != null) {

			return canvas;

		} else {

			return null;
		}
	}

	public void setRunnable(boolean run) {
		this.run = run;
	}

	public void run() {

		while (run) {
			canvas = null;

			try {
				pwm.pwmWriteFAngle("pwm0", 15, servo1.convertAngle(joy1.getJoyX()));
//				Log.d("CV", Integer.toString(joy1.getX()) +" = "+ Float.toString(servo1.convertAngle(joy1.getX())));
				pwm.pwmWriteFAngle("pwm0", 14, servo1.convertAngle(joy1.getJoyY()));
//				Log.d("CV", Integer.toString(joy1.getY()) +" = "+ Float.toString(servo1.convertAngle(joy1.getY())));
				pwm.pwmWriteFAngle("pwm0", 13, servo2.convertAngle(joy2.getJoyX()));
//				Log.d("CV", Integer.toString(joy2.getX()) +" = "+ Float.toString(servo1.convertAngle(joy2.getX())));
				pwm.pwmWriteFAngle("pwm0", 12, servo2.convertAngle(joy2.getJoyY()));
//				Log.d("CV", Integer.toString(joy2.getY()) +" = "+ Float.toString(servo1.convertAngle(joy2.getY())));
				canvas = holder.lockCanvas(null);
				
				synchronized (holder) {
					remoteView.doDraw(canvas);

				}
				sleep(10);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

				if (canvas != null) {

					holder.unlockCanvasAndPost(canvas);
				}

			}
			
		}
	}



}
