package backEnd.GameData;

import backEnd.GameData.State.PlayerStatusModifier;
import backEnd.GameData.State.PlayerStatusReader;
import backEnd.GameData.State.State;

public interface GameDataInterface {
	
	public State getState();
	
	public Rules getRules();
	
	public PlayerStatusModifier getModifiablePlayerStatus();
	
	public PlayerStatusReader getReadOnlyPlayerStatus();
	
	
}
