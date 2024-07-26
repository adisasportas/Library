package library.service;

import library.model.Book;
import library.model.LibrarySummary;
import library.model.Loan;
import library.model.Member;
import library.util.BookStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {
    private Library library;
    private Book book;
    private Member member;

    @BeforeEach
    void setUp() {
        library = new Library();
        book = new Book("1", "Test Book", "Test Author", 2023);
        member = new Member("1", "Test Member");
    }

    @Test
    void testAddAndRemoveBook() {
        library.addBook(book);
        assertEquals(book, library.findBookById("1"));

        library.removeBook(book);
        assertNull(library.findBookById("1"));
    }

    @Test
    void testAddAndRemoveMember() {
        library.addMember(member);
        assertEquals(member, library.findMemberById("1"));

        library.removeMember(member);
        assertNull(library.findMemberById("1"));
    }

    @Test
    void testBorrowAndReturnBook() {
        library.addBook(book);
        library.addMember(member);

        Loan loan = library.borrowBook(book, member);
        assertNotNull(loan);
        assertEquals(BookStatus.BORROWED, book.getStatus());

        library.returnBook(loan);
        assertEquals(BookStatus.AVAILABLE, book.getStatus());
        assertNotNull(loan.getReturnDate());
    }

    @Test
    void testGetLibrarySummary() {
        library.addBook(book);
        library.addMember(member);
        library.borrowBook(book, member);

        LibrarySummary summary = library.getLibrarySummary();
        assertEquals(0, summary.getAvailableBooks());
        assertEquals(1, summary.getBorrowedBooks());
        assertEquals(1, summary.getActiveMembers());
        assertEquals(1, summary.getTotalLoans());
    }
}
