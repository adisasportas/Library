package library.service;

import library.model.Book;
import library.model.LibrarySummary;
import library.model.Loan;
import library.model.Member;
import library.util.BookStatus;

/**
 * Manages the library system, serving as both a Singleton and a Facade.
 * This class provides a simplified interface for client code to interact with the library system.
 */
public class LibraryManager {
    private static LibraryManager instance;
    private Library library;
    private BookFactory bookFactory;

    /**
     * Private constructor to prevent direct instantiation.
     */
    private LibraryManager() {
        this.library = new Library();
        this.bookFactory = new BookFactory();
    }

    public LibraryManager(Library library, BookFactory bookFactory){
        this.library = library;
        this.bookFactory = bookFactory;
    }

    /**
     * Gets the single instance of LibraryManager.
     *
     * @return The LibraryManager instance.
     */
    public static synchronized LibraryManager getInstance() {
        if (instance == null) {
            instance = new LibraryManager();
        }
        return instance;
    }

    /**
     * Adds a book to the library.
     *
     * @param type            The type of book to create.
     * @param id              The ID of the book.
     * @param title           The title of the book.
     * @param author          The author of the book.
     * @param publicationYear The publication year of the book.
     */
    public void addBook(String type, String id, String title, String author, int publicationYear) {
        Book book = bookFactory.createBook(type, id, title, author, publicationYear);
        library.addBook(book);
    }

    /**
     * Removes a book from the library.
     *
     * @param bookId The ID of the book to remove.
     * @return true if the book was successfully removed, false otherwise.
     */
    public boolean removeBook(String bookId) {
        Book book = library.findBookById(bookId);
        if (book != null) {
            return library.removeBook(book);
        }
        return false;
    }

    /**
     * Adds a member to the library.
     *
     * @param id   The ID of the member.
     * @param name The name of the member.
     */
    public void addMember(String id, String name) {
        Member member = new Member(id, name);
        library.addMember(member);
    }

    /**
     * Removes a member from the library.
     *
     * @param memberId The ID of the member to remove.
     * @return true if the member was successfully removed, false otherwise.
     */
    public boolean removeMember(String memberId) {
        Member member = library.findMemberById(memberId);
        if (member != null) {
            return library.removeMember(member);
        }
        return false;
    }

    /**
     * Borrows a book for a member.
     *
     * @param bookId   The ID of the book to borrow.
     * @param memberId The ID of the member borrowing the book.
     * @return true if the book was successfully borrowed, false otherwise.
     */
    public boolean borrowBook(String bookId, String memberId) {
        Book book = library.findBookById(bookId);
        Member member = library.findMemberById(memberId);
        if (book != null && member != null) {
            Loan loan = library.borrowBook(book, member);
            return loan != null;
        }
        return false;
    }

    /**
     * Returns a borrowed book.
     *
     * @param bookId The ID of the book to return.
     * @return true if the book was successfully returned, false otherwise.
     */
    public boolean returnBook(String bookId) {
        Book book = library.findBookById(bookId);
        if (book != null && book.getStatus() == BookStatus.BORROWED) {
            Loan loan = library.getActiveLoans().stream()
                    .filter(l -> l.getBook().getId().equals(bookId))
                    .findFirst()
                    .orElse(null);
            if (loan != null) {
                library.returnBook(loan);
                return true;
            }
        }
        return false;
    }

    /**
     * Gets a summary of the library's current state.
     *
     * @return A LibrarySummary object containing current statistics.
     */
    public LibrarySummary getLibrarySummary() {
        return library.getLibrarySummary();
    }

    /**
     * Duplicates a book in the library.
     *
     * @param bookId The ID of the book to duplicate.
     * @return The ID of the new book if successful, null otherwise.
     */
    public String duplicateBook(String bookId) {
        Book originalBook = library.findBookById(bookId);
        if (originalBook != null) {
            try {
                Book newBook = originalBook.clone();
                String newId = bookId + "_copy";
                newBook.setId(newId);
                library.addBook(newBook);
                return newId;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
