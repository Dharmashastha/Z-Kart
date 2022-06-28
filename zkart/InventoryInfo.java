package zkart;

import common.CustomException;
import common.HelperUtility;

public class InventoryInfo {
	
	private String category;
	private String brand;
	private String model;
	private double price;
	private int stock;
	private int discount;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) throws CustomException {
		HelperUtility.checkString(category);
		this.category = category;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) throws CustomException {
		HelperUtility.checkString(brand);
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) throws CustomException {
		HelperUtility.checkString(model);
		this.model = model;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	
	@Override
	public String toString() {
		return "InventoryInfo [category=" + category + ", brand=" + brand + ", model=" + model + ", price=" + price
				+ ", stock=" + stock + ", discount=" + discount + "]";
	}
	
	
}
