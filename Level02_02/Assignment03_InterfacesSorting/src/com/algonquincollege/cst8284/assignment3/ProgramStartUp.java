/*
 * Assessment: CST8284 Assignment 03 (21W)
 * Modified by Student: Simon Ao
 * Lab Section:312
 * Lab Professor:Leanne Seaward
 */

// ToDo: Update the comment block at the top of the file.
package com.algonquincollege.cst8284.assignment3;

/**
 * This class launches the application.
 * 
 * @author Simon Ao<br>
 * Student ID:040983402 <br>
 * Section:CST8284-312 <br>
 * Update:2021-4-1
 * @version Java 1.8.0
 */
public class ProgramStartUp {
	/**
	 * The main method simply instantiates the CharacterRecordUtility
	 * and calls method processFile() to run the program.
	 * @param args Command Line Arguments are not used by this program.
	 */
	public static void main(String[] args) {
		new CharacterRecordUtility().processFile();
	}
}
