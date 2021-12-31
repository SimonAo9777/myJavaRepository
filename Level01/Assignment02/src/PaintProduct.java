/*
 * Student Name: Simon AO
 * Lab Professor: Professor  Stanley Pieda
 * Due Date: Oct 23, 2020.
 * Modified: Oct 22, 2020.
 * Description: Assignment2 1 section 340, calculate the number of paint cans according to the wall area
  */
public class PaintProduct {
	private final int LITERS_PER_CAN = 4;// the company currently uses comes in 4 litre cans
	private final int SQUARE_FEET_PER_CAN = 400;// paint covers 400 square feet per can
	private double paintSurfaceArea = 0.0;// customer's use of wall areas

	/* Default no parameters constructor */
	public PaintProduct() {

	}

	/* Accessor for paintSurfaceArea */
	public double getpaintSurfaceArea() {
		return paintSurfaceArea;
	}

	/* Accessor for paintSurfaceArea */
	public void setpaintSurfaceArea(double paintSurfaceArea) {
		this.paintSurfaceArea = paintSurfaceArea;
	}

	/* calculate the integer of the can of paint */
	public int consumeCans() {
		int cans;
		if (paintSurfaceArea % SQUARE_FEET_PER_CAN != 0) {
			cans = (int) (paintSurfaceArea / SQUARE_FEET_PER_CAN) + 1;

		} else {
			cans = (int) (paintSurfaceArea / SQUARE_FEET_PER_CAN);

		}
		return cans;

	}

	/* calculate the integer of the liters */
	public int consumeLitres() {
		int litres = consumeCans() * LITERS_PER_CAN;
		return litres;

	}

}// end of class
