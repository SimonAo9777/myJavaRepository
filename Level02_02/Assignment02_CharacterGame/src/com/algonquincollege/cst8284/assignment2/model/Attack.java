/*
 * Copyright(2021) Algonquin College
 * CST8284 (21W) Assignment 2 Starter Code
 */
package com.algonquincollege.cst8284.assignment2.model;

/**
 * This class represents an Attack a character generates and
 * contains a damage score.
 * Upgrade the code provided by the professor, abstract the Attack class
 * @see Character
 * @author @author Simon Ao<br>
 * Student ID:040983402 <br>
 * Section:CST8284-312 <br>
 * Update:2021-3-17
 * @version Java 1.8.0
 */
public abstract class Attack {//abstract the Attack class
	
	/**
	 * the damage of this attack
	 */
	private int damage;
	
	/**
	 * This constructor sets the damage to zero (i.e. 0)
	 */
	public Attack() {
		this(0);
	}
	
	/**
	 * This constructor sets the value specified into the damage
	 * field, no data validation is performed.
	 * @param damage the damage score for this attack
	 */
	public Attack(int damage) {
		this.damage = damage;
	}

	/**
	 * Gets the damage for this attack
	 * @return the damage score for this attack
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * Sets the damage score for this atttack, no
	 * data validation is performed.
	 * @param damage The amount of damage for this attack.
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	/**
	 * This overridden toString() returns the name of the class,
	 * removing any package information with formatted text:
	 * <i>ClassName</i> with <i>%d</i> damage
	 * A newline character is not added to the end of the String.
	 */
	@Override
	public String toString() {
		String className = getClass().getName();
		int index = className.lastIndexOf("."); // where is last . in packages
		if(index != -1) {
			className = className.substring(index + 1); // +1 to skip the . char
		}
		return String.format("%s with %d damage", className,
				getDamage());
	}

}
