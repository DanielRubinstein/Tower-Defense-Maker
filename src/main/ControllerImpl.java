package main;

import backEnd.GameData;
import backEnd.Environment.Environment;
import backEnd.Environment.EnvironmentInterface;
import backEnd.GameEngine.GameProcessController;
import backEnd.Model.Model;
import backEnd.State.State;
import backEnd.State.StateImpl;
import frontEnd.ViewEditor;
import frontEnd.ViewImpl;
import frontEnd.Skeleton.Skeleton;
import frontEnd.Skeleton.SkeletonImpl;
import javafx.stage.Stage;

public class ControllerImpl implements Controller{
	private Model myModel;
	private ViewImpl myView;
	private EnvironmentInterface myEnvironment;
	private State myState;
	private GameProcessController myEngineController;
	
	public void start(Stage stage){
		
		//myView = new View();
		//myView.setGameDataListener(this::setupModelViewBridge);
		//myView.start(stage);

		SkeletonImpl skeleton = new SkeletonImpl(new ViewImpl());
		skeleton.display(stage);
		//myEnvironment = new Environment();
		//myState = new StateImpl(20,20);
		//myEngineController = new GameProcessController(myState, null); //this should get Rules, not null
		
	}
	
	public void setupModelViewBridge(GameData gameData) {
		myModel = new Model(gameData);
		//myModel.addObserver(myView);
		//myView.setModel(myModel);
	}


}
