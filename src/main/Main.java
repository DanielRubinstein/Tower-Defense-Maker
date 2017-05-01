package main;

import frontEnd.CustomJavafxNodes.ErrorDialog;
import javafx.application.Application;
import javafx.stage.Stage;
import resources.constants.StringResourceBundle;

public class Main extends Application {
	
	private StringResourceBundle strResources = new StringResourceBundle();
	/**
	 * Start the program
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try{
			Controller controller = new ControllerImpl();
			controller.start(primaryStage);
		}catch(Exception e){
			//as a last resort
			ErrorDialog eD = new ErrorDialog();
			eD.create(strResources.getFromErrorMessages("Default_Error"), e.getMessage());
		}
	}

}