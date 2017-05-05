package backEnd.GameEngine.Engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

import backEnd.GameData.GameData;
import backEnd.GameEngine.EngineStatus;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Duration;
import resources.constants.numeric.NumericResourceBundle;

public class GameProcessController {

	private List<Engine> myEngines;
	private GameData myGameData;
	private final static String RESOURCES_PATH = "resources/GameProcessController";
	private final static ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PATH);
	private EngineStatus engineStatus;
	private SimpleStringProperty engineStatusProperty;
	private NumericResourceBundle numericResourceBundle = new NumericResourceBundle();

	public Timeline animation = new Timeline();

	public GameProcessController(GameData gameData) {
		engineStatus = EngineStatus.PAUSED;
		engineStatusProperty = new SimpleStringProperty(engineStatus.toString());
		myEngines = new ArrayList<Engine>();
		myGameData = gameData;
		addNewFrameToAnimation();
		EngineFactory engineFactory = new EngineFactory();
		Enumeration<String> n = myResources.getKeys();
		for (String key : Collections.list(n)) {
			myEngines.add(engineFactory.getEngine(myResources.getString(key)));
		}
	}

	private void addNewFrameToAnimation() {
		KeyFrame frame = new KeyFrame(Duration.millis(numericResourceBundle.getMillisecondDelay()), e -> step(numericResourceBundle.getSecondDelay()));
		animation.setCycleCount(Animation.INDEFINITE);
		animation.getKeyFrames().add(frame);
	}

	public void playAnimation() {
		// TODO include rule checking
		if (animation.getKeyFrames().isEmpty()) {
			addNewFrameToAnimation();
		}
		engineStatus = EngineStatus.RUNNING;
		engineStatusProperty.set(engineStatus.toString());
		animation.play();
		//System.out.println(this.getClass().getSimpleName() + ": GAME STARTED");
	}

	/**
	 * controls the animation of the State
	 */
	private void step(double delay) {
		// System.gc();
		//System.out.println(this.getClass().getSimpleName() + "Game loop step preformed: " + delay);
		this.run(delay); // TODO: TESTING ONLY
		//System.out.println(this.getClass().getSimpleName() + " : " + engineStatus + " : " + myGameData.getState().gameIsRunning());
		if (engineStatus.toString().equals("RUNNING")) {
			//System.out.println(this.getClass().getSimpleName() + " | " + "Step Complete | Time: " + myGameData.getGameTime());
			myGameData.incrementGameTime(delay);
			//System.out.println(this.getClass().getSimpleName() + " : " + myGameData.getGameTime() +" incremented by "+ delay);
		}
	}

	public void run(double stepTime) {
		for (Engine engine : myEngines) {
			engine.gameLoop(myGameData, stepTime);
//			System.out.println("steptime is "+ stepTime);
		}
		// System.out.println(stepTime);
		// Has won/lost? check myRules after each loop?
	}

	public SimpleStringProperty getEngineStatus() {
		return engineStatusProperty;
	}

	public void pause() {
		animation.pause();
		engineStatus = EngineStatus.PAUSED;
		engineStatusProperty.set(engineStatus.toString());
		//System.out.println(this.getClass().getSimpleName() + ": GAME PAUSED");
	}
}