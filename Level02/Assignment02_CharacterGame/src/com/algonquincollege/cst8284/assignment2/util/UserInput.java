/*
 * Copyright(2021) Algonquin College
 * CST8284 (21W) Assignment 2 Starter Code
 */
package com.algonquincollege.cst8284.assignment2.util;

import java.util.Scanner;

/**
 * This is a utility class with static methods to garner console input
 * from a user, text as an entire line, an integer number, and decimal point
 * number. The methods that read in numerical data have basic validation for
 * non-numerical data and will prompt the user for an appropriate number
 * (integer or decimal point number). Caution, this validation uses a loop
 * that in theory could iterate forever unless the user enters a numerical 
 * value. 
 * The code of Program class is provided by the professor
 * @author Simon Ao<br>
 * Student ID:040983402 <br>
 * Section:CST8284-312 <br>
 * Update:2021-3-17
 * @version Java 1.8.0
 */
public class UserInput {
	/**
	 * Scanner is used to get inputs from the standard input stream.
	 * @see java.util.Scanner
	 */
	private static Scanner keyboard = new Scanner(System.in);
	
	/**
	 * This method returns a line of text from the standard input stream.
	 * @return A line of text from the standard input stream.
	 */
	public static String readText() {
		return keyboard.nextLine();
	}
	
	/**
	 * This method returns an integer (int) value as read from the input stream.
	 * It removes any remaining line terminators before returning, and if the 
	 * data in the input stream is not numerical it uses a simple loop to 
	 * report an error and ask the user to enter an integer number.
	 * @return An int read from the standard input stream.
	 */
	public static int readInt() {
		int value = 0;
		while( ! keyboard.hasNextInt()) {
			System.out.println("Please enter an integer number");
			keyboard.nextLine();
		}
		value = keyboard.nextInt();
		keyboard.nextLine();
		return value;
	}
	
	/**
	 * This method returns a double value as read from the input stream.
	 * It removes any remaining line terminators before returning, and if the 
	 * data in the input stream is not numerical it uses a simple loop to 
	 * report an error and ask the user to enter a number.
	 * @return A double read from the standard input stream.
	 */
	public static double readDouble() {
		double value = 0;
		while( ! keyboard.hasNextDouble()) {
			System.out.println("Please enter a number");
			keyboard.nextLine();
		} 
		value = keyboard.nextDouble();
		keyboard.nextLine();
		return value;
	}
}
