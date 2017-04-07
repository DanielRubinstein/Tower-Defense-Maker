package backEnd.GameEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import backend.SLogoData;

public interface Behavior extends Observer {
	
	/**
	 * when the engine decides to run a behavior on a component, that component's corresponding behavior is executed
	 */
	public void execute(Map<String, Attribute<?>> myAttributes);


}