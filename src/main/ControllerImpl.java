package main;

import java.util.function.Consumer;

import ModificationFromUser.ModificationFromUser;
import backEnd.ModelImpl;
import backEnd.GameData.GameData;
import backEnd.GameEngine.EngineStatus;
import data.GamePrep.DataInputLoader;
import data.GamePrep.MainMenu;
import frontEnd.ViewImpl;
import frontEnd.CustomJavafxNodes.ErrorDialog;
import frontEnd.Facebook.FacebookConnector;
import frontEnd.Facebook.FacebookInteractor;
import javafx.stage.Stage;

public class ControllerImpl implements Controller {
	private ViewImpl myView;
	private ModelImpl myModel;
	private FacebookInteractor myFb;
	private EngineStatus myEngineStatus=EngineStatus.PAUSED;


	public void start(Stage stage) {
		
		
		Consumer<ModificationFromUser> viewMod = 
				(ModificationFromUser m) -> {
					try {
						executeInteraction(m);
						System.out.println("Modification from user sent to back end");
					} catch (Exception e) {
						System.out.println("Error in Modification sent");
						e.printStackTrace();
						ErrorDialog errDia = new ErrorDialog();
						errDia.create("InGame Error", e.getMessage());
						e.printStackTrace();
						
					}
				};

		Consumer<Object> setGameData = o -> {
			try {
				DataInputLoader dataInput = new DataInputLoader(o);
				GameData initialGameData = dataInput.getGameData();
				initialGameData.setEngineStatus(myEngineStatus);
				myModel = new ModelImpl(initialGameData, myEngineStatus);
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
	public void setFb(FacebookInteractor fb){
		myFb=fb;
	}
	
	private void executeInteraction(ModificationFromUser myInteraction) throws Exception{
		myInteraction.invoke(myModel);
	}
}