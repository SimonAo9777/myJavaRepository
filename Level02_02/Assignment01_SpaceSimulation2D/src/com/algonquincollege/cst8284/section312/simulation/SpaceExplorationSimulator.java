package com.algonquincollege.cst8284.section312.simulation;

import java.util.Scanner;

/**
 * This class is a ship moving simulation game. The ship may collide with the
 * Asteroid during the movement and move the ship away according to random
 * circumstances. Eventually it may leave successfully or be destroyed by
 * collision.
 * 
 * @author Simon Ao <br>
 * Student ID:040983402 <br>
 * Section:CST8284-312 <br>
 * Update:2021-2-22
 * @version Java 1.8.0
 * 
 */
public class SpaceExplorationSimulator {

	/**
	 * Set the maximum value to rows , and limit 5 ships to move in the game
	 */
	private final int MAX_ROWS;

	/**
	 * Set the maximum value to columns, the number of positions the ship moves
	 */
	private final int MAX_COLUMNS;

	/**
	 * Set the maximum value to ships
	 */
	private final int MAX_SHIPS;

	/**
	 * Number of ships collided and destroyed
	 */
	private int shipsDestroyed = 0;

	/**
	 * Number of ships escaped
	 */
	private int shipsEscaped = 0;

	/**
	 * Record the total number of game runs
	 */
	private int turnCount = 0;

	/**
	 * The user chooses whether to display the bar
	 */
	private boolean useBar;

	/**
	 * Scanner is used to get inputs from the standard input stream.
	 * 
	 * @see java.util.Scanner
	 */
	private Scanner input = new Scanner(System.in);

	/**
	 * 2d Array of references to actors
	 */
	private final Actor[][] actors;

	/**
	 * Chained default constructor,set row to 5,column to 20
	 * 
	 */
	public SpaceExplorationSimulator() {
		this(5, 20);
	}

	/**
	 * Overloaded constructor
	 * 
	 * @param rowNum MAX_ROWS of the SpaceExplorationSimulator
	 * @param colNum MAX_COLUMNS of the the SpaceExplorationSimulator
	 * rowNum MAX_SHIPS of the the SpaceExplorationSimulator Attempts to
	 * create an actor with the array specified
	 */
	public SpaceExplorationSimulator(int rowNum, int colNum) {
		MAX_ROWS = rowNum;
		MAX_COLUMNS = colNum;
		MAX_SHIPS = rowNum;
		actors = new Actor[MAX_ROWS][MAX_COLUMNS];
	}

	/**
	 * This category is the entrance to the game preparation 
	 * and the program ends when the conditions are met 
	 * Keep all original codes and cannot be modified
	 */
	public void runSimulation() {
		
		 //run the simulation asking the user to continue for each turn, or until the
		 // end of the simulation is detected. add a new asteroid using the addAsteroid()
		 // method every 10 turns. Senior Programmer: This method was verified, do not
		 // change it or we will fire you.
		
		turnCount = 0;
		boolean continueSimulation = true;

		System.out.println("Welcome to Space Simulation");		
		System.out.println("Use enter key to run next turn");
		System.out.println("Typing anything other than return will end program");
		initializeArray();
		drawSpaceSimulation();

		do {
			addAsteroid();
			prepareActorsForMovement();
			moveActors();
			drawSpaceSimulation();
			turnCount++;
			System.out.println("Use enter key to run next turn");
			System.out.println("Typing anything other than return will end program");
			String userInput = input.nextLine();
			if (userInput.length() > 0) {
				continueSimulation = false;
			}
		} while (continueSimulation && !isEndOfSimulation());
		//once the simulation ends the program shuts down.
		System.out.println("Thanks for using the simulation");
	}

	private void initializeArray() {	
		 // set up for start of simulation, placing actors put 5 ships at the far left in
		 //each of the rows in column 0 add one asteroid using method addAsteroid()
		 // Set up whether to use bar to select entrance		
		 System.out.println("Do you want to draw vertical bars?");
		 String line = input.nextLine();
		 useBar = !line.equalsIgnoreCase("n");
		 
		for (int row = 0; row < actors.length; row++) {
			actors[row][0] = new Actor('S');
		}
		addAsteroid();
	}

	private void addAsteroid() {		
		 // 10% chance that an asteroid will be added to the play field when this method
		 // is called.		
		final int CHANCE = 10;
		int result = (int) (Math.random() * 100) + 1;
		if (result < CHANCE) {			
			 // place one asteroid in the right-most column, but in a row randomly selected
			 // from 0 to 4. if there is a ship in the right-most column when the asteroid is
			// added it should destroy the ship and increment the shipsDestroyed variable.			
			int row = (int) (Math.random() * 5);
			int col = actors[row].length - 1;
			if (actors[row][col] == null) {
				actors[row][col] = new Actor('A');
			} else if (actors[row][col].getIcon() == 'S') {
				actors[row][col] = new Actor('A');
				shipsDestroyed++;
			} else {//asteroid to asteroid, so they destroy each other 				
				actors[row][col] = null;
			}
		}
	}

