package backEnd.GameEngine.Behaviors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import backEnd.GameEngine.Attribute;


public interface Behavior extends Observer {
	
	/**
	 * when the engine decides to run a behavior on a component, that component's corresponding behavior is executed
	 */
	public void execute(Map<String, Attribute<?>> myAttributes);


}