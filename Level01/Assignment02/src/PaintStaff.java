
/*
 * Student Name: Simon AO
 * Lab Professor:  Stanley Pieda
 * Due Date: Oct 23, 2020.
 * Modified: Oct 22, 2020.
 * Description: Assignment2 1 section 340, Staff with input,output,processing methods
  */
import java.util.Scanner;

/*
 * PaintStaff calss, has methods for output
 * inputNumber as an int, and calculate total cans and liters
 * 
 * */

public class PaintStaff {
	public static void main(String[] args) {

		/*
		 * instance of class Scanner for inputs,variable is named input declarations
		 */
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter the wall square feet:");
		/*
		 * input input int
		 */
		double sft = input.nextDouble();
		input.nextLine();

		
		while (sft < 0) { // Remind the user to enter a positive number when entering a negative number
			System.out.println("please enter a postive number");// use input number1
			sft = input.nextDouble();// use input number1
		}

		/*
		 * declarations
		 */
		PaintProduct pp = new PaintProduct();
		pp.setpaintSurfaceArea(sft);
		/*
		 * output square feet to be painted require, the liters of paint required, the
		 * total whole cans of paint required,the satff member name
		 */
		System.out.println("The wall square feet to be painted: " + pp.getpaintSurfaceArea());
		System.out.println("The liters of paint required:" + pp.consumeLitres());
		System.out.println("The total whole cans of paint required:" + pp.consumeCans());
		System.out.println();
		System.out.println("The satff member name: Simon Ao");

	}// end methods

}// end calss
