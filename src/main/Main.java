package main;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	/**
	 * Start the program
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		Controller controller = new ControllerImpl();
		controller.start(primaryStage);
	}

}