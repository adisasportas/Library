package library.model;

import library.util.BookStatus;

import java.util.Date;

/**
 * Represents a loan of a book to a member in the library system.
 */
public class Loan {
    private Book book;
    private Member member;
    private Date borrowDate;
    private Date dueDate;
    private Date returnDate;

    /**
     * Constructs a new Loan with the given details.
     *
     * @param book       The book being loaned.
     * @param member     The member borrowing the book.
     * @param borrowDate The date the book was borrowed.
     * @param dueDate    The date the book is due to be returned.
     */
    public Loan(Book book, Member member, Date borrowDate, Date dueDate) {
        this.book = book;
        this.member = member;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = null;
    }

    // Getters and setters

    public Book getBook() {
        return book;
    }

    public Member getMember() {
        return member;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the return date of the loan and updates the book's status to available.
     *
     * @param returnDate The date the book was returned.
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
        this.book.setStatus(BookStatus.AVAILABLE);
    }

    /**
     * Checks if the loan is currently active (not returned).
     *
     * @return true if the book has not been returned, false otherwise.
     */
    public boolean isActive() {
        return returnDate == null;
    }

    /**
     * Checks if the loan is overdue.
     *
     * @return true if the current date is after the due date and the book has not been returned, false otherwise.
     */
    public boolean isOverdue() {
        return isActive() && new Date().after(dueDate);
    }

    public void returnBook() {
        this.returnDate = new Date();
    }

    @Override
    public String toString() {
        return "Loan{" +
                "book=" + book.getTitle() +
                ", member=" + member.getName() +
                ", borrowDate=" + borrowDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                ", isActive=" + isActive() +
                ", isOverdue=" + isOverdue() +
                '}';
    }
}