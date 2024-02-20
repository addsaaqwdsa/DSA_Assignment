/*
 * This is the implementation of Kruskal algorithm and priority queue using minimum heap.
 * The Edge class represents an edge between two vertices with a weight, implementing the
   Comparable interface to compare edges based on weight.
 * The DisjointSet class implements the disjoint-set data structure to track connected 
   components and facilitate cycle detection.
 * The MinimumHeap class implements a min-heap data structure to efficiently extract the 
   minimum weight edge, providing methods for insertion, extraction, and checking if empty.

   Test:
   Input: 
   edges.add(new Edge(0, 1, 5));
    edges.add(new Edge(0, 2, 3));
    edges.add(new Edge(0, 3, 2));
    edges.add(new Edge(1, 3, 6));
    edges.add(new Edge(2, 3, 4));

    Output:
    Edges in the minimum spanning tree:
    0 - 3: 2
    0 - 2: 3
    0 - 1: 5
 */
import java.util.*;

class Edge implements Comparable<Edge> {
    int source, destination, weight;

    public Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

class DisjointSet {
    int[] parent, rank;

    public DisjointSet(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++)
            parent[i] = i;
    }

    int find(int u) {
        if (parent[u] != u)
            parent[u] = find(parent[u]);
        return parent[u];
    }

    void union(int u, int v) {
        int rootU = find(u);
        int rootV = find(v);

        if (rank[rootU] < rank[rootV])
            parent[rootU] = rootV;
        else if (rank[rootU] > rank[rootV])
            parent[rootV] = rootU;
        else {
            parent[rootV] = rootU;
            rank[rootU]++;
        }
    }
}

class MinimumHeap {
    List<Edge> heap;

    public MinimumHeap() {
        heap = new ArrayList<>();
    }

    public void insert(Edge edge) {
        heap.add(edge);
        int index = heap.size() - 1;
        bubbleUp(index);
    }

    public Edge extractMin() {
        Edge minEdge = heap.get(0);
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);
        bubbleDown(0);
        return minEdge;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    private void bubbleUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap.get(index).compareTo(heap.get(parentIndex)) >= 0)
                break;
            Collections.swap(heap, index, parentIndex);
            index = parentIndex;
        }
    }

    private void bubbleDown(int index) {
        int smallest = index;
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;

        if (leftChild < heap.size() && heap.get(leftChild).compareTo(heap.get(smallest)) < 0)
            smallest = leftChild;
        if (rightChild < heap.size() && heap.get(rightChild).compareTo(heap.get(smallest)) < 0)
            smallest = rightChild;

        if (smallest != index) {
            Collections.swap(heap, index, smallest);
            bubbleDown(smallest);
        }
    }
}

public class Q3B {
    public static List<Edge> kruskalMST(List<Edge> edges, int n) {
        List<Edge> minimumSpanningTree = new ArrayList<>();
        Collections.sort(edges);
        DisjointSet disjointSet = new DisjointSet(n);

        MinimumHeap minHeap = new MinimumHeap();
        minHeap.heap.addAll(edges);

        while (!minHeap.isEmpty()) {
            Edge currentEdge = minHeap.extractMin();
            int sourceParent = disjointSet.find(currentEdge.source);
            int destParent = disjointSet.find(currentEdge.destination);

            if (sourceParent != destParent) {
                minimumSpanningTree.add(currentEdge);
                disjointSet.union(sourceParent, destParent);
            }
        }
        return minimumSpanningTree;
    }

    public static void main(String[] args) {
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(0, 1, 10));
        edges.add(new Edge(0, 2, 6));
        edges.add(new Edge(0, 3, 5));
        edges.add(new Edge(1, 3, 15));
        edges.add(new Edge(2, 3, 4));

        int n = 4;

        List<Edge> minimumSpanningTree = kruskalMST(edges, n);

        System.out.println("Edges in the minimum spanning tree:");
        for (Edge edge : minimumSpanningTree) {
            System.out.println(edge.source + " - " + edge.destination + ": " + edge.weight);
        }
        /*
         * Output:
         * Edges in the minimum spanning tree:
            2 - 3: 4
            0 - 3: 5
            0 - 1: 10
         */
    }
}
