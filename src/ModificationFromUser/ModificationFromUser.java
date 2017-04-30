package ModificationFromUser;

import backEnd.Model;
/**
 * Invokable classes that the front end instantiates when the user wants to modify the backend.
 * 
 * @author Derek
 *
 */
public interface ModificationFromUser {

	public void invoke(Model myModel) throws Exception;
}
