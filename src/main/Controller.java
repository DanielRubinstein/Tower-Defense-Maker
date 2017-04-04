package main;

import backEnd.Model.Model;
import frontEnd.View;
import frontEnd.Skeleton.View.Skeleton;
import javafx.stage.Stage;

public class Controller implements ControllerInterface{
	private Model myModel;
	private View myView;
	
	public void start(Stage stage){
		Skeleton skeleton = new Skeleton();
		skeleton.display(stage);
		//setupModelViewBridge();
		//myView.start(stage);

	}
	

	private void setupModelViewBridge() {
		myModel = new Model();
		myView = new View();
		myModel.addObserver(myView);
		myView.setModel(myModel);
	}


}
