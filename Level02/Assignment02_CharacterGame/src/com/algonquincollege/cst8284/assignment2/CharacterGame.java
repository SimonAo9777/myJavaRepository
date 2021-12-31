/*
 * Copyright(2021) Algonquin College
 * CST8284 (21W) Assignment 2 Starter Code
 */
package com.algonquincollege.cst8284.assignment2;

import com.algonquincollege.cst8284.assignment2.util.UserInput;
import com.algonquincollege.cst8284.assignment2.model.Attack;
import com.algonquincollege.cst8284.assignment2.model.Character;
import com.algonquincollege.cst8284.assignment2.model.MutantBeaver;
import com.algonquincollege.cst8284.assignment2.model.WaterElemental;
import com.algonquincollege.cst8284.assignment2.model.AttackReport;

/**
 * This class is the core of the program, it allocates characters into an array
 * on start up then interacts with a user who can select characters (by array
 * index) to run a combat simulation.
 * 
 * The code of CharacterGame class is provided by the professor
 * @author Simon Ao<br>
 * Student ID:040983402 <br>
 * Section:CST8284-312 <br>
 * Update:2021-3-17
 * @version Java 1.8.0
 *
 */
public class CharacterGame {
	/**
	 * Maxium size of the array storing references to characters
	 */
	private final static int MAX_ARRAY_SIZE = 10;

	/**
	 * Array storing references to characters, it is assumed that this array will
	 * always be 'full' i.e. no null values.
	 */
	private Character[] characters = new Character[MAX_ARRAY_SIZE];

	/**
	 * Initializes the array of character references with a new character for each
	 * element within the characters array.
	 */
	public CharacterGame() {
		for (int index = 0; index < characters.length; index++) {
			if (index < 5) {
				characters[index] = new WaterElemental();
			} else {
				characters[index] = new MutantBeaver();
			}
		}
	}

	/**
	 * This is the core logic of the program, it
	 * <ul>
	 * <li>displays the characters available</li>
	 * <li>asks the user to enter two index values</li>
	 * <li>calls method fight to process the combat match</li>
	 * <li>heals all of the characters to full health after the combat</li>
	 * <li>prompts the user if they want to run another fight-match (Y/N)</li>
	 * </ul>
	 */
	public void runGame() {
		String fightAgain = "N";
		do {
			System.out.println("Here are the characters available:");
			for (int index = 0; index < characters.length; index++) {
				System.out.printf("index %d, %s%n", index, characters[index].toString());
			}
			System.out.println("\nChoose characters to fight");
			System.out.print("\nPlease enter index of character 1: ");
			int playerOne = UserInput.readInt();

			System.out.print("\nPlease enter index of character 2: ");
			int playerTwo = UserInput.readInt();
			System.out.println();

			fight(playerOne, playerTwo);

			System.out.println("\nAll of the characters are healed for next match");
			for (Character character : characters) {
				character.fullRecover();
			}

			System.out.println("\nTo run another fight, enter \"Y\"");
			fightAgain = UserInput.readText();

		} while (fightAgain.equalsIgnoreCase("Y"));
	}

	/**
	 * this method examines the indexes for the two characters to verify that:
	 * <ul>
	 * <li>the indexes are not the same, i.e. cannot fight itself</li>
	 * <li>the indexes are within bounds of the array indexes</li>
	 * <li>copies the references from the indexed array elements and calls method
	 * conductFight(Character,Character)</li>
	 * </ul>
	 * 
	 * @param index1 array index of first selected character
	 * @param index2 array index of second selected character
	 */
	private void fight(int index1, int index2) {
		if (index1 == index2) {
			System.out.println("Fighter cannot fight itself");
		} else if (index1 < 0 || index2 < 0) {
			System.out.println("Invalid fighter selection");
		} else if (index1 >= characters.length || index2 >= characters.length) {
			System.out.println("Invalid fighter selection");
		} else {
			Character one = characters[index1];
			Character two = characters[index2];
			conductFight(one, two);
		}
	}

