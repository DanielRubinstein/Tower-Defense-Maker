package main;

import backEnd.GameData;
import backEnd.Environment.Environment;
import backEnd.Environment.EnvironmentInterface;
import backEnd.GameEngine.Engine;
import backEnd.GameEngine.GameProcessController;
import backEnd.Model.Model;
import backEnd.State.State;
import backEnd.State.StateImpl;
import frontEnd.View;
import frontEnd.Skeleton.Skeleton;
import javafx.stage.Stage;

public interface Controller{
	
	public void start(Stage stage);
	
	public void setupModelViewBridge(GameData gameData);
}
