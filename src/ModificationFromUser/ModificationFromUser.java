package ModificationFromUser;

import backEnd.Mode.ModeEnum;
import main.InteractivityController;

/**
 * Invokable classes that the front end creates when the user wants to modify the state.
 * 
 * 
 * @author Derek
 *
 */
public interface ModificationFromUser {

	
	public void invoke(ModeEnum currentMode, InteractivityController myController);
}
