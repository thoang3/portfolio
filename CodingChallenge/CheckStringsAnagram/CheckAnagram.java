// Determine if given two strings are anagrams of each other.

import java.util.*;

public class CheckAnagram {
	
	public static boolean checkAnagram(String str1, String str2) {
		
		// Strings have different length => Not anagrams
		if (str1.length() != str2.length())
			return false;
		
		// Use hash map to store (character, occurrence) pair in string 1
		HashMap<Character, Integer> hm = new HashMap<Character, Integer>();
		
		for (int i = 0; i < str1.length(); i++) {
			char ch = str1.charAt(i);
			if (!hm.containsKey(ch)) {
				hm.put(ch, 1);
			}
			else {
				hm.put(ch, hm.get(ch) + 1);
			}
		}
		
		// Then update the hash map by decrement character in string 2 that also occurs in string 1. 
		// If two strings are anagram, the updated values in hash map should all be zeros.
		for (int i = 0; i < str2.length(); i++) {
			char ch = str2.charAt(i);
			if (hm.containsKey(ch)) {
				hm.put(ch, hm.get(ch) - 1);
			}
		}
		
		for (Map.Entry<Character, Integer> entry : hm.entrySet()) {
			if (entry.getValue() != 0)
				return false;
		}
	
		return true;
	}
	
	public static void main(String[] args) {
		String str1 = " tri angle";
		String str2 = "integra l ";
		
		System.out.println(checkAnagram(str1, str2));	
	}
}
