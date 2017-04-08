package backEnd;

import ModificationFromUser.ModificationFromUser;
import backEnd.Bank.Environment;
import backEnd.GameData.State.State;
import backEnd.GameEngine.GameProcessController;
import backEnd.Mode.ModeEnum;

/**
 * Controller the front end calls when it detects a backend modification from the user,
 * 
 * Front end creates instance of the appropriate ModificationFromUser class and passes it as a parameter to the 
 * executeInteraction() method
 * 
 * @author Derek
 *
 */
public class Model {

	private ModeEnum currentMode;
	private ModeEnum nextMode;
	private Environment myEnvironment;
	private State myState;
	private GameProcessController myEngine;
	
	public Model(ModeEnum startingMode, State myState, Environment myEnv, GameProcessController myEngine){
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
