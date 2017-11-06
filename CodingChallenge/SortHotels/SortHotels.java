import java.io.*;
import java.util.*;

public class Booking_SortHotels {
    /*
     * Complete the function below. Do not allow to write new class outside of Solution class
     */
    static int[] sort_hotels(String keywords, int[] hotel_ids, String[] reviews) {

		String[] arrKeywords = keywords.split(" "); // get array of positive keywords
		System.out.println(Arrays.toString(arrKeywords)); // print to debug

		// hash map to store pair (hotel id, number or positive keywords)
		HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();

		// update hash map by looping through all reviews
		for (int i = 0; i < hotel_ids.length; i++) {
			int count = 0;

			for (int j = 0; j < arrKeywords.length; j++) {
				if (reviews[i].toLowerCase().contains(arrKeywords[j]))
					count++;
			}
			if (!hm.containsKey(hotel_ids[i])) {	
				hm.put(hotel_ids[i], count);
			}
			else {
				count = count + (int) hm.get(hotel_ids[i]);
				hm.put(hotel_ids[i], count);
			}
		}
		System.out.println(hm); // print to debug
		
		// sort hash map based on number of positive reviews' keywords
		List<Map.Entry<Integer, Integer>> list = new LinkedList<Map.Entry<Integer, Integer>>(hm.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
			public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		Map<Integer, Integer> result = new LinkedHashMap<Integer, Integer>();
		
		for (Map.Entry<Integer, Integer> entry: list)
		{
			result.put(entry.getKey(), entry.getValue());
		}
		
		System.out.println(result); // print to debug
		
		Integer[] ar = result.keySet().toArray(new Integer[result.size()]);
		int[] arr = new int[result.size()];
		int index = 0;
		
		for (Integer element : ar)
		{
			arr[index++] = element.intValue();
		}
		
		return arr;
    }

  	public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        final String fileName = System.getenv("OUTPUT_PATH");
        BufferedWriter bw = null;
        if (fileName != null) {
            bw = new BufferedWriter(new FileWriter(fileName));
        }
        else {
            bw = new BufferedWriter(new OutputStreamWriter(System.out));
        }

        int[] res;
        String keywords;
        try {
            keywords = in.nextLine();
        } catch (Exception e) {
            keywords = null;
        }

        int hotel_ids_size = 0;
        hotel_ids_size = Integer.parseInt(in.nextLine().trim());

        int[] hotel_ids = new int[hotel_ids_size];
        for(int i = 0; i < hotel_ids_size; i++) {
            int hotel_ids_item;
            hotel_ids_item = Integer.parseInt(in.nextLine().trim());
            hotel_ids[i] = hotel_ids_item;
        }

        int reviews_size = 0;
        reviews_size = Integer.parseInt(in.nextLine().trim());

        String[] reviews = new String[reviews_size];
        for(int i = 0; i < reviews_size; i++) {
            String reviews_item;
            try {
                reviews_item = in.nextLine();
            } catch (Exception e) {
                reviews_item = null;
            }
            reviews[i] = reviews_item;
        }

        res = sort_hotels(keywords, hotel_ids, reviews);
        for(int res_i = 0; res_i < res.length; res_i++) {
            bw.write(String.valueOf(res[res_i]));
            bw.newLine();
        }
        
        bw.close();
        in.close();
    }
}