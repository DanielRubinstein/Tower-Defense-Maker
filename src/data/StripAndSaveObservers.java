package data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import backEnd.GameData.State.SerializableObservable;
import backEnd.GameData.State.SerializableObserver;

public class StripAndSaveObservers {
	private List<SerializableObservable> myObservables;
	private List<List<SerializableObserver>> strippedObservers;
	
	@SuppressWarnings("unchecked")
	public <T> StripAndSaveObservers(List<SerializableObservable> observables){
		Collections.sort(observables);
		this.myObservables =  observables;
	}
	
	public void stripObservers(){
		strippedObservers = new ArrayList<List<SerializableObserver>>();
		for (int i = 0; i < myObservables.size(); i++){
			strippedObservers.add(myObservables.get(i).getObservers());
			myObservables.get(i).clearObservers();
		}
	}
	
	public void giveBackObservers(){
		for (int i = 0; i < myObservables.size(); i++){
			myObservables.get(i).setObservers(strippedObservers.get(i));
		}
	}
}
