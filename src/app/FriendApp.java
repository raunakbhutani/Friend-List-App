/*
 What This File Does:
Prompts for username & password (basic auth).

Uses FriendService for all operations.

Displays a menu to add, view, update, and delete friends.

Works entirely in the console (CLI-based).
 */

package app;

import service.FriendService;
import java.io.*;
import java.util.Properties;
import java.util.Scanner;

public class FriendApp {

    private static final String CONFIG_FILE = "config/app.properties";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String username = login(scanner);

        if (username == null) {
            System.out.println("Login failed. Exiting...");
            return;
        }

        String userFilePath = "data/" + username + "_FriendList.txt";
        FriendService service = new FriendService(userFilePath, scanner);

        while (true) {
            System.out.println("\n--- Friend List Menu ---");
            System.out.println("1. Add Friend");
            System.out.println("2. View Friends");
            System.out.println("3. Update Friend");
            System.out.println("4. Delete Friend");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> service.addFriend();
                case 2 -> service.viewFriends();
                case 3 -> service.updateFriend();
                case 4 -> service.deleteFriend();
                case 5 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static String login(Scanner scanner) {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream(CONFIG_FILE));

            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            String storedPassword = props.getProperty(username);
            if (storedPassword != null && storedPassword.equals(password)) {
                System.out.println("Login successful.");
                return username;
            } else {
                System.out.println("Invalid credentials.");
                return null;
            }
        } catch (IOException e) {
            System.out.println("Error loading config file.");
            return null;
        }
    }
}