/*
 * Copyright(2021) Algonquin College
 * CST8284 (21W) Assignment 2 Starter Code
 */
package com.algonquincollege.cst8284.assignment2.model;

/**
 * This class contains two pieces of information, the first
 * is a text-based description of how a character defended
 * against an attack, the second is the actualDamage the 
 * character received, note that actualDamage could have a
 * negative value indicating healing. Class Character uses
 * this class as part of it's defend(Attack) method.
 * @see Character
 * @author piedas
 *
 */
public class AttackReport {
	/**
	 * Contains the report detailing the defense.
	 */
	private String report;
	
	/**
	 * Contains the damage received from an attack,
	 * can be a negative value (healing).
	 */
	private int actualDamage;
	
	/**
	 * Sets the report to "no report" and the actual damage
	 * to zero (i.e. 0).
	 */
	public AttackReport() {
		this("no report", 0);
	}
	
	/**
	 * Sets the report and actualDamage to the respective fields.
	 * Does not perform data validation.
	 * @param report the text data for this report
	 * @param actualDamage the actual damage for this report
	 */
	public AttackReport(String report, int actualDamage){
		this.report = report;
		this.actualDamage = actualDamage;
	}

	/**
	 * Gets the report for this object, can also return null
	 * @return the report for this object, or null
	 */
	public String getReport() {
		return report;
	}

	/**
	 * Sets the report for this object, does not perform
	 * any validation.
	 * @param report the report for this object
	 */
	public void setReport(String report) {
		this.report = report;
	}

	/**
	 * Gets the actual damage as part of this AttackReport,
	 * the returned value can be negative indicating healing.
	 * @return the actual damage
	 */
	public int getActualDamage() {
		return actualDamage;
	}

	/**
	 * Sets the actual damage value for this AttackReport, no
	 * data validation is performed.
	 * @param actualDamage the actual damage
	 */
	public void setActualDamage(int actualDamage) {
		this.actualDamage = actualDamage;
	}
	
	/**
	 * Overridden toString() returns a formatted message consisting
	 * of the report + " actual damage: " + actualDamage.
	 * Uses methods getReport() and getActualDamage()
	 */
	@Override
	public String toString() {
		return getReport() + " actual damage: " + getActualDamage();
	}
}
