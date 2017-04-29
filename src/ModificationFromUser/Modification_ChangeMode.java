package ModificationFromUser;

import backEnd.ModelImpl;

public class Modification_ChangeMode implements ModificationFromUser {

	@Override
	public void invoke(ModelImpl myModel) {
		myModel.getMode().toggleUserMode();		
	}
	
	

}
