package main;

import frontEnd.Facebook.FacebookConnector;
import javafx.stage.Stage;

public interface Controller{
	
	public void start(Stage stage);
	
	public void setFb(FacebookConnector fb);
}