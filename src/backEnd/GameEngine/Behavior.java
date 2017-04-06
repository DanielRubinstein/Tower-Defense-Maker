package backEnd.GameEngine;

import java.util.ArrayList;
import java.util.List;

public interface Behavior {
	
	
	// If You're Cool Use : List<Attribute<?>> associatedAttributes = new ArrayList<Attribute<?>>(); //every implementation of behavior will need a list of associated attributes	
	
	/**
	 * when the engine decides to run a behavior on a component, that component's corresponding behavior is executed
	 */
	public void execute();


}
