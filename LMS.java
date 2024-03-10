// Areesha Fatima 454459
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

// Book class
class Book {
    private int bookID;
    private String title, author, genre;
    private boolean available;

    // Constructor
    public Book(int book_ID, String title_, String author_, String genre_) {
        bookID = book_ID;
        title = title_;
        author = author_;
        genre = genre_;
        available = true;
    }

    // Getters and setters for Book attributes
    public void setBookID(int book_ID) {
        bookID = book_ID;
    }
    public int getBookID() {
        return bookID;
    }

    public void setTitle(String title_) {
        title = title_;
    }
    public String getTitle() {
        return title;
    }

    public void setAuthor(String author_) {
        author = author_;
    }
    public String getAuthor() {
        return author;
    }
    public void setGenre(String genre_) {
        genre = genre_;
    }
    public String getGenre() {
        return genre;
    }
    public void setAvailable(boolean available_) {
        available = available_;
    }

    public boolean isAvailable() {
        return available;
    }

    // To display book details
    public void displayBookDetails() {
        System.out.println("Book ID: " + bookID);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Genre: " + genre);
        System.out.print("Availability: ");
        if(available == true){
            System.out.println("Available");
        }else{
            System.out.println("Not Available");
        }
    }
}

// User class
class User {
    private int userID;
    private String name, contactInfo;
    private ArrayList<Book> borrowedBooks; //array list for dynamic memory

    // Constructor
    public User(int user_ID, String name_, String contact_Info) {
        userID = user_ID;
        name = name_;
        contactInfo = contact_Info;
        borrowedBooks = new ArrayList<>();
    }

    // Getters and setters for User attributes
    public void setUserID(int user_ID) {
        userID = user_ID;
    }

    public int getUserID() {
        return userID;
    }
    public void setName(String name_) {
        name = name_;
    }
    public String getName() {
        return name;
    }
    public void setContactInfo(String contact_Info) {
        contactInfo = contact_Info;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }


    // Method to display user info
    public void displayUserInfo() {
        System.out.println("User ID: " + userID);
        System.out.println("Name: " + name);
        System.out.println("Contact Info: " + contactInfo);
        System.out.println("Borrowed Books:");
        for (Book book : borrowedBooks) {  
     // enhanced for loop to iterate over a book object in the borrowedbooks array
            book.displayBookDetails();
        }
    }
}

// Library class
class Library {
    private ArrayList<Book> books;  // array lists for books and users
    private ArrayList<User> users;
    

