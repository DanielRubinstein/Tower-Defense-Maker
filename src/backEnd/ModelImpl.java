package backEnd;

import java.io.File;

import backEnd.Bank.BankController;
import backEnd.Data.DataController;
import backEnd.GameData.GameData;
import backEnd.GameData.State.State;
import backEnd.GameData.State.StateImpl;
import backEnd.GameEngine.Engine.GameProcessController;
import backEnd.Mode.Mode;
import backEnd.Mode.ModeImpl;
import backEnd.Mode.ModeReader;
import backEnd.Mode.UserModeType;
import frontEnd.Splash.StartingInput;

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
	private GameProcessController myEngine;
	
	public ModelImpl(GameData gameData, Mode startingMode, DataController dataController){
		myGameData = gameData;
		myMode = startingMode;
		myEngine = new GameProcessController(myGameData.getState(), myGameData.getRules());
		
		// FIXME what are the parameters to BankController
		myBankController = new BankController(dataController.getTileMap(), dataController.getComponentsMap());
	}
	
	public ModelImpl(GameData gameData) {
		myGameData = gameData;
		myMode = new ModeImpl(null, UserModeType.AUTHOR);
	}

	public ModelImpl(StartingInput dim) {
		this(createGameData(dim));
	}

	private static GameData createGameData(StartingInput dim) {
		StateImpl state = new StateImpl(dim.getTilesWide(), dim.getTilesHigh(), 400, 400);
		GameData gameData = new GameData(state, null);
		return gameData;
		
	}

	public State getState(){
		return myGameData.getState();
	}
	/**
	 * Miguel likes the cast
	 */
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
}
