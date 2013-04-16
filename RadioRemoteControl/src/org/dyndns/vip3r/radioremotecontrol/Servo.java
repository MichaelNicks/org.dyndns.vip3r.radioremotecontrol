package org.dyndns.vip3r.radioremotecontrol;

public class Servo {

	private int maxAngle;
	private int maxJoystick;
	private float degree;
	
	public Servo(int maxAngle, int maxJoystick)
	{
		this.maxAngle=maxAngle;
		if(maxAngle > 90)
			maxAngle=90;
		this.maxJoystick = maxJoystick;
		degree = maxJoystick / maxAngle;
	}
	
	public float getMaxAngle(){
		return maxAngle;
	}
	
	public void setMaxAngle(int maxAngle){
		this.maxAngle=maxAngle;
		if(maxAngle > 90)
			maxAngle=90;
		degree = maxJoystick / maxAngle;
	}
	
	public float convertAngle(int value){
		float angle = value / degree;
		if(angle < 5 && angle > -5)
			angle=0;
		return angle;
	}
}