    // Constructor
    public Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
        readBooksFile("books.txt");
        readUsersFile("users.txt");
    }

    // Getters for books and users
    public ArrayList<Book> getBooksArrayList() {
        return books; // returns the array list
    }

    public ArrayList<User> getUsersArrayList() {
        return users;
    }

    // Method to add a user
    public void addUser(int userID, String name, String contactInfo) {
        users.add(new User(userID, name, contactInfo));
        // .add is a built-in function for arraylists, adds a user object
        saveUsersToFile("users.txt");
        System.out.println("User added successfully!");
    }
    // Method to add a book
    public void addBook(int bookID, String title, String author, String genre) {
        books.add(new Book(bookID, title, author, genre));
        saveBooksToFile("books.txt");
        System.out.println("Book added successfully!");
    }
    // Method to save users to a file
    public void saveUsersToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (User user : users) {// iterating over each user object
                writer.write(user.getUserID() + "," + user.getName() + "," + user.getContactInfo() + "\n");
            }
            System.out.println("Users saved to file successfully.");
        } catch (IOException e) {
            System.err.println("Error saving users to file: " + e.getMessage());
        }
    }
    // Method to save books to a file
    public void saveBooksToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename, false)) {
            for (Book book : books) {
                writer.write(book.getBookID() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getGenre() + "," + book.isAvailable() + "\n");
            }
            System.out.println("Books saved to file successfully.");
        } catch (IOException e) {
            System.err.println("Error saving books to file: " + e.getMessage());
        }
    }
    // Method to read users from a file
    public void readUsersFile(String filename) {
        try (Scanner input = new Scanner(new File(filename))) {
            while (input.hasNextLine()) {  // to iterate over each line
                String line = input.nextLine();
                String[] parts = line.split(","); // splitting the line to an array
                if (parts.length == 3) {
                    int userID = Integer.parseInt(parts[0]); // convertin gstring to int
                    String name = parts[1];
                    String contactInfo = parts[2];
                    users.add(new User(userID, name, contactInfo));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error reading users from file: " + e.getMessage());
        }
    }
    // Method to read books from a file
    public void readBooksFile(String filename) {
        try (Scanner input = new Scanner(new File(filename))) { //takes the filename as input
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] parts = line.split(","); 
                if (parts.length == 5) { // all book details stored in its respective variables
                    int bookID = Integer.parseInt(parts[0]);
                    String title = parts[1];
                    String author = parts[2];
                    String genre = parts[3];
                    books.add(new Book(bookID, title, author, genre));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error reading books from file: " + e.getMessage());
        }
    }

    // Method to borrow a book
    public boolean borrowBook(int userID, int bookID) {
        User user = findUser(userID);
        if (user == null) { 
            System.out.println("User not found!");
            return false;
        }
        Book book = findBook(bookID);
        if (book == null) {
            System.out.println("Book not found!");
            return false;
        }
        if (book.isAvailable() == true) {
            book.setAvailable(false);
            user.getBorrowedBooks().add(book);  // adds the book to the user's borrow list
            return true;
        } else {
            System.out.println("Book not available for borrowing!");
            return false;
        }
    }
    // method to find a user by ID
    public User findUser(int userID) {
        for (User user : users) {
            if (user.getUserID() == userID) {
                return user;
            }
        }
        return null;  // no object that points to this user ID
    }

    // method to find a book by ID
    public Book findBook(int bookID) {
        for (Book book : books) {
            if (book.getBookID() == bookID) {
                return book;
            }
        }
        return null;
    }
    // Method to return a book
    public boolean returnBook(int userID, int bookID) {
        User user = findUser(userID);
        if (user == null) {
            System.out.println("User not found!");
            return false;
        }
        Book book = findBook(bookID);
        if (book == null) {
            System.out.println("Book not found!");
            return false;
        }

        if (user.getBorrowedBooks().contains(book)) { // if the user contains the specific book
            book.setAvailable(true);
            user.getBorrowedBooks().remove(book); // removes the book
            return true;
        } else {
            System.out.println("Book was not borrowed by this user!");
            return false;
        }
    }

    
    // Method to display all books
    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
        } else {
            for (Book book : books) { // iterates over book objects
                book.displayBookDetails(); 
                System.out.println();
            }
        }
    }

    // Method to search for books by user ID
    public void searchBooksByUserID(int userID) {
        User user = findUser(userID);
        if (user == null) {
            System.out.println("User not found!");
            return;
        }

        System.out.println("Books borrowed by User ID " + userID + ":");
        user.displayUserInfo();
    }
}

// Main class
public class LMS extends JFrame implements ActionListener { // for GUI
    private Library library;
    private JFrame frame;
    private JPanel panel;
    private JButton addUserButton, addBookButton, borrowBookButton, returnBookButton, displayBooksButton,searchbyIDButton,exitButton;

