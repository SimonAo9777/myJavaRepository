// Needs programmer comments on top of file, class, constructor(s), methods

public class MathMachine {
	private double leftOperand = 0;
	private double rightOperand = 0;
	
	public MathMachine() {
		
	}
	
	public MathMachine(double leftOperand, double rightOperand) {
		this.leftOperand = leftOperand;
		this.rightOperand = rightOperand;
	}
	
	public double getLeftOperand() {
		return leftOperand;
	}
	
	public void setLeftOperand(double leftOperand) {
		this.leftOperand = leftOperand;
	}
	
	public double getRightOperand() {
		return rightOperand;
	}
	
	public void setRightOperand(double rightOperatnd) {
		this.rightOperand = rightOperatnd;
	}
	
	public double add() {
		return leftOperand + rightOperand;
	}
	
	public double subtract() {
		return leftOperand - rightOperand;
	}
	
	public double multiply() {
		return leftOperand * rightOperand;
	}
	
	public double divide() {
		return leftOperand / rightOperand;
	}
	
	public double remainder() {
		return leftOperand % rightOperand;
	}
	
	public String reportStatus() {
		return String.format("Left Operand: %f, Right Operand: %f", leftOperand, rightOperand);
	}

}
