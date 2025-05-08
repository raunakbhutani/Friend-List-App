/* 
this class will:

Use a Scanner to collect friend info

Perform basic file operations (append, read, overwrite)

Assume friend info is stored as Name|Contact|Address|DOB (pipe-separated)
*/

package service;

import model.Friend;
import java.io.*;
import java.util.*;

public class FriendService {
    private final String filePath;
    private final Scanner scanner;

    public FriendService(String filePath, Scanner scanner) {  
        this.filePath = filePath;
        this.scanner = scanner;
    }

    public void addFriend() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter friend's name: ");
        String name = sc.nextLine();

        System.out.print("Enter contact number: ");
        String contact = sc.nextLine();

        System.out.print("Enter address: ");
        String address = sc.nextLine();

        System.out.print("Enter date of birth (dd-mm-yyyy): ");
        String dob = sc.nextLine();

        Friend friend = new Friend(name, contact, address, dob);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(friend.toDataLine());
            writer.newLine();
            System.out.println("Friend added successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }

        // sc.close();
    }

    public void viewFriends() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean found = false;
            System.out.println("\n--- Friend List ---");

            while ((line = reader.readLine()) != null) {
                Friend friend = Friend.fromDataLine(line);
                if (friend != null) {
                    System.out.println(friend);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No friends found.");
            }

        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }

    public void updateFriend() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the name of the friend to update: ");
        String nameToUpdate = sc.nextLine();
    
        File inputFile = new File(filePath);
        File tempFile = new File(inputFile.getParentFile(), "temp.txt");
    
        boolean updated = false;
    
        try (
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                Friend friend = Friend.fromDataLine(line);
                if (friend != null && friend.getName().equalsIgnoreCase(nameToUpdate)) {
                    System.out.print("Enter new contact number: ");
                    friend.setContact(sc.nextLine());
    
                    System.out.print("Enter new address: ");
                    friend.setAddress(sc.nextLine());
    
                    System.out.print("Enter new DOB (dd-mm-yyyy): ");
                    friend.setDob(sc.nextLine());
    
                    writer.write(friend.toDataLine());
                    writer.newLine();
                    updated = true;
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }
    
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error processing file: " + e.getMessage());
            return;
        }
    
        if (updated) {
            try {
                boolean deleted = inputFile.delete();
                boolean renamed = tempFile.renameTo(inputFile);
    
                if (deleted && renamed) {
                    System.out.println("Friend updated successfully.");
                } else {
                    System.out.println("Error updating file.");
                    System.out.println("Deleted: " + deleted + ", Renamed: " + renamed);
                }
            } catch (Exception e) {
                System.out.println("Exception during file rename: " + e.getMessage());
            }
        } else {
            tempFile.delete();
            System.out.println("Friend not found.");
        }
    }
      

public void deleteFriend() {
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter the name of the friend to delete: ");
    String nameToDelete = sc.nextLine();

    File inputFile = new File(filePath);
    File tempFile = new File(inputFile.getParentFile(), "temp.txt");

    boolean deleted = false;

    try (
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
    ) {
        String line;
        while ((line = reader.readLine()) != null) {
            Friend friend = Friend.fromDataLine(line);
            if (friend != null && friend.getName().equalsIgnoreCase(nameToDelete)) {
                deleted = true;
                continue; // skip writing this line
            }
            writer.write(line);
            writer.newLine();
        }

        writer.flush();
    } catch (IOException e) {
        System.out.println("Error processing file: " + e.getMessage());
        return;
    }

    if (deleted) {
        try {
            boolean inputDeleted = inputFile.delete();
            boolean renamed = tempFile.renameTo(inputFile);

            if (inputDeleted && renamed) {
                System.out.println("Friend deleted successfully.");
            } else {
                System.out.println("Error updating file.");
                System.out.println("Deleted: " + inputDeleted + ", Renamed: " + renamed);
            }
        } catch (Exception e) {
            System.out.println("Exception during file rename: " + e.getMessage());
        }
    } else {
        tempFile.delete();
        System.out.println("Friend not found.");
    }
}

}