	/**
	 * Rule for a single move	 * 
	 * @param row    current row
	 * @param col    current column
	 * @param newRow new row
	 * @param newCol new column
	 */
	private void moveCell(int row, int col, int newRow, int newCol) {
		if (!isMoveOffBoard(newRow, newCol)) {			
			if (actors[newRow][newCol] == null) {//The ship did not collision
				actors[newRow][newCol] = actors[row][col];
				actors[row][col] = null;
			} else if (actors[newRow][newCol].getIcon() == 'A') {			
				if (actors[row][col].getIcon() == 'S') {//The ship to asteroid
					actors[row][col] = null;
					shipsDestroyed++;
				} else {

					/**
					 * The asteroid to asteroid
					 */
					actors[row][col] = null;
					actors[newRow][newCol] = null;
				}
			} else if (actors[newRow][newCol].getIcon() == 'S') {			
				if (actors[row][col].getIcon() == 'A') {//The asteroid to ship
					actors[newRow][newCol] = actors[row][col];
					actors[row][col] = null;
					shipsDestroyed++;
				} else {//The ship to ship
					
				}
			} else {
				
				 //no move, it's a ship up top
				
			}
		} else {		
			if (actors[row][col].getIcon() == 'A') {//ship escaped to right
				actors[row][col] = null;				
			} else if (actors[row][col].getIcon() == 'S' && newCol > col) {//ship escaped to right
				actors[row][col] = null;
				shipsEscaped++;
			}
		}

	}

	/**
	 * The loop of this method simulates the ship moving in the direction of up, down and right,
	 * using three cases to produce the result of destruction or escape
	 * */
	private void moveActors() {		
		 // - loop over the 2-D array and move each actor, depending on the icon, S for
		 // ship, A for asteroid. movement rules: - Ships move up, right, down at random.
		 // - Asteroids move left only. - After each actor is moved, it is flagged so
		 // that it is not moved more than once per call of this method. Another method
		 // is used to flag the actors as movable before this method is called again. -
		 // Ship to Asteroid: Ship is destroyed (set element to null) - Ship to Ship: The
		 // ship skips it's turn and does not move. - Asteroid to Ship: Ship is removed,
		 // asteroid moves. - Asteroid to Asteroid: Both asteroids are removed. - Ships
		 // are not allowed to leave the top or bottom of the play area - Ships that are
		 // destroyed should be counted. - Ships that attempt to move past the right-most
		 // column are considered safe and should be removed from the simulation, i.e.
		 // mark their place in the 2D array as null, and counted as safe.		
		for (int row = 0; row < actors.length; row++) {
			for (int col = 0; col < actors[row].length; col++) {				
				 // if the element has an actor, and they have not already moved.				
				if (actors[row][col] != null && !actors[row][col].isMoved()) {
					int newRow = row;
					int newCol = col;
					actors[row][col].setMoved(true);				
					if (actors[row][col].getIcon() == 'S') {//ship move logic					
						int direction = (int) (Math.random() * 3);//ship move 0 to 2
						switch (direction) {					
						case 0: //ship move up						
							newRow = row - 1; //deduct 1 to move 'up'
							moveCell(row, col, newRow, newCol);
							break;					
						case 1: //ship move right
							newCol = col + 1;
							moveCell(row, col, newRow, newCol);
							break;						
						case 2://ship move down						
							newRow = row + 1;//ship add 1 to move 'down'
							moveCell(row, col, newRow, newCol);
							break;
						}						
					} else {//it is an asteroid we are moving
						newCol = col - 1;
						moveCell(row, col, newRow, newCol);
					}
				}
			}
		}
	}

	/**
	 * Program to complete row text by running display ship destroyed,escaped and
	 * turn count
	 */
	private void drawSpaceSimulation() {

		
		// draw the grid for the simulation using nested loops. write out your name on
		 // screen as author for each loop. Sample:
		//
		 // |S| | | | |A| | | | | | | | | | | | | | | | |S| | |S| | | | | |A| | | | | | |
		 // | | | | | | |S| | | | | | | | | | | | | | | | | | | | |S| | | | | | | | | | |
		 // |A| | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | Simulation by
		 // Simon Ao
		
		String bar = (useBar ? "|" : " ");
		String rowText = "";	
		for (Actor[] row : actors) {
			rowText += bar;
			for (Actor actor : row) {
				if (actor == null) {
					rowText += " ";
				} else {
					rowText += actor.getIcon();
				}
				rowText += bar;
			}
			rowText += "\n";
		}
		System.out.print(rowText);
		System.out.println("Simulation by Simon Ao");
		System.out.println("Ships destroyed: " + shipsDestroyed);
		System.out.println("Ships escaped: " + shipsEscaped);
		System.out.println("Turn number: " + turnCount);
		System.out.println();
	}

	private boolean isEndOfSimulation() {		
		 // ToDo: check the number of ships at start against the number of ships
		 // destroyed + number of ships safe if there are no more active ships return
		 // true to indicate end of the simulation.[ToDone?]		
		boolean result = false;
		if (shipsDestroyed + shipsEscaped >= MAX_SHIPS) {		
			result = true;
		}
		return result;
	}

	/**
	 * checks if a proposed move would be outside the bounds of the 2D array, if it
	 * is outside the bounds it reports true.
	 */
	private boolean isMoveOffBoard(int newRow, int newCol) {
		boolean isOffBoard = true;
		if ((newRow >= 0 && newRow < MAX_ROWS) && (newCol >= 0 && newCol < MAX_COLUMNS)) {			
			   isOffBoard = false;//attempt to move too far left or right
		}
		return isOffBoard;
	}

	/**
	 * This methods loops over the 2D array and re-activates all of the actors so
	 * that when the moveActors() methods loops over the array each actor will be
	 * moved at least once.
	 */
	private void prepareActorsForMovement() {
		for (int row = 0; row < actors.length; row++) {
			for (int col = 0; col < actors[row].length; col++) {
				if (actors[row][col] != null) {
					actors[row][col].setMoved(false);
				}
			}
		}
	}

}
