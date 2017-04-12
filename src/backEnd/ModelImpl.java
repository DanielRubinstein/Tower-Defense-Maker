package backEnd;

import backEnd.Bank.BankController;
import backEnd.Data.DataController;
import backEnd.Data.XMLReadingException;
import backEnd.GameData.GameData;
import backEnd.GameData.State.State;
import backEnd.GameEngine.Engine.GameProcessController;
import backEnd.Mode.Mode;
import backEnd.Mode.ModeImpl;
import backEnd.Mode.ModeReader;

/**
 * Controller the front end calls when it detects a backend modification from the user,
 * 
 * Front end creates instance of the appropriate ModificationFromUser class and passes it as a parameter to the 
 * executeInteraction() method
 * 
 * @author Derek
 *
 */
public class ModelImpl implements Model{
	private GameData myGameData;
	private Mode myMode;
	private BankController myBankController;
	private GameProcessController myEngines;
	
	public ModelImpl(DataController dataController, GameData gameData) throws XMLReadingException {
		myGameData = gameData;
		myMode = new ModeImpl();
		myEngines = new GameProcessController(myGameData.getState(), myGameData.getRules());
		//myBankController = dataController.generateBanks();
	}

	public State getState(){
		return myGameData.getState();
	}

	
	public ModeReader getModeReader(){
		return (ModeReader) myMode;
	}
	
	public Mode getMode(){
		return this.myMode;
	}
	
	@Override
	public BankController getBankController(){
		return this.myBankController;
	}
	

	@Override
	public GameProcessController getGameProcessController(){
		return myEngines;
	}
}
