/*
 * This algorithm identifies individuals who knows secret based on given intervals. It 
 * initializes a boolean array to track whether each individual is known. 
 * Then, it iterates through each interval, marking the individuals within 
 * those intervals as known. Finally, it constructs and returns a list of 
 * indices corresponding to known individuals.
 * 
 * Tests:
 * 
 * Input:{{0, 1}, {3, 4}}
 * Output: [0,1,3,4]
 */
import java.util.*;

public class Q2B {
    public static ArrayList<Integer> findIndividuals(int n, int[][] intervals) {
        boolean[] isKnown = new boolean[n]; 
        Arrays.fill(isKnown, false); 
        
        for (int[] interval : intervals) {
            int start = interval[0];
            int end = interval[1];
            
            
            for (int i = start; i <= end; i++) {
                isKnown[i] = true;
            }
        }
        
        
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (isKnown[i]) {
                result.add(i);
            }
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        int[][] intervals = {{0, 2}, {1, 3}, {2, 4}};
        
        List<Integer> individuals = findIndividuals(5, intervals);
        System.out.println(individuals); //Output: [0, 1, 2, 3, 4]
    }
}
