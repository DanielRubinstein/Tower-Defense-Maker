package util.observer;

/**
 * Provides a skeletal implementation of the <code>Observable</code> interface.
 * When an <code>Observer</code> is added to this <code>Observable</code>, it
 * is automatically notified of the latest change.
 * @author Mike Liu
 *
 */
public abstract class QuickNotifyObservable<T> extends AbstractObservable<T> {
    
    /**
     * Adds <code>obs</code> as an observer of this object.
     * Immediately notifies the new observer of the latest change.
     * @param obs
     */
    @Override
    public void addObserver(Observer<T> obs) {
        super.addObserver(obs);
        obs.update(notification());
    }
    
    /**
     * Notifies all of this object's observers with the latest change.
     */
    public void notifyObservers() {
        notifyObservers(notification());
    }
    
    /**
     * Returns the latest change of this object.
     * @return
     */
    protected abstract T notification();

}
