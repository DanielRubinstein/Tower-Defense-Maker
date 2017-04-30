package frontEnd;

import backEnd.Bank.BankControllerReader;
import backEnd.GameData.State.*;
import backEnd.LevelProgression.LevelProgressionControllerReader;
import backEnd.Mode.ModeReader;
import frontEnd.Facebook.FacebookInteractor;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * This interface defines the View, determines how Front End should be
 * controlled and how the Front End and Model interact. This interface has less
 * permissions and is at the highest level of the View hierarchy.
 * 
 * @author Tim, Miguel
 *
 */
public interface ViewReader {

	/**
	 * Offers the front end the status of the Engine as a string property (e.g.
	 * running, paused, ...)
	 * 
	 * @return Engine run status as a String property
	 */
	public SimpleStringProperty getRunStatus();

	/**
	 * Returns true if in Author Mode, returns false if not
	 * 
	 * @return a Boolean property for the Author Mode
	 */
	public SimpleBooleanProperty getBooleanAuthorModeProperty();

	/**
	 * Returns name of the string name of the current game (e.g. Plants vs.
	 * Zombies, Bloons)
	 * 
	 * @return Name of current game as a string property
	 */
	public SimpleStringProperty getStringGameModeProperty();

	/**
	 * Returns name of the string name of the current level. This value is set
	 * by the user when they save the level.
	 * 
	 * @return Name of current level as a string property
	 */
	public SimpleStringProperty getStringLevelModeProperty();

	/**
	 * Returns the main window of the application. Currently only used to ensure
	 * popups have priority, forced focus over the main window
	 * 
	 * @return The main Window of the Applicatin
	 */
	public Window getMainWindow();

	/** 
	 * Displays an error message with the error supplied. If the error has no message, it will check the cause of the error and display that.
	 * @param e
	 */
	public void reportError(Exception e);

	/** Returns the Level Progression Controller which holds the possible games and levels. 
	 * 
	 * @return a read-only version of the Level Progression Controller
	 */
	public LevelProgressionControllerReader getLevelProgressionController();

	/**
	 * Returns a read-only version of the Player Status so that the frontend can
	 * view the score, health, and other player status values.
	 * 
	 * @return a read-only version of Player status
	 */
	public PlayerStatusReader getPlayerStatus();

	/**
	 * When set, this allows the front end to interact with facebook via the API
	 * of the utility
	 * 
	 * @return FacebookInteractor
	 */
	public FacebookInteractor getFb();

	/**
	 * Gets a read-only version of the BankController which allows access to the
	 * accessible Presets and to interact in a read-only manner with the backing
	 * preset map in BankController (e.g. getting name from component, getting
	 * attribute owner from name, and so on)
	 * 
	 * @return a read-only version of the BankController
	 */
	public BankControllerReader getBankControllerReader();

}
