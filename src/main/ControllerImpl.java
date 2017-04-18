package main;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;

import ModificationFromUser.ModificationFromUser;
import backEnd.ModelImpl;
import backEnd.GameData.GameData;
import data.XMLReadingException;
import data.GamePrep.DataInputLoader;
import data.GamePrep.MainMenu;
import frontEnd.ViewImpl;
import frontEnd.Facebook.FacebookConnector;
import frontEnd.Menus.ErrorDialog;
import javafx.stage.Stage;

public class ControllerImpl implements Controller {
	private ViewImpl myView;
	private ModelImpl myModel;
	private FacebookConnector myFb;


	public void start(Stage stage) {
		
		
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
				DataInputLoader dataInput = new DataInputLoader(o);
				GameData initialGameData = dataInput.getGameData();
				myModel = new ModelImpl(initialGameData);
				myView = new ViewImpl(myModel, viewMod,myFb);

			} catch (Exception e) {
				ErrorDialog errDia = new ErrorDialog();
				errDia.create("Cannot Load Game", e.getMessage());
				e.printStackTrace();
			} 
			
		};
					
		MainMenu myMenu = new MainMenu(setGameData,this);
		myMenu.showMenus(stage);
	}
	public void setFb(FacebookConnector fb){
		myFb=fb;
		System.out.println(" in control impl fb " + myFb);
	}
	
	private void executeInteraction(ModificationFromUser myInteraction) throws Exception{
		myInteraction.invoke(myModel);
	}
}