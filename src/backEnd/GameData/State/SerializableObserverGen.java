package backEnd.GameData.State;

/**
 * Interface that represents a SerializableObserver but takes a generic type T.
 * This eliminates the need for casting.
 * @author Tim
 *
 * @param <T>
 */
public interface SerializableObserverGen<T>{

	/**
	 * Updates the Observer with this object and an argument of the object that changed
	 * @param object
	 * @param obj
	 */
	public void update(SerializableObservableGen<T> object, T obj);

	
}
