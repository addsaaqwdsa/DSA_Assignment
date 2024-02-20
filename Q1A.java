/*
This algorithm finds the minimum cost to decorate venues with different 
themes. It uses dynamic programming table to store the minimum cost 
for each venue and theme combination. By iterating through each venue and 
theme, it calculates the minimum cost by considering the cost of the current 
theme and the minimum cost from the previous venue with different themes. 
Finally, it returns the minimum cost computed for the last venue.

Tests: 

Input: {{1, 2, 1}, {4, 6, 7}, {2, 1, 1}}
Output: 6

Input: {{1, 2, 4}, {4, 6, 7}, {2, 3, 4}}
Output: 9

 */
public class Q1A {
    public static int minCostToDecorate(int[][] venues) {
        if (venues == null || venues.length == 0 || venues[0].length == 0) {
            return 0;
        }

        int n = venues.length;
        int k = venues[0].length;
        int[][] dp = new int[n][k];

        for (int i = 0; i < k; i++) {
            dp[0][i] = venues[0][i];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < k; j++) {
                int minCost = Integer.MAX_VALUE;
                for (int prevTheme = 0; prevTheme < k; prevTheme++) {
                    if (prevTheme != j) {
                        minCost = Math.min(minCost, dp[i - 1][prevTheme] + venues[i][j]);
                    }
                }
                dp[i][j] = minCost;
            }
        }
        int minCost = Integer.MAX_VALUE;
        for (int cost : dp[n - 1]) {
            minCost = Math.min(minCost, cost);
        }
        return minCost;
    }

    public static void main(String[] args) {
        int[][] venues = {{1, 3, 2}, {4, 6, 8}, {3, 1, 5}};
        System.out.println(minCostToDecorate(venues)); //Output: 7
    }
}