	/**
	 * This method uses a loop to conduct a combat match, i.e. fight, between two
	 * characters. The loop procedurally has combatant1 attack combatant2, and
	 * combatant2 then attack combatant2. It then reports the results to the screen.
	 * The loop stops either when one of the combatant has health at or below zero,
	 * or after 100 rounds of combat as a sanity-check so the program does not run
	 * forever.
	 * 
	 * @param combatant1 The first combatant
	 * @param combatant2 The second combatant
	 */
	private void conductFight(Character combatant1, Character combatant2) {
		boolean stopFight = false;
		int combatRoundCount = 1;
		while (!stopFight) {

			Attack attack1 = combatant1.attack();
			AttackReport defend2 = combatant2.defend(attack1);

			Attack attack2 = combatant2.attack();
			AttackReport defend1 = combatant1.defend(attack2);

			System.out.println("Round: " + combatRoundCount);
			System.out.println(reportCombat(combatant1, attack1, defend1));
			System.out.println();

			System.out.println(reportCombat(combatant2, attack2, defend2));
			System.out.println();

			if (isCombatEndedCharacterDeath(combatant1, combatant2)) {
				stopFight = true;
				System.out.println(reportEndOfMatch(combatant1, combatant2));
				System.out.println();

			}
			combatRoundCount++;
			if (combatRoundCount >= 100) {
				stopFight = true;
				System.out.println(reportEndOfMatch(combatant1, combatant2));
				System.out.println();
			}
		}
	}

	/**
	 * This method generates a report on how a character performed, with the
	 * combatant details, what attack they generated, how they defended.
	 * 
	 * @param combatant The combatant
	 * @param attack    The attack made by the combatant
	 * @param report    The attack defended against by the combatant
	 * @return A String with combatant details, the attack, and attack report
	 */
	private String reportCombat(Character combatant, Attack attack, AttackReport report) {
		String format = "%s%n%s%n%s";
		return String.format(format, combatant.toString(), attack.toString(), report.toString());
	}

	/**
	 * This method checks the current health of the two Characters and returns true
	 * if either once has current health less than or equal to zero.
	 * 
	 * @param combatant1 The first combatant
	 * @param combatant2 The second combatant
	 * @return true if either combatant has zero or less health
	 */
	private boolean isCombatEndedCharacterDeath(Character combatant1, Character combatant2) {
		boolean isCombatEnded = false;
		if (combatant1 != null && combatant2 != null) {
			if (combatant1.getCurrentHealth() <= 0 || combatant2.getCurrentHealth() <= 0) {
				isCombatEnded = true;
			}
		}
		return isCombatEnded;
	}

	/**
	 * This method accepts two Characters, and based on their current health
	 * determines who won, who lost, or if a fight was a draw (i.e. both characters
	 * are living). This method assumes that the fighting is over.
	 * 
	 * @param combatant1 The first combatant
	 * @param combatant2 The second combatant
	 * @return report on fight outcome, based on the combatant's current health
	 */
	private String reportWinLoose(Character combatant1, Character combatant2) {
		String report = "winner could not be determined";
		if (combatant1 != null && combatant2 != null) {
			int health1 = combatant1.getCurrentHealth();
			int health2 = combatant2.getCurrentHealth();
			if (health1 < 0 && health2 < 0) {
				report = "no winner, both combatants are dead";
			} else if (health1 < 0) {
				report = "combatant 1 lost.";
			} else if (health2 < 0) {
				report = "combatant 2 lost.";
			} else {
				report = "tie, both combatants are alive";
			}
		}
		return report;
	}

	/**
	 * This method simply generates a report based on the statistics of the two
	 * characters passed, calling toString() on each.
	 * 
	 * @param combatant1 The first combatant
	 * @param combatant2 The second combatant
	 * @return A report based on calling toString() on each Character
	 */
	private String reportEndingState(Character combatant1, Character combatant2) {
		String format = "%s%n%s%n%s";
		return String.format(format, "Ending Statistics", combatant1.toString(), combatant2.toString());
	}

	/**
	 * This method generates a report suitable for display after a fight has
	 * concluded, it includes the fight outcome (winner, looser, draw), as well as
	 * each Character's state.
	 * 
	 * @param combatant1 The first combatant
	 * @param combatant2 The second combatant
	 * @return report on fight outcome, and each Character's state.
	 */
	private String reportEndOfMatch(Character combatant1, Character combatant2) {
		String format = "%s%n%s";
		return String.format(format, reportWinLoose(combatant1, combatant2), reportEndingState(combatant1, combatant2));
	}
}
