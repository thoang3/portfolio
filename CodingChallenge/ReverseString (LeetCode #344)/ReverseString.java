// Reverse a string. In-place => use 2 pointers.
// 

public class ReverseString {
	
	public static String reverseString(String str) {		
		StringBuilder sb = new StringBuilder(str);
		sb.reverse();
		return sb.toString();
	}
	
	public static String reverseString2(String str) {		
		StringBuilder sb = new StringBuilder("");
		
		for (int i = str.length() -1; i >= 0; i--) {
			sb.append(str.charAt(i));
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		String str = "tung";
		
		System.out.println(reverseString(str));
		System.out.println(reverseString2(str));
	}
}
