package backEnd.GameEngine.Engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

import backEnd.Model;
import backEnd.GameData.GameData;
import backEnd.GameData.Rules.Rule;
import backEnd.GameData.State.PlayerStatus;
import backEnd.GameData.State.State;
import backEnd.GameEngine.EngineStatus;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.stage.Stage;
import javafx.util.Duration;
import resources.Constants;

public class GameProcessController {

	private List<Engine> myEngines; 
	private GameData myGameData;
	private final static String RESOURCES_PATH = "resources/GameProcessController";
	private final static ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PATH);
	private EngineStatus engineStatus;
	private SimpleStringProperty engineStatusProperty;
	
	public Timeline animation = new Timeline();
	
	public GameProcessController(GameData gameData){
		engineStatus = EngineStatus.PAUSED;
		engineStatusProperty = new SimpleStringProperty(engineStatus.toString());
		myEngines = new ArrayList<Engine>();
		myGameData = gameData;
		KeyFrame frame = new KeyFrame(Duration.millis(Constants.MILLISECOND_DELAY), e -> step(Constants.SECOND_DELAY));
		animation.setCycleCount(Animation.INDEFINITE);
		animation.getKeyFrames().add(frame);
		
		EngineFactory engineFactory = new EngineFactory();
		Enumeration<String> n = myResources.getKeys();
		for(String key : Collections.list(n)){
			myEngines.add(engineFactory.getEngine(myResources.getString(key)));
		}
	}
	
	public void playAnimation() {
		// TODO include rule checking
		if (animation.getKeyFrames().isEmpty()){
			KeyFrame frame = new KeyFrame(Duration.millis(Constants.MILLISECOND_DELAY), e -> step(Constants.SECOND_DELAY));
			animation.setCycleCount(Animation.INDEFINITE);
			animation.getKeyFrames().add(frame);
		}
		engineStatus = EngineStatus.RUNNING;
		engineStatusProperty.set(engineStatus.toString());
		animation.play();
		System.out.println("GAME STARTED");
	}
	

	
	/**
	 * controls the animation of the State
	 */
	private void step(double delay) {
		//System.gc();
		//System.out.println("Game loop step preformed");
		this.run(delay); // TODO: TESTING ONLY
		myGameData.incrementGameTime(delay);
	}
	
	public void run(double stepTime) {
		for(Engine engine : myEngines){
			engine.gameLoop(myGameData,stepTime);
			//System.out.println("steptime is  "+ stepTime);
		}
		//System.out.println(stepTime);
		//Has won/lost? check myRules after each loop?
	}
	
	public SimpleStringProperty getEngineStatus(){
		return engineStatusProperty;
	}

	public void pause() {
		animation.pause();
		engineStatus = EngineStatus.PAUSED;
		engineStatusProperty.set(engineStatus.toString());
		System.out.println("GAME PAUSED");
	}	
}