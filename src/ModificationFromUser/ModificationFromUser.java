package ModificationFromUser;

import backEnd.Model;
/**
 * Invokable classes that the front end instantiates when the user wants to modify the backend.
 * 
 * @author Derek
 *
 */
public interface ModificationFromUser {

	
	/**
	 * Method that is called to perform the backend modification implemented in a class that implements this interface
	 * 
	 * @param myModel
	 * @throws Exception
	 */
	public void invoke(Model myModel) throws Exception;
}
