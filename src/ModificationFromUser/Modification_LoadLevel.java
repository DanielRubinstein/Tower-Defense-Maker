package ModificationFromUser;

import java.io.File;
import backEnd.ModelImpl;
import backEnd.GameData.GameData;
import data.GamePrep.DataInputLoader;
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
			
			DataInputLoader dataInput;
			
			if (myLevel == null)
			{
				File file = load(myModel);
				myLevel = file.getName();
				dataInput = new DataInputLoader(myLevel, file.getParentFile().getPath());
				
			}
			else
			{
				dataInput = new DataInputLoader(myLevel);
			}
			
			
			myGameData = dataInput.getGameData();
			myModel.getGameData().updateGameData(myGameData);
		
	}
	
	private File load(ModelImpl myModel) {
		DirectoryChooser chooser = new DirectoryChooser();
		
		switch (myModel.getMode().getUserMode())
		{
		case "PLAYER":
			chooser.setInitialDirectory(new File("./data/SavedGames/"));
			break;
		case "AUTHOR":
			chooser.setInitialDirectory(new File("./data/LevelTemplates/"));
		}
		
	
		return chooser.showDialog(new Stage());
	}

}
