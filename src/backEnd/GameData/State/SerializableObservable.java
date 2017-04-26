package backEnd.GameData.State;

import java.util.List;

public interface SerializableObservable {

	void addObserver(SerializableObserver o);

	List<SerializableObserver> getObservers();

	void clearObservers();

	void setObservers(List<SerializableObserver> observersave);
	
}