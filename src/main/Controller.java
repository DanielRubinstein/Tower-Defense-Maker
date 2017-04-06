package main;

import backEnd.GameData;
import backEnd.Model.Model;
import frontEnd.View;
import frontEnd.Skeleton.Skeleton;
import javafx.stage.Stage;

public class Controller implements ControllerInterface{
	private Model myModel;
	private View myView;
	
	public void start(Stage stage){
		//myView = new View();
		//myView.setGameDataListener(this::setupModelViewBridge);
		//myView.start(stage);
		
		Skeleton skeleton = new Skeleton();
		skeleton.display(stage);
		
		
	}
	
	public void setupModelViewBridge(GameData gameData) {
		myModel = new Model(gameData);
		myModel.addObserver(myView);
		myView.setModel(myModel);
	}


}
