package util.observer;

/**
 * Represents an observable object that, when updated,
 * send and object of type T to its observers.
 * @author Mike Liu
 *
 * @param <T>
 */
public interface Observable<T> {

    /**
     * Adds <code>obs</code> as an observer of this object.
     * @param obs
     */
    void addObserver(Observer<T> obs);

    /**
     * Removes <code>obs</code> as the observer of this object.
     * @param obs
     */
    void removeObserver(Observer<T> obs);

    /**
     * Notifies all of this object's observers with <code>arg</code>.
     * @param arg
     */
    void notifyObservers(T arg);

}