package zkart;


import java.util.List;

import common.CustomException;
import common.HelperUtility;

public class OrderHistoryInfo {

	private long invoiceNumber;
	private String date;
	private int creditsUsed;
	private double totalPrice;
	private int creditsAdded;
	private String userName;
	private List<String> model;

	public long getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) throws CustomException {
		HelperUtility.checkString(date);
		this.date = date;
	}

	public int getCreditsUsed() {
		return creditsUsed;
	}

	public void setCreditsUsed(int creditsUsed) {
		this.creditsUsed = creditsUsed;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getCreditsAdded() {
		return creditsAdded;
	}

	public void setCreditsAdded(int creditsAdded) {
		this.creditsAdded = creditsAdded;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) throws CustomException {
		HelperUtility.checkString(userName);
		this.userName = userName;
	}

	public List<String> getModel() {
		return model;
	}

	public void setModel(List<String> model) {
		this.model = model;
	}

	@Override
	public String toString() {
		return "OrderHistoryInfo [invoiceNumber=" + invoiceNumber + ", date=" + date + ", creditsUsed=" + creditsUsed
				+ ", totalPrice=" + totalPrice + ", creditsAdded=" + creditsAdded + ", userName=" + userName
				+ ", model=" + model + "]";
	}

}