    // Constructor
    public LMS() {
        library = new Library();
        frame = new JFrame("Library Management System");
        panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1));
        panel.setBackground(Color.LIGHT_GRAY);
        // Buttons
        addUserButton = new JButton("Add User");
        addBookButton = new JButton("Add Book");
        borrowBookButton = new JButton("Borrow a Book");
        returnBookButton = new JButton("Return a Book");
        displayBooksButton = new JButton("Display All Books");
        searchbyIDButton = new JButton("Search for a Book");
        exitButton = new JButton("Exit");

        // Adding buttons to panel
        panel.add(addUserButton);
        panel.add(addBookButton);
        panel.add(borrowBookButton);
        panel.add(returnBookButton);
        panel.add(displayBooksButton);
        panel.add(searchbyIDButton);
        panel.add(exitButton);

        // Adding panel to frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setVisible(true);

        // Adding action listeners to buttons
        addUserButton.addActionListener(this);
        addBookButton.addActionListener(this);
        borrowBookButton.addActionListener(this);
        returnBookButton.addActionListener(this);
        displayBooksButton.addActionListener(this);
        searchbyIDButton.addActionListener(this);
        exitButton.addActionListener(this);
    }
    // ActionListener implementation
    public void actionPerformed(ActionEvent e) {
        Scanner input = new Scanner(System.in);
        if (e.getSource() == addUserButton) {
            JTextField userIDField = new JTextField(); // for the user to input
            JTextField nameField = new JTextField();
            JTextField contactInfoField = new JTextField();
            JPanel myPanel = new JPanel(new GridLayout(3, 2));
            myPanel.add(new JLabel("User ID:"));
            myPanel.add(userIDField);
            myPanel.add(new JLabel("Name:"));
            myPanel.add(nameField);
            myPanel.add(new JLabel("Contact Info:"));
            myPanel.add(contactInfoField);

            int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter User Details", JOptionPane.OK_CANCEL_OPTION);
                // shows a panel for options with "Ok" and "Cancel" options
            if (result == JOptionPane.OK_OPTION) {
                String userIDText = userIDField.getText();
                // getText is used to retrieve the string of text entered
                String name = nameField.getText();
                String contactInfo = contactInfoField.getText();
                if (!userIDText.isEmpty() && !name.isEmpty() && !contactInfo.isEmpty()) {
                    int userID = Integer.parseInt(userIDText);
                    if (contactInfo.length() == 10) { // contact info to have a length of 10
                        library.addUser(userID, name, contactInfo); //calling addUser method
                        JOptionPane.showMessageDialog(null, "User added!");
                    } else{
                        JOptionPane.showMessageDialog(null,"Invalid contact info! Please enter a 10-digit phone number.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields!");
                }
            }
        } else if (e.getSource() == addBookButton) {
            JTextField bookIDField = new JTextField();
            JTextField titleField = new JTextField();
            JTextField authorField = new JTextField();
            JTextField genreField = new JTextField();
            // book details
            JPanel panel = new JPanel(new GridLayout(4, 2));
            panel.add(new JLabel("Book ID:"));
            panel.add(bookIDField);
            panel.add(new JLabel("Title:"));
            panel.add(titleField);
            panel.add(new JLabel("Author:"));
            panel.add(authorField);
            panel.add(new JLabel("Genre:"));
            panel.add(genreField);
            int result = JOptionPane.showConfirmDialog(frame, panel, "Add Book",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String bookIDText = bookIDField.getText();
                String title = titleField.getText();
                String author = authorField.getText();
                String genre = genreField.getText();
                if (!bookIDText.isEmpty() && !title.isEmpty() && !author.isEmpty() && !genre.isEmpty()) {
                    // to make sure all entries are input
                    int bookID = Integer.parseInt(bookIDText);
                    library.addBook(bookID, title, author, genre);
                    JOptionPane.showMessageDialog(frame, "Book added successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields!");
                }
            }
        } else if (e.getSource() == borrowBookButton) {
            JTextField userIDField = new JTextField();
            JTextField bookIDField = new JTextField();
            JPanel panel = new JPanel(new GridLayout(2, 2));
            panel.add(new JLabel("User ID:"));
            panel.add(userIDField);
            panel.add(new JLabel("Book ID:"));
            panel.add(bookIDField);

            int result = JOptionPane.showConfirmDialog(frame, panel, "Borrow a Book",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String userIDText = userIDField.getText();
                String bookIDText = bookIDField.getText();
                if (!userIDText.isEmpty() && !bookIDText.isEmpty()) {
                    int userID = Integer.parseInt(userIDText); // converting string to int
                    int bookID = Integer.parseInt(bookIDText);
                    boolean success = library.borrowBook(userID, bookID);
            if (success == true) {
                JOptionPane.showMessageDialog(frame, "Book borrowed successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to borrow book. User or book not found or book is not available.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please fill in all fields!");
        }
    }
        } else if (e.getSource() == returnBookButton) {
            JTextField userIDField = new JTextField(); // creating text fields to input data
            JTextField bookIDField = new JTextField();
            JPanel panel = new JPanel(new GridLayout(2, 2));
            panel.add(new JLabel("User ID:"));
            panel.add(userIDField);
            panel.add(new JLabel("Book ID:"));
            panel.add(bookIDField);
            int result = JOptionPane.showConfirmDialog(frame, panel, "Return a Book",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String userIDText = userIDField.getText();
                String bookIDText = bookIDField.getText();
                if (!userIDText.isEmpty() && !bookIDText.isEmpty()) {
                    int userID = Integer.parseInt(userIDText);
                    int bookID = Integer.parseInt(bookIDText);
                    boolean success = library.returnBook(userID, bookID); // calling method to return book
                    if (success) {
                        JOptionPane.showMessageDialog(frame, "Book returned successfully!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to return book. User or book not found or book was not borrowed by this user.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields!");
                }
            }
        } else if (e.getSource() == searchbyIDButton) {
            String ID = JOptionPane.showInputDialog(frame, "Enter User ID:");
    if (ID != null && !ID.isEmpty()) { // User ID entered, perform search operation
        try {
            int userID = Integer.parseInt(ID);
            User user = library.findUser(userID);
            if (user != null) {
                JTextArea textArea = new JTextArea(20, 40);
                textArea.setEditable(false); // so that the user cannot edit any text
                if (user.getBorrowedBooks().isEmpty()) {
                    textArea.append("User has not borrowed any books.");
                } else {
                    textArea.append("Books borrowed by User ID " + userID + ":\n\n");
                    for (Book book : user.getBorrowedBooks()) {
                        textArea.append("Book ID: " + book.getBookID() + "\n");
                        textArea.append("Title: " + book.getTitle() + "\n");
                        textArea.append("Author: " + book.getAuthor() + "\n");
                        textArea.append("Genre: " + book.getGenre() + "\n\n");
                    }
                }
                JScrollPane scrollPane = new JScrollPane(textArea);
                // so that the pane can be scrolled to view all the text
                JOptionPane.showMessageDialog(frame, scrollPane, "Borrowed Books", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "User not found!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid User ID! Please enter a valid integer.");
        }
    } else {
        JOptionPane.showMessageDialog(frame, "Please enter a User ID!");
    }
        }else if (e.getSource() == displayBooksButton) {
            JTextArea textArea = new JTextArea(20, 40);
            textArea.setEditable(false);
            if (library.getBooksArrayList().isEmpty()) { // library is empty
                textArea.append("No books available in the library.");
            } else {
                for (Book book : library.getBooksArrayList()) {
                    textArea.append("Book ID: " + book.getBookID() + "\n");
                    textArea.append("Title: " + book.getTitle() + "\n");
                    textArea.append("Author: " + book.getAuthor() + "\n");
                    textArea.append("Genre: " + book.getGenre() + "\n");
                    textArea.append("Availability: " + (book.isAvailable() ? "Available" : "Not Available") + "\n\n");
                }
            }
            JScrollPane scrollPane = new JScrollPane(textArea);
            JOptionPane.showMessageDialog(frame, scrollPane, "Library Books", JOptionPane.PLAIN_MESSAGE);
        } else if (e.getSource() == exitButton) {
            JOptionPane.showMessageDialog(frame, "Exiting...");
            System.exit(0);  // to exit the program
        }
        input.close();
    }
    // Main method in LMS 
    public static void main(String[] args) {
        new LMS();  // calling constructor
    }
}
