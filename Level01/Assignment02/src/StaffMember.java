/*
 * Student Name: Simon AO
 * Lab Professor:   Mohammad Patoary
 * Due Date: Oct 25, 2020.
 * Modified: Oct 24, 2020.
 * Description: Assignment2 1 section 342, Staff with input,output,processing methods
  */
import java.util.Scanner;
/*
 * StaffMember calss, has methods for output
 * inputNumber as an int, and calculate total cans and liters
 * 
 * */

public class StaffMember {

	public static void main(String[] args) {
		String StaffMemberFristName;// Staff member frist name
		String StaffMemberLastName;// Staff member last name
		int area;// paint area

		/*
		 * instance of class Scanner for inputs,variable is named input declarations
		 */
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter your first name:");
		StaffMemberFristName = input.nextLine();

		System.out.println("Please enter your last name:");
		StaffMemberLastName = input.nextLine();

		System.out.println("Please enter the  square feet:");
		int sqr = input.nextInt();

		while (sqr < 0) { // Remind the user to enter a positive number when entering a negative number
			System.out.println("Please enter a postive number");// use input number1
			sqr = input.nextInt();// use input number1
			input.nextLine();
		}
		/*
		 * declarations
		 */
		PaintStore pse = new PaintStore();
		pse.setarea(sqr);

		/*
		 * output square feet to be painted require, the liters of paint required, the
		 * total whole cans of paint required,the satff member name
		 */

		System.out.println("First name of the staff member:" + StaffMemberFristName);
		System.out.println("Last name of the staff member:" + StaffMemberLastName);
		System.out.println("The total square feet to be painted is: " + pse.getarea());
		System.out.println("Liters =  " + pse.calculateLiters());
		System.out.println("Number of cans =" + pse.calculateCans());
		System.out.println("Number of total cans=" + (int) Math.ceil(pse.calculateCans()));

		System.out.println("report:" + StaffMemberFristName + " " + StaffMemberLastName + " area" + pse.getarea()
				+ " Liters" + pse.calculateLiters() + " Cans" + pse.calculateCans() + " TotalCans"
				+ (int) Math.ceil(pse.calculateCans()));

		System.out.println();
		System.out.println("Project by Simon Ao");
		/*
		 * input input intas
		 */

	}

}//end class
