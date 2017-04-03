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
	
	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {
		Controller controller = new Controller();
		controller.start(primaryStage);
	}

}