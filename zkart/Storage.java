package zkart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Storage 
{
	private Map<String, CustomerInfo> storeCustomer = new HashMap<>();

	private Map<String, InventoryInfo> storeInventory = new HashMap<>();

	private Map<String, List<OrderHistoryInfo>> storeOrder = new HashMap<>();
	
	public void cacheFile(Map<String, CustomerInfo> storeCustomer,Map<String, InventoryInfo> storeInventory,Map<String, List<OrderHistoryInfo>> storeOrder)
	{
		this.storeCustomer = storeCustomer;
		
		this.storeInventory = storeInventory;
		
		this.storeOrder = storeOrder;
	}
	
	public Map<String, CustomerInfo> getCustomer()
	{
		return storeCustomer;
	}
	
	public Map<String, InventoryInfo> getInventory()
	{
		return storeInventory;
	}
	
	public Map<String, List<OrderHistoryInfo>> getOrder()
	{
		return storeOrder;
	}
}
