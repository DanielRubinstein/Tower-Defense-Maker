package backEnd.GameEngine.Behaviors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import backEnd.GameData.State.Tile;
import backEnd.GameEngine.Attribute;
import backEnd.GameEngine.Component;


public interface Behavior extends Observer {
	
	/**
	 * when the engine decides to run a behavior on a component, that component's corresponding behavior is executed
	 * @param <T>
	 */
	public <T> void execute(T toModify);


}