package ModificationFromUser;

import java.io.File;

import backEnd.ModelImpl;

/**
 * 
 * @author Derek
 *
 */
public class Modification_Load implements ModificationFromUser {
	
	private String myGameName;
	private File myGameFile;
	
	
	public Modification_Load(String gameName){
		myGameName = gameName;
	}
	
	public Modification_Load(File gameFile){
		myGameFile = gameFile;
	}

	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		if (myGameName != null){
			myModel.getGameData().updateGameData(myModel.getDataController().generateGameData(myGameName));
		} else if (myGameFile != null){
			myModel.getGameData().updateGameData(myModel.getDataController().generateGameData(myGameFile));
		} 
	}

}
