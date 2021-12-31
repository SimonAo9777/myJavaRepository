/*
 * Author: Simon Ao
 * Professor: Mohammad Patoary
 * Date: December 11, 2020
 * Description: Lab exam 2 section 342 
 * reference by Lab exam 1 
 */

/* This class abstracts a cell phone */
public class CellPhone {
	private int batterySize; // mAh
	private double screenSize; // square cm

	/* Default, no parameters constructor */
	public CellPhone() {

	}

	/* Accessor for batterySize */
	public int getBatterySize() {
		return batterySize;
	}

	/* Mutator for batterySize */
	public void setBatterySize(int batterySize) {
		this.batterySize = batterySize;
	}

	/* Accessor for screenSize */
	public double getScreenSize() {
		return screenSize;
	}

	/* Mutator for screenSize */
	public void setScreenSize(double screenSize) {
		this.screenSize = screenSize;
	}

	/* Work method to estimate battery life of cell phone */
	public double estimateBatteryLife() {
		return batterySize - screenSize * 5.0;
	}
}
