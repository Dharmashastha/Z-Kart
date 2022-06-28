package zkart;

import common.CustomException;

public class Controller {
		
	public static void main(String[] args) {

		ControllerMethod conCall = new ControllerMethod();
		
		boolean flag = false;

		int choice = 0;
		
		try 
		{
			conCall.logicCall.writeDbInfo();
			conCall.logicCall.readDbInfo();
		}
		catch(CustomException e)
		{
			System.out.println(e.getMessage());
		}
		
		
		while (!flag) {

			System.out.println(
					"Press -> 1.Login\nPress -> 2.Sign up\nPress -> 3.Add New Inventory\nPress -> 4.Create new Table\nPress -> 5.Exit");

			System.out.println("Enter Your Choice");

			try {
				choice = conCall.inputCall.getInt();
			} catch (CustomException e) {
				e.printStackTrace();
			}

			switch (choice) {
			case 1: {
				try {
					conCall.loginCheck();
				} catch (CustomException e) {
					// e.printStackTrace();
					System.out.println(e.getMessage());
				}
				break;
			}

			case 2: {
				try {
					conCall.newCustomer();
				} catch (CustomException e) {
					// e.printStackTrace();
					System.out.println(e.getMessage());
				}
				break;
			}

			case 3: {
				try {
					conCall.newInventory();
				} catch (CustomException e) {
					// e.printStackTrace();
					System.out.println(e.getMessage());
				}
				break;
			}

			case 4: {
				try {
					conCall.createNewTable();
				} catch (CustomException e) {
					System.out.println(e.getMessage());
					// e.printStackTrace();
				}
				break;
			}

			case 5: {
				flag = true;
				break;
			}

			default: {
				System.out.println("Enter the Valid Choice");
				break;
			}
			}
		}
	}

}
