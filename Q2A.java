/*
 * This algorithm aims to determine the minimum number of moves required 
 * to equalize number of dresses in sewing machine. It first sorts the array, then 
 * iterates from both ends towards the center, calculating the difference 
 * between the maximum and minimum elements at each step and accumulating 
 * the total moves needed.
 * Dividing the total moves by 2 serves to handle the fact that each move 
 * involves adjusting two elements—one incremented and one decremented—bringing 
 * them closer to each other.
 * 
 * Tests:
 * Input: {1,2,3}
 * Output: 1 (Just pass 1 from 3 to 1 making array [2,2,2])
 * 
 * Input: {4,1,1}
 * Output: 2 (Pass from 1st to 2nd machine --> [3,2,1]--again pass from 1st to 3rd machine--[2,2,2])
 */
import java.util.Arrays;

public class Q2A {
    public int minMoves2(int[] nums) {
        Arrays.sort(nums);
        int i=0;
        int j=nums.length-1;
        int moves=0;
        while(i<j){
          moves+=nums[j]-nums[i];
          i++;
          j--;
        }
        return moves;
    }

    public static void main(String[] args) {
      Q2A solution = new Q2A();
        int[] nums = {1, 0, 5};
        int moves=solution.minMoves2(nums);
        int minMoves;
        if(moves%2==0){
            minMoves=moves/2;
        }
        else{
            minMoves=(moves/2)+1;
        }
        System.out.println("Minimum moves required: " + minMoves ); //Output: 3
    }
}
