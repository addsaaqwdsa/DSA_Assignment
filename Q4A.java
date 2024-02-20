/*
 * This code finds the shortest path to collect all lowercase keys in a grid-based 
 * game using BFS with a queue. It keeps track of visited positions and collected keys 
 * to avoid revisiting states. The algorithm returns the minimum number of moves 
 * required or -1 if impossible.
 * 
 * Input: {'S','P','q','P','P'},
            {'W', 'W', 'W', 'P', 'W'},
            {'P','r','Q','P','R'}

    Output: 7
 * 
 */
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class Q4A {
    public int minMovesToCollectKeys(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int totalKeys = 0;
        int[] start = new int[2];
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'S') {
                    start[0] = i;
                    start[1] = j;
                } else if (Character.isLowerCase(grid[i][j])) {
                    totalKeys++;
                }
            }
        }
        
        if (totalKeys == 0) {
            return 0;
        }
        
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        Set<String> visited = new HashSet<>();
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{start[0], start[1], 0, 0}); // {row, col, steps, collectedKeys}
        
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            int steps = current[2];
            int collectedKeys = current[3];
            
            if (visited.contains(row + "-" + col + "-" + collectedKeys)) {
                continue;
            }
            
            visited.add(row + "-" + col + "-" + collectedKeys);
            
            if (Integer.bitCount(collectedKeys) == totalKeys) {
                return steps;
            }
            
            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n && grid[newRow][newCol] != 'W') {
                    char cell = grid[newRow][newCol];
                    
                    if (Character.isLowerCase(cell) && ((collectedKeys >> (cell - 'a')) & 1) == 0) {
                        int newCollectedKeys = collectedKeys | (1 << (cell - 'a'));
                        queue.offer(new int[]{newRow, newCol, steps + 1, newCollectedKeys});
                    } else if (Character.isUpperCase(cell) && ((collectedKeys >> (cell - 'A')) & 1) == 1) {
                        queue.offer(new int[]{newRow, newCol, steps + 1, collectedKeys});
                    } else if (cell == 'P' || cell == 'S') {
                        queue.offer(new int[]{newRow, newCol, steps + 1, collectedKeys});
                    }
                }
            }
        }
        
        return -1;
    }
    
    public static void main(String[] args) {
        char[][] grid = {
            {'S','P','q','P','P'},
            {'W', 'W', 'W', 'P', 'W'},
            {'r','P','Q','P','R'}
        };
        
        Q4A solution = new Q4A();
        System.out.println(solution.minMovesToCollectKeys(grid)); // Output: 8
    }
}

