package backEnd;

import java.util.List;
import java.util.function.Consumer;

import backEnd.GameData.GameData;
import backEnd.GameData.Rules.RuleReader;
import backEnd.GameData.State.PlayerStatusModifier;
import backEnd.GameData.State.PlayerStatusReader;
import backEnd.GameData.State.State;
import backEnd.GameEngine.EngineStatus;
import backEnd.GameEngine.Engine.GameProcessController;
import backEnd.LevelProgression.LevelProgressionControllerEditor;
import backEnd.LevelProgression.LevelProgressionControllerImpl;
import backEnd.Mode.Mode;
import backEnd.Mode.ModeImpl;
import backEnd.Mode.ModeReader;
import data.DataController;
import data.XMLReadingException;
import frontEnd.Skeleton.SplashScreens.SplashScreen;
import frontEnd.Skeleton.SplashScreens.SplashScreenData;
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
	private LevelProgressionControllerImpl myLevelProgressionController;
	private EngineStatus myEngineStatus;
	
	public ModelImpl(GameData gameData, EngineStatus engineStatus, Consumer<SplashScreenData> splashScreenLoader) throws XMLReadingException {
		myDataController = new DataController();
		myGameData = gameData;
		myMode = new ModeImpl("AUTHOR", "DEFAULT", "DEFAULT", myLevelProgressionController);
		myLevelProgressionController = new LevelProgressionControllerImpl(myMode, myDataController);
		myGameData.setLevelProgressionController(myLevelProgressionController);
		myEngine = new GameProcessController(myGameData);
		myBankController = new BankController(myMode, myDataController.loadTileMap(), myDataController.loadComponentMap());
		myGameData.setBankController(myBankController);
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

	
		
	public LevelProgressionControllerEditor getLevelProgressionController() {
		return  myLevelProgressionController;
	}

	@Override
	public List<RuleReader> getRulesList() {
		return myGameData.getRules().getRuleReaderList();
	}
}
