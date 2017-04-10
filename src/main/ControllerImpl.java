package main;

import ModificationFromUser.ModificationFromUser;
import backEnd.Model;
import backEnd.Data.DataController;
import backEnd.GameData.GameData;
import backEnd.Mode.ModeImpl;
import backEnd.Mode.UserModeType;
import frontEnd.ViewImpl;
import frontEnd.Skeleton.SkeletonImpl;
import javafx.stage.Stage;

public class ControllerImpl implements Controller {
	private ViewImpl myView;
	private Model myModel;
	private ModeImpl myMode;
	private GameData myGameData;
	private DataController myDataController;

	public void start(Stage stage) {
		//developerTestingSkeleton(stage);
		myMode = new ModeImpl(null, UserModeType.AUTHOR);
		
		/*
		myDataController = new DataController();
		myGameData = myDataController.getGameData("");
		
		myModel = new Model(myGameData, myMode, myDataController);
		*/
		myView = new ViewImpl(myMode, 
				(ModificationFromUser m) -> {
					try {
						executeInteraction(m);
						System.out.println("Modification from user sent to back end");
					} catch (Exception e) {
						System.out.println("Error in Modification sent");
						if (myModel == null){
							System.out.println("   No model created");
						}
					}
					
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
	
	private void executeInteraction(ModificationFromUser myInteraction) throws Exception{
		myInteraction.invoke(myModel);
	}
}