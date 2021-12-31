// Needs programmer comments on top of file, class, constructor(s), methods
// remove the comment above, and the "to do" comments below and replace with
// your own comments.
import java.util.Scanner;

public class UserInput {
	private Scanner keyboard = new Scanner(System.in);
	
	// To Do: stop this from crashing on invalid input.
	//        enforce the min and max range values
	//        loop forever until the user gets it right
	public int input(int minValue, int maxValue) {
		int userInput = 0;

		userInput = keyboard.nextInt(); // read number

		return userInput;
	}
	
	// To Do: stop this from crashing on invalid input.
	//        enforce the min and max range values
	//        loop forever until the user gets it right
	//		  EPSILON is your friend to check if input exactly equals min or
	//	      max values.
	public double input(double minValue, double maxValue) {
		double userInput;
		
	    userInput = keyboard.nextDouble(); // read number

		return userInput;
	}

}
