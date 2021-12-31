/*
 * Copyright(2021) Algonquin College
 * CST8284 (21W) Assignment 2 Starter Code
 */
package com.algonquincollege.cst8284.assignment2.model;

import com.algonquincollege.cst8284.assignment2.util.DiceBag;

/**
 * This class represents a Character prototype, suitable for
 * use in a Role-Playing Game.
 * Upgrade the code provided by the professor, abstract the Character class
 * @author Simon Ao<br>
 * Student ID:040983402 <br>
 * Section:CST8284-312 <br>
 * Update:2021-3-17
 * @version Java 1.8.0
 */
public abstract class Character {//abstract the Character class

	/**
	 * Max health for the character as an integer value.
	 */
	private int maxHealth;
	
	/**
	 * Current health for the character as an integer value.
	 */
	private int currentHealth;
	
	/**
	 * Strength of the character as an integer value.
	 */
	private int strength;
	
	/**
	 * This constructor uses the DiceBag class to randomize
	 * the max health and strength of the character at creation.
	 * maxHealth will be 10 to 200 (inclusive), strength will
	 * be 3 to 18 (inclusive). The current health is set to be the
	 * same as the maxHealth.
	 * @see DiceBag
	 */
	public Character() {
		maxHealth = DiceBag.rollTwentySidedDice() * 10;
		currentHealth = maxHealth;
		strength = DiceBag.rollSixSidedDice() * 3;
	}
	
	/**
	 * This constructor allows specific values to be set.
	 * @param maxHealth the maximum health of the character
	 * @param currentHealth the current health of the character
	 * @param strength the strength of the character
	 */
	public Character(int maxHealth, int currentHealth, int strength) {
		setMaxHealth(maxHealth);
		setCurrentHealth(currentHealth);
		setStrength(strength);
	}
	
	/**
	 * Reports the class name for this instance, it also removes any
	 * package information returning only the class name itself.
	 * @return name of the class definition for this object
	 */
	public String getTypeClassName() {
		String className = getClass().getName();
		int index = className.lastIndexOf("."); // where is last . in packages
		if(index != -1) {
			className = className.substring(index + 1); // +1 to skip the . char
		}
		return className;
	}
	
	/**
	 * Gets max health for this character, method fullRecover()
	 * will use this value to update the currentHealth
	 * @return maximum health of this character
	 */
	public int getMaxHealth() {
		return maxHealth;
	}
	
	/**
	 * Sets the max Health value for this character, there
	 * is no data validation.
	 * @param maxHealth maximum health for this character
	 */
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	
	/**
	 * Gets the current health of this character
	 * @return current health of this character
	 */
	public int getCurrentHealth() {
		return currentHealth;
	}
	
	/**
	 * Sets a new value for the current health of this character
	 * @param currentHealth current health for this character
	 */
	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}
	
	/**
	 * Gets the strength for this character, strength
	 * is used as part of attack calculations for this character
	 * @return The strength of this character
	 */
	public int getStrength() {
		return strength;
	}
	
	/**
	 * Sets the strength for this character, there
	 * is no data validation.
	 * @param strength strength for this character
	 */
	public void setStrength(int strength) {
		this.strength = strength;
	}
	
	/**
	 * Provides a String representation for this Character, 
	 * formatted as "type: %s, max health: %d, current health
	 *               %d, strength: %d"
	 * A new-line character is not added to the end of the String
	 */
	@Override
	public String toString() {
		String reportFormat = 
				"type: %s, max health: %d, " +
		        "current health: %d, strength: %d";
		String report =
				String.format(reportFormat, 
						getTypeClassName(),getMaxHealth(),
						getCurrentHealth(), getStrength());
		return report;
	}
	
	/**
	 * This method generates an Attack from this character,
	 * it uses strength + (1 to 8 inclusive) for the damage calculation.
	 * @see DiceBag
	 * @return an Attack
	 */
	public abstract Attack attack();//abstract the Attack attack()
	
	/**
	 * This method processes an Attack made against this character,
	 * to determine if full damage was taken or if the damage was 
	 * reduced or increased. Currently it defaults to keeping 
	 * the incoming damage amount unchanged.
	 * @param attack the Attack against this character
	 * @return an AttackReport on how this character defended
	 */
	public abstract AttackReport defend(Attack attack);//abstract the AttackReport defend(Attack attack)
	
	/**
	 * This is a convenience method, it simply changes the 
	 * currentHealth to match the maxHealth.
	 * 
	 */
	public void fullRecover() {
		currentHealth = maxHealth;
	}
}
