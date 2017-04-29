package backEnd.GameEngine.Behaviors;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import backEnd.Attribute.AttributeImpl;
import backEnd.GameData.State.ComponentImpl;
import backEnd.GameData.State.Tile;


public interface Behavior {
	
	/**
	 * when the engine decides to run a behavior on a component, that component's corresponding behavior is executed
	 * @param <T>
	 * @throws FileNotFoundException 
	 */
	public <T> void execute(T toModify) throws FileNotFoundException;


}