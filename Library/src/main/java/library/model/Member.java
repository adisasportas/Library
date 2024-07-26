package library.model;
import library.util.BookAvailabilityObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a member of the library.
 * This class implements the Observer pattern to receive updates about book availability.
 */
public class Member implements BookAvailabilityObserver {
    private String id;
    private String name;
    private List<Loan> loans;
    private List<Book> interestedBooks;

    /**
     * Constructs a new Member with the given details.
     *
     * @param id   The unique identifier of the member.
     * @param name The name of the member.
     */
    public Member(String id, String name) {
        this.id = id;
        this.name = name;
        this.loans = new ArrayList<>();
        this.interestedBooks = new ArrayList<>();
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Loan> getLoans() {
        return new ArrayList<>(loans);
    }

    /**
     * Adds a loan to the member's loan list.
     *
     * @param loan The loan to add.
     */
    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    /**
     * Removes a loan from the member's loan list.
     *
     * @param loan The loan to remove.
     */
    public void removeLoan(Loan loan) {
        loans.remove(loan);
    }

    /**
     * Adds a book to the member's interested books list and registers the member as an observer.
     *
     * @param book The book the member is interested in.
     */
    public void addInterestedBook(Book book) {
        interestedBooks.add(book);
        book.addObserver(this);
    }

    /**
     * Removes a book from the member's interested books list and unregisters the member as an observer.
     *
     * @param book The book the member is no longer interested in.
     */
    public void removeInterestedBook(Book book) {
        interestedBooks.remove(book);
        book.removeObserver(this);
    }

    /**
     * Updates the member when a book they're interested in becomes available.
     *
     * @param book The book that has become available.
     */
    @Override
    public void update(Book book) {
        if (interestedBooks.contains(book)) {
            System.out.println("Notification for member " + name + ": The book '" + book.getTitle() + "' is now available.");
        }
    }

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", loans=" + loans.size() +
                ", interestedBooks=" + interestedBooks.size() +
                '}';
    }
}
