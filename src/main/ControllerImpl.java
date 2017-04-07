package main;

import backEnd.GameData;
import backEnd.Model.Model;
import frontEnd.View;
import frontEnd.Skeleton.SkeletonImpl;
import javafx.stage.Stage;

public class ControllerImpl implements Controller{
	private Model myModel;
	private View myView;
	
	public void start(Stage stage){
		//myView = new View();
		//myView.setGameDataListener(this::setupModelViewBridge);
		//myView.start(stage);
		
		SkeletonImpl skeleton = new SkeletonImpl();
		skeleton.display(stage);
		
		
	}
	
	public void setupModelViewBridge(GameData gameData) {
		myModel = new Model(gameData);
		myModel.addObserver(myView);
		myView.setModel(myModel);
	}


}
