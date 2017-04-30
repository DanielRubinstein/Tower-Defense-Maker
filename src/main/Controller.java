package main;

import javafx.stage.Stage;

public interface Controller{
	
	/**
	 * Sends the Controller the primary stage for the application
	 * @param stage
	 */
	public void start(Stage stage);
	
}