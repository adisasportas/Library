package library.service;

import library.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookFactoryTest {
    private BookFactory bookFactory;

    @BeforeEach
    void setUp() {
        bookFactory = new BookFactory();
    }

    @Test
    void testCreateFictionBook() {
        Book book = bookFactory.createBook("fiction", "F1", "The Great Gatsby", "F. Scott Fitzgerald", 1925);
        assertTrue(book instanceof FictionBook);
        assertEquals("F1", book.getId());
        assertEquals("The Great Gatsby", book.getTitle());
        assertEquals("F. Scott Fitzgerald", book.getAuthor());
        assertEquals(1925, book.getPublicationYear());
        assertEquals("Unknown", ((FictionBook) book).getGenre());
    }

    @Test
    void testCreateNonFictionBook() {
        Book book = bookFactory.createBook("non-fiction", "NF1", "A Brief History of Time", "Stephen Hawking", 1988);
        assertTrue(book instanceof NonFictionBook);
        assertEquals("NF1", book.getId());
        assertEquals("A Brief History of Time", book.getTitle());
        assertEquals("Stephen Hawking", book.getAuthor());
        assertEquals(1988, book.getPublicationYear());
        assertEquals("Unknown", ((NonFictionBook) book).getSubject());
    }

    @Test
    void testCreateReferenceBook() {
        Book book = bookFactory.createBook("reference", "R1", "Oxford English Dictionary", "Oxford University Press", 2010);
        assertTrue(book instanceof ReferenceBook);
        assertEquals("R1", book.getId());
        assertEquals("Oxford English Dictionary", book.getTitle());
        assertEquals("Oxford University Press", book.getAuthor());
        assertEquals(2010, book.getPublicationYear());
        assertFalse(((ReferenceBook) book).canBeBorrowed());
    }

    @Test
    void testCreateBookWithInvalidType() {
        assertThrows(IllegalArgumentException.class, () -> {
            bookFactory.createBook("invalid-type", "I1", "Invalid Book", "Test Author", 2023);
        });
    }

    @Test
    void testCreateBookWithNullType() {
        assertThrows(IllegalArgumentException.class, () -> {
            bookFactory.createBook(null, "N1", "Null Type Book", "Test Author", 2023);
        });
    }

    @Test
    void testCreateFictionBookWithGenre() {
        FictionBook book = (FictionBook) bookFactory.createBook("fiction", "F2", "Pride and Prejudice", "Jane Austen", 1813);
        book.setGenre("Romance");
        assertEquals("Romance", book.getGenre());
    }

    @Test
    void testCreateNonFictionBookWithSubject() {
        NonFictionBook book = (NonFictionBook) bookFactory.createBook("non-fiction", "NF2", "The Origin of Species", "Charles Darwin", 1859);
        book.setSubject("Biology");
        assertEquals("Biology", book.getSubject());
    }

    @Test
    void testCreateReferenceBookBorrowableStatus() {
        ReferenceBook book = (ReferenceBook) bookFactory.createBook("reference", "R2", "Webster's Dictionary", "Merriam-Webster", 2023);
        assertFalse(book.canBeBorrowed());
        book.setCanBeBorrowed(true);
        assertTrue(book.canBeBorrowed());
    }

    @Test
    void testBookTypeIsCaseInsensitive() {
        assertDoesNotThrow(() -> {
            bookFactory.createBook("FICTION", "F3", "Case Insensitive Book", "Test Author", 2023);
            bookFactory.createBook("NoN-FiCtIoN", "NF3", "Case Insensitive Book", "Test Author", 2023);
            bookFactory.createBook("REFerence", "R3", "Case Insensitive Book", "Test Author", 2023);
        });
    }
}