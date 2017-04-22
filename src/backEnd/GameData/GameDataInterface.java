package backEnd.GameData;

import backEnd.GameData.Rules.Rule;
import backEnd.GameData.State.PlayerStatusModifier;
import backEnd.GameData.State.PlayerStatusReader;
import backEnd.GameData.State.State;

public interface GameDataInterface {
	
	public State getState();
	
	public Rule getRules();
	
	public PlayerStatusModifier getModifiablePlayerStatus();
	
	public PlayerStatusReader getReadOnlyPlayerStatus();
	
	
}
