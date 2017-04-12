package ModificationFromUser;

import backEnd.ModelImpl;

/**
 * 
 * @author Derek
 *
 */
public class Modification_Load implements ModificationFromUser {
	
	private String myGameName;
	
	
	public Modification_Load(String gameName){
		myGameName = gameName;
	}

	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		myModel.getGameData().updateGameData(myModel.getDataController().generateGameData(myGameName));
	}

}
