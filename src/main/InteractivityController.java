package main;

import ModificationFromUser.ModificationFromUser;
import backEnd.Environment.Environment;
import backEnd.GameEngine.Engine;
import backEnd.Mode.ModeEnum;
import backEnd.State.State;

/**
 * Controller the front end calls when it detects a backend modification from the user,
 * 
 * Front end creates instance of the appropriate ModificationFromUser class and passes it as a parameter to the 
 * executeInteraction() method
 * 
 * @author Derek
 *
 */
public class InteractivityController {

	private ModeEnum currentMode;
	private ModeEnum nextMode;
	private Environment myEnvironment;
	private State myState;
	private Engine myEngine;
	
	public InteractivityController(ModeEnum startingMode, State myState, Environment myEnv, Engine myEngine){
		currentMode = startingMode;
		nextMode = ModeEnum.getNextMode(currentMode);
		this.myState = myState;
		this.myEnvironment = myEnv;
		this.myEngine = myEngine;
	}
	
	public void changeMode(){
		ModeEnum tempMode = currentMode;
		currentMode = nextMode;
		nextMode = tempMode;
		
	}
	
	public void executeInteraction(ModificationFromUser myInteraction){
		myInteraction.invoke(currentMode, this);
	}
	
	public State getState(){
		return myState;
	}
}
