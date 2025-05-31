package introduction;

public class Variables {

	public static void main(String[] args) {
		
		double firstNumber;
		firstNumber = 1;
		double secondNumber = 5;
		
		double doubleResult = firstNumber/secondNumber;
		System.out.println(doubleResult);
		
		int intResult = (int) (firstNumber / secondNumber);
		System.out.println(intResult);
		
		
		boolean alwaysTrue=true;
		boolean secondBoolean = false;
		System.out.println(alwaysTrue && secondBoolean);
		System.out.println(alwaysTrue || secondBoolean);
		System.out.println(!alwaysTrue);
	
		
		String name = "Petar";
		String lastName = "Petrovic";
		System.out.println(name + " " + lastName + " Njegos");
		
		if (firstNumber > 0) {
			System.out.println("Number is positive");	
			firstNumber=-1;
		} else if (firstNumber < 0)
			System.out.println("Number is negative");
		else
			System.out.println("Number is equal to zero");
		
		int i = 1;
		while(i <= 10) {
			System.out.println(i);
			i++;
		}
		
		//Zadatak 3
		for(i = 0; i<=10;i++) {
			if(i%2==0) {
				continue;
			}
			System.out.println(i);
		}
		//Zadatak 1
		String operation = "+";
		if (operation == "+")
			System.out.println(24 + 12);
		else if (operation == "-")
			System.out.println(24 - 12);
		else if (operation == "*")
			System.out.println(24 * 12);
		else
			System.out.println(24 / 12);
		
		//Zadatak 3;
		
		int f = 1;
		i=5;
		while(i>0) {
			f=f*i;
			i--;
		}
		System.out.println(f);
			
		
	} 
	
}
