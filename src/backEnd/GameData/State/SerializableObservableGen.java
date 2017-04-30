package backEnd.GameData.State;

import java.util.List;

public interface SerializableObservableGen<T> extends SerializableObservable{

	void addObserver(SerializableObserverGen<T> o);

	List<SerializableObserver> getObservers();

	void clearObservers();

	void setObservers(List<SerializableObserver> observersave);
}
