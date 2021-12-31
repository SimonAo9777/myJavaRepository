/*
 * Copyright(2021) Algonquin College
 * CST8284 (21W) Assignment 2 Starter Code
 */
package com.algonquincollege.cst8284.assignment2.util;

import java.util.Random;

/**
 * This class contains utility methods to simulate rolling
 * a variety of polyhedral dice, 4-sided, 6-sided, 8-sided,
 * 10-sided, 12-sided, 20-sided, and 100-sided.
 * The code of Program class is provided by the professor
 * @author Simon Ao<br>
 * Student ID:040983402 <br>
 * Section:CST8284-312 <br>
 * Update:2021-3-17
 * @version Java 1.8.0
 *
 */
public class DiceBag {
	/**
	 * This instance of random is used for all of the 
	 * methods in the class.
	 */
	private static Random random = new Random();
	
	/**
	 * Uses random number generation to return a value
	 * between 1 to 4
	 * @return a random value between 1 to 4 (inclusive)
	 */
	public static int rollFourSidedDice() {
        return random.nextInt(4) + 1;
	}
	
	/**
	 * Uses random number generation to return a value
	 * between 1 to 6
	 * @return a random value between 1 to 6 (inclusive)
	 */
	public static int rollSixSidedDice() {
		return random.nextInt(6) + 1;
	}
	
	/**
	 * Uses random number generation to return a value
	 * between 1 to 8
	 * @return a random value between 1 to 8 (inclusive)
	 */
	public static int rollEightSidedDice() {
		return random.nextInt(8) + 1;
	}
	
	/**
	 * Uses random number generation to return a value
	 * between 1 to 10
	 * @return a random value between 1 to 10 (inclusive)
	 */
	public static int rollTenSidedDice() {
		return random.nextInt(10) + 1;
	}
	
	/**
	 * Uses random number generation to return a value
	 * between 1 to 12
	 * @return a random value between 1 to 12 (inclusive)
	 */
	public static int rollTwelveSidedDice() {
		return random.nextInt(12) + 1;
	}
	
	/**
	 * Uses random number generation to return a value
	 * between 1 to 20
	 * @return a random value between 1 to 20 (inclusive)
	 */
	public static int rollTwentySidedDice() {
		return random.nextInt(20) + 1;
	}
	
	/**
	 * Uses random number generation to return a value
	 * between 1 to 100
	 * @return a random value between 1 to 100 (inclusive)
	 */
	public static int rollOneHundredSidedDice() {
		return random.nextInt(100) + 1;
	}
	
}
