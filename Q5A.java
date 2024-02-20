/*
 * Test:
 * Input: {0, 5, 10, 15},
    {5, 0, 6, 12},
    {10, 6, 0, 8},
    {15, 12, 8, 0}
    Output: [0, 1, 2, 3, 0]
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Q5A {
    private double[][] pheromoneMatrix;
    private int[][] distanceMatrix;
    private int numberOfCities;
    private int numberOfAnts;
    private double alpha;
    private double beta;
    private double evaporationRate;
    private double initialPheromone;

    public Q5A(int numberOfCities, int numberOfAnts, double alpha, double beta,
                        double evaporationRate, double initialPheromone) {
        this.numberOfCities = numberOfCities;
        this.numberOfAnts = numberOfAnts;
        this.alpha = alpha;
        this.beta = beta;
        this.evaporationRate = evaporationRate;
        this.initialPheromone = initialPheromone;
        pheromoneMatrix = new double[numberOfCities][numberOfCities];
        distanceMatrix = new int[numberOfCities][numberOfCities];
    }

    public void initializePheromoneMatrix() {
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                pheromoneMatrix[i][j] = initialPheromone;
            }
        }
    }

    public void initializeDistanceMatrix(int[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public List<Integer> findShortestPath() {
        Random random = new Random();
        List<Integer> bestPath = null;
        int bestDistance = Integer.MAX_VALUE;
        for (int iteration = 0; iteration < numberOfAnts; iteration++) {
            List<Integer> antPath = constructAntPath(random);
            int antDistance = calculatePathDistance(antPath);
            if (antDistance < bestDistance) {
                bestDistance = antDistance;
                bestPath = antPath;
            }
            updatePheromoneTrail(antPath, antDistance);
        }
        return bestPath;
    }

    private List<Integer> constructAntPath(Random random) {
        List<Integer> antPath = new ArrayList<>();
        boolean[] visitedCities = new boolean[numberOfCities];
        int startCity = 0; //starting from 0
        antPath.add(startCity);
        visitedCities[startCity] = true;
        int currentCity = startCity;
        while (antPath.size() < numberOfCities) {
            int nextCity = selectNextCity(currentCity, visitedCities, random);
            antPath.add(nextCity);
            visitedCities[nextCity] = true;
            currentCity = nextCity;
        }
        // Return to the starting city to complete the tour
        antPath.add(startCity);
        return antPath;
    }

    private int selectNextCity(int currentCity, boolean[] visitedCities, Random random) {
        double[] probabilities = new double[numberOfCities];
        double probabilitiesSum = 0.0;
        for (int city = 0; city < numberOfCities; city++) {
            if (!visitedCities[city]) {
                double pheromoneLevel = Math.pow(pheromoneMatrix[currentCity][city], alpha);
                double distance = 1.0 / Math.pow(distanceMatrix[currentCity][city], beta);
                probabilities[city] = pheromoneLevel * distance;
                probabilitiesSum += probabilities[city];
            }
        }
        double randomValue = random.nextDouble();
        double cumulativeProbability = 0.0;
        for (int city = 0; city < numberOfCities; city++) {
            if (!visitedCities[city]) {
                double probability = probabilities[city] / probabilitiesSum;
                cumulativeProbability += probability;
                if (randomValue <= cumulativeProbability) {
                    return city;
                }
            }
        }
        // Should never reach here
        return -1;
    }

    private int calculatePathDistance(List<Integer> path) {
        int distance = 0;
        int pathSize = path.size();
        for (int i = 0; i < pathSize - 1; i++) {
            int currentCity = path.get(i);
            int nextCity = path.get(i + 1);
            distance += distanceMatrix[currentCity][nextCity];
        }
        return distance;
    }

    private void updatePheromoneTrail(List<Integer> path, int distance) {
        double pheromoneDeposit = 1.0 / distance;
        for (int i = 0; i < path.size() - 1; i++) {
            int currentCity = path.get(i);
            int nextCity = path.get(i + 1);
            pheromoneMatrix[currentCity][nextCity] = (1 - evaporationRate) *
                    pheromoneMatrix[currentCity][nextCity] + evaporationRate * pheromoneDeposit;
            // Update the pheromone for the reverse direction too
            pheromoneMatrix[nextCity][currentCity] = pheromoneMatrix[currentCity][nextCity];
        }
    }

    public static void main(String[] args) {
        // Example usage
        int[][] distanceMatrix = {{ 0, 10, 15, 20 },
        { 5, 0, 9, 10 },
        { 6, 13, 0, 12 },
        { 8, 8, 9, 0 }};
        int numberOfCities = 4;
        int numberOfAnts = 10;
        double alpha = 1.0;
        double beta = 2.0;
        double evaporationRate = 0.5;
        double initialPheromone = 0.1;
        Q5A antColony = new Q5A(numberOfCities, numberOfAnts,
                alpha, beta, evaporationRate, initialPheromone);
        antColony.initializePheromoneMatrix();
        antColony.initializeDistanceMatrix(distanceMatrix);
        List<Integer> shortestPath = antColony.findShortestPath();
        System.out.println("Shortest path: " + shortestPath); //Output: [0, 1, 2, 3, 0]
    }
}
