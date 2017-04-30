package backEnd.GameData.State;

public interface SerializableObserverGen<T>{

	public void update(SerializableObservableGen<T> object, T obj);

	
}
