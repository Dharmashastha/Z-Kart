package common;

import java.util.Scanner;

public class InputCenter {

	Scanner inputCall = new Scanner(System.in);

	public String getString() throws CustomException {
		String inputString = "";

		try {
			inputString = inputCall.nextLine();
			HelperUtility.checkString(inputString);
		} catch (CustomException e) {
			System.out.println(e.getMessage());
		}
		return inputString;
	}

	public int getInt() throws CustomException {
		int inputInt = 0;

		try {
			String inputString = inputCall.nextLine();
			HelperUtility.checkString(inputString);
			inputInt = Integer.parseInt(inputString);
		} catch (CustomException e) {
			System.out.println(e.getMessage());
		}
		return inputInt;
	}

	public double getDouble() throws CustomException {
		double inputDouble = 0.00;

		try {
			String inputString = inputCall.nextLine();
			HelperUtility.checkString(inputString);
			inputDouble = Double.parseDouble(inputString);
		} catch (CustomException e) {
			System.out.println(e.getMessage());
		}
		return inputDouble;
	}

	public long getLong() throws CustomException {
		long inputLong = 0l;

		try {
			String inputString = inputCall.nextLine();
			HelperUtility.checkString(inputString);
			inputLong = Long.parseLong(inputString);
		} catch (CustomException e) {
			System.out.println(e.getMessage());
		}
		return inputLong;
	}

}
