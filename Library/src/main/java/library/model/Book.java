package library.model;

import library.util.BookAvailabilityObserver;
import library.util.BookAvailabilitySubject;
import library.util.BookStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a book in the library system.
 * This class implements the Prototype pattern for easy duplication
 * and the Observer pattern for notifying members about availability changes.
 */
public class Book implements Cloneable, BookAvailabilitySubject {
    private String id;
    private String title;
    private String author;
    private int publicationYear;
    private BookStatus status;
    private List<BookAvailabilityObserver> observers;

    /**
     * Constructs a new Book with the given details.
     *
     * @param id              The unique identifier of the book.
     * @param title           The title of the book.
     * @param author          The author of the book.
     * @param publicationYear The year the book was published.
     */
    public Book(String id, String title, String author, int publicationYear) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.status = BookStatus.AVAILABLE;
        this.observers = new ArrayList<>();
    }

    /**
     * Gets the unique identifier of the book.
     *
     * @return The book's ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the book.
     *
     * @param id The new ID for the book.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the title of the book.
     *
     * @return The book's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     *
     * @param title The new title for the book.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the author of the book.
     *
     * @return The book's author.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the book.
     *
     * @param author The new author for the book.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the publication year of the book.
     *
     * @return The year the book was published.
     */
    public int getPublicationYear() {
        return publicationYear;
    }

    /**
     * Sets the publication year of the book.
     *
     * @param publicationYear The new publication year for the book.
     */
    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    /**
     * Gets the current status of the book.
     *
     * @return The book's status.
     */
    public BookStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the book and notifies observers if the book becomes available.
     *
     * @param status The new status of the book.
     */
    public void setStatus(BookStatus status) {
        BookStatus oldStatus = this.status;
        this.status = status;
        if (oldStatus != BookStatus.AVAILABLE && status == BookStatus.AVAILABLE) {
            notifyObservers();
        }
    }

    /**
     * Creates and returns a copy of this book.
     *
     * @return A clone of this book.
     * @throws CloneNotSupportedException If cloning is not supported.
     */
    @Override
    public Book clone() throws CloneNotSupportedException {
        Book clonedBook = (Book) super.clone();
        clonedBook.observers = new ArrayList<>();
        return clonedBook;
    }

    /**
     * Adds an observer to be notified of availability changes.
     *
     * @param observer The observer to add.
     */
    @Override
    public void addObserver(BookAvailabilityObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the notification list.
     *
     * @param observer The observer to remove.
     */
    @Override
    public void removeObserver(BookAvailabilityObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers of a change in availability.
     */
    @Override
    public void notifyObservers() {
        for (BookAvailabilityObserver observer : observers) {
            observer.update(this);
        }
    }

    /**
     * Returns a string representation of the book.
     *
     * @return A string describing the book.
     */
    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publicationYear=" + publicationYear +
                ", status=" + status +
                '}';
    }
}