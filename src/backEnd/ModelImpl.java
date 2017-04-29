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
import data.DataControllerReader;
import data.XMLReadingException;
import frontEnd.Skeleton.SplashScreens.SplashScreenData;
import javafx.beans.property.SimpleStringProperty;
import resources.constants.StringResourceBundle;

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
	private Consumer<Object> gameLoader;
	private static final StringResourceBundle strResource = new StringResourceBundle();;
	
	public ModelImpl(GameData gameData, EngineStatus engineStatus, Consumer<SplashScreenData> splashScreenLoader, Consumer<Object> gameLoader) throws XMLReadingException {
		myDataController = new DataController();
		myGameData = gameData;
		this.gameLoader = gameLoader;
		myMode = new ModeImpl(strResource.getFromStringConstants("AUTHOR"), strResource.getFromStringConstants("DEFAULT"),
				strResource.getFromStringConstants("DEFAULT"), myLevelProgressionController);
		myLevelProgressionController = new LevelProgressionControllerImpl(myMode, myDataController, splashScreenLoader, gameLoader);
		myGameData.setLevelProgressionController(myLevelProgressionController);
		myEngine = new GameProcessController(myGameData);
		myBankController = new BankController(myMode, (DataControllerReader) myDataController);
		myGameData.setBankController(myBankController);
		myDataController.setBankController(myBankController);

	}

	public Consumer<Object> getGameLoader()
	{
		return gameLoader;
	}
	
	public State getState()
	{
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
