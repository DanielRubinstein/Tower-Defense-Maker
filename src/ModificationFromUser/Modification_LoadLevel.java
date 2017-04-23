package ModificationFromUser;

import java.io.File;

import backEnd.ModelImpl;
import backEnd.GameData.GameData;
import data.DataController;
import data.XMLReadingException;
import data.GamePrep.DataInputLoader;
import data.GamePrep.GameLoader;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * 
 * @author Derek
 *
 */
public class Modification_LoadLevel implements ModificationFromUser {
	
	private String myLevel;
	private File myGameFile;
	private GameData myGameData;
	
	public Modification_LoadLevel(){
		myLevel = load();
	}

	@Override
	public void invoke(ModelImpl myModel) throws Exception {		
		if (myLevel != null){
			DataInputLoader dataInput = new DataInputLoader(myLevel);
			myGameData = dataInput.getGameData();
		} else if (myGameFile != null){
			DataInputLoader dataInput = new DataInputLoader(myGameFile);
			myGameData = dataInput.getGameData();
		} 
		
		//myModel = new ModelImpl(myGameData);
		
	}
	
	private String load() {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setInitialDirectory(new File("./data/GameStateData/"));
	
		return chooser.showDialog(new Stage()).getName();
	}

}
