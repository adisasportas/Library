package library.service;

import library.model.Book;
import library.model.LibrarySummary;
import library.model.Loan;
import library.model.Member;
import library.util.BookStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryManagerTest {

    private LibraryManager libraryManager;
    private Library mockLibrary;
    private BookFactory mockBookFactory;

    @BeforeEach
    public void setUp() {
        mockLibrary = Mockito.mock(Library.class);
        mockBookFactory = Mockito.mock(BookFactory.class);
        libraryManager = new LibraryManager(mockLibrary, mockBookFactory);
    }

    @Test
    public void testAddBook() {
        Book mockBook = Mockito.mock(Book.class);
        Mockito.when(mockBookFactory.createBook("Fiction", "1", "Title", "Author", 2023)).thenReturn(mockBook);

        libraryManager.addBook("Fiction", "1", "Title", "Author", 2023);

        Mockito.verify(mockLibrary).addBook(mockBook);
    }

    @Test
    public void testRemoveBook() {
        Book mockBook = Mockito.mock(Book.class);
        Mockito.when(mockLibrary.findBookById("1")).thenReturn(mockBook);
        Mockito.when(mockLibrary.removeBook(mockBook)).thenReturn(true);

        boolean result = libraryManager.removeBook("1");

        assertTrue(result);
        Mockito.verify(mockLibrary).removeBook(mockBook);
    }

    @Test
    public void testBorrowBook() {
        Book mockBook = Mockito.mock(Book.class);
        Member mockMember = Mockito.mock(Member.class);
        Loan mockLoan = Mockito.mock(Loan.class);

        Mockito.when(mockLibrary.findBookById("1")).thenReturn(mockBook);
        Mockito.when(mockLibrary.findMemberById("1")).thenReturn(mockMember);
        Mockito.when(mockLibrary.borrowBook(mockBook, mockMember)).thenReturn(mockLoan);

        boolean result = libraryManager.borrowBook("1", "1");

        assertTrue(result);
        Mockito.verify(mockLibrary).borrowBook(mockBook, mockMember);
    }

    @Test
    public void testReturnBook() {
        Book mockBook = Mockito.mock(Book.class);
        Loan mockLoan = Mockito.mock(Loan.class);

        Mockito.when(mockLibrary.findBookById("1")).thenReturn(mockBook);
        Mockito.when(mockBook.getStatus()).thenReturn(BookStatus.BORROWED);
        Mockito.when(mockLibrary.getActiveLoans()).thenReturn(List.of(mockLoan));
        Mockito.when(mockLoan.getBook()).thenReturn(mockBook);
        Mockito.when(mockBook.getId()).thenReturn("1");

        boolean result = libraryManager.returnBook("1");

        assertTrue(result);
        Mockito.verify(mockLibrary).returnBook(mockLoan);
    }

    @Test
    public void testGetLibrarySummary() {
        LibrarySummary mockSummary = Mockito.mock(LibrarySummary.class);
        Mockito.when(mockLibrary.getLibrarySummary()).thenReturn(mockSummary);

        LibrarySummary summary = libraryManager.getLibrarySummary();

        assertEquals(mockSummary, summary);
        Mockito.verify(mockLibrary).getLibrarySummary();
    }

    @Test
    public void testDuplicateBook() {
        Book mockBook = Mockito.mock(Book.class);

        Mockito.when(mockLibrary.findBookById("1")).thenReturn(mockBook);
        try {
            Mockito.when(mockBook.clone()).thenReturn(mockBook);
        } catch (CloneNotSupportedException e) {
            fail("CloneNotSupportedException should not be thrown");
        }

        String newId = libraryManager.duplicateBook("1");

        assertNotNull(newId);
        Mockito.verify(mockLibrary).addBook(mockBook);
    }
}
