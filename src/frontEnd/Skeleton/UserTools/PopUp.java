package frontEnd.Skeleton.UserTools;

import javafx.stage.Stage;

/**
 * This interface defines anything in the UI that "pop ups", i.e. opens a new stage. 
 * @author Tim
 *
 */
public interface PopUp {

	/**
	 * Displays this object as a popUp by launching a new stage with the given stage as a parent. 
	 * @param stage TODO
	 */
	public void displayOnStage(Stage stage);
}
