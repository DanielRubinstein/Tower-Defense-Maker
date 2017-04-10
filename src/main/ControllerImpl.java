package main;

import java.util.function.Consumer;

import ModificationFromUser.ModificationFromUser;
import backEnd.ModelImpl;
import backEnd.Data.DataController;
import backEnd.Data.XMLReadingException;
import backEnd.GameData.GameData;
import backEnd.Mode.ModeImpl;
import frontEnd.ViewImpl;
import frontEnd.Menus.ErrorDialog;
import frontEnd.Skeleton.SkeletonImpl;
import frontEnd.Splash.MainMenu;
import javafx.stage.Stage;

public class ControllerImpl implements Controller {
	private ViewImpl myView;
	private ModelImpl myModel;
	private ModeImpl myMode;
	private GameData myGameData;
	private DataController myDataController;

	public void start(Stage stage) {
		//developerTestingSkeleton(stage);
		//myMode = new ModeImpl(null, UserModeType.AUTHOR);
		
		
		myDataController = new DataController();
		
		Consumer<ModificationFromUser> viewMod = 
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
				};
				
				
		Consumer<Object> setGameData = o -> {
			try {
				myModel = new ModelImpl(myDataController.generateGameData(o));
				myView = new ViewImpl(myModel, viewMod);
			} catch (XMLReadingException e) {
				ErrorDialog errDia = new ErrorDialog();
				errDia.create("Cannot Load Game", e.getMessage());
			}
			
		};
					
		
		MainMenu myMenu = new MainMenu(setGameData);
		myMenu.showMenus(stage);
	}

	/**
	 * The skeleton should be instantiated within View. This is here just for
	 * testing purposes
	 * 
	 * @param stage
	 */
	private void developerTestingSkeleton(Stage stage) {
		SkeletonImpl skeleton = new SkeletonImpl(myView, myModel);
		skeleton.display(stage);

	}
	
	private void executeInteraction(ModificationFromUser myInteraction) throws Exception{
		myInteraction.invoke(myModel);
	}
}