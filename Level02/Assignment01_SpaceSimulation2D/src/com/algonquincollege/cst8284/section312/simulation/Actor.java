package com.algonquincollege.cst8284.section312.simulation;

/**
 * This class abstracts a Actor, with icon and isMoved.
 * @author Simon Ao <br>
 * Student ID:040983402 <br>
 * Section:CST8284-312 <br>
 *  Update:2021-2-22
 * @version Java 1.8.0
 * 
 */
public class Actor {
	/**
	 * Icon for this Actor
	 */
	private char icon;

	/**
	 * Choose whether to move for this Actor
	 */
	private boolean isMoved;

	/**
	 * Default constructor, sets Icon to 'A'
	 */
	public Actor() {
		this('A');
	}

	/**
	 * Overloaded constructor.
	 * 
	 * @param  icon of the actor
	 * 
	 */
	public Actor(char icon) {
		this.icon = icon;
	}

	/**
	 * Accessor for icon
	 * 
	 * @return name icon value
	 * 
	 */
	public char getIcon() {
		return icon;
	}

	/**
	 * Mutator for icon
	 * 
	 * @param  icon for this actor
	 * 
	 */
	public void setIcon(char icon) {
		this.icon = icon;
	}

	/**
	 * Accessor for isMoved
	 * 
	 * @return  isMoved value
	 * 
	 */
	public boolean isMoved() {
		return isMoved;
	}

	/**
	 * Mutator for move
	 * 
	 * @param  isMoved for this Actor
	 * 
	 */
	public void setMoved(boolean isMoved) {
		this.isMoved = isMoved;
	}

}
