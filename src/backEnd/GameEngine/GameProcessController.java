package backEnd.GameEngine;

import java.util.List;

import backEnd.Rules;
<<<<<<< HEAD
import backEnd.GameEngine.Engine.Engine;
=======
>>>>>>> master
import backEnd.State.State;

public class GameProcessController {

	private List<Engine> myEngines; 
	private State myCurrentState; 
	private Rules myRules;
	
	
	public GameProcessController(State currentState, Rules gameRules){
		myCurrentState = currentState;
		myRules = gameRules;
		
		//TODO get all possible engines from where? and put into myEngines
	}
	
}