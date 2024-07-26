package library.util;

import library.model.Book;

/**
 * Represents an observer in the Observer pattern for book availability.
 * This interface is implemented by classes that need to be notified
 * when a book's availability changes.
 */
public interface BookAvailabilityObserver {
    /**
     * Called when a book's availability changes.
     *
     * @param book The book whose availability has changed.
     */
    void update(Book book);
}
