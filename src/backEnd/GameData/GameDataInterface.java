package backEnd.GameData;

import backEnd.GameData.State.PlayerStatus;

import java.util.List;
import java.util.Map;

import backEnd.GameData.Rules.Rule;
import backEnd.GameData.Rules.RulesMap;
import backEnd.GameData.State.PlayerStatusModifier;
import backEnd.GameData.State.PlayerStatusReader;
import backEnd.GameData.State.State;

public interface GameDataInterface {
	
	public State getState();
	
	public RulesMap getRules();
	
	public PlayerStatusModifier getModifiablePlayerStatus();
	
	public PlayerStatusReader getReadOnlyPlayerStatus();

	public PlayerStatus getStatus();
	
	
}
