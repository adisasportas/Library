package library;

import library.gui.LibraryManagementSystemGUI;
import library.service.LibraryManager;

/**
 * The LibrarySystem class serves as the entry point for the library management application.
 * It initializes the LibraryManager, adds sample data, and launches the graphical user interface.
 */
public class LibrarySystem {
    /**
     * The main method that starts the library management system.
     *
     * @param args Command line arguments (not used in this application)
     */
    public static void main(String[] args) {
        // Initialize the LibraryManager
        LibraryManager libraryManager = LibraryManager.getInstance();

        // Add some sample data
        addSampleData(libraryManager);

        // Launch the GUI
        javax.swing.SwingUtilities.invokeLater(() -> {
            LibraryManagementSystemGUI gui = new LibraryManagementSystemGUI(libraryManager);
            gui.setVisible(true);
        });
    }

    /**
     * Adds sample data to the library management system.
     * This method is used for demonstration purposes and populates the system with
     * example books and members.
     *
     * @param libraryManager The LibraryManager instance to which sample data will be added
     */
    private static void addSampleData(LibraryManager libraryManager) {
        // Add some sample books
        libraryManager.addBook("fiction", "B001", "The Great Gatsby", "F. Scott Fitzgerald", 1925);
        libraryManager.addBook("non-fiction", "B002", "A Brief History of Time", "Stephen Hawking", 1988);
        libraryManager.addBook("reference", "B003", "Oxford English Dictionary", "Oxford University Press", 2010);

        // Add some sample members
        libraryManager.addMember("M001", "John Doe");
        libraryManager.addMember("M002", "Jane Smith");

        // Borrow a book
        libraryManager.borrowBook("B001", "M001");
    }
}