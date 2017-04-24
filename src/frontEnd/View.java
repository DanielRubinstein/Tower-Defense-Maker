package frontEnd;

import java.util.Collection;
import java.util.Map;

import ModificationFromUser.ModificationFromUser;
import backEnd.BankController;
import backEnd.Model;
import backEnd.GameData.Rules.RuleReader;
import backEnd.GameEngine.Engine.Spawning.SpawnQueue;
import backEnd.LevelProgression.LevelProgressionControllerEditor;
import backEnd.LevelProgression.LevelProgressionControllerReader;
import backEnd.Mode.ModeReader;
import frontEnd.Facebook.FacebookConnector;
import frontEnd.Facebook.FacebookInteractor;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public interface View extends ViewReader{

	public void viewRules();

	public void editRules();
	
	public void sendUserModification(ModificationFromUser mod);

	public BankController getBankController();
	
	public Node getScreenGrid();
	
	public FacebookInteractor getFb();
	
	public Map<String, SpawnQueue> getSpawnQueues();
	
	public ModeReader getModeReader();
	
	LevelProgressionControllerEditor getLevelProgressionController();
	
	public Collection<RuleReader> getRules();

}
