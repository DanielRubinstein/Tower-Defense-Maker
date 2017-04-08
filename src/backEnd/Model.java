package backEnd;

import ModificationFromUser.ModificationFromUser;
import backEnd.Bank.BankController;
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
	private BankController myBankController;
	private State myState;
	private GameProcessController myEngine;
	
	public Model(ModeEnum startingMode, State myState, BankController myBankController, GameProcessController myEngine){
		currentMode = startingMode;
		nextMode = ModeEnum.getNextMode(currentMode);
		this.myState = myState;
		this.myBankController = myBankController;
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
