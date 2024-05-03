import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactManager {
    private List<Contact> contacts;
    private String fileName;

    public ContactManager(String fileName) {
        this.contacts = new ArrayList<>();
        this.fileName = fileName;
        loadContacts(); // Load contacts from file on initialization
    }

    // Load contacts from file
    @SuppressWarnings("unchecked")
    private void loadContacts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            contacts = (List<Contact>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Contacts file not found. Creating new file.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading contacts: " + e.getMessage());
        }
    }

    // Save contacts to file
    private void saveContacts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(contacts);
        } catch (IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }
    }

    // Add a new contact
    public void addContact(String name, String phoneNumber, String emailAddress) {
        Contact newContact = new Contact(name, phoneNumber, emailAddress);
        contacts.add(newContact);
        saveContacts();
    }

    // View all contacts
    public void viewContacts() {
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }

    // Edit an existing contact
    public void editContact(int index, String name, String phoneNumber, String emailAddress) {
        if (index >= 0 && index < contacts.size()) {
            Contact contact = contacts.get(index);
            contact.setName(name);
            contact.setPhoneNumber(phoneNumber);
            contact.setEmailAddress(emailAddress);
            saveContacts();
        } else {
            System.out.println("Invalid contact index.");
        }
    }

    // Delete a contact
    public void deleteContact(int index) {
        if (index >= 0 && index < contacts.size()) {
            contacts.remove(index);
            saveContacts();
        } else {
            System.out.println("Invalid contact index.");
        }
    }

    public static void main(String[] args) {
        ContactManager contactManager = new ContactManager("contacts.txt");
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nContact Manager Menu:");
            System.out.println("1. Add New Contact");
            System.out.println("2. View All Contacts");
            System.out.println("3. Edit Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Enter email address: ");
                    String emailAddress = scanner.nextLine();
                    contactManager.addContact(name, phoneNumber, emailAddress);
                    break;
                case 2:
                    System.out.println("\nContacts List:");
                    contactManager.viewContacts();
                    break;
                case 3:
                    System.out.print("Enter index of contact to edit: ");
                    int editIndex = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new phone number: ");
                    String newPhoneNumber = scanner.nextLine();
                    System.out.print("Enter new email address: ");
                    String newEmailAddress = scanner.nextLine();
                    contactManager.editContact(editIndex, newName, newPhoneNumber, newEmailAddress);
                    break;
                case 4:
                    System.out.print("Enter index of contact to delete: ");
                    int deleteIndex = scanner.nextInt();
                    contactManager.deleteContact(deleteIndex);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
