// Needs programmer comments on top of file, class, constructor(s), methods

public class MenuSystem {
	public static final int EDIT_VALUES = 1;
	public static final int SHOW_STATUS = 2;
	public static final int ADD = 3;
	public static final int SUBTRACT = 4;
	public static final int MULTIPLY = 5;
	public static final int DIVIDE = 6;
	public static final int REMAINDER = 7;
	public static final int EXIT = 8;

	public MenuSystem() {
		
	}
	
	public String optionsList() {
		String formatString = "%nPlease select from an option below:%n";
		formatString += "%d to edit the operands%n";
		formatString += "%d to view operand values%n";
		formatString += "%d to add the operands%n";
		formatString += "%d to subtract the operands%n";
		formatString += "%d to multiply the operands%n";
		formatString += "%d to divide the operands%n";
		formatString += "%d to calculate the remainder%n";
		formatString += "%d to exit the program%n";
		formatString += "Program by ***Your Name Here***"; // fix this part.
		
		return String.format(formatString, EDIT_VALUES, SHOW_STATUS, ADD, 
				SUBTRACT, MULTIPLY, DIVIDE, REMAINDER, EXIT);
	}
}
