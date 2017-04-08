package main;

import backEnd.Bank.Environment;
import backEnd.Bank.EnvironmentInterface;
import backEnd.GameData.GameData;
import backEnd.GameData.State.State;
import backEnd.GameData.State.StateImpl;
import backEnd.GameEngine.GameProcessController;
import frontEnd.ViewImpl;
import frontEnd.Skeleton.SkeletonImpl;
import javafx.stage.Stage;

public class ControllerImpl implements Controller{
	private ViewImpl myView;
	private EnvironmentInterface myEnvironment;
	private State myState;
	private GameProcessController myEngineController;
	
	public void start(Stage stage){
		
		SkeletonImpl skeleton = new SkeletonImpl(new ViewImpl());
		skeleton.display(stage);
		
		// make view
		
		// make model
	}

}
