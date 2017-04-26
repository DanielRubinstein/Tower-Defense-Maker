package backEnd.GameData.State;

import java.util.List;
import java.util.Observer;

public interface SerializableObservable {

	void addObserver(Observer o);

	List<Observer> getObservers();

	void clearObservers();

	void setObservers(List<Observer> observersave);
	
}
