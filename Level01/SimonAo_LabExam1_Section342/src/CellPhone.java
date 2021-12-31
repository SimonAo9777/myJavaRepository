/*
 * Author: Simon Ao
 * Professor: Lab Professor
 * Date: Oct 7, 2020
 * Description: Lab exam 1 section 342 sample answer by Simon Ao
 */

public class CellPhone {
	private double batterySize;
	private double screenSize;

	/*
	 * default constructor
	 */
	public CellPhone() {

	}

	/*
	 * accessor for batterSize
	 */
	public double getBatterySize() {
		return batterySize;
	}

	/*
	 * mutator for batterSize
	 */
	public void setBatterSize(double batterySize) {
		this.batterySize = batterySize;
	}

	/*
	 * accessor for screenSize
	 */

	public double getScreenSize() {
		return screenSize;
	}

	/*
	 * mutator for screenSize
	 */
	public void setScreenSize(double screenSize) {
		this.screenSize = screenSize;
	}

	public double estimateBatterLife() {
		double report = batterySize - screenSize * 5.0;
		return report;

	}

}
