/* Assessment: Assignment 01
 * Student Name: Simon Ao
 * Due Date: Oct 4, 2020.
 * Description: this class outputs create a digital program form within method main.
 * Professor Name: Stanley Pieda
 */

import java.util.Scanner;

public class Assignment01 {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);// input stream

		double number1, number2; // Declare the number entered by the user

		System.out.println("please enter a postive number");
		number1 = input.nextDouble();// use input number1

		while (number1 < 0) { // Remind the user to enter a positive number when entering a negative number
			System.out.println("please enter a postive number");// use input number1
			number1 = input.nextDouble();// use input number1
		}

		System.out.println("please enter a postive number");
		number2 = input.nextDouble();// use input number2

		while (number2 < 0) {// use input number2
			System.out.println("please enter a postive number");// use input number2
			number2 = input.nextDouble();// use input number2
		}

		double sum = number1 + number2;// Calculate the sum of the two numbers.
		double average = (sum / 2);// Calculate the average of the two numbers.
		double sqrt = Math.sqrt(average); // Calculate the square root of the average of the two numbers.
		double pow1 = Math.pow(average, number1);// Raise the first number to the power of the average of the two
													// numbers.
		double pow2 = Math.pow(average, number2); // Raise the second number to the power of the average of the two
													// numbers.

		System.out.println("the sum of two numbers is " + sum); // System.out.-->output stream
		System.out.println("the average of two numbers is " + average);// System.out.-->output stream
		System.out.println("the square root of the average of the two numbers is " + sqrt);// System.out.-->output
																							// stream
		System.out.println("raise the first number to the power of the average of the two numbers is " + pow1);// System.out.-->output
																												// stream
		System.out.println("raise the second number to the power of the average of the two numbers is " + pow2);// System.out.-->output
																												// stream

		System.out.println("Author: Simon Ao");// System.out.-->output stream

	}

}
