package zkart;

import common.CustomException;
import common.HelperUtility;

public class CustomerInfo {

	private String userName;
	private String password;
	private String name;
	private String mobileNumber;
	private int credit;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) throws CustomException {
		HelperUtility.checkString(userName);
		this.userName = userName;
	}
	
	private String getPassword()
	{
		String encryptedPwd = "";
		int size = password.length();
		for (int iter = 0; iter < size; iter++) {
			encryptedPwd += (char)(password.charAt(iter) - 5);
		}
		password = encryptedPwd;
		return password;
	}
	
	public String validStore()
	{
		return password;
	}

	public void setPassword(String password) throws CustomException {
		HelperUtility.checkString(password);
		String encryptedPwd = "";
		int size = password.length();
		for (int iter = 0; iter < size; iter++) {
			encryptedPwd += (char)(password.charAt(iter) + 5);
		}
		this.password = encryptedPwd;
		
	}

	public String getName() {
		return name;
	}
	

	public void setName(String name) throws CustomException {
		HelperUtility.checkString(name);
		this.name = name;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) throws CustomException {
		HelperUtility.checkString(mobileNumber);
		this.mobileNumber = mobileNumber;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}
	
	public boolean checkerLogin(String userName,String password) throws CustomException
	{
		HelperUtility.checkString(userName);
		HelperUtility.checkString(password);
		return this.userName.equals(userName) && getPassword().equals(password);
	}

	@Override
	public String toString() {
		return "CustomerInfo [userName=" + userName + ", name=" + name + ", mobileNumber=" + mobileNumber + ", credit="
				+ credit + "]";
	}

}
