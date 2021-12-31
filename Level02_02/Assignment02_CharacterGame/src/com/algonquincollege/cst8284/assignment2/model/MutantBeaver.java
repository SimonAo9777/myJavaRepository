/*
 * Copyright(2021) Algonquin College
 * CST8284 (21W) Assignment 2 Starter Code
 */
package com.algonquincollege.cst8284.assignment2.model;

import com.algonquincollege.cst8284.assignment2.util.DiceBag;

/**
 * This class extends Character class represents a Character prototype, suitable for use in a
 * Role-Playing Game.
 * based on student number%10(040983402),and the assigned Character Set 2
 * Water Elemental vs Mutant Beaver. With Basic Attack ,Frost Attack, Wood Attack
 * @author Simon Ao<br>
 * Student ID:040983402 <br>
 * Section:CST8284-312 <br>
 * Update:2021-3-17
 * @version Java 1.8.0
 *
 */
public class MutantBeaver extends Character {

	public MutantBeaver() {
		int maxHealth = DiceBag.rollFourSidedDice() * 20;
		int strength = DiceBag.rollTwelveSidedDice() * 4;
		this.setMaxHealth(maxHealth);
		this.setCurrentHealth(maxHealth);
		this.setStrength(strength);
	}

	public MutantBeaver(int maxHealth, int currentHealth, int strength) {
		super(maxHealth, currentHealth, strength);
	}

	/**
	 * This method generates an Attack from this character, it uses strength + (1 to
	 * 8 inclusive) for the damage calculation.
	 * 
	 * @see DiceBag
	 * @return an Attack
	 */
	public Attack attack() {
		int myDamage = getStrength() + DiceBag.rollSixSidedDice();
		int percent = DiceBag.rollOneHundredSidedDice();
		Attack attack = null;
		if (percent <= 60) {
			attack = new BasicAttack(myDamage);
		} else {
			attack = new WoodAttack(myDamage);
		}
		return attack;
	}

	/**
	 * This method processes an Attack made against this character, to determine if
	 * full damage was taken or if the damage was reduced or increased. Currently it
	 * defaults to keeping the incoming damage amount unchanged.
	 * 
	 * @param attack the Attack against this character
	 * @return an AttackReport on how this character defended
	 */
	public AttackReport defend(Attack attack) {
		AttackReport result = null;
		if (attack != null) {
			result = new AttackReport();

			int damage = 0;
			if (attack instanceof WoodAttack) {
				damage = - DiceBag.rollTenSidedDice();
				result.setReport(getTypeClassName() + " Took heal");// A report shows the result
			} else if (attack instanceof FrostAttack) {
				damage = 0;
				result.setReport(getTypeClassName() + " Took no damage");// A report shows the result
			} else if (attack instanceof BasicAttack) {
				damage = attack.getDamage();
				result.setReport(getTypeClassName() + " Took full damage");// A report shows the result
			}

			this.setCurrentHealth(this.getCurrentHealth() - damage);

			result.setActualDamage(damage);
		}
		return result;
	}

}
