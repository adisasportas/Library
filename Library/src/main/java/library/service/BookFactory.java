package library.service;

import library.model.Book;

/**
 * Factory class for creating different types of books.
 * This class implements the Factory Method pattern.
 */
public class BookFactory {

    /**
     * Creates a book of the specified type.
     *
     * @param type            The type of book to create.
     * @param id              The ID of the book.
     * @param title           The title of the book.
     * @param author          The author of the book.
     * @param publicationYear The publication year of the book.
     * @return A Book object of the specified type.
     */
    public Book createBook(String type, String id, String title, String author, int publicationYear) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Book type cannot be null or empty");
        }
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Book ID cannot be null or empty");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Book author cannot be null or empty");
        }


        switch (type.toLowerCase()) {
            case "fiction":
                return new FictionBook(id, title, author, publicationYear);
            case "non-fiction":
                return new NonFictionBook(id, title, author, publicationYear);
            case "reference":
                return new ReferenceBook(id, title, author, publicationYear);
            default:
                throw new IllegalArgumentException("Unknown book type: " + type);
        }
    }
}

/**
 * Represents a fiction book in the library.
 */
class FictionBook extends Book {
    private String genre;

    public FictionBook(String id, String title, String author, int publicationYear) {
        super(id, title, author, publicationYear);
        this.genre = "Unknown";
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}

/**
 * Represents a non-fiction book in the library.
 */
class NonFictionBook extends Book {
    private String subject;

    public NonFictionBook(String id, String title, String author, int publicationYear) {
        super(id, title, author, publicationYear);
        this.subject = "Unknown";
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}

/**
 * Represents a reference book in the library.
 */
class ReferenceBook extends Book {
    private boolean canBeBorrowed;

    public ReferenceBook(String id, String title, String author, int publicationYear) {
        super(id, title, author, publicationYear);
        this.canBeBorrowed = false;
    }

    public boolean canBeBorrowed() {
        return canBeBorrowed;
    }

    public void setCanBeBorrowed(boolean canBeBorrowed) {
        this.canBeBorrowed = canBeBorrowed;
    }
}
