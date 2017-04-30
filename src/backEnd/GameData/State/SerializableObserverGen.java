package backEnd.GameData.State;

public interface SerializableObserverGen<T> {

	void update(SerializableObservableGen<T> object, T obj);

	
}
