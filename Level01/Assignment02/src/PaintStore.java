/*
 * Student Name: Simon AO
 * Lab Professor: Professor  Professor Mohammad Patoary
 * Due Date: Oct 25, 2020.
 * Modified: Oct 24, 2020.
 * Description: Assignment2 1 section 342, calculate the number of paint cans according to the wall area
  */
public class PaintStore {
	private int area = 0;
	private double liters = 0.0;
	private double numberOfCans = 0.0;

	/* Default no parameters constructor */
	public PaintStore() {

	}

	/* Accessor for paint area */
	public int getarea() {
		return area;
	}

	/* mutator for paint area */
	public void setarea(int area) {
		this.area = area;
	}

	/* calculates for liters */
	public double calculateLiters() {
		liters = 4.0d / 400 * area;
		return liters;
	}

	/* calculates for cans */
	public double calculateCans() {
		numberOfCans = 1 / 4.0 * liters;
		return numberOfCans;
	}

}// end class
