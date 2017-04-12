package backEnd.GameEngine.Engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

import backEnd.GameData.Rules;
import backEnd.GameData.State.State;

public class GameProcessController {

	private List<Engine> myEngines; 
	private State myCurrentState; 
	private Rules myRules;
	private final static String RESOURCES_PATH = "resources/GameProcessController";
	private final static ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PATH);
	
	public GameProcessController(State currentState, Rules gameRules){
		myEngines = new ArrayList<Engine>();
		myCurrentState = currentState;
		myRules = gameRules;
		EngineFactory engineFactory = new EngineFactory();
		Enumeration<String> n = myResources.getKeys();
		for(String key : Collections.list(n)){
			myEngines.add(engineFactory.getEngine(myResources.getString(key)));
		}
	}
	
	public void run(double stepTime) {
		for(Engine engine : myEngines){
			engine.gameLoop(myCurrentState,stepTime);
		}
		//Has won/lost?
	}	
}