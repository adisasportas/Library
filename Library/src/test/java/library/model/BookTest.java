package library.model;

import library.util.BookStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class BookTest {
    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book("1", "Test Book", "Test Author", 2023);
    }

    @Test
    void testBookCreation() {
        assertEquals("1", book.getId());
        assertEquals("Test Book", book.getTitle());
        assertEquals("Test Author", book.getAuthor());
        assertEquals(2023, book.getPublicationYear());
        assertEquals(BookStatus.AVAILABLE, book.getStatus());
    }

    @Test
    void testSetStatus() {
        book.setStatus(BookStatus.BORROWED);
        assertEquals(BookStatus.BORROWED, book.getStatus());
    }

    @Test
    void testClone() throws CloneNotSupportedException {
        Book clonedBook = book.clone();
        assertNotSame(book, clonedBook);
        assertEquals(book.getId(), clonedBook.getId());
        assertEquals(book.getTitle(), clonedBook.getTitle());
        assertEquals(book.getAuthor(), clonedBook.getAuthor());
        assertEquals(book.getPublicationYear(), clonedBook.getPublicationYear());
        assertEquals(book.getStatus(), clonedBook.getStatus());
    }
}