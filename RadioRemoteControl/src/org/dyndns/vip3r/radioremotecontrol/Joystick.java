package org.dyndns.vip3r.radioremotecontrol;

public class Joystick {

	private int x0;
	private int y0;
	private int radius;
	private int x;
	private int y;
	private int id = -1;

	public Joystick(int xBegin, int yBegin, int viewWidth, int viewHeight) {
		radius = viewHeight / 6;
		x0 = xBegin + radius;
		y0 = yBegin + radius;
		x = x0;
		y = y0;
	}

	public void checkJoyDown(float eventX, float eventY, int _id) {
		if ((int) eventX > getXMin() && (int) eventX < getXMax()
				&& (int) eventY > getYMin() && (int) eventY < getYMax()) {
			setID(_id);
			setXY(eventX, eventY);
		}
	}

	public void checkJoyUp(int _id) {
		if (_id == getID()) {
			setID(-1);
			setXY(getX0(), getY0());
		}

	}

	public void checkJoyMove(float eventX, float eventY, int _id) {
		if (_id == getID()) {
			if ((int) eventX > getXMin() && (int) eventX < getXMax()
					&& (int) eventY > getYMin() && (int) eventY < getYMax()) {
				setXY(eventX, eventY);
			} else {
				float _x = eventX;
				float _y = eventY;

				if ((int) eventX < getXMin()) {
					_x = getX0() - getRadius();
				} else if ((int) eventX > getXMax()) {
					_x = getX0() + getRadius();
				}

				if ((int) eventY < getYMin()) {
					_y = getY0() - getRadius();
				} else if ((int) eventY > getYMax()) {
					_y = getY0() + getRadius();
				}

				setXY(_x, _y);

			}
		}

	}

	public void setID(int _id) {
		this.id = _id;
	}

	public int getID() {
		return id;
	}

	public int getX0() {
		return x0;
	}

	public int getY0() {
		return y0;
	}

	public int getRadius() {
		return radius;
	}

	public int getJoyX() {
		return x-x0;
	}

	public int getJoyY() {
		return y-y0;
	}

	public int getXMin() {
		return x0 - radius;
	}

	public int getXMax() {
		return x0 + radius;
	}

	public int getYMin() {
		return y0 - radius;
	}

	public int getYMax() {
		return y0 + radius;
	}

	public void setXY(float eventX, float eventY) {
		if ((int) eventX < x0 - radius) {
			x = x0 - radius;
		} else if ((int) eventX > x0 + radius) {
			x = x0 + radius;
		} else {
			x = (int) eventX;
		}

		if ((int) eventY < y0 - radius) {
			y = y0 - radius;
		} else if ((int) eventY > y0 + radius) {
			y = y0 + radius;
		} else {
			y = (int) eventY;
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
