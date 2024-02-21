/*
 * 
 * The algorithm calculates the total number of dresses across all machines and determines the average number of dresses 
 * per machine. Then, it iterates through each machine to find the surplus dresses (dresses above the average) and the deficit 
 * dresses (dresses below the average), summing up the surplus and deficit dresses separately. Finally, it returns the maximum 
 * value between surplus and deficit dresses, representing the minimum number of moves needed to equalize the dresses in 
 * all machines.
 * 
 * Tests:
 * Input: {1,2,3}
 * Output: 1 (Just pass 1 from 3 to 1 making array [2,2,2])
 * 
 * Input: {4,1,1}
 * Output: 2 (Pass from 1st to 2nd machine --> [3,2,1]--again pass from 1st to 3rd machine--[2,2,2])
 * 
 * Input: {0,0,12}
 * Output: 8 (You need to pass 4 clothes to machine 1 and 4 to machine to, i.e. 4+4=8)
 */
public class Q2A {
    public static int minMovesToEqualize(int[] arr) {
        int totalDresses = 0;
        int numDevices = arr.length;

        
        for (int dresses : arr) {
            totalDresses += dresses;
        }
        
        int averageDresses = totalDresses / numDevices;

        int surplus = 0;
        int deficit = 0;

        
        for (int dresses : arr) {
            surplus += Math.max(0, dresses - averageDresses);
            deficit += Math.max(0, averageDresses - dresses);
        }

        return Math.max(surplus, deficit);
    }

    public static void main(String[] args) {
        int[] arr = {1, 0, 5};
        int minMoves = minMovesToEqualize(arr);
        System.out.println("Minimum number of moves to equalize: " + minMoves); //Output: 3
    }
}
