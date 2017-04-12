package ModificationFromUser;

import backEnd.ModelImpl;
/**
 * Invokable classes that the front end instantiates when the user wants to modify the backend.
 * 
 * @author Derek
 *
 */
public interface ModificationFromUser {

	public void invoke(ModelImpl myModel) throws Exception;
}
