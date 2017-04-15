package backEnd;

import backEnd.Bank.BankController;
import backEnd.Data.DataController;
import backEnd.Data.XMLReadingException;
import backEnd.GameData.GameData;
import backEnd.GameData.State.State;
import backEnd.GameEngine.Engine.GameProcessController;
import backEnd.Mode.Mode;
import backEnd.Mode.ModeImpl;
import backEnd.Mode.ModeReader;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import resources.Constants;

/**
 * Controller the front end calls when it detects a backend modification from the user,
 * 
 * Front end creates instance of the appropriate ModificationFromUser class and passes it as a parameter to the 
 * executeInteraction() method
 * 
 * @author Derek
 *
 */
public class ModelImpl implements Model{
	private GameData myGameData;
	private Mode myMode;
	private BankController myBankController;
	private DataController myDataController;
	private GameProcessController myEngine;
	private static final double MILLISECOND_DELAY = Constants.MILLISECOND_DELAY;
	private static final double SECOND_DELAY = Constants.SECOND_DELAY;
	private Timeline animation;
	
	public ModelImpl(DataController dataController, GameData gameData) throws XMLReadingException {
		myDataController = dataController;
		myGameData = gameData;
		myMode = new ModeImpl();

		myDataController = dataController;
		myEngine = new GameProcessController(myGameData.getState(), myGameData.getRules());
		myBankController = dataController.generateBanks();
		
		animation = new Timeline();

	}

	public State getState(){
		return myGameData.getState();
	}

	
	public ModeReader getModeReader(){
		return (ModeReader) myMode;
	}
	
	public Mode getMode(){
		return this.myMode;
	}
	
	@Override
	public BankController getBankController(){
		return this.myBankController;
	}
	

	public DataController getDataController(){
		return myDataController;
	}
	
	public GameData getGameData(){
		return myGameData;
	}

	@Override
	public GameProcessController getGameProcessController() {
		return myEngine;

	}
	
	
	public void play(){
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
		//System.out.println("game loop is running");
		myEngine.run(delay); // TODO: TESTING ONLY
	}
}
