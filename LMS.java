// Areesha Fatima 454459
import javax.swing.*; // for GUI
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;  // for file handling
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;  // for exception handling

// Book class
class Book {
    private int bookID;  // book attributes
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
    private int userID;  // user attributes
    private String name, contactInfo;
    private ArrayList<Book> borrowedBooks; //array list for dynamic memory

    // Constructor
    public User(int user_ID, String name_, String contact_Info) {
        userID = user_ID;
        name = name_;
        contactInfo = contact_Info;
        borrowedBooks = new ArrayList<>(); // array list for borrowed books
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
    public ArrayList<Book> getBorrowedBooks() {  // returns the borrowedbooks list
        return borrowedBooks;
    }

    // Method to display user info
    public void displayUserInfo() {
        System.out.println("User ID: " + userID);
        System.out.println("Name: " + name);
        System.out.println("Contact Info: " + contactInfo);
        System.out.println("Borrowed Books:");
        for (Book book : borrowedBooks) {  
     // enhanced for loop to iterate over a book object in the borrowedbooks arraylist
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
        books = new ArrayList<>();  //  intialized to store book objects
        users = new ArrayList<>();  //  intialized to store user objects
        readBooksFile("books.txt");  // reads the book details from book.txt
        readUsersFile("users.txt");
    }

    // Getters for books and users
    public ArrayList<Book> getBooksArrayList() {
        return books; // returns the book arraylist
    }
    public ArrayList<User> getUsersArrayList() {
        return users;  // returns the user arraylist
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
        saveBooksToFile("books.txt"); // saves the book to the books.txt file
        System.out.println("Book added successfully!");
    }
    // Method to save users to a file
    public void saveUsersToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (User user : users) {// iterating over each user object
                writer.write(user.getUserID() + "," + user.getName() + "," + user.getContactInfo() + "\n");
            }  // and writes each attribute to the file in 1 line for 1 user separated by commas
            System.out.println("Users saved to file successfully.");
        } catch (IOException e) {  // if an error arises
            System.err.println("Error saving users to file: " + e.getMessage());
        }
    }
    // Method to save books to a file
    public void saveBooksToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename) { 
            for (Book book : books) {  // same procedure as users
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
                String[] parts = line.split(","); // splitting the line to an array using .split() function
                if (parts.length == 3) {
                    int userID = Integer.parseInt(parts[0]); // converting string to int
                    String name = parts[1];
                    String contactInfo = parts[2];
                    users.add(new User(userID, name, contactInfo)); // adds the user object to the users list
                }
            }
        } catch (FileNotFoundException e) {  // if file not found
            System.err.println("Error reading users from file: " + e.getMessage());  // used for printing errors
        }
    }
    // Method to read books from a file
    public void readBooksFile(String filename) {
        try (Scanner input = new Scanner(new File(filename))) { //takes the filename as input
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] parts = line.split(","); 
                if (parts.length == 5) { // all book details stored in its respective variables
                    int bookID = Integer.parseInt(parts[0]); // converting string to int
                    String title = parts[1];
                    String author = parts[2];
                    String genre = parts[3];
                    books.add(new Book(bookID, title, author, genre)); // adds book to books list
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error reading books from file: " + e.getMessage());
        }
    }

    // Method to borrow a book
    public boolean borrowBook(int userID, int bookID) {
        User user = findUser(userID);
        if (user == null) {   // if user object does not point to any memory space, the user does not exist
            System.out.println("User not found!");
            return false;
        }
        Book book = findBook(bookID);
        if (book == null) {   // does not point to a book object
            System.out.println("Book not found!");
            return false;
        }
        if (book.isAvailable() == true) {  // if the book has not been borrowed before
            book.setAvailable(false);
            user.getBorrowedBooks().add(book);  // adds the book to the user's borrowed books list
            return true;
        } else {
            System.out.println("Book not available for borrowing!");
            return false;
        }
    }
    // method to find a user by ID
    public User findUser(int userID) {
        for (User user : users) {  // iterates over all user objects and finds the respective one
            if (user.getUserID() == userID) {
                return user;
            }
        }
        return null;  // no object that points to this user ID
    }

    // method to find a book by ID
    public Book findBook(int bookID) {
        for (Book book : books) { // iterates over all book objects and finds the respective one
            if (book.getBookID() == bookID) {
                return book;
            }
        }
        return null;  // returns null if book is not found
    }
    // Method to return a book
    public boolean returnBook(int userID, int bookID) {
        User user = findUser(userID);
        if (user == null) {       // checks if user exists
            System.out.println("User not found!");
            return false;
        }
        Book book = findBook(bookID);
        if (book == null) {    // checks if book exists
            System.out.println("Book not found!");
            return false;
        }

        if (user.getBorrowedBooks().contains(book)) { // if the user's borrow list contains the specific book
            book.setAvailable(true);  // setting the book's availability status as true now
            user.getBorrowedBooks().remove(book); // removes the book
            return true;
        } else {
            System.out.println("Book was not borrowed by this user!");
            return false;
        }
    }

    
    // Method to display all books
    public void displayAllBooks() {
        if (books.isEmpty()) {   // uses the isEmpty() function to check if there are any books to be displayed
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
        if (user == null) {  //if user does not exist
            System.out.println("User not found!");
            return;
        }

        System.out.println("Books borrowed by User ID " + userID + ":");
        user.displayUserInfo();
    }
}

