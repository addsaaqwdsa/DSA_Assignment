/*
 * The algorithm initializes a graph with vertices and edges, along with a designated 
 * broken node. It then removes the broken node and its connections from the graph. 
 * Next, it performs a Depth-First Search (DFS) to identify disconnected subgraphs 
 * in the modified graph, excluding the broken node and the original source node. 
 * Finally, it returns a list of vertices belonging to the disconnected subgraphs.
 * Test:
 * For same Graph
 * Input: brokenNode = 1;
 * Output: [3]
 * 
 * Input: brokenNode = 2;
 * Output: []
 */
import java.util.ArrayList;
import java.util.List;

public class Q5B {

    int V;
    int[][] adjMatrix;
    int brokenNode;

    Q5B(int V, int[][] edges, int brokenNode) {
        this.V = V;
        this.adjMatrix = new int[V][V];
        for (int[] edge : edges) {
            addEdge(edge[0], edge[1]);
        }
        this.brokenNode = brokenNode;
    }

    void addEdge(int src, int dest) {
        adjMatrix[src][dest] = 1;
        adjMatrix[dest][src] = 1;
    }

    void removeNode() {
        for (int i = 0; i < V; i++) {
            adjMatrix[i][brokenNode] = 0;
            adjMatrix[brokenNode][i] = 0;
        }
    }

    List<Integer> getDisconnectedSubgraphs() {
        removeNode();
        boolean[] visited = new boolean[V];
        List<Integer> subgraphs = new ArrayList<>();

        for (int v = 0; v < V; ++v) {
            if (!visited[v] && v != brokenNode) {
                List<Integer> subgraph = new ArrayList<>();
                DFSUtil(v, visited, subgraph);
                if (!subgraph.contains(brokenNode) && !subgraph.contains(0)) {
                    subgraphs.addAll(subgraph);
                }
            }
        }
        return subgraphs;
    }

    void DFSUtil(int v, boolean[] visited, List<Integer> subgraph) {
        visited[v] = true;
        subgraph.add(v);
        for (int i = 0; i < V; ++i) {
            if (adjMatrix[v][i] == 1 && !visited[i]) {
                DFSUtil(i, visited, subgraph);
            }
        }
    }

    public static void main(String[] args) {
        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {1, 6}, {2, 4}, {4, 6}, {4, 5}, {5, 7}};
        int brokenNode = 4;
        Q5B q5b = new Q5B(8, edges, brokenNode);

        System.out.println("Interrupted Networks:");
        List<Integer> subgraphs = q5b.getDisconnectedSubgraphs();
        System.out.println(subgraphs); //Output: [5,7]
    }
}
