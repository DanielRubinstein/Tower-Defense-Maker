package main;

import ModificationFromUser.ModificationFromUser;
import backEnd.Model;
import backEnd.Data.DataController;
import backEnd.GameData.GameData;
import backEnd.Mode.Mode;
import backEnd.Mode.UserModeType;
import frontEnd.ViewImpl;
import frontEnd.Skeleton.SkeletonImpl;
import javafx.stage.Stage;

public class ControllerImpl implements Controller {
	private ViewImpl myView;
	private Model myModel;
	private Mode myMode;
	private GameData myGameData;
	private DataController myDataController;

	public void start(Stage stage) {
		//developerTestingSkeleton(stage);

		
		myMode = new Mode(null, UserModeType.AUTHOR);
		
		/*
		myDataController = new DataController();
		myGameData = myDataController.getGameData("");
		
		myModel = new Model(myGameData, myMode, myDataController);
		*/
		
		myView = new ViewImpl(myMode, 
				(ModificationFromUser m) -> {
					executeInteraction(m);
					System.out.println("Modification from user sent to back end");
				});
		
	}

	/**
	 * The skeleton should be instantiated within View. This is here just for
	 * testing purposes
	 * 
	 * @param stage
	 */
	private void developerTestingSkeleton(Stage stage) {
		SkeletonImpl skeleton = new SkeletonImpl(myView);
		skeleton.display(stage);

	}
	
	private void executeInteraction(ModificationFromUser myInteraction){
		myInteraction.invoke(myModel);
	}
}