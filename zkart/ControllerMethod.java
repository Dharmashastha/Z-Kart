package zkart;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import common.CustomException;
import common.HelperUtility;
import common.InputCenter;

public class ControllerMethod 
{
	InputCenter inputCall = new InputCenter();

	ZkartLogicLayer logicCall = new ZkartLogicLayer();

	Connected connectCall = logicCall.getConnect();
	
	private void creditsCheck(int credits, int creditsUsed) throws CustomException {
		if (credits < creditsUsed) {
			throw new CustomException("Check Your entry creditsused but you have credit " + credits);
		}
	}
	
	private void cardOptions(double totalPrice,boolean flag) throws CustomException
	{
		System.out.println("Your Card Number");
		long card = inputCall.getLong();
		String saved = String.valueOf(card);
		if (saved.length() == 16 && saved.charAt(0) - '0' >= 4) {
			System.out.println("Payment Successful " + totalPrice);
			flag = true;
		} else {
			System.out.println("Payment Failed");
		}
	}

	private void paymentOptions(double totalPrice) throws CustomException {
		boolean flag = false;

		while (!flag) {

			System.out.println("Press -> 1.Cash\nPress ->  2.Card\nPress ->  3.UPI ID\nPress -> 4.Exit");

			System.out.println("Enter Your Choice");

			int choice = inputCall.getInt();

			switch (choice) {
			case 1: {
				System.out.println("Payment Successful " + totalPrice);
				flag = true;
				break;
			}

			case 2: {
				cardOptions(totalPrice,flag);
				break;
			}

			case 3: {
				System.out.println("Enter the Your Upi Id");

				inputCall.getString();

				System.out.println("Payment Successful " + totalPrice);
				
				flag = true;
				break;
			}

			case 4: {
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

	private void shopping() throws CustomException {
		
		boolean flag = false;
		
		while(!flag)
		{
			System.out.println("Press -> 1.Show Shopping\nPress -> 2.Add My cart\nPress -> 3.Exit");

			int choice = 0;
			try {
				choice = inputCall.getInt();
			} catch (CustomException e) {
				System.out.println(e.getMessage());
				// e.printStackTrace();
			}

			switch (choice) {
			case 1: {
				System.out.println(logicCall.showShop());
				break;
			}

			case 2: {
				System.out.println("Enter Your Desire Model");
				String model = inputCall.getString();
				logicCall.addMyCart(model);

				break;
			}
			case 3:
			{
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
	
	private void orderSetValue(String userName,int creditsUsed,List<String> model,double totalPrice) throws CustomException
	{
		OrderHistoryInfo orderCall = new OrderHistoryInfo();

		orderCall.setInvoiceNumber(logicCall.invoiceNumberGenerate());

		orderCall.setDate(logicCall.findDate());

		orderCall.setCreditsUsed(creditsUsed);

		orderCall.setTotalPrice(totalPrice);

		orderCall.setCreditsAdded(logicCall.calculateCredits(totalPrice));

		orderCall.setUserName(userName);

		orderCall.setModel(model);
		
		logicCall.creditUpdate(userName, orderCall.getCreditsAdded());
		
		logicCall.storeOrderHistory(orderCall);
		
		connectCall.orderHistoryInsert(orderCall);
	}

	private void myCart(String userName) throws CustomException {

		List<InventoryInfo> cartList = logicCall.myCartList();

		List<String> model = new ArrayList<>();

		int size = cartList.size();

		double totalPrice = 0;

		for (int iter = 0; iter < size; iter++) {
			InventoryInfo inventoryCall = cartList.get(iter);

			model.add(inventoryCall.getModel());

			totalPrice += logicCall.calculatePrice(inventoryCall.getPrice(), inventoryCall.getDiscount());
			
			logicCall.stockUpdate(inventoryCall.getModel());
		}
		CustomerInfo customerCall = logicCall.getCustomer(userName);

		int credit = customerCall.getCredit();

		System.out.println("You have Credits : " + credit);

		System.out.println("How many credits used you would like to offer : ");

		int creditsUsed = inputCall.getInt();

		creditsCheck(credit, creditsUsed);

		totalPrice -= creditsUsed;

		paymentOptions(totalPrice);
		
		orderSetValue(userName, creditsUsed, model, totalPrice);
		
		logicCall.clearMyCart();
	}

	private void orderHistory(String userName) throws CustomException {
		
		List<OrderHistoryInfo> orderList = logicCall.getOrderHistory(userName);

		int size = orderList.size();

		for (int iter = 0; iter < size; iter++) {
			OrderHistoryInfo orderCall = orderList.get(iter);

			System.out.println("Invoice Number : " + orderCall.getInvoiceNumber());

			System.out.println("Date : " + orderCall.getDate());

			List<String> modelList = orderCall.getModel();

			int length = modelList.size();

			for (int ite = 0; ite < length; ite++) {
				
				String model = modelList.get(ite);

				InventoryInfo inventoryCall = logicCall.getInventoryInfo(model);

				System.out.println(" Category          Brand          Model        Price ");

				System.out.println("---------          ------         -----        ------");

				System.out.println(inventoryCall.getCategory() + "          " + inventoryCall.getBrand() + "         "
						+ inventoryCall.getModel() + "        "
						+ logicCall.calculatePrice(inventoryCall.getPrice(), inventoryCall.getDiscount()));
			}

			System.out.println("Credits Used : " + orderCall.getCreditsUsed());

			System.out.println("Total Price : " + orderCall.getTotalPrice());

			System.out.println("Credits Added : " + orderCall.getCreditsAdded());

		}

	}
	
	private void updateDiscount() throws CustomException
	{
		System.out.println("Enter the Model Name");
		
		String model = inputCall.getString();
		
		System.out.println("Update the Discount Value");
		
		int updateDiscount = inputCall.getInt();
		
		System.out.println(logicCall.discountUpdate(model, updateDiscount));
		
	}
	
	private void updateStock() throws CustomException
	{
		System.out.println("Enter the Model Name");
		
		String model = inputCall.getString();
		
		System.out.println("Update the Stock Value");
		
		int updateStock = inputCall.getInt();
		
		System.out.println(logicCall.stockUpdate(model, updateStock));
		
	}
	
	private void adminOptions() throws CustomException
	{
		System.out.println("Welcome to Z-Kart Admin");
		
		boolean flag = false;
		
		while(!flag)
		{
			System.out.println("Press -> 1.Display all  items in stock\nPress -> 2.Low items in stock Display\n"
					+ "Press -> 3.Update Stock Value of an item\nPress -> 4.Update discount value of an item\nPress -> 5.Exit");
			
			System.out.println("Enter the Choice");
			
			int choice = inputCall.getInt();
			
			switch(choice)
			{
				case 1:
				{
					System.out.println(logicCall.showStockItems());
					break;
				}
				
				case 2:
				{
					System.out.println(logicCall.showLessStockItems());
					System.out.println("Hurry Up!Quick Add to Stock");
					break;
				}
				
				case 3:
				{
					updateStock();
					break;
				}
				
				case 4:
				{
					updateDiscount();
					break;
				}
				
				case 5:
				{
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

	private void customerOptions(String userName) throws CustomException {

		boolean flag = false;

		while (!flag) {
			System.out
					.println("Press -> 1.Shopping\nPress -> 2.MyCart\nPress -> 3.My Order History\nPress -> 4.Logout");

			System.out.println("Enter the Choice");

			int	choice = inputCall.getInt();

			switch (choice) {
			case 1: {
				shopping();
				break;
			}

			case 2: {
				myCart(userName);
				break;
			}

			case 3: {
				orderHistory(userName);
				break;
			}

			case 4: {
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

	public void loginCheck() throws CustomException {
		System.out.println("Enter Your UserName :");

		String userName = inputCall.getString();

		System.out.println("Enter Your Password");

		String password = inputCall.getString();
		
		if(userName.equals("admin@zoho.com") && password.equals("xyzzy"))
		{
			adminOptions();
		}
		else if(logicCall.checkLogin(userName, password)) {
			System.out.println("WelCome To Z-Kart");
			customerOptions(userName);

		}
		else {
			throw new CustomException("Your Password is Invalid\nPress -> 2. Sign Up");
		}
	}

	private void checkPassword(String password, String rePassword) throws CustomException {
		if (!password.equals(rePassword)) {
			throw new CustomException("Those passwords didn’t match! Try again.");
		}
	}

	private boolean passwordValid(String password) throws CustomException {
		HelperUtility.checkString(password);
		String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,15}$";

		Pattern patt = Pattern.compile(regex);

		Matcher match = patt.matcher(password);

		return match.matches();
	}

	private void mobileCheck(String mobileNumber) throws CustomException {
		if (mobileNumber.matches("[a-zA-Z@#$%^&+=]")) {
			throw new CustomException("Mobile Number Only Numbers");
		}
	}

	private String enterPassword() throws CustomException {
		System.out.println("Your password must be length 8 to 15,numeric,mixed case and special characters{@#$%^&+=}");

		System.out.println("Enter the New Password");

		String password = inputCall.getString();

		System.out.println("Re-Enter New Password");

		String checkPassword = inputCall.getString();

		checkPassword(password, checkPassword);

		return password;
	}
	
	private void customerSetValue(String userName,String password,String name,String mobileNumber) throws CustomException
	{
		CustomerInfo custCall = new CustomerInfo();
		
		custCall.setUserName(userName);

		custCall.setPassword(password);

		custCall.setName(name);

		custCall.setMobileNumber(mobileNumber);

		custCall.setCredit(0);
		
		logicCall.storeCustomerDetails(custCall);
		
		connectCall.customerInsertInfo(custCall);
	
	}

	public void newCustomer() throws CustomException {

		System.out.println("Enter the New UserName");

		String userName = inputCall.getString();

		String password = enterPassword();

		while (!passwordValid(password)) {
			System.out.println("Your password Invalid");
			password = enterPassword();
		}

		System.out.println("Password Accepted");

		System.out.println("Enter the your name");

		String name = inputCall.getString();

		System.out.println("Enter the Mobile Number");

		String mobileNumber = inputCall.getString();

		mobileCheck(mobileNumber);
		
		customerSetValue(userName, password, name, mobileNumber);
	}

	
	private void inventorySetValue(String category,String brand,String model,double price,int stock,int discount) throws CustomException
	{
		InventoryInfo inventoryCall = new InventoryInfo();
		
		inventoryCall.setCategory(category);

		inventoryCall.setBrand(brand);

		inventoryCall.setModel(model);

		inventoryCall.setPrice(price);

		inventoryCall.setStock(stock);

		inventoryCall.setDiscount(discount);
		
		logicCall.storeInventoryDetails(inventoryCall);
		
		connectCall.inventoryInsertInfo(inventoryCall);
	}
	
	public void newInventory() throws CustomException {
		
		System.out.println("Enter the Category");

		String category = inputCall.getString();

		System.out.println("Enter the Brand");

		String brand = inputCall.getString();

		System.out.println("Enter the Model");

		String model = inputCall.getString();

		System.out.println("Enter the Price");

		double price = inputCall.getDouble();

		System.out.println("Enter the Stock");

		int stock = inputCall.getInt();

		System.out.println("Enter the Discount");

		int discount = inputCall.getInt();
		
		inventorySetValue(category, brand, model, price, stock, discount);
	}

	public void createNewTable() throws CustomException {
		
		/*CREATE TABLE CustomerInfo (UserName VARCHAR(30),Password VARCHAR(15) NOT NULL,Name VARCHAR(30),MobileNumber VARCHAR(30) NOT NULL,Credit INT NOT NULL,PRIMARY KEY(UserName),UNIQUE (MobileNumber));
		
		CREATE TABLE InventoryInfo (Category VARCHAR(30) NOT NULL,Brand VARCHAR(30) NOT NULL,Model VARCHAR(30),
		Price FLOAT(15) NOT NULL,Stock INT NOT NULL,Discount INT NOT NULL,PRIMARY KEY(Model));
		
		CREATE TABLE OrderHistoryInfo (InvoiceNumber INT,Date VARCHAR(30) NOT NULL,CreditsUsed INT NOT NULL,TotalPrice Float(15) NOT NULL,CreditsAdded INT NOT NULL,UserName VARCHAR(30) NOT NULL,Model VARCHAR(255) NOT NULL,FOREIGN KEY (UserName) REFERENCES CustomerInfo(UserName),PRIMARY KEY(InvoiceNumber));*/
		
		System.out.println("Enter the Create New Table Query");

		System.out.println(connectCall.createTableQuery(inputCall.getString()));
	}
}
