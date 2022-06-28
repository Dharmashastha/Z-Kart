package zkart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import common.CustomException;
import common.HelperUtility;

public class ZkartLogicLayer {
	
	
	Storage storageCall = new Storage();

	private Map<String, CustomerInfo> storeCustomer = new HashMap<>();

	private Map<String, InventoryInfo> storeInventory = new HashMap<>();

	private Map<String, List<OrderHistoryInfo>> storeOrder = new HashMap<>();
	
	private List<InventoryInfo> cartList = new ArrayList<>();
	
	List<OrderHistoryInfo> orderList = null;
	
	public static long invoiceNumber = 1000000;
	
	
	Connected connect;
	
	public ZkartLogicLayer()
	{
		try 
		{
			Class<?> connectedCall = Class.forName("zkart.ZkartDbLayer");
		
			Object dummy = connectedCall.getDeclaredConstructor().newInstance();
		
			connect = (Connected) dummy;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Connected getConnect()
	{
		return connect;
	}
	
	
	private void nullCheckerCustomer(CustomerInfo customerCall) throws CustomException {
		if (Objects.isNull(customerCall)) {
			throw new CustomException("Your UserName is Invalid");
		}
	}
	
	private void nullCheckerInventory(InventoryInfo inventoryCall) throws CustomException {
		if (Objects.isNull(inventoryCall)) {
			throw new CustomException("Your Inventory is Invalid");
		}
	}
	
	private void nullCheckerOrder(OrderHistoryInfo orderCall) throws CustomException {
		if (Objects.isNull(orderCall)) {
			throw new CustomException("Your Order is Invalid");
		}
	}
	
	public void storeCustomerDetails(CustomerInfo customerCall) throws CustomException
	{
		nullCheckerCustomer(customerCall);
		storeCustomer.put(customerCall.getUserName(), customerCall);
	}
	
	public void storeInventoryDetails(InventoryInfo inventoryCall) throws CustomException
	{
		nullCheckerInventory(inventoryCall);
		storeInventory.put(inventoryCall.getModel(), inventoryCall);
	}
	
	public void storeOrderHistory(OrderHistoryInfo orderCall) throws CustomException
	{
		nullCheckerOrder(orderCall);
		
		if(Objects.isNull(storeOrder.get(orderCall.getUserName())))
		{
			 orderList = new ArrayList<>();
		}
		orderList.add(orderCall);
		storeOrder.put(orderCall.getUserName(), orderList);
	}
		

	public CustomerInfo getCustomer(String userName) throws CustomException {
		HelperUtility.checkString(userName);
		CustomerInfo customerCall = storeCustomer.get(userName);
		nullCheckerCustomer(customerCall);
		return customerCall;
	}

	public boolean checkLogin(String userName, String password) throws CustomException {
		HelperUtility.checkString(userName);
		HelperUtility.checkString(password);
		CustomerInfo customerCall = getCustomer(userName);
		return customerCall.checkerLogin(userName, password);
	}
	
	
	public double calculatePrice(double price,int discount)
	{
		double nowPrice = price * (discount/100);
		return price - nowPrice;
	}
	
	
	public String showShop()
	{
		StringBuilder saved = new StringBuilder();
		
		saved.append("Category         Brand         Model         Price         Stock\n");
		saved.append("--------         -----         -----         -----         -----\n");
			
		for(String model:storeInventory.keySet())
		{
			InventoryInfo inventoryCall = storeInventory.get(model); 			//"  " + inventoryCall.getDiscount() +"% off"
			
			saved.append(inventoryCall.getCategory() + "        \t " + inventoryCall.getBrand() + "        " + inventoryCall.getModel() + "        \t " + calculatePrice(inventoryCall.getPrice(), inventoryCall.getDiscount()) + "         " + inventoryCall.getStock() + "\n");
		}
		
		return saved.toString();
	}
	
	public void addMyCart(String model) throws CustomException
	{
		HelperUtility.checkString(model);
		InventoryInfo inventoryCall = storeInventory.get(model);
		myCart(inventoryCall);
	}
	
	public InventoryInfo getInventoryInfo(String model) throws CustomException
	{
		HelperUtility.checkString(model);
		return storeInventory.get(model);
	}
	
	private void myCart(InventoryInfo inventory) throws CustomException
	{
		nullCheckerInventory(inventory);
		cartList.add(inventory);
	}
	
	public List<InventoryInfo> myCartList()
	{
		return cartList;	
	}
	
	public long invoiceNumberGenerate()
	{
		return ++invoiceNumber;
	}
	
	public String findDate()
	{
		
		SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-YYYY");
		
		Date date = new Date(System.currentTimeMillis());
		
		String dateStr = dateformat.format(date);
		
		return dateStr;
	}
	
	public int calculateCredits(double totalPrice)
	{
		return (int) (20 * (totalPrice / 1000));
	}
	
	public List<OrderHistoryInfo> getOrderHistory(String userName) throws CustomException
	{
		HelperUtility.checkString(userName);
		return storeOrder.get(userName);
	}
	
	public String showStockItems()
	{
		StringBuilder saved = new StringBuilder();
		
		saved.append("Category         Brand         Model         Price         Stock       Discount\n");
		saved.append("--------         -----         -----         -----         -----       --------\n");
			
		for(String model:storeInventory.keySet())
		{
			InventoryInfo inventoryCall = storeInventory.get(model);
			
			if(inventoryCall.getStock() > 0)
			{
				saved.append(inventoryCall.getCategory() + "         " + inventoryCall.getBrand() + "         " + inventoryCall.getModel() + "         "
					+ calculatePrice(inventoryCall.getPrice(), inventoryCall.getDiscount()) + inventoryCall.getDiscount() +"% off" + "         " 
					+ inventoryCall.getStock() + "       "+ inventoryCall.getDiscount() +"\n");
			}
		}
		
		return saved.toString();
	}
	
	
	public String showLessStockItems()
	{
		StringBuilder saved = new StringBuilder();
		
		saved.append("Category         Brand         Model         Price         Stock       Discount\n");
		saved.append("--------         -----         -----         -----         -----       --------\n");
			
		for(String model:storeInventory.keySet())
		{
			InventoryInfo inventoryCall = storeInventory.get(model);
			
			if(inventoryCall.getStock() < 5)
			{
				saved.append(inventoryCall.getCategory() + "         " + inventoryCall.getBrand() + "         " + inventoryCall.getModel() + "         "
					+ calculatePrice(inventoryCall.getPrice(), inventoryCall.getDiscount()) + "  " + inventoryCall.getDiscount() +"% off" + "         " 
					+ inventoryCall.getStock() + "       "+ inventoryCall.getDiscount() +"\n");
			}
		}
		
		return saved.toString();
	}
	
	public String stockUpdate(String model) throws CustomException
	{
		HelperUtility.checkString(model);
		
		readDbInfo();
		
		InventoryInfo inventoryCall = storeInventory.get(model);
		
		inventoryCall.setStock(inventoryCall.getStock() - 1);
		
		return connect.stockUpdate(inventoryCall.getStock(), model);
	}
	
	
	public String stockUpdate(String model,int stock) throws CustomException
	{
		HelperUtility.checkString(model);
		
		readDbInfo();
		
		InventoryInfo inventoryCall = storeInventory.get(model);
		
		inventoryCall.setStock(inventoryCall.getStock() + stock);
		
		return connect.stockUpdate(inventoryCall.getStock(), model);
	}
	
	public String discountUpdate(String model,int discount) throws CustomException
	{
		HelperUtility.checkString(model);
		
		readDbInfo();
		
		InventoryInfo inventoryCall = storeInventory.get(model);
		
		inventoryCall.setDiscount(discount);
		
		return connect.discountUpdate(discount, model);
	}
	
	public String creditUpdate(String userName,int credit) throws CustomException
	{
		HelperUtility.checkString(userName);
		
		CustomerInfo customerCall = storeCustomer.get(userName);
		
		customerCall.setCredit(customerCall.getCredit() + credit);
		
		return connect.creditUpdate(customerCall.getCredit(), userName);
	}
	
	public void writeDbInfo() throws CustomException
	{
		storeCustomer = connect.getCustomerData();
		
		storeInventory = connect.getInventoryData();
		
		storeOrder = connect.getOrderHistoryData();
		
		storageCall.cacheFile(storeCustomer, storeInventory, storeOrder);
	}
	
	public void readDbInfo()
	{
		storeCustomer = storageCall.getCustomer();
		
		storeInventory = storageCall.getInventory();
		
		storeOrder = storageCall.getOrder();
	}
	
	public void clearMyCart()
	{
		cartList.clear();
	}

}
