package library.gui;

import library.model.LibrarySummary;
import library.service.LibraryManager;

import javax.swing.*;
import java.awt.*;

/**
 * The LibraryManagementSystemGUI class provides a graphical user interface for the Library Management System.
 * It allows users to perform operations such as adding books and members, borrowing and returning books,
 * and viewing a summary of the library's current state.
 */
public class LibraryManagementSystemGUI extends JFrame {
    private LibraryManager libraryManager;
    private JTextField bookIdField, bookTitleField, bookAuthorField, bookYearField, bookTypeField;
    private JTextField memberIdField, memberNameField;
    private JTextArea outputArea;
    private JButton addBookButton, addMemberButton, borrowBookButton, returnBookButton, showSummaryButton;

    /**
     * Constructs a new LibraryManagementSystemGUI with the specified LibraryManager.
     *
     * @param libraryManager the LibraryManager instance to be used for library operations
     */
    public LibraryManagementSystemGUI(LibraryManager libraryManager) {
        this.libraryManager = libraryManager;
        initializeUI();
    }

    /**
     * Initializes the user interface components and layouts.
     */
    private void initializeUI() {
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create panels
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        JPanel buttonPanel = new JPanel(new FlowLayout());
        outputArea = new JTextArea(10, 40);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Add components to input panel
        inputPanel.add(new JLabel("Book ID:"));
        bookIdField = new JTextField(10);
        inputPanel.add(bookIdField);

        inputPanel.add(new JLabel("Book Title:"));
        bookTitleField = new JTextField(10);
        inputPanel.add(bookTitleField);

        inputPanel.add(new JLabel("Book Author:"));
        bookAuthorField = new JTextField(10);
        inputPanel.add(bookAuthorField);

        inputPanel.add(new JLabel("Publication Year:"));
        bookYearField = new JTextField(10);
        inputPanel.add(bookYearField);

        inputPanel.add(new JLabel("Book Type:"));
        bookTypeField = new JTextField(10);
        inputPanel.add(bookTypeField);

        inputPanel.add(new JLabel("Member ID:"));
        memberIdField = new JTextField(10);
        inputPanel.add(memberIdField);

        inputPanel.add(new JLabel("Member Name:"));
        memberNameField = new JTextField(10);
        inputPanel.add(memberNameField);

        // Add buttons
        addBookButton = new JButton("Add Book");
        addMemberButton = new JButton("Add Member");
        borrowBookButton = new JButton("Borrow Book");
        returnBookButton = new JButton("Return Book");
        showSummaryButton = new JButton("Show Summary");

        buttonPanel.add(addBookButton);
        buttonPanel.add(addMemberButton);
        buttonPanel.add(borrowBookButton);
        buttonPanel.add(returnBookButton);
        buttonPanel.add(showSummaryButton);

        // Add action listeners
        addBookButton.addActionListener(e -> addBook());
        addMemberButton.addActionListener(e -> addMember());
        borrowBookButton.addActionListener(e -> borrowBook());
        returnBookButton.addActionListener(e -> returnBook());
        showSummaryButton.addActionListener(e -> showSummary());

        // Add panels to frame
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }

    /**
     * Handles the action of adding a new book to the library.
     * Validates input fields and displays appropriate error messages if input is invalid.
     */
    private void addBook() {
        String id = bookIdField.getText().trim();
        String title = bookTitleField.getText().trim();
        String author = bookAuthorField.getText().trim();
        String yearStr = bookYearField.getText().trim();
        String type = bookTypeField.getText().trim();

        if (id.isEmpty() || title.isEmpty() || author.isEmpty() || yearStr.isEmpty() || type.isEmpty()) {
            showError("All fields are required for adding a book.");
            return;
        }

        try {
            int year = Integer.parseInt(yearStr);
            if (year < 0 || year > 9999) {
                showError("Invalid year. Please enter a year between 0 and 9999.");
                return;
            }

            libraryManager.addBook(type, id, title, author, year);
            outputArea.append("Book added: " + title + " by " + author + "\n");
            clearBookFields();
        } catch (NumberFormatException e) {
            showError("Invalid year format. Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            showError("Error adding book: " + e.getMessage());
        }
    }

    /**
     * Handles the action of adding a new member to the library.
     * Validates input fields and displays appropriate error messages if input is invalid.
     */
    private void addMember() {
        String id = memberIdField.getText().trim();
        String name = memberNameField.getText().trim();

        if (id.isEmpty() || name.isEmpty()) {
            showError("Both member ID and name are required.");
            return;
        }

        try {
            libraryManager.addMember(id, name);
            outputArea.append("Member added: " + name + " (ID: " + id + ")\n");
            clearMemberFields();
        } catch (IllegalArgumentException e) {
            showError("Error adding member: " + e.getMessage());
        }
    }

    /**
     * Handles the action of borrowing a book.
     * Validates input fields and displays appropriate error messages if input is invalid or the operation fails.
     */
    private void borrowBook() {
        String bookId = bookIdField.getText().trim();
        String memberId = memberIdField.getText().trim();

        if (bookId.isEmpty() || memberId.isEmpty()) {
            showError("Both book ID and member ID are required for borrowing.");
            return;
        }

        try {
            boolean success = libraryManager.borrowBook(bookId, memberId);
            if (success) {
                outputArea.append("Book (ID: " + bookId + ") borrowed by member (ID: " + memberId + ")\n");
            } else {
                showError("Failed to borrow book. Please check if the book is available and the member exists.");
            }
        } catch (IllegalArgumentException e) {
            showError("Error borrowing book: " + e.getMessage());
        }
    }

    /**
     * Handles the action of returning a book.
     * Validates input fields and displays appropriate error messages if input is invalid or the operation fails.
     */
    private void returnBook() {
        String bookId = bookIdField.getText().trim();

        if (bookId.isEmpty()) {
            showError("Book ID is required for returning a book.");
            return;
        }

        try {
            boolean success = libraryManager.returnBook(bookId);
            if (success) {
                outputArea.append("Book (ID: " + bookId + ") returned successfully\n");
            } else {
                showError("Failed to return book. Please check if the book was borrowed.");
            }
        } catch (IllegalArgumentException e) {
            showError("Error returning book: " + e.getMessage());
        }
    }

    /**
     * Displays a summary of the library's current state.
     */
    private void showSummary() {
        try {
            LibrarySummary summary = libraryManager.getLibrarySummary();
            outputArea.append("Library Summary:\n");
            outputArea.append("Available Books: " + summary.getAvailableBooks() + "\n");
            outputArea.append("Borrowed Books: " + summary.getBorrowedBooks() + "\n");
            outputArea.append("Active Members: " + summary.getActiveMembers() + "\n");
            outputArea.append("Total Loans: " + summary.getTotalLoans() + "\n");
        } catch (Exception e) {
            showError("Error getting library summary: " + e.getMessage());
        }
    }

    /**
     * Displays an error message in a dialog box.
     *
     * @param message the error message to display
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Clears all book-related input fields.
     */
    private void clearBookFields() {
        bookIdField.setText("");
        bookTitleField.setText("");
        bookAuthorField.setText("");
        bookYearField.setText("");
        bookTypeField.setText("");
    }

    /**
     * Clears all member-related input fields.
     */
    private void clearMemberFields() {
        memberIdField.setText("");
        memberNameField.setText("");
    }
}