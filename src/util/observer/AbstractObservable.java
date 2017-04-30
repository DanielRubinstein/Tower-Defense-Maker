package util.observer;

import java.util.HashSet;
import java.util.Set;

/**
 * Provides a skeletal implementation of the <code>Observable</code> interface
 * @author Mike Liu
 *
 */
public class AbstractObservable<T> implements Observable<T> {
    
    private Set<Observer<T>> myObservers;
    
    public AbstractObservable() {
        myObservers = new HashSet<>();
    }

    @Override
    public void addObserver(Observer<T> obs) {
        myObservers.add(obs);
    }

    @Override
    public void removeObserver(Observer<T> obs) {
        myObservers.remove(obs);
    }

    @Override
    public void notifyObservers(T arg) {
        for(Observer<T> obs : myObservers) {
            obs.update(arg);
        }
    }

}
