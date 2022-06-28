package zkart;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import common.ConnectionUtility;
import common.CustomException;
import common.HelperUtility;

public class ZkartDbLayer implements Connected{
	
	
	private String encryptedPassword(String password) throws CustomException
	{
		HelperUtility.checkString(password);
		String encryptedPwd = "";
		int size = password.length();
		for (int iter = 0; iter < size; iter++) {
			encryptedPwd += (char)(password.charAt(iter) - 5);
		}
		return encryptedPwd;
	}

	public String createTableQuery(String newTable) throws CustomException {
		HelperUtility.checkString(newTable);
		String saved = null;
		try (Statement state = ConnectionUtility.getConnection().createStatement();) {
			if (state.execute(newTable)) {
				saved = " Table Created.";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return saved;
	}

	public String customerInsertInfo(CustomerInfo customerCall) throws CustomException {
		
		HelperUtility.checkObject(customerCall);
		
		final String insertQuery = "INSERT INTO CustomerInfo VALUES(?,?,?,?,?);";
		
		String saved = null;

		try (PreparedStatement state = ConnectionUtility.getConnection().prepareStatement(insertQuery);) {
			state.setString(1, customerCall.getUserName());
			state.setString(2, customerCall.validStore());
			state.setString(3, customerCall.getName());
			state.setString(4, customerCall.getMobileNumber());
			state.setInt(5, customerCall.getCredit());
			state.executeUpdate();

			saved = 1 + " Row Created.";
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return saved;
	}

	public String inventoryInsertInfo(InventoryInfo inventoryCall) throws CustomException {
		
		HelperUtility.checkObject(inventoryCall);
		
		final String insertQuery = "INSERT INTO InventoryInfo VALUES(?,?,?,?,?,?);";

		String saved = null;

		try (PreparedStatement state = ConnectionUtility.getConnection().prepareStatement(insertQuery);) {
			state.setString(1, inventoryCall.getCategory());
			state.setString(2, inventoryCall.getBrand());
			state.setString(3, inventoryCall.getModel());
			state.setDouble(4, inventoryCall.getPrice());
			state.setInt(5, inventoryCall.getStock());
			state.setInt(6, inventoryCall.getDiscount());
			state.executeUpdate();

			saved = 1 + " Row Created.";
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return saved;
	}
	
	
	public String orderHistoryInsert(OrderHistoryInfo orderCall) throws CustomException {
		
		HelperUtility.checkObject(orderCall);
		
		final String insertQuery = "INSERT INTO OrderHistoryInfo VALUES(?,?,?,?,?,?,?);";
		
		String saved = null;

		try (PreparedStatement state = ConnectionUtility.getConnection().prepareStatement(insertQuery);) {
			state.setLong(1, orderCall.getInvoiceNumber());
			state.setString(2, orderCall.getDate());
			state.setInt(3, orderCall.getCreditsUsed());
			state.setDouble(4, orderCall.getTotalPrice());
			state.setInt(5, orderCall.getCreditsAdded());
			state.setString(6, orderCall.getUserName());
			
			List<String> modelList = orderCall.getModel();
			
			StringBuilder dummy = new StringBuilder();
			
			int size = modelList.size();
			
			for(int iter = 0; iter < size; iter++)
			{
				dummy.append(modelList.get(iter)).append(",");
			}
			
			String temp = dummy.toString().substring(0, dummy.length() - 1);
			
			state.setString(7, temp);
			
			state.executeUpdate();

			saved = 1 + " Row Created.";
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return saved;
	}
	
	public String stockUpdate(int stock,String model) throws CustomException
	{
		HelperUtility.checkString(model);
		
		final String update = "UPDATE InventoryInfo SET Stock = ? WHERE Model = ?";
		
		HelperUtility.checkString(update);
		
		String saved = null;
		
		try(PreparedStatement state = ConnectionUtility.getConnection().prepareStatement(update);)
		{
			state.setInt(1, stock);
			state.setString(2, model);
			int check = state.executeUpdate();
			if(check != 0)
			{
				saved = check + " Row Updated.";
			}
			else
			{
				saved = "No Data";
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return saved;
	}
	
	public String discountUpdate(int discount,String model) throws CustomException
	{
		HelperUtility.checkString(model);
		
		final String update = "UPDATE InventoryInfo SET Discount = ? WHERE Model = ?";
		
		HelperUtility.checkString(update);
		
		String saved = null;
		
		try(PreparedStatement state = ConnectionUtility.getConnection().prepareStatement(update);)
		{
			state.setInt(1, discount);
			state.setString(2, model);
			int check = state.executeUpdate();
			if(check != 0)
			{
				saved = check + " Row Updated.";
			}
			else
			{
				saved = "No Data";
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return saved;
	}
	
	public String creditUpdate(int credit,String userName) throws CustomException
	{
		HelperUtility.checkString(userName);
		
		final String update = "UPDATE CustomerInfo SET Credit = ? WHERE UserName = ?";
		
		HelperUtility.checkString(update);
		
		String saved = null;
		
		try(PreparedStatement state = ConnectionUtility.getConnection().prepareStatement(update);)
		{
			state.setInt(1, credit);
			state.setString(2, userName);
			int check = state.executeUpdate();
			if(check != 0)
			{
				saved = check + " Row Updated.";
			}
			else
			{
				saved = "No Data";
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return saved;
	}
	
	public Map<String, CustomerInfo> getCustomerData() throws CustomException
	{
		Map<String, CustomerInfo> getData = new HashMap<>();
		
		final String customerQuery = "select * from CustomerInfo;";
		
		try(PreparedStatement state = ConnectionUtility.getConnection().prepareStatement(customerQuery);)
		{
			try(ResultSet rs = state.executeQuery();)
			{
				while(rs.next())
				{
					CustomerInfo customerCall = new CustomerInfo();
					String userName = rs.getString(1);
					String password = rs.getString(2);
					String name = rs.getString(3);
					String mobileNumber = rs.getString(4);
					int credit = rs.getInt(5);
					password = encryptedPassword(password);
					customerCall.setUserName(userName);
					customerCall.setPassword(password);
					customerCall.setName(name);
					customerCall.setMobileNumber(mobileNumber);
					customerCall.setCredit(credit);
					getData.put(userName, customerCall);
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return getData;
	}
	
	
	public Map<String, InventoryInfo> getInventoryData() throws CustomException
	{
		Map<String, InventoryInfo> getData = new HashMap<>();
		
		final String inventoryQuery = "select * from InventoryInfo;";
		
		try(PreparedStatement state = ConnectionUtility.getConnection().prepareStatement(inventoryQuery);)
		{
			try(ResultSet rs = state.executeQuery();)
			{
				while(rs.next())
				{
					InventoryInfo inventoryCall = new InventoryInfo();
					String category = rs.getString(1);
					String brand = rs.getString(2);
					String model = rs.getString(3);
					double price = rs.getDouble(4);
					int stock = rs.getInt(5);
					int discount = rs.getInt(6);
					inventoryCall.setCategory(category);
					inventoryCall.setBrand(brand);
					inventoryCall.setModel(model);
					inventoryCall.setPrice(price);
					inventoryCall.setStock(stock);
					inventoryCall.setDiscount(discount);
					getData.put(model, inventoryCall);
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return getData;
	}
	
	
	public Map<String, List<OrderHistoryInfo>> getOrderHistoryData() throws CustomException
	{
		Map<String, List<OrderHistoryInfo>> getData = new HashMap<>();
		
		List<OrderHistoryInfo> orderList = null;
		
		final String orderQuery = "select * from OrderHistoryInfo;";
		
		try(PreparedStatement state = ConnectionUtility.getConnection().prepareStatement(orderQuery);)
		{
			try(ResultSet rs = state.executeQuery();)
			{
				while(rs.next())
				{
					OrderHistoryInfo orderCall = new OrderHistoryInfo();
					
					long invoiceNumber = rs.getLong(1);
					
					String date = rs.getString(2);
					
					int creditsUsed = rs.getInt(3);
					
					double totalPrice = rs.getDouble(4);
					
					int creditsAdded = rs.getInt(5);
					
					String userName = rs.getString(6);
					
					String model = rs.getString(7);
					
					String[] modelArray = model.split(",");
					
					List<String> modelList = new ArrayList<>();
					
					for(int iter = 0; iter < modelArray.length; iter++)
					{
						modelList.add(modelArray[iter]);
					}
					
					orderCall.setInvoiceNumber(invoiceNumber);
					
					orderCall.setDate(date);
					
					orderCall.setCreditsUsed(creditsUsed);
					
					orderCall.setTotalPrice(totalPrice);
					
					orderCall.setCreditsAdded(creditsAdded);
					
					orderCall.setUserName(userName);
					
					orderCall.setModel(modelList);
					
					if(Objects.isNull(getData.get(orderCall.getUserName())))
					{
						 orderList = new ArrayList<>();
					}
					orderList.add(orderCall);
					
					getData.put(orderCall.getUserName(), orderList);
					
					
					ZkartLogicLayer.invoiceNumber = invoiceNumber;
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return getData;
	}
}
