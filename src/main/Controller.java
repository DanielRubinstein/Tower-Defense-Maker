package main;

import frontEnd.Facebook.FacebookInteractor;
import javafx.stage.Stage;

public interface Controller{
	
	public void start(Stage stage);
	
	public void setFb(FacebookInteractor fbInter);
}