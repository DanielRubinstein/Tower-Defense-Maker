package ModificationFromUser.savingAndLoading;

import ModificationFromUser.ModificationFromUser;
import backEnd.Model;

public class Modification_NewGame implements ModificationFromUser {
	
	public Modification_NewGame(){
		
	}
	
	
	@Override
	public void invoke(Model myModel) throws Exception {
		System.out.println("Make a new game");
	}


}
