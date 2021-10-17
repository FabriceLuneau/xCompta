package etatViewer;

public class Conversion {
	public String numberToString(int number) {
		int modulo;
		int  result = number;
		String value = "";
		
		while(number > 25)  {
		result = number  /26;
		modulo =  number % 26;
		
			value = charForNumber(modulo) + value;
			
			number = result-1;
		}
		
		value = charForNumber(number) + value;
		
	return value;
	}
	
	public int stringToNumber(String string) {
int value = 0;
int j = 0;

for(int i=0;i<string.length();i++) {
	value += charValue(string.charAt(i)) +Math.pow(26, j);
	
	j++;
}

value -=1;

		return value;
	}
		
	public int charValue(char d) {
		int i = 0;
		
		for (char c = 'a'; c <= 'z'; c++) {
			if(c == d){
				return i;
			}
			i++;
		}
	return 99;
		}
		
	public char charForNumber(int number) {   
		int i=0;
		
		for (char c = 'a'; c <= 'z'; c++) {
			if(i == number) {
				return c;
			}
			i++;
		}
		return ' ';
	}
		
		public static void main(String[] args) {
			Conversion c = new Conversion();
			
				//System.out.println(c.charValue('a'));
				//System.out.println(c.charValue('A'));
				//System.out.println(c.charValue('z'));
				
			System.out.println(c.charForNumber(0));	
			System.out.println(c.charForNumber(5));
				//System.out.println(c.charForNumber(0));
				//System.out.println(c.charForNumber(1));

			//System.out.println(c.numberToChar(0));
//System.out.println(c.numberToChar(1));
				//System.out.println(c.numberToChar(25));
				//System.out.println(c.numberToChar(26));
//				System.out.println(c.numberToChar(701));
				//System.out.println(c.numberToChar(29));
			
//			for(int i=26;i<1727;i++) {
		//System.out.println(i + "*" +c.numberToChar(i));
			//}
			
//			for(int  i=0;i<100000;i++) {
//				String a = c.numberToString(i);
//			int  b = c.stringToNumber(a);
			
//			if(b != i) {
//				System.out.println(i + " - " + a);
//			}
//			}
				
			//System.out.println(c.stringToNumber("aaa"));//ABC 730
	}
}
