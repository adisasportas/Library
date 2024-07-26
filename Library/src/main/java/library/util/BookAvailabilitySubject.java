package library.util;
/**
 * Represents a subject in the Observer pattern for book availability.
 * This interface is implemented by classes that need to notify observers
 * when a book's availability changes.
 */
public interface BookAvailabilitySubject {
    /**
     * Adds an observer to be notified of availability changes.
     *
     * @param observer The observer to add.
     */
    void addObserver(BookAvailabilityObserver observer);

    /**
     * Removes an observer from the notification list.
     *
     * @param observer The observer to remove.
     */
    void removeObserver(BookAvailabilityObserver observer);

    /**
     * Notifies all registered observers of a change in availability.
     */
    void notifyObservers();
}

