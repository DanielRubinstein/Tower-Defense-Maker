package backEnd.GameData.State;

import java.util.List;

public interface SerializableObservableGen<T>{

	void addObserver(SerializableObserverGen<T> o);
	
	List<SerializableObserverGen<T>> getObserversGen();

	void clearObservers();
	
	void setObserversGen(List<SerializableObserverGen<T>> observersave);

}
