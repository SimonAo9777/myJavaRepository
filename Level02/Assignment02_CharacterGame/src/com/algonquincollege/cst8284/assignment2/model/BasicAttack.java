/*
 * Copyright(2021) Algonquin College
 * CST8284 (21W) Assignment 2 Starter Code
 */
package com.algonquincollege.cst8284.assignment2.model;

/**
 * Examine the character sub-class descriptions,  
 * create sub-classes BasicAttack
 * based on student number%10(040983402),and the assigned Character Set 2
 * Water Elemental vs Mutant Beaver. With Basic Attack ,Frost Attack, Wood Attack
 * @see Character
 * @author @author Simon Ao<br>
 * Student ID:040983402 <br>
 * Section:CST8284-312 <br>
 * Update:2021-3-17
 * @version Java 1.8.0
 */
public class BasicAttack extends Attack {
	/**
	 * Set no-parameter constructor
	 */
	public BasicAttack() {
	}
	/**
	 * This constructor sets the value specified into the damage
	 * field, no data validation is performed.
	 * @param damage the damage score for this attack
	 */
	public BasicAttack(int damage) {
		super(damage);
	}

}
