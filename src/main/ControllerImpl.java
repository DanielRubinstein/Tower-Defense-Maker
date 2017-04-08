package main;

import backEnd.Bank.Environment;
import backEnd.Bank.EnvironmentInterface;
import backEnd.Data.XMLWriter;
import backEnd.Data.XMLWriterImpl;
import backEnd.GameData.GameData;
import backEnd.GameData.State.State;
import backEnd.GameData.State.StateImpl;
import backEnd.GameEngine.GameProcessController;

import frontEnd.ViewImpl;
import frontEnd.Skeleton.Skeleton;
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
		
		GameData gData = new GameData(new StateImpl(10,10,10,10), null);
		
		XMLWriter xmlWriter = new XMLWriterImpl();
		xmlWriter.saveGameStateData(gData, "data/GameStateData/", "TestGame");
		// make view
		
		// make model
	}
}
