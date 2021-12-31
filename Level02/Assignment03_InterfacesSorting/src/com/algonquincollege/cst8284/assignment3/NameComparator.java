package com.algonquincollege.cst8284.assignment3;

import java.util.Comparator;

/**
 * This class implement java.util.Comparator<T>
 * override the compare(<T>,<T>)method,replace T with CharacterReport
 * 
 * @author Simon Ao<br>
 * Student ID:040983402 <br>
 * Section:CST8284-312 <br>
 * Update:2021-4-1
 * @version Java 1.8.0
 * @see CharacterRecord
 * @see java.util.Comparator
 */
public class NameComparator implements Comparator<CharacterRecord> {

	public NameComparator() {
	}

	/**
	 * Compares Changes the name field with the value passed(o1 and o2)
	 * result is based on using String.compareTo(name2)
	 * 
	 */
	@Override
	public int compare(CharacterRecord o1, CharacterRecord o2) {
		String name1 = (o1 == null) ? null : o1.getName();
		String name2 = (o2 == null) ? null : o2.getName();

		if (name1 == null && name2 == null) {
			return 0;
		} else if (name1 == null && name2 != null) {
			return -1;
		} else if (name1 != null && name2 == null) {
			return 1;
		} else {
			return name1.compareTo(name2);
		}
	}

}
