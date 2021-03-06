package backEnd.GameData;

import java.util.Map;

import backEnd.Bank.BankControllerImpl;
import backEnd.GameData.Rules.RulesMap;
import backEnd.GameData.State.PlayerStatus;
import backEnd.GameData.State.PlayerStatusModifier;
import backEnd.GameData.State.PlayerStatusReader;
import backEnd.GameData.State.State;
import backEnd.GameData.State.StateImpl;
import backEnd.GameEngine.EngineStatus;
import backEnd.LevelProgression.LevelProgressionControllerEditor;
import javafx.beans.property.ReadOnlyDoubleWrapper;

public class GameData implements GameDataInterface{
	private StateImpl myState;
	private RulesMap myRules;
	private PlayerStatus myPlayerStatus;
	private EngineStatus myEngineStatus;
	private LevelProgressionControllerEditor myLPC;
	private double myGameTime;
	private BankControllerImpl myBankController;
	
	public GameData(StateImpl state, PlayerStatus playerStatus, RulesMap myRules){
		this.myState = state;
		this.myRules = myRules;
		this.myPlayerStatus = playerStatus;
		myEngineStatus=EngineStatus.PAUSED;
		myState.setEngineStatus(myEngineStatus);
		myGameTime = 0;
	}

	@Override
	public State getState() {
		return myState;
	}

	@Override
	public RulesMap getRules() {
		return myRules;
	}
	
	public void setEngineStatus(EngineStatus currentEngineStatus){
		myEngineStatus=currentEngineStatus;
	}
	
	/**
	 * copies info from newGameData and puts it in current GameData
	 * @param newGameData
	 */
	public void updateGameData(GameData newGameData){
		//update state
		myState.updateState(newGameData.getState());
		//update rules
		myRules = newGameData.getRules();
		//update playerstatus
		updatePlayerStatus(newGameData.getStatus());
	}

	private void updatePlayerStatus(PlayerStatus newStatus){
		for(String key : myPlayerStatus.getKeySet()){
			myPlayerStatus.setStatusItemValue(key, newStatus.getStatusItemValue(key));
		}
	}
	
	@Override
	public PlayerStatusModifier getModifiablePlayerStatus() {
		return myPlayerStatus;
	}

	@Override
	public PlayerStatusReader getReadOnlyPlayerStatus() {
		return myPlayerStatus;
	}

	public PlayerStatus getStatus() {
		return this.myPlayerStatus;
	}

	public void incrementGameTime(double gameStep) {
		myGameTime += gameStep;
	}
	
	public double getGameTime() {
		return myGameTime;
	}

	public void setLevelProgressionController(LevelProgressionControllerEditor myLPC){
		this.myLPC = myLPC;
		
	}
	
	public LevelProgressionControllerEditor getLevelProgressionController(){
		return myLPC;
	}

	public void setBankController(BankControllerImpl bankController) {
		myBankController = bankController;
	}
	
	public BankControllerImpl getBankController() {
		return myBankController;
	}
	
	public Map<String, ReadOnlyDoubleWrapper> getStartingStatus(){
		return myPlayerStatus.getStartingMap();
	}

}