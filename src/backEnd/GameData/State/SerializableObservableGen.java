package backEnd.GameData.State;

import java.util.List;

/**
 * Defines a serializableObservable that takes a generic type. 
 * @author Tim
 *
 * @param <T>
 */
public interface SerializableObservableGen<T>{

	/**
	 * Adds this observer to the list of observers.
	 * @param o
	 */
	public void addObserver(SerializableObserverGen<T> o);
	
	/**
	 * Gets the list of observers for this observable
	 * @return
	 */
	public List<SerializableObserverGen<T>> getObserversGen();

	/**
	 * Clears all observers
	 */
	public void clearObservers();
	
	/**
	 * Sets the given parameter as an observer
	 * @param observersave
	 */
	public void setObserversGen(List<SerializableObserverGen<T>> observersave);

}
