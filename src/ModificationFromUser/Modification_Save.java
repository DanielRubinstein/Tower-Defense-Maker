package ModificationFromUser;

import backEnd.ModelImpl;

public class Modification_Save implements ModificationFromUser {

	private String myGameName;
	
	public Modification_Save(String gameName){
		myGameName = gameName;
		
	}
	
	
	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		myModel.getDataController().saveCurrentGameStateData(myModel.getGameData(), myGameName);

	}

}
