package ModificationFromUser.savingAndLoading;

import java.io.File;

import ModificationFromUser.ModificationFromUser;
import backEnd.Model;
import backEnd.ModelImpl;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * 
 * @author Derek
 * @author Juan
 *
 */
public class Modification_LoadLevel implements ModificationFromUser
{

	@Override
	public void invoke(Model myModel) throws Exception
	{
		File fileToLoad = load(myModel);
		if(fileToLoad != null){
			myModel.getGameLoader().accept(fileToLoad);
		} else {
			// do nothing
		}
	}
	
	private File load(Model myModel)
	{
		DirectoryChooser chooser = new DirectoryChooser();
		
		switch (myModel.getMode().getUserMode())
		{
		case "PLAYER":
			chooser.setInitialDirectory(new File("data/games/" + myModel.getMode().getGameMode() + "/saves/"));
			break;
		case "AUTHOR":
			chooser.setInitialDirectory(new File("data/games/"+ myModel.getMode().getGameMode() + "/templates/"));
		}
	
		return chooser.showDialog(new Stage());
		
		
	}

}