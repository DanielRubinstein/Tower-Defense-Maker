package backEnd;

import java.util.List;
import java.util.function.Consumer;

import ModificationFromUser.Modification_ChangeMode;
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;
import backEnd.Bank.BankController;
import backEnd.Bank.BankControllerImpl;
import backEnd.Bank.BankControllerReader;
import backEnd.GameData.GameData;
import backEnd.GameData.Rules.RuleReader;
import backEnd.GameData.State.PlayerStatusModifier;
import backEnd.GameData.State.PlayerStatusReader;
import backEnd.GameData.State.State;
import backEnd.GameEngine.EngineStatus;
import backEnd.GameEngine.Engine.GameProcessController;
import backEnd.GameEngine.Engine.Spawning.SpawnData;
import backEnd.GameEngine.Engine.Spawning.SpawnDataReader;
import backEnd.LevelProgression.LevelProgressionControllerEditor;
import backEnd.LevelProgression.LevelProgressionControllerImpl;
import backEnd.Mode.Mode;
import backEnd.Mode.ModeImpl;
import backEnd.Mode.ModeReader;
import data.DataController;
import data.DataControllerReader;
import data.XMLReadingException;
import frontEnd.Skeleton.SplashScreens.SplashScreenReader;
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
	private BankControllerImpl myBankController;
	private DataController myDataController;
	private GameProcessController myEngine;
	private LevelProgressionControllerImpl myLevelProgressionController;
	private Consumer<Object> gameLoader;
	private static final StringResourceBundle strResource = new StringResourceBundle();;
	
	public ModelImpl(GameData gameData, EngineStatus engineStatus, Consumer<SplashScreenReader> splashScreenLoader, Consumer<Object> gameLoader, 
		String gameName, String levelName) throws XMLReadingException {
		myDataController = new DataController();
		myGameData = gameData;
		this.gameLoader = gameLoader;
		myMode = new ModeImpl(strResource.getFromStringConstants("AUTHOR"), gameName, levelName, myLevelProgressionController);
		myLevelProgressionController = new LevelProgressionControllerImpl(myMode, myDataController, splashScreenLoader, gameLoader);
		myGameData.setLevelProgressionController(myLevelProgressionController);
		myEngine = new GameProcessController(myGameData);
		myBankController = new BankControllerImpl(myMode, (DataControllerReader) myDataController);
		myGameData.setBankController(myBankController);
		myDataController.setBankController(myBankController);
		new Modification_ChangeMode().invoke(this);
	}

	@Override
	public Consumer<Object> getGameLoader()
	{
		return gameLoader;
	}
	
	@Override
	public State getState()
	{
		return myGameData.getState();
	}
	
	@Override
	public ModeReader getModeReader(){
		return (ModeReader) myMode;
	}
	
	@Override
	public Mode getMode(){
		return this.myMode;
	}
	
	@Override
	public BankControllerReader getBankControllerReader(){
		return this.myBankController;
	}

	@Override
	public DataController getDataController(){
		return myDataController;
	}
	
	@Override
	public GameData getGameData(){
		return myGameData;
	}

	@Override
	public GameProcessController getGameProcessController() {
		return myEngine;

	}
	
	@Override
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
		
	@Override
	public LevelProgressionControllerEditor getLevelProgressionController() {
		return  myLevelProgressionController;
	}

	@Override
	public List<RuleReader> getRulesList() {
		return myGameData.getRules().getRuleReaderList();
	}
	
	@Override
	public BankController getBankController() {
		return myBankController;
	}

	
	///////////////////////////////////////////////////////////////////////////////
	// Security Methods
	///////////////////////////////////////////////////////////////////////////////
	
	@Override
	public AttributeOwner getAttributeOwner(AttributeOwnerReader attributeOwnerReader) {
		return (AttributeOwner) attributeOwnerReader;
	}

	@Override
	public SpawnData getSpawnData(SpawnDataReader mySpawnDataReader) {
		return (SpawnData) mySpawnDataReader;
	}

	
}
