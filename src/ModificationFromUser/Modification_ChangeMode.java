package ModificationFromUser;

import backEnd.Model;

/**
 * @author Derek
 *
 */
public class Modification_ChangeMode implements ModificationFromUser {

	@Override
	public void invoke(Model myModel) {
		myModel.getMode().toggleUserMode();	
	}
}
