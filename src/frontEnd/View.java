package frontEnd;

import java.util.Collection;
import java.util.Map;

import ModificationFromUser.ModificationFromUser;
import backEnd.Model;
import backEnd.Bank.BankControllerImpl;
import backEnd.Bank.BankControllerReader;
import backEnd.GameData.Rules.RuleReader;
import backEnd.GameData.State.Component;
import backEnd.GameEngine.Engine.Spawning.SpawnQueues;
import backEnd.LevelProgression.LevelProgressionControllerEditor;
import backEnd.LevelProgression.LevelProgressionControllerReader;
import backEnd.Mode.ModeReader;
import frontEnd.Facebook.FacebookConnector;
import frontEnd.Facebook.FacebookInteractor;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public interface View extends ViewReader{
	
	public void sendUserModification(ModificationFromUser mod);
	
	public Map<String, SpawnQueues> getSpawnQueues();
	
	LevelProgressionControllerEditor getLevelProgressionController();
	
	public Collection<RuleReader> getRules();

	public boolean isComponentOnGrid(Component c);

}