// Main class
public class LMS extends JFrame implements ActionListener { // for GUI
    // LMS class inherits the methods of the class JFrame and implements the action listener interface(used for buttons and handle actions)
    private Library library;  // object of Library class
    private JFrame frame;   // object of JFrame class
    private JPanel panel;     // object of JPanel class
    private JButton addUserButton, addBookButton, borrowBookButton, returnBookButton, displayBooksButton,searchbyIDButton,exitButton;
// used for buttons
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
        frame.add(panel, BorderLayout.CENTER);  // to organize the layout of the panel
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // setting close operation as default
        frame.setSize(400, 500);
        frame.setVisible(true);  // to display the screen as when a JFrame is created it is not visible by default

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
        if (e.getSource() == addUserButton) {  // getSource() method returns which event is causing an action to perform
            JTextField userIDField = new JTextField(); // textfields are provided for the user to input
            JTextField nameField = new JTextField();
            JTextField contactInfoField = new JTextField();
            JPanel myPanel = new JPanel(new GridLayout(3, 2));   
 // creates 3 rows and 2 columns  using the GridLayout() method and creates an object of JPanel to create a new panel for adding user
            myPanel.add(new JLabel("User ID:"));  //JLabel is used to print a descriptive text and prompts user to add User ID
            myPanel.add(userIDField);    // takes the UserID as input
            myPanel.add(new JLabel("Name:"));  // same as user ID
            myPanel.add(nameField);
            myPanel.add(new JLabel("Contact Info:"));
            myPanel.add(contactInfoField);

            int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter User Details", JOptionPane.OK_CANCEL_OPTION);
                // null so that this option is displayed at the center, display the panel created for adding user
              // The 3rd parameter is the title of the dialog box and 4th shows a panel for options with "Ok" and "Cancel" 
            if (result == JOptionPane.OK_OPTION) {  // compares what the user chose "OK" or "Cancel" and then proceeds
                String userIDText = userIDField.getText(); // getText is used to retrieve the text entered by the user
                String name = nameField.getText();
                String contactInfo = contactInfoField.getText();
                if (!userIDText.isEmpty() && !name.isEmpty() && !contactInfo.isEmpty()) { // to make sure that no field is left empty
                    int userID = Integer.parseInt(userIDText);  // converting the user ID from string to int
                    if (contactInfo.length() == 10) { // contact info to have a length of 10 to be valid
                        library.addUser(userID, name, contactInfo); //calling addUser method
                        JOptionPane.showMessageDialog(null, "User added!");  // null specifies that there the text is to be printed at the center
                    } else{
                        JOptionPane.showMessageDialog(null,"Invalid contact info! Please enter a 10-digit phone number.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields!");
                }
            }
        } else if (e.getSource() == addBookButton) {
            JTextField bookIDField = new JTextField(); // textfields are provided for the user to input
            JTextField titleField = new JTextField();
            JTextField authorField = new JTextField();
            JTextField genreField = new JTextField();
            // book details
            JPanel panel = new JPanel(new GridLayout(4, 2));
             // creates 4 rows and 2 columns  using the GridLayout() method and creates an object of JPanel to create a new panel for adding book
            panel.add(new JLabel("Book ID:")); // used to prompt the user to enter Book ID and respective book details
            panel.add(bookIDField);  // takes the value entered
            panel.add(new JLabel("Title:"));
            panel.add(titleField);
            panel.add(new JLabel("Author:"));
            panel.add(authorField);
            panel.add(new JLabel("Genre:"));
            panel.add(genreField);
            int result = JOptionPane.showConfirmDialog(frame, panel, "Add Book",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            // null so that this option is displayed at the center, display the panel created for adding book
             // The 3rd parameter is the title of the dialog box and 4th shows a panel for options with "Ok" and "Cancel" 
            if (result == JOptionPane.OK_OPTION) {
                String bookIDText = bookIDField.getText();  // gets the values entered by the user
                String title = titleField.getText();
                String author = authorField.getText();
                String genre = genreField.getText();
                if (!bookIDText.isEmpty() && !title.isEmpty() && !author.isEmpty() && !genre.isEmpty()) { // to make sure no field is left empty
                    int bookID = Integer.parseInt(bookIDText);  // converting the bookID from string to int
                    library.addBook(bookID, title, author, genre);  // calls the addBook method
                    JOptionPane.showMessageDialog(frame, "Book added successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields!");
                }
            }
        } else if (e.getSource() == borrowBookButton) {
            JTextField userIDField = new JTextField();  // text field to enter text
            JTextField bookIDField = new JTextField();
            JPanel panel = new JPanel(new GridLayout(2, 2));   // creating panale of 2 rows and 2 columns
            panel.add(new JLabel("User ID:"));
            panel.add(userIDField);
            panel.add(new JLabel("Book ID:"));
            panel.add(bookIDField);
            int result = JOptionPane.showConfirmDialog(frame, panel, "Borrow a Book",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
     // frame to determine the position of the dialog box, displays the panel created for borrowing a book
     // The 3rd parameter is the title of the dialog box , 4th shows a panel for options with "Ok" and "Cancel" and 5th specifies the message type that it is plain
            if (result == JOptionPane.OK_OPTION) 
    //The showConfirmDialog method returns an integer value representing the user's choice. If the user clicks the OK button, it returns JOptionPane.OK_OPTION. If the user clicks the Cancel button or closes the dialog, it returns JOptionPane.CANCEL_OPTION
                String userIDText = userIDField.getText(); // retrieves the user and book ID
                String bookIDText = bookIDField.getText();
                if (!userIDText.isEmpty() && !bookIDText.isEmpty()) {  // to make sure no field is left empty
                    int userID = Integer.parseInt(userIDText); // converting string to int
                    int bookID = Integer.parseInt(bookIDText);
                    boolean success = library.borrowBook(userID, bookID); // borrow book returns a boolean value
            if (success == true) {  // if book borrowed else
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
            JPanel panel = new JPanel(new GridLayout(2, 2));  // creating grid of 2 rows  and 2 columns
            panel.add(new JLabel("User ID:"));  // to input the user's ID and book ID respectively
            panel.add(userIDField);
            panel.add(new JLabel("Book ID:"));
            panel.add(bookIDField);
            int result = JOptionPane.showConfirmDialog(frame, panel, "Return a Book",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
// frame to determine the position of the dialog box, displays the panel created for returning a book
// The 3rd parameter is the title of the dialog box , 4th shows a panel for options with "Ok" and "Cancel" and 5th specifies the message type that it is plain
            if (result == JOptionPane.OK_OPTION) {
                String userIDText = userIDField.getText();
                String bookIDText = bookIDField.getText();
                if (!userIDText.isEmpty() && !bookIDText.isEmpty()) {  // no field to be left empty
                    int userID = Integer.parseInt(userIDText);
                    int bookID = Integer.parseInt(bookIDText);
                    boolean success = library.returnBook(userID, bookID); // calling method to return book
                    if (success) {  // if method returns true else
                        JOptionPane.showMessageDialog(frame, "Book returned successfully!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to return book. User or book not found or book was not borrowed by this user.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields!");
                }
            }
        } else if (e.getSource() == searchbyIDButton) {
            String ID = JOptionPane.showInputDialog(frame, "Enter User ID:"); // prompting user to add ID
    if (ID != null && !ID.isEmpty()) { // User ID entered, perform search operation
        try {
            int userID = Integer.parseInt(ID); // converting string to int
            User user = library.findUser(userID);  // finds that user exists in users list
            if (user != null) {  // user not NULL so exists
                JTextArea textArea = new JTextArea(20, 40);  // to display multiple lines of text, height: 20, width:40
                textArea.setEditable(false); // so that the user cannot edit any text
                if (user.getBorrowedBooks().isEmpty()) {
                    textArea.append("User has not borrowed any books."); //appends text to the end of the text area
                } else {
                    textArea.append("Books borrowed by User ID " + userID + ":\n\n");
                    for (Book book : user.getBorrowedBooks()) {  // displays details of the book borrowed by user
                        textArea.append("Book ID: " + book.getBookID() + "\n");
                        textArea.append("Title: " + book.getTitle() + "\n");
                        textArea.append("Author: " + book.getAuthor() + "\n");
                        textArea.append("Genre: " + book.getGenre() + "\n\n");
                    }
                }
                JScrollPane scrollPane = new JScrollPane(textArea);// so that the pane can be scrolled to view all the text
                JOptionPane.showMessageDialog(frame, scrollPane, "Borrowed Books", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "User not found!");
            }
        } catch (NumberFormatException ex) { // if an invalid string input is added which cannot be converted to int
            JOptionPane.showMessageDialog(frame, "Invalid User ID! Please enter a valid integer.");
        }
    } else {
        JOptionPane.showMessageDialog(frame, "Please enter a User ID!");
    }
        }else if (e.getSource() == displayBooksButton) {
            JTextArea textArea = new JTextArea(20, 40); // creatinf text area of height: 20 and width:40
            textArea.setEditable(false);  // so that the user cannot edit any text in the text area
            if (library.getBooksArrayList().isEmpty()) { // library is empty
                textArea.append("No books available in the library.");
            } else {
                for (Book book : library.getBooksArrayList()) { // iterates over the books list to display book details
                    textArea.append("Book ID: " + book.getBookID() + "\n");
                    textArea.append("Title: " + book.getTitle() + "\n");
                    textArea.append("Author: " + book.getAuthor() + "\n");
                    textArea.append("Genre: " + book.getGenre() + "\n");
                    textArea.append("Availability: " + (book.isAvailable() ? "Available" : "Not Available") + "\n\n");
                    // using ternary operator to check if the book is available or not
                }
            }
            JScrollPane scrollPane = new JScrollPane(textArea);  // to scroll over the pane if text cannot be displayed on 1 screen frame
            JOptionPane.showMessageDialog(frame, scrollPane, "Library Books", JOptionPane.PLAIN_MESSAGE);
        } else if (e.getSource() == exitButton) {
            JOptionPane.showMessageDialog(frame, "Exiting...");
            System.exit(0);  // to exit the program
        }
        input.close();  // closing thr Scanner object input
    }
    // Main method in LMS 
    public static void main(String[] args) {
        new LMS();  // calling constructor
    }
}
