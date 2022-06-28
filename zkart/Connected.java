package zkart;

import java.util.List;
import java.util.Map;

import common.CustomException;

public interface Connected 
{
	public String createTableQuery(String newTable) throws CustomException;
	
	public String customerInsertInfo(CustomerInfo customerCall) throws CustomException;
	
	public String inventoryInsertInfo(InventoryInfo inventoryCall) throws CustomException;
	
	public String orderHistoryInsert(OrderHistoryInfo orderCall) throws CustomException;
	
	public String stockUpdate(int stock,String model) throws CustomException;
	
	public String discountUpdate(int discount,String model) throws CustomException;
	
	public Map<String, CustomerInfo> getCustomerData() throws CustomException;
	
	public Map<String, InventoryInfo> getInventoryData() throws CustomException;

	public Map<String, List<OrderHistoryInfo>> getOrderHistoryData() throws CustomException;
	
	public String creditUpdate(int credit,String userName) throws CustomException;
}
