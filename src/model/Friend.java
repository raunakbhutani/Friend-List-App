/*
 The Friend class will:

Hold fields for name, contact, address, and dob

Include a constructor, getters, setters, and toString()

Help keep your app organized and avoid working with raw strings in your app logic
 */

package model;

public class Friend {

    private String name;
    private String contact;
    private String address;
    private String dob;

    public Friend(String name, String contact, String address, String dob) {
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.dob = dob;
    }

    // Getters
    public String getName() { return name; }
    public String getContact() { return contact; }
    public String getAddress() { return address; }
    public String getDob() { return dob; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setContact(String contact) { this.contact = contact; }
    public void setAddress(String address) { this.address = address; }
    public void setDob(String dob) { this.dob = dob; }

    @Override
    public String toString() {
        return "Name: " + name + "\n" +
               "Contact: " + contact + "\n" +
               "Address: " + address + "\n" +
               "DOB: " + dob + "\n------------------";
    }

    // Converts to file line
    public String toDataLine() {
        return name + "|" + contact + "|" + address + "|" + dob;
    }

    // Creates Friend object from file line
    public static Friend fromDataLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 4) return null;
        return new Friend(parts[0], parts[1], parts[2], parts[3]);
    }
}
