package backEnd.GameEngine;

import java.util.Map;

public interface Behavior {
	
	/**
	 * when the engine decides to run a behavior on a component, that component's corresponding behavior is executed
	 */
	public void execute(Map<String, Attribute<?>> myAttributes);


}
