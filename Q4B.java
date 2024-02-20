/*
 * This code finds the closest x values to a target k in a binary search tree by 
 * traversing in-order. It maintains an ArrayList of closest values encountered 
 * and updates it when a closer value is found. Finally, prints the closest values.
 * 
 * Input: x=2, k=4.2
 * Output: 4 5
 */
import java.util.ArrayList;
import java.util.Collections;

public class Q4B {
    public static class Node {
        Node left;
        Node right;
        int data;

        Node(int data) {
            this.data = data;
            right = left = null;
        }
    }

    public ArrayList<Integer> findClosest(int x, double k, Node root) {
        ArrayList<Integer> closest = new ArrayList<>();
        inOrder(root, k, closest, x);
        return closest;
    }

    private void inOrder(Node root, double k, ArrayList<Integer> closest, int x) {
        if (root == null)
            return;

        inOrder(root.left, k, closest, x);

        if (closest.size() < x) {
            closest.add(root.data);
        } else {
            double diff1 = Math.abs(root.data - k);
            double diff2 = Math.abs(closest.get(0) - k);
            if (diff1 < diff2) {
                closest.remove(0);
                closest.add(root.data);
            } else {
                /*  Since the tree is traversed in ascending order, if the difference between the current node
                and k is greater than the difference of the first element in the closest list and k,
                there's no need to continue traversal because the differences will only increase further.
                */
                return;
            }
        }
        inOrder(root.right, k, closest, x);
    }

    public static void main(String[] args) {
        Q4B tree = new Q4B();
        Node root = new Node(4);
        root.left = new Node(2);
        root.right = new Node(5);
        root.left.left = new Node(1);
        root.left.right = new Node(3);
        double k = 3.8 ;
        int x = 2;
        ArrayList<Integer> closeValues = tree.findClosest(x, k, root);
        Collections.sort(closeValues);
        System.out.print("The closest values are : ");
        for (double value : closeValues) {
            System.out.print((int) value + "  ");  //Output: 3  4
        }
    }
}
