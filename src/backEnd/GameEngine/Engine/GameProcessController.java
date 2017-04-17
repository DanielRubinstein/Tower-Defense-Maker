package backEnd.GameEngine.Engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

import backEnd.Model;
import backEnd.GameData.Rules;
import backEnd.GameData.State.State;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import resources.Constants;

public class GameProcessController {

	private List<Engine> myEngines; 
	private State myCurrentState; 
	private Rules myRules;
	private final static String RESOURCES_PATH = "resources/GameProcessController";
	private final static ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PATH);
	
	public Timeline animation = new Timeline();
	private static final double MILLISECOND_DELAY = Constants.MILLISECOND_DELAY;
	private static final double SECOND_DELAY = Constants.SECOND_DELAY;
	
	public GameProcessController(State currentState, Rules gameRules){
		myEngines = new ArrayList<Engine>();
		myCurrentState = currentState;
		myRules = gameRules;

		EngineFactory engineFactory = new EngineFactory();
		Enumeration<String> n = myResources.getKeys();
		for(String key : Collections.list(n)){
			myEngines.add(engineFactory.getEngine(myResources.getString(key)));
		}
	}
	
	public void playAnimation() {
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
		animation.setCycleCount(Animation.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}
	
	/**
	 * controls the animation of the State
	 */
	private void step(double delay) {
		System.gc();
		System.out.println("game loop is running");
		this.run(delay); // TODO: TESTING ONLY
	}
	
	public void run(double stepTime) {
		for(Engine engine : myEngines){
			engine.gameLoop(myCurrentState,stepTime);
		}
		//Has won/lost? check myRules after each loop?
	}	
}