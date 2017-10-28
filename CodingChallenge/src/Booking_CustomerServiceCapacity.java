import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Booking_CustomerServiceCapacity {


    /*
     * Complete the function below.
     */
    static int howManyAgentsToAdd(int noOfCurrentAgents, int[][] callsTimes) {

    	
    	return 0;
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

        int res;
        int noOfCurrentAgents;
        noOfCurrentAgents = Integer.parseInt(in.nextLine().trim());

        int callsTimes_rows = 0;
        int callsTimes_cols = 0;
        callsTimes_rows = Integer.parseInt(in.nextLine().trim());
        callsTimes_cols = Integer.parseInt(in.nextLine().trim());

        int[][] callsTimes = new int[callsTimes_rows][callsTimes_cols];
        for(int callsTimes_i = 0; callsTimes_i < callsTimes_rows; callsTimes_i++) {
            for(int callsTimes_j = 0; callsTimes_j < callsTimes_cols; callsTimes_j++) {
                callsTimes[callsTimes_i][callsTimes_j] = in.nextInt();

            }
        }

        if(in.hasNextLine()) {
          in.nextLine();
        }

        res = howManyAgentsToAdd(noOfCurrentAgents, callsTimes);
        bw.write(String.valueOf(res));
        bw.newLine();

        bw.close();
    }
}
