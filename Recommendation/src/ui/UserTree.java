package ui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import static java.util.Collections.emptyList;
import java.util.HashMap;
import java.util.List;

public class UserTree {

    private HashMap<String, HashMap<String, StringBuilder>> categoryToUserPostsMap;

    public UserTree() {
        categoryToUserPostsMap = new HashMap<>();
    }
    
    //adds post to hashmap by interest
    public void addContent(String content, String username, String interest) {
        categoryToUserPostsMap.putIfAbsent(interest, new HashMap<>());
        HashMap<String, StringBuilder> userPostsMap = categoryToUserPostsMap.get(interest);
        userPostsMap.putIfAbsent(username, new StringBuilder());
        userPostsMap.get(username).append(content).append("\n");
        writeToFile(content, username, interest); //writes to file
    }
    
    //writes hashmap to file
    private void writeToFile(String content, String username, String interest) {
        String filePath = "src/txt/" + interest + ".txt";
        try {
            FileWriter writer = new FileWriter(filePath, true);
            writer.write("Username: " + username + "\n");
            writer.write("Content: " + content + "\n\n");
            writer.close();
            System.out.println("Content added to " + filePath + " for interest: " + interest);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    //gets all posts by username
    public List<String> getAllUserPosts(String username) {
        List<String> allUserPosts = new ArrayList<>();
        for (String interest : getAllInterests()) {
            List<String> userPosts = getUserPosts(interest, username);
            allUserPosts.addAll(userPosts);
        }
        return allUserPosts;
    }
    
    //gets posts by interest and username
    private List<String> getUserPosts(String interest, String username) {
        String filePath = "src/txt/" + interest + ".txt";
        List<String> userPosts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean userFound = false;
            StringBuilder userContent = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Username: ")) {
                    if (userFound) {
                        userPosts.add(userContent.toString());
                        userContent = new StringBuilder();
                    }
                    userFound = line.substring(10).equals(username);
                } else if (userFound && line.startsWith("Content: ")) {
                    userContent.append(line.substring(9)).append("\n");
                }
            }


            if (userFound) {
                userPosts.add(userContent.toString());
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return userPosts;
    }
    
    //returns the interests, not needed to create separately, but if list is big, seperate method is better!! 
    private List<String> getAllInterests() {
        return List.of("Sports", "Entertainment", "IT");
    }

    //gets category to which a user is interested
    public String findInterestWithMaxPostsByUser(String username) {
        int maxPosts = 0;
        String interestWithMaxPosts = "";

        for (String interest : getAllInterests()) {
            int userPostsCount = getMaxPostsInInterest(interest, username);
            if (userPostsCount > maxPosts) {
                maxPosts = userPostsCount;
                interestWithMaxPosts = interest;
            }
        }

        return interestWithMaxPosts;
    }

    
    private int getMaxPostsInInterest(String interest, String username) {
        String filePath = "src/txt/" + interest + ".txt";
        int userPostsCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Username: ") && line.substring(10).equals(username)) {
                    userPostsCount++;
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return userPostsCount;
    }

    
    public List<String> getAllPostsByInterest(String interest) {
        if(interest.equals("")){
            System.out.println("checkpoint");
            return emptyList();
        }
        List<String> allPosts = new ArrayList<>();
        String filePath = "src/txt/" + interest + ".txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            StringBuilder postContent = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Content: ")) {
                    postContent.append(line.substring(9)).append("\n");
                } else if (line.isEmpty()) {
                    allPosts.add(postContent.toString());
                    postContent = new StringBuilder();
                }
            }
            if (postContent.length() > 0) {
                allPosts.add(postContent.toString());
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return allPosts;
    }

    public static void main(String[] args) {
        
    }
}
