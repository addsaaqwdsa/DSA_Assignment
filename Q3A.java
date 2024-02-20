/*
 * This algorithm tracks scores and calculates their median. It maintains a 
 * sorted list of scores and updates it whenever a new score is added. To 
 * find the median, it checks if the number of scores is even or odd: if 
 * even, it calculates the average of the two middle scores; if odd, it 
 * returns the middle score. This ensures accurate median calculation even 
 * as scores are added dynamically.
 * 
 * 
 */
import java.util.*;

public class Q3A {
    private ArrayList<Double> scores;

    public Q3A() {
        scores = new ArrayList<>();
    }

    public void addScore(double score) {
        scores.add(score);
        Collections.sort(scores);
    }

    public double getMedianScore() {
        int size = scores.size();
        if (size == 0) {
            System.out.println("No scores have been entered.");
            return -99999999.9;
        }
        if (size % 2 == 0) {
            // If the number of scores is even, return the average of the two middle scores
            int mid = size / 2;
            return (scores.get(mid - 1) + scores.get(mid)) / 2.0;
        } else {
            // If the number of scores is odd, return the middle score
            return scores.get(size / 2);
        }
    }

    public static void main(String[] args) {
        Q3A scoreTracker = new Q3A();
        scoreTracker.addScore(85.5);
        scoreTracker.addScore(92.3);
        scoreTracker.addScore(77.8);
        scoreTracker.addScore(90.1);
        double median1 = scoreTracker.getMedianScore();
        System.out.println("Median 1: " + median1);

        scoreTracker.addScore(81.2);
        scoreTracker.addScore(88.7);
        double median2 = scoreTracker.getMedianScore();
        System.out.println("Median 2: " + median2);
        /*
        Output: 
        Median 1: 87.8     
        Median 2: 87.1
        */
    }
}
