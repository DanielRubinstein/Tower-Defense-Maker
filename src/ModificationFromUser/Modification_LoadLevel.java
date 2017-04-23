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
	private GameData myGameData;
	
	public Modification_LoadLevel(){
		
	}
	
	public Modification_LoadLevel(String levelName)
	{
		myLevel = levelName;
	}

	@Override
	public void invoke(ModelImpl myModel) throws Exception {	
			
			if (myLevel == null) myLevel = load(myModel);
			System.out.println(myLevel);
			DataInputLoader dataInput = new DataInputLoader(myLevel);
			myGameData = dataInput.getGameData();
		
			myModel.getState().updateState(myGameData.getState());
		
	}
	
	private String load(ModelImpl myModel) {
		DirectoryChooser chooser = new DirectoryChooser();
		
		
		chooser.setInitialDirectory(new File("./data/SavedGames/"));
	
		return chooser.showDialog(new Stage()).getName();
	}

}
