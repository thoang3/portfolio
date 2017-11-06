//
// Create a function that removes duplicate characters in a string
//

import java.util.*;

public class RemoveStringDuplicate {
	
	public static String removeDuplicate(String str) {
		// Build the return string without duplicates.
		StringBuilder sb = new StringBuilder();
		
		// Hash map to keep track whether the character already appears in the string or not
		HashMap<Character, Integer> hm = new HashMap<Character, Integer>();
		
		// Iterate through the string
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			
			// If this is the first occurrence, mark in hash map, and put the character in the return string
			if (!hm.containsKey(ch)) {
				hm.put(ch, 1);
				sb.append(ch);
			}
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		String str = "hoangthanhtung";
		System.out.println(removeDuplicate(str));
		
	}
}
