package backEnd;

import backEnd.Bank.BankController;
import backEnd.GameData.GameData;
import backEnd.GameData.PlayerStatus.PlayerStatusModifier;
import backEnd.GameData.PlayerStatus.PlayerStatusReader;
import backEnd.GameData.State.State;
import backEnd.GameEngine.Engine.GameProcessController;
import backEnd.LevelProgression.LevelProgressionController;
import backEnd.Mode.Mode;
import backEnd.Mode.ModeImpl;
import backEnd.Mode.ModeReader;
import data.DataController;
import data.XMLReadingException;
import javafx.beans.property.SimpleStringProperty;

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
	private DataController myDataController;
	private GameProcessController myEngine;
	private LevelProgressionController myLevelProgressionController;
	
	public ModelImpl(GameData gameData) throws XMLReadingException {
		myDataController = new DataController();
		myGameData = gameData;
		myMode = new ModeImpl();
		myEngine = new GameProcessController(myGameData.getState(), myGameData.getRules());
		myBankController = myDataController.generateBanks();
		myLevelProgressionController = new LevelProgressionController();
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
	

	public DataController getDataController(){
		return myDataController;
	}
	
	public GameData getGameData(){
		return myGameData;
	}

	@Override
	public GameProcessController getGameProcessController() {
		return myEngine;

	}
	
	public SimpleStringProperty getEngineStatus(){
		return myEngine.getEngineStatus();
	}

	@Override
	public PlayerStatusReader getPlayerStatusReader() {
		return myGameData.getReadOnlyPlayerStatus();
	}

	@Override
	public PlayerStatusModifier getModifiablePlayerStatus() {
		return myGameData.getModifiablePlayerStatus();
	}
}
