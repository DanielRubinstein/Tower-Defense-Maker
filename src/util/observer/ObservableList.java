package util.observer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A list that is observable.
 * @author Mike Liu
 *
 * @param <E>
 */
public class ObservableList<E> extends QuickNotifyObservable<List<E>> implements Iterable<E> {
    
    private List<E> myList;
    
    public ObservableList() {
        super();
        myList = new ArrayList<>();
    }
    
    /**
     * Appends the specified element to the end of this list.
     * @param e
     */
    public void add(E e) {
        myList.add(e);
        notifyObservers();
    }
    
    /**
     * Appends all of the elements in the specified collection to the end of this list,
     * in the order that they are returned by the specified collection's Iterator.
     * @param c
     */
    public void addAll(Collection<? extends E> c) {
        myList.addAll(c);
        notifyObservers();
    }
    
    /**
     * Returns true if this list contains the specified element.
     * @param o
     * @return
     */
    public boolean contains(Object o) {
        return myList.contains(o);
    }
    
    /**
     * Returns the element at the specified position in this list.
     * @param index
     * @return
     */
    public E get(int index) {
        return myList.get(index);
    }
    
    /**
     * Returns a <code>List</code> of the elements of this list.
     * @return
     */
    public List<E> toList() {
        return new ArrayList<E>(myList);
    }
    
    /**
     * Removes the element at the specified position in this list.
     * @param index
     * @return
     */
    public E remove(int index) {
        E ret = myList.remove(index);
        notifyObservers();
        return ret;
    }
    
    /**
     * Removes the first occurrence of the specified element from this list, if it is present.
     * @param o
     * @return
     */
    public boolean remove(Object o) {
        boolean ret = myList.remove(o);
        notifyObservers();
        return ret;
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     * @return
     */
    @Override
    public Iterator<E> iterator() {
        return myList.iterator();
    }

    @Override
    protected List<E> notification() {
        return Collections.unmodifiableList(myList);
    }
}