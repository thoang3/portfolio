//
// Given a String of [a-z] characters, find and return the first character that appears only once.
//

import java.util.*;

public class FirstNonRepeatingCharacter {
	
	public static int firstUniqChar(String str) {
        // Use LinkedHashMap to store (character, first occurrences) pair.
		Map<Character, Integer> hm = new LinkedHashMap<Character, Integer>();

		for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
			if (!hm.containsKey(ch)) {
				hm.put(ch, i);
			}
			else {
				hm.put(ch, -1); // if character appears more than once, mark it by -1.
			}
		}

		// Return the first character's position that appears only once
		for (Map.Entry<Character, Integer> entry: hm.entrySet()) {
			if (entry.getValue() != -1) {
				return entry.getValue();
			}
		}
		System.out.println(hm); // Print to debug

        // If no such character exists, return -1
		return -1;
    }
	
	public static void main(String[] args) {
		String str = "hoangthanhtung";
		
		if (firstUniqChar(str) == -1) {
			System.out.println("No such character exists!");
		} else {
			System.out.println(str.charAt(firstUniqChar(str)));
		}
		
		
	}
}
