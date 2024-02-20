/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

/**
 *
 * @author LENOVO
 */
import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class Graph {

    private List<String> nodes;
    private Map<String, List<String>> adjacencyList;

    public Graph(List<String> nodes) {
        this.nodes = nodes;
        this.adjacencyList = new HashMap<>();
        for (String node : nodes) {
            adjacencyList.put(node, new ArrayList<>());
        }
    }

    /*to create a connection between user, if user1(source) follows user2(destination), 
    edge is created between them as user -> user2; */
    public void addEdge(String source, String destination) {
        Graph graph = new Graph(nodes);

        Graph dummyGraph = graph.createDummyGraph();

        List<String> connectedNodes = findConnectedNodes(dummyGraph, source);
        for(String node:connectedNodes){
            if(node.equals(destination)){
                JOptionPane.showMessageDialog(null, " You are already following " + destination);
                return;
            }
        }
        adjacencyList.get(source).add(destination);
        writeEdgeToFile(source, destination);  //write the edge to file
        JOptionPane.showMessageDialog(null, "You are now following " + destination);
    }
    
    
    //used to check if edge exists or not
    public boolean hasEdge(String source, String destination) {
        Graph graphDuplicate = createDummyGraph();
        return graphDuplicate.hasEdgeTraversal(source, destination);
    }

    public boolean hasEdgeTraversal(String source, String destination) {
        List<String> visited = new ArrayList<>();
        visited.add(source); 
        return dfs(source, destination, visited);
    }
    //traversal to check is connection exists or not
    private boolean dfs(String source, String destination, List<String> visited) {
        List<String> neighbors = adjacencyList.get(source);
        if (neighbors == null) {
            return false;
        }
        if (neighbors.contains(destination)) {
            return true;
        }

        for (String neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                visited.add(neighbor);
                if (dfs(neighbor, destination, visited)) {
                    return true;
                }
            }
        }

        return false;
    }

    //write the graph to file
    public void writeEdgeToFile(String source, String destination) {
        try (FileWriter writer = new FileWriter("src/txt/connections.txt", true)) {
            writer.write(source + " -> " + destination + ";\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //reads the txt and creates graph
    public void readGraphFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" -> ");
                if (parts.length == 2) {
                    String source = parts[0];
                    String destination = parts[1].replaceAll(";$", ""); // Remove semicolon at the end
                    addEdge(source, destination);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //create dummy graph for traversal so that original graph is not altered
    public Graph createDummyGraph() {
        Graph graphDuplicate = new Graph(nodes);
        try (BufferedReader reader = new BufferedReader(new FileReader("src/txt/connections.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int arrowIndex = line.indexOf("->");
                if (arrowIndex != -1) {
                    String source = line.substring(0, arrowIndex).trim();
                    String destination = line.substring(arrowIndex + 2).replaceAll(";$", "").trim(); // Remove semicolon at the end
                    if (!graphDuplicate.adjacencyList.containsKey(source)) {
                        graphDuplicate.adjacencyList.put(source, new ArrayList<>()); // Add the source node if not present
                    }
                    graphDuplicate.adjacencyList.get(source).add(destination); // Add the edge to the adjacency list
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graphDuplicate;
    }
    
    //method to see whom a user is following
    public static List<String> findConnectedNodes(Graph graph, String startNode) {
        List<String> connectedNodes = new ArrayList<>();
        List<String> neighbors = graph.adjacencyList.getOrDefault(startNode, new ArrayList<>());
        connectedNodes.addAll(neighbors); // Add immediate neighbors
        return connectedNodes;
    }

    //method for finding recommended friends....we search for friends of our friends
    public static List<String> findNeighborsOfNeighbors(Graph graph, String startNode) {
        List<String> neighborsOfNeighbors = new ArrayList<>();
        List<String> neighbors = graph.adjacencyList.getOrDefault(startNode, new ArrayList<>());
        for (String neighbor : neighbors) {
            List<String> neighborNeighbors = graph.adjacencyList.getOrDefault(neighbor, new ArrayList<>());
            for (String neighborOfNeighbor : neighborNeighbors) {
                if (!neighborOfNeighbor.equals(startNode) && !neighbors.contains(neighborOfNeighbor)
                        && !neighborsOfNeighbors.contains(neighborOfNeighbor)) {
                    neighborsOfNeighbors.add(neighborOfNeighbor);
                }
            }
        }
        return neighborsOfNeighbors;
    }

    public static void main(String[] args) {

    }
}
