package util.observer;

/**
 * A class can implement the <code>Observer</code> interface
 * when it wants to be informed of changes in <code>Observable</code> objects.
 * @author Mike Liu
 *
 * @param <T>
 */
public interface Observer<T> {

    /**
     * This method is called whenever the observed object is changed.
     * @param arg
     */
    void update(T arg);
}
