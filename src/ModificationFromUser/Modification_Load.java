package ModificationFromUser;

import java.io.File;

import backEnd.ModelImpl;
import backEnd.GameData.GameData;
import data.DataController;
import data.XMLReadingException;
import data.GamePrep.DataInputLoader;
import data.GamePrep.GameLoader;

/**
 * 
 * @author Derek
 *
 */
public class Modification_Load implements ModificationFromUser {
	
	private String myGameName;
	private File myGameFile;
	private GameData myGameData;
	
	public Modification_Load(){
		myGameFile = load();
	}
	
	
	public Modification_Load(String gameName){
		myGameName = gameName;
	}
	
	public Modification_Load(File gameFile){
		myGameFile = gameFile;
	}

	@Override
	public void invoke(ModelImpl myModel) throws Exception {		
		if (myGameName != null){
			DataInputLoader dataInput = new DataInputLoader(myGameName);
			myGameData = dataInput.getGameData();
		} else if (myGameFile != null){
			DataInputLoader dataInput = new DataInputLoader(myGameFile);
			myGameData = dataInput.getGameData();
		} 
		
		myModel = new ModelImpl(myGameData);
		
	}
	
	private File load() {
		GameLoader gL = new GameLoader();
		File fileToLoad = null;
		try {
			fileToLoad = gL.loadGame();
		} catch (XMLReadingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileToLoad;
	}

}
