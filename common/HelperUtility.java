package common;

import java.util.Objects;

public class HelperUtility {
	
	public static void checkString(String checkString) throws CustomException
	{
		if (Objects.isNull(checkString) || checkString.isEmpty()) {
			throw new CustomException("Input is Empty/Null");
		}
	}
	
	public static void checkObject(Object checkObject) throws CustomException
	{
		if (Objects.isNull(checkObject)) {
			throw new CustomException("Object is Null");
		}
	}
}
