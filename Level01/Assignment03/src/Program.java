// Needs programmer comments on top of file, class, constructor(s), methods

public class Program {

	public static void main(String[] args) {
		// replace all of this with the program logic for main,
		// this is just testing the classes provided.
		MathMachine math = new MathMachine();
		MenuSystem menu = new MenuSystem();
		UserInput user = new UserInput();
		
		System.out.println(menu.optionsList());
		
		System.out.println("Please enter an option from 0 to 8");
		int inputTest1 = user.input(0, 8);
		System.out.println("user entered: " + inputTest1);
		
		System.out.println("Please enter an option from -1000.0 to 1000.0");
		double inputTest2 = user.input(-1000.0, 1000.0);
		System.out.println("user entered: " + inputTest2);
		
		math.setLeftOperand(10.0);
		math.setRightOperand(20.0);
		
		System.out.println("Running Tests");
		System.out.println("Left operand: " + math.getLeftOperand());
		System.out.println("Right operand: " + math.getRightOperand());
		System.out.println("status report: " + math.reportStatus());
		System.out.println("add: " + math.add());
		System.out.println("subtract: " + math.subtract());
		System.out.println("divide: " + math.divide());
		System.out.println("multiply: " + math.multiply());
		System.out.println("remainder: " + math.remainder());

	}

}
