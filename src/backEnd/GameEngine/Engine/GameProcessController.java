package backEnd.GameEngine.Engine;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import backEnd.Rules;
import backEnd.State.State;

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
		while(myResources.getKeys().hasMoreElements()){
			myEngines.add(engineFactory.getEngine(myResources.getString(myResources.getKeys().nextElement())));
		}
	}
	
	public void run() {
		for(Engine engine : myEngines){
			engine.gameLoop(myCurrentState);
		}
		//Has won/lost?
	}	
}