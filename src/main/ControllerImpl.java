package main;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Consumer;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Parameter;

import ModificationFromUser.ModificationFromUser;
import backEnd.ModelImpl;
import backEnd.Data.DataController;
import backEnd.Data.XMLReadingException;
import backEnd.GameData.GameData;
import frontEnd.ViewImpl;
import frontEnd.Menus.ErrorDialog;
import frontEnd.Skeleton.SkeletonImpl;
import frontEnd.Splash.MainMenu;
import frontEnd.Splash.StartingInput;
import javafx.stage.Stage;

public class ControllerImpl implements Controller {
	private ViewImpl myView;
	private ModelImpl myModel;
	private GameData myGameData;
	private DataController myDataController;
	private Map<Object,String> dataMap;

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
						e.printStackTrace();
						if (myModel == null){
							System.out.println("   No model created");
						}
					}
				};

		Consumer<Object> setGameData = o -> {
			try {
				//Method m = myDataController.getClass().getMethod("generateGameData", o.getClass());
				//myGameData = (GameData) m.invoke(myDataController,o);
				DataInputLoader dataInput = new DataInputLoader(o,myDataController);
				myGameData = dataInput.getGameData();
				myModel = new ModelImpl(myDataController, myGameData);
				myView = new ViewImpl(myModel, myDataController, viewMod);

			} catch (InstantiationException | IllegalArgumentException | XMLReadingException | NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException e) {
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