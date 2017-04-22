package backEnd.GameData;

import backEnd.GameData.PlayerStatus.PlayerStatusModifier;
import backEnd.GameData.PlayerStatus.PlayerStatusReader;
import backEnd.GameData.State.State;

public interface GameDataInterface {
	
	public State getState();
	
	public Rules getRules();
	
	public PlayerStatusModifier getModifiablePlayerStatus();
	
	public PlayerStatusReader getReadOnlyPlayerStatus();
	
	
}
