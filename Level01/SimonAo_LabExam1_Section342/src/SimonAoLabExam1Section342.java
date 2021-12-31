/*
 * Author: Simon Ao
 * Professor: Mohammad Patoary
 * Date: Oct 7, 2020
 * Description: Lab exam 1 section 342 sample answer by Simon Ao
 */
/*
 * Testing CellPhone Program
*/
public class SimonAoLabExam1Section342 {
	/*
	 * Entry point for the application
	*/
	public static void main(String[] args) {
		CellPhone phone = new CellPhone();
		System.out.println("7500 mAh for the battery size,and 94.25 cm squared for screen size");

		phone.setBatterSize(7500);
		phone.setScreenSize(94.25);
		

		System.out.println(phone.getBatterySize());	
		System.out.println(phone.getScreenSize());	
		
		System.out.println("Testing work method: "+phone.estimateBatterLife());	
		System.out.println("Program by Simon Ao");	
		
	}//end main

}//end class
