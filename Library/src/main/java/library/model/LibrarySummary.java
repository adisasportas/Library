package library.model;
/**
 * Represents a summary of the library's current state.
 * This class encapsulates key statistics about the library.
 */
public class LibrarySummary {
    private int availableBooks;
    private int borrowedBooks;
    private int activeMembers;
    private int totalLoans;

    /**
     * Constructs a new LibrarySummary with the given statistics.
     *
     * @param availableBooks The number of available books.
     * @param borrowedBooks The number of currently borrowed books.
     * @param activeMembers The number of active members (members with at least one current loan).
     * @param totalLoans The total number of loans (including both active and returned loans).
     */
    public LibrarySummary(int availableBooks, int borrowedBooks, int activeMembers, int totalLoans) {
        this.availableBooks = availableBooks;
        this.borrowedBooks = borrowedBooks;
        this.activeMembers = activeMembers;
        this.totalLoans = totalLoans;
    }

    // Getters

    public int getAvailableBooks() {
        return availableBooks;
    }

    public int getBorrowedBooks() {
        return borrowedBooks;
    }

    public int getActiveMembers() {
        return activeMembers;
    }

    public int getTotalLoans() {
        return totalLoans;
    }

    @Override
    public String toString() {
        return "LibrarySummary{" +
                "availableBooks=" + availableBooks +
                ", borrowedBooks=" + borrowedBooks +
                ", activeMembers=" + activeMembers +
                ", totalLoans=" + totalLoans +
                '}';
    }
}