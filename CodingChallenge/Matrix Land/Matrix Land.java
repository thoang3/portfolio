import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    static int matrixLand(int[][] A) {
        // Complete this function
        int numRows = A.length;
        int numCols = A[0].length;
        
        // store maximum possible points to reach to current position of the current row
        int[] maxPoints = new int[numCols]; 
        
        // store left possible points of the current position j in current row only (do not count previous rows)
        int[] leftRowValues = new int[numCols];
        
        // store right possible points of the current position j in current row only (do not count previous rows)
        int[] rightRowValues = new int[numCols];
        
        // store left possible points from jumping to current row from the left of current position
        int[] prevLeft = new int[numCols];
        
        // store right possible points from jumping to current row from the right of current position
        int[] prevRight = new int[numCols];
        
        for (int i = 0; i < numRows; i ++) {
            int[] curRow = A[i];
            
            int temp = 0;           
            // compute left possible points of the current position j
            for (int j = 0; j < numCols; j++) {
                leftRowValues[j] = Math.max(0, temp);
                temp = Math.max(curRow[j], temp + curRow[j]);
            }
            temp = 0;
            // compute right possible points of the current position j
            for (int j = numCols - 1; j >= 0; j--) {
                rightRowValues[j] = Math.max(0, temp);
                temp = Math.max(curRow[j], temp + curRow[j]);
            }
            
            // at the far left of current row, there's only one option to to get to current position by jumping from the top
            prevLeft[0] = curRow[0] + maxPoints[0];
            
            // at the far right of current row, there's only one option to to get to current position by jumping from the top
            prevRight[numCols - 1] = curRow[numCols - 1] + maxPoints[numCols - 1];
            
            // compute left possible points from jumping to current row from the left of current position
            for (int j = 1; j < numCols; j++) {
                prevLeft[j] = Math.max(prevLeft[j-1] + curRow[j], maxPoints[j] + leftRowValues[j] + curRow[j]);
            }
            // compute right possible points from jumping to current row from the right of current position
            for (int j = numCols -2; j >=0; j--) {
                prevRight[j] = Math.max(prevRight[j+1] + curRow[j], maxPoints[j] + rightRowValues[j] + curRow[j]);
            }
            
            // compare three cases and choose the largest value for the current position: 
            // 1. if jump to current row from the left of the current position, then collect possible extra points by exploring the right side of current position as well
            // 2. if jump to current row from the top of the current position, then collect possible extra points by exploring both left and right of the current posision as well
            // 3. if jump to current row from the right of the current position, then collect possible extra points by exploring the left side of current position as well
            for (int j = 0; j < numCols; j++) {
                maxPoints[j] = Math.max(prevLeft[j] + rightRowValues[j], Math.max(maxPoints[j] + curRow[j] + leftRowValues[j] + rightRowValues[j], prevRight[j] + leftRowValues[j]));
            }
        }
        
        // at the last row now, the largest value would be the answer
        int _max = maxPoints[0];
        for (int j = 1; j < numCols; j++) {
            if (_max < maxPoints[j]) 
                _max = maxPoints[j];
        }
        
        return _max;
    }

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(
                              new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] A = new int[n][m];
        for(int A_i = 0; A_i < n; A_i++){
            st = new StringTokenizer(br.readLine());
            for(int A_j = 0; A_j < m; A_j++){
                A[A_i][A_j] = Integer.parseInt(st.nextToken());
            }
        }
        //Scanner in = new Scanner(System.in);
        //int n = in.nextInt();
        //int m = in.nextInt();
        //int[][] A = new int[n][m];
        //for(int A_i = 0; A_i < n; A_i++){
            //for(int A_j = 0; A_j < m; A_j++){
                //A[A_i][A_j] = in.nextInt();
            //}
        //}
        int result = matrixLand(A);
        System.out.println(result);
        in.close();
    }
}
