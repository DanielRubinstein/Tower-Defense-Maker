package ModificationFromUser;

import backEnd.Mode.ModeEnum;

/**
 * Invokable classes that the front end creates when the user wants to modify the state.
 * 
 * 
 * @author Derek
 *
 */
public interface ModificationFromUser {

	
	public void invoke(ModeEnum currentMode);
}
