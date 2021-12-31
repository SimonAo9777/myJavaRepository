package com.algonquincollege.cst8284.section312.simulation;

/**
 * This class is only used to start the application, with method main.
 * The SpaceProgram is instantiates the SpaceExplorationSimulator and run this game
 * @author Simon Ao <br>
 * Student ID:040983402 <br>
 * Section:CST8284-312 <br>
 * Update:2021-2-22
 * @version Java 1.8.0
 * 
 */
public class SpaceProgram {

	/**
	 * Entry point for this application.
	 * @param args instantiate the SpaceExplorationSimulator.
	 * 
	 */
	public static void main(String[] args) {
		// TODO instantiate the SpaceExplorationSimulator
		// TODO call the runSimulation() method
		SpaceExplorationSimulator simulator = new SpaceExplorationSimulator();
		simulator.runSimulation();
	}

}
