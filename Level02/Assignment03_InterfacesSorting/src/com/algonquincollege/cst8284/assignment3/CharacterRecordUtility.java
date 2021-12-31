/*
 * Assessment: CST8284 Assignment 03 (21W)
 * Modified by Student: Simon Ao
 * Lab Section:312
 * Lab Professor:Leanne Seaward
 */
package com.algonquincollege.cst8284.assignment3;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// ToDo:
// - See the instructions in this file, above or in each of the methods.
//   Remove the not-yet-implemented throw statement.
// - Provide Javadoc comments for each method.

/**
 * 
 * This class most of your assignment efforts will be to provide 
 * implementations of the method stubs provided
 * 
 * @author Simon Ao<br>
 * Student ID:040983402 <br>
 * Section:CST8284-312 <br>
 * Update:2021-4-1
 * @version Java 1.8.0
 */
public class CharacterRecordUtility {

	private String[] columnNames = null; // used for storing the column names.
	
	    // ToDo:
		// Create a List<T> records = new ArrayList<T>()
		// for storing Character Records, i.e. replace <T> with <CharacterRecord>
		// Note: List<String> is forbidden to be used!

		// No need for a constructor, let the compiler
		// provide the default-no-parameter constructor for this class.

	/**
	 * Create a List<T> records = new ArrayList<T>()
	 */
	
	
	private List<CharacterRecord> records = new ArrayList<>();
	private LineNumberReader reader;

	
	public void processFile() {
		// ToDo:
		// - use one try with multiple catch blocks to enclose the method body
		// - catch the checked exceptions, use a catch(Exception e) at the
		// bottom of the catch blocks.
		// - There is no reason to alter the starter code presented here

		// try here
		
		/**
		 * Use one try with multiple catch block to enclose the method
		 * body catch the checked exceptions		 
		 * 
		 * **/
		try {
			System.out.println("Attempting to open CharacterRecordsUnsorted.csv ...");
			loadFile();

			System.out.println("Sorting by name ...");
			sortName();

			System.out.println("Saving to SortedByName.csv ...");
			System.out.println("(Will overwrite any file with same name)");
			saveFile("SortedByName.csv");

			System.out.println("Sorting by attackChance1 ...");
			sortAttackChance1();

			System.out.println("Saving to SortedByAttackChance1.csv ...");
			System.out.println("(Will overwrite any file with same name)");
			saveFile("SortedByAttackChance1.csv");

			System.out.println("Character Data Sorted and Saved.");
			System.out.println("Program by Simon Ao");
			// catches here
			
		}
		/**
		 * catch the checked exceptions, use a catch(Exception e) at the
		 * bottom of the catch blocks.
		 * 
		 */
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader!=null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					reader=null;
				}
			}
		}
	}

	private void loadFile() throws IOException {
		// ToDo: - Open the provided CharacterRecordUnsorted.csv file
		// - capture the first record as column names, do not use
		// as part of the List of Character Records.
		// - parse each line of text into an array, load the fields
		// into a new CharacterRecord, place reference to
		// the character record into the list. You will need to convert
		// the attackChance fields from text into numbers when
		// placing them into each CharacterRecord object.
		// - Tip: The first thing you should do is actually empty the list
		// in case this method is being called more than once. There
		// may be a method of List that clears (clear()?) it of all items.
		// - Catch any exceptions, ensure your file is closed in all cases
		// - re-throw any caught exception so that the calling method
		// is notified there was a problem.
		// - Place the csv file provided into the project folder itself,
		// not the src folder. Eclipse will move the csv file where it is
		// needed to run the program. (Outside of Eclipse when using only
		// the file-name, the relative path used is the folder that the
		// java.exe file was run in)
		// - Tip: You will need to add any CheckedExceptions to the method signature.
		
		/**
		 * capture the first record as column names, do not use
		 * as part of the List of Character Records.
		 */
		reader = new LineNumberReader(new FileReader("CharacterRecordsUnsorted.csv"));
		String line = reader.readLine();
		if (line == null) {
			return;
		}
		columnNames = line.split(",");
		line = reader.readLine();
		while (line != null) {
			String[] values = line.split(",");
			CharacterRecord record=new CharacterRecord();
			int length=Math.min(columnNames.length, values.length);
			for (int i = 0; i < length; i++) {
				setProperty(record, columnNames[i], values[i]);
			}
			records.add(record);
			line = reader.readLine();
		}

	}

	/**
	 * 
	 * @param record
	 * @param name
	 * @param value
	 */
	private void setProperty(CharacterRecord record, String name, String value) {
		String cname = name.toLowerCase();
		switch (cname) {
		case "name":
			record.setName(value);
			break;
		case "health":
			record.setHealth(value);
			break;
		case "strength":
			record.setStrength(value);
			break;
		case "attackdamage":
			record.setAttackDamage(value);
			break;
		case "attackchance1":
			record.setAttackChance1(Integer.parseInt(value));
			break;
		case "attackchance2":
			record.setAttackChance2(Integer.parseInt(value));
			break;
		case "attacktype1":
			record.setAttackType1(value);
			break;
		case "attacktype2":
			record.setAttackType2(value);
			break;
		case "defense":
			record.setDefense(value);
			break;
		default:
			break;
		}
	}

	/**
	 * 
	 * @param record
	 * @param name
	 * @return value
	 */
	private Object getProperty(CharacterRecord record, String name) {
		String cname = name.toLowerCase();
		Object value=null;
		switch (cname) {
		case "name":
			value = record.getName();
			break;
		case "health":
			value = record.getHealth();
			break;
		case "strength":
			value = record.getStrength();
			break;
		case "attackdamage":
			value = record.getAttackDamage();
			break;
		case "attackchance1":
			value = record.getAttackChance1();
			break;
		case "attackchance2":
			value = record.getAttackChance2();
			break;
		case "attacktype1":
			value = record.getAttackType1();
			break;
		case "attacktype2":
			value = record.getAttackType2();
			break;
		case "defense":
			value = record.getDefense();
			break;
		default:
			break;
		}
		return value;
	}

	private void sortName() {
		// ToDo: Sort the list using Collections.sort(list, Comparator<T>)
		// Create a class that implements comparator and overrides
		// method compare(<T>,<T>);
		Collections.sort(records, new NameComparator());
	}

	private void sortAttackChance1() {
		// ToDo: Sort the list using Collections.sort(list, Comparator<T>)
		// Create a class that implements comparator and overrides
		// method compare(<T>,<T>);
		Collections.sort(records, new AttackChance1Comparator());
	}

	private void saveFile(String fileName) throws IOException {
		// ToDo:
		// - Write out the records to a csv file using the fileName argument
		// - However, the first line written should be the column names
		// separated by commas.
		// - Overwrite the file if it already exists, this is the default behavior.
		// - Capture any exceptions, and re-throw them to caller.
		// - Ensure the file is closed down after writing is complete
		// - Tip1: Method toString() of class CharacterRecord was set up
		// for this assignment to produce a comma-separated-value
		// representation of the CharacterRecord, call toString() and
		// write the data out on separate lines (add a line terminator
		// character, either \n or better yet %n in a format-string.
		// - Tip2: The last write operation should be a line-terminator.
		// - Tip3: You will need to add any CheckExceptions to the method signature.
		
		/**
		 *  Write out the records to a csv file using the fileName argument
		 *  the first line written should be the column names 
		 *  separated by commas.
		 *  Ensure the file is closed down after writing is complete
		 */
		OutputStream out=Files.newOutputStream(Path.of(fileName), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
		PrintStream writer=new PrintStream(out);
		if (columnNames==null) {
			writer.close();
			return;
		}
		for (int i=0; i<columnNames.length; i++) {
			writer.append(columnNames[i]);
			if (i!=columnNames.length-1) {
				writer.append(",");
			}
		}
		writer.println();
		if (records == null) {
			writer.close();
			return;			
		}
		for (CharacterRecord record:records)
		{
			for (int i=0; i<columnNames.length; i++) {
				Object value=getProperty(record, columnNames[i]);
				if (value!=null) {
					writer.append(value.toString());
				}
				if (i!=columnNames.length-1) {
					writer.append(",");
				}
			}
			writer.println();
		}
		writer.close();
	}

}
