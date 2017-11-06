//
// Determine the first repeating character in a string
//

import java.util.LinkedHashMap;
import java.util.Map;

public class FirstRepeatingCharacter {
public static char FirstChar(String str) {
		
		// Use LinkedHashMap to store (character, occurrences) pair.
		Map<Character, Integer> hm = new LinkedHashMap<Character, Integer>();
		
		for (char ch : str.toCharArray()) {
			if (!hm.containsKey(ch)) {
				hm.put(ch, 1);
			}
			else {
				hm.put(ch, hm.get(ch) + 1);
			}
		}
		
		// Print out the first character that appears more than once
		for (Map.Entry<Character, Integer> entry: hm.entrySet()) {
			if (entry.getValue() > 1) {
				return entry.getKey();
			}
		}
		System.out.println(hm); // print to debug
		System.out.println("No such character exists!");
		return ' ';
	}
	
	public static void main(String[] args) {
		String str = "dklgjlgfhjioewurowexmbnxcjgflghjf";
		String str2 = "tunghoa";
		System.out.println(FirstChar(str));
		System.out.println(FirstChar(str2));
	}
}

