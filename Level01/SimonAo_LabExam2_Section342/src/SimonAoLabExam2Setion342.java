
/*
 * Student Name: Simon Ao
 * Lab Professor:   Mohammad Patoary
 * Due Date: December 11, 2020.
 * Modified: December 11, 2020.
 * Description: Lab exam 2 section 342 
 * a menu-driven application that runs until an Cell-Phone option is selected                                    
 */
import java.util.Scanner;

/* 
 * Class that abstracts user input with validation of ranges. 
 */
public class SimonAoLabExam2Setion342 {
	/*
	 * Entry point for the application
	 */
	public static void main(String[] args) {
		CellPhone phone = new CellPhone();
		Scanner keyboard = new Scanner(System.in);
		/*
		 * set final constants declarations
		 */
		int settingOption;
		final int EDIT_CELL_PHONE = 342;
		final int PRINT_CELL_PHONE = 684;
		final int EXIT_CELL_PHONE = 1026;

		/*
		 * Output the menu options each time the loop repeats
		 */
		do {
			System.out.println("Enter Option:");
			System.out.println(EDIT_CELL_PHONE + " to edit cell phone");
			System.out.println(PRINT_CELL_PHONE + " to show battery life");
			System.out.println(EXIT_CELL_PHONE + " to exit program");
			System.out.println("Program by Simon Ao");

			settingOption = keyboard.nextInt();// read option number

			/*
			 * use switch-statement within the method in testing the option
			 * 
			 */
			switch (settingOption) {
			/*
			 * show edit cell phone
			 */
			case EDIT_CELL_PHONE:
				System.out.print("Enter battery size ");
				int newBatterySize = keyboard.nextInt();
				phone.setBatterySize(newBatterySize);

				System.out.print("Enter screen size ");
				double newScreenSize = keyboard.nextDouble();
				phone.setScreenSize(newScreenSize);
				break;
			/*
			 * show cell phone battery life
			 */
			case PRINT_CELL_PHONE:
				System.out.println(phone.estimateBatteryLife());
				break;
			/*
			 * show exit program
			 */
			case EXIT_CELL_PHONE:
				System.out.println("Pragram exits");
				break;

			default:
				System.out.println("invalid menu input");// show this is the input was invalid.

			}// end switch

		} while (!(settingOption == EXIT_CELL_PHONE));

	}// end main

}// end class
