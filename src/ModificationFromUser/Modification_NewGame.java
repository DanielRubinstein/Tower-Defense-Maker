package ModificationFromUser;

import backEnd.ModelImpl;

public class Modification_NewGame implements ModificationFromUser {
	
	public Modification_NewGame(){
		
	}
	
	
	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		System.out.println("Make a new game");
	}


}
