/*
 This algorithm calculates the minimum time required to build all engines, 
 given an array representing the time each engine takes to build and the 
 cost of splitting engineers. It sorts the array and iterates through it, 
 updating the maximum time needed for each engineer to build an engine while 
 considering the split cost. Finally, it returns the total time required to 
 build all engines.

 Tests:

 Input: {8,2,1,5}, k=1
 Output: 9
 */
import java.util.Arrays;

public class Q1B {
    public static int minTime(int[] a, int k) {
        Arrays.sort(a);
        int maxValue=k+Math.max(a[0], a[1]);
        for(int i=2;i<a.length;i++){
            maxValue=k+Math.max(a[i], maxValue);
        }
        return maxValue;
    }
    public static void main(String[] args) {
        int[] engines = {1,2,3}; 
        int splitCost = 1;
        System.out.println(minTime(engines, splitCost)); //Output: 4
    }
}
