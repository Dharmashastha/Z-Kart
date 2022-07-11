package common;

public class Name {

	public static void main(String[] args) {
		
		String saved = "Dharmashastha";
		
		int length = saved.length();
		
		for(int i = 0; i < length; i++)
		{
			for(int j = i; j < length; j++)
			{
				System.out.print(" ");
			}
			
			for(int j = 0; j <= i; j++)
			{
				System.out.print(saved.charAt(j));
			}
			
			System.out.println();
		}
		}
	}
