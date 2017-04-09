package backEnd;

import backEnd.Bank.BankController;
import backEnd.Data.DataController;
import backEnd.GameData.GameData;
import backEnd.GameData.State.State;
import backEnd.GameEngine.GameProcessController;
import backEnd.Mode.Mode;

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
	private GameData myGameData;
	private Mode myMode;
	private BankController myBankController;
	private GameProcessController myEngine;
	
	public Model(GameData gameData, Mode startingMode, DataController dataController){
		myGameData = gameData;
		myMode = startingMode;
		myEngine = new GameProcessController(myGameData.getState(), myGameData.getRules());
		
		// FIXME what are the parameters to BankController
		myBankController = new BankController(dataController.getTileMap(), dataController.getComponentsMap());
	}
	
	public State getState(){
		return myGameData.getState();
	}
	
	public Mode getMode(){
		return myMode;
	}
	
	public BankController getBankController(){
		return myBankController;
	}
}
