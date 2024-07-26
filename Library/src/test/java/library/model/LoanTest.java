package library.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class LoanTest {
    private Loan loan;
    private Book book;
    private Member member;

    @BeforeEach
    void setUp() {
        book = new Book("1", "Test Book", "Test Author", 2023);
        member = new Member("1", "Test Member");
        loan = new Loan(book, member, new Date(), new Date());
    }

    @Test
    void testLoanCreation() {
        assertEquals(book, loan.getBook());
        assertEquals(member, loan.getMember());
        assertEquals(new Date().getDate(), loan.getBorrowDate().getDate());
        assertNull(loan.getReturnDate());
    }

    @Test
    void testReturnBook() {
        loan.returnBook();
        assertNotNull(loan.getReturnDate());
        assertEquals(new Date().getDate(), loan.getReturnDate().getDate());
    }

    @Test
    void testIsOverdue() {
        Date currentDate = new Date();

// Create a Calendar instance and set it to the current date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

// Subtract 15 days
        calendar.add(Calendar.DAY_OF_MONTH, -15);

// Get the new date
        Date dateMinusFifteenDays = calendar.getTime();

        loan = new Loan(book, member, dateMinusFifteenDays, new Date()); // Assuming 14-day loan period
        assertFalse(loan.isOverdue());
    }
}
