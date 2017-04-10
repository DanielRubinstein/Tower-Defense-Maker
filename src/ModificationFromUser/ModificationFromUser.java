package ModificationFromUser;

import backEnd.ModelImpl;
/**
 * Invokable classes that the front end creates when the user wants to modify the state.
 * 
 * 
 * @author Derek
 *
 */
public interface ModificationFromUser {

	
	public void invoke(ModelImpl myModel) throws Exception;
}
