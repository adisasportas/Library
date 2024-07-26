package library.service;
import library.model.Book;
import library.model.LibrarySummary;
import library.model.Loan;
import library.model.Member;
import library.util.BookStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the main library system, managing books, members, and loans.
 */
public class Library {
    private List<Book> books;
    private List<Member> members;
    private List<Loan> loans;

    /**
     * Constructs a new Library with empty lists for books, members, and loans.
     */
    public Library() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        this.loans = new ArrayList<>();
    }

    /**
     * Adds a book to the library.
     *
     * @param book The book to add.
     */
    public void addBook(Book book) {
        books.add(book);
    }

    /**
     * Removes a book from the library.
     *
     * @param book The book to remove.
     * @return true if the book was successfully removed, false otherwise.
     */
    public boolean removeBook(Book book) {
        return books.remove(book);
    }

    /**
     * Adds a member to the library.
     *
     * @param member The member to add.
     */
    public void addMember(Member member) {
        members.add(member);
    }

    /**
     * Removes a member from the library.
     *
     * @param member The member to remove.
     * @return true if the member was successfully removed, false otherwise.
     */
    public boolean removeMember(Member member) {
        return members.remove(member);
    }

    /**
     * Creates a loan for a book to a member.
     *
     * @param book   The book to be borrowed.
     * @param member The member borrowing the book.
     * @return The created Loan object, or null if the book is not available.
     */
    public Loan borrowBook(Book book, Member member) {
        if (book.getStatus() == BookStatus.AVAILABLE) {
            Date borrowDate = new Date();
            Date dueDate = new Date(borrowDate.getTime() + 14 * 24 * 60 * 60 * 1000); // 14 days loan period
            Loan loan = new Loan(book, member, borrowDate, dueDate);
            loans.add(loan);
            book.setStatus(BookStatus.BORROWED);
            member.addLoan(loan);
            return loan;
        }
        return null;
    }

    /**
     * Returns a borrowed book.
     *
     * @param loan The loan to be returned.
     */
    public void returnBook(Loan loan) {
        if (loan.isActive()) {
            loan.setReturnDate(new Date());
            loan.getBook().setStatus(BookStatus.AVAILABLE);
            loan.getMember().removeLoan(loan);
        }
    }

    /**
     * Gets a summary of the library's current state.
     *
     * @return A LibrarySummary object containing current statistics.
     */
    public LibrarySummary getLibrarySummary() {
        int availableBooks = (int) books.stream().filter(b -> b.getStatus() == BookStatus.AVAILABLE).count();
        int borrowedBooks = (int) books.stream().filter(b -> b.getStatus() == BookStatus.BORROWED).count();
        int activeMembers = (int) members.stream().filter(m -> !m.getLoans().isEmpty()).count();
        int totalLoans = loans.size();

        return new LibrarySummary(availableBooks, borrowedBooks, activeMembers, totalLoans);
    }

    /**
     * Finds a book by its ID.
     *
     * @param id The ID of the book to find.
     * @return The found Book object, or null if not found.
     */
    public Book findBookById(String id) {
        return books.stream().filter(b -> b.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Finds a member by their ID.
     *
     * @param id The ID of the member to find.
     * @return The found Member object, or null if not found.
     */
    public Member findMemberById(String id) {
        return members.stream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Gets all active loans.
     *
     * @return A list of all active Loan objects.
     */
    public List<Loan> getActiveLoans() {
        return loans.stream().filter(Loan::isActive).collect(Collectors.toList());
    }

    /**
     * Gets all overdue loans.
     *
     * @return A list of all overdue Loan objects.
     */
    public List<Loan> getOverdueLoans() {
        return loans.stream().filter(Loan::isOverdue).collect(Collectors.toList());
    }
}