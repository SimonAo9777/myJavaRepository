package com.algonquincollege.cst8284.assignment3;

import java.util.Comparator;

/**
 * This class implement java.util.Comparator<T>
 * override the compare(<T>,<T>)method,replace T with CharacterReport
 * @author Simon Ao<br>
 * Student ID:040983402 <br>
 * Section:CST8284-312 <br>
 * Update:2021-4-1
 * @version Java 1.8.0
 */
public class AttackChance1Comparator implements Comparator<CharacterRecord> {

	public AttackChance1Comparator() {
	}

	/**
	 * Compares changes the attackChance1 field with the value passed
	 * attackChance1 cannot be negative, cannot exceed 100
	 */
	@Override
	public int compare(CharacterRecord o1, CharacterRecord o2) {
		int ac1 = (o1 == null) ? 0 : o1.getAttackChance1();
		int ac2 = (o2 == null) ? 0 : o2.getAttackChance1();
		if (ac1 == ac2) {
			return 0;
		} else if (ac1 < ac2) {
			return -1;
		} else {
			return 1;
		}
	}

}
