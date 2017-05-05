package main;

import java.util.function.Consumer;
import java.util.ConcurrentModificationException;

import ModificationFromUser.ModificationFromUser;
import backEnd.ModelImpl;
import backEnd.GameData.GameData;
import backEnd.GameEngine.EngineStatus;
import data.GamePrep.DataInputLoader;
import data.GamePrep.Menus.MenuController;
import frontEnd.ViewImpl;
import frontEnd.CustomJavafxNodes.ErrorDialog;
import frontEnd.Facebook.FacebookInteractor;
import javafx.stage.Stage;
import resources.constants.StringResourceBundle;
import frontEnd.Skeleton.SplashScreens.SplashScreenReader;

public class ControllerImpl implements Controller {
	private ViewImpl myView;
	private ModelImpl myModel;
	private FacebookInteractor myFb;
	private Consumer<Object> setGameData;
	private EngineStatus myEngineStatus = EngineStatus.PAUSED;
	private static final StringResourceBundle strResources = new StringResourceBundle();


	public void start(Stage stage) {
		
		Consumer<SplashScreenReader> splashScreenLoader = (splashScreen) ->
		{
			myView.setSplashScreen(splashScreen);
		};
		
		Consumer<ModificationFromUser> viewMod = 
				(ModificationFromUser m) -> {
					try {
						executeInteraction(m);
						//System.out.println("In Controller - Modification from fE to bE executed");
					} catch (ConcurrentModificationException e){
						// do nothing
						// this occurs a lot because of the way we save
						// we have not found an issue with it though
					} catch (Exception e) {
						ErrorDialog errDia = new ErrorDialog();
						String eMessage = "";
						if(e.getMessage() != null){
							eMessage = e.getMessage();
						} else {
							try{
								eMessage = e.getCause().getMessage();
							} catch (NullPointerException nullE){
								eMessage = e.toString();
							}	
						}
						errDia.create("InGame Error", eMessage);
					}
				};

		setGameData = inputGameObject ->
		{
			try {
				if (myView != null){
					myView.closeMainWindow();
				}
				DataInputLoader loader = new DataInputLoader(inputGameObject);
				GameData initialGameData = loader.getGameData();
				
				initialGameData.setEngineStatus(myEngineStatus);
				
				myModel = new ModelImpl(initialGameData, myEngineStatus, splashScreenLoader, setGameData, loader.getGameName(), loader.getLevelName());
				myView = new ViewImpl(myModel, viewMod,myFb);

			} catch (Exception e) {
				ErrorDialog errDia = new ErrorDialog();
				errDia.create("Cannot Load Game", e.getMessage());
				//e.printStackTrace();
			} 
			
		};
					
		MenuController myMenu = new MenuController(setGameData, (FacebookInteractor f) -> setFb(f));
		myMenu.showMenus(stage);
	}
	
	private void setFb(FacebookInteractor fb){
		myFb=fb;
	}
	
	private void executeInteraction(ModificationFromUser myInteraction) throws Exception{
		myInteraction.invoke(myModel);
	}
}