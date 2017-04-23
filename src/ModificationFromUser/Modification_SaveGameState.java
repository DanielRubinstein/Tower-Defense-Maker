package ModificationFromUser;

import java.util.Arrays;
import java.util.List;

import backEnd.ModelImpl;
import frontEnd.CustomJavafxNodes.SingleFieldPrompt;

// Player Mode: save current GameData to a new GameState folder within a given Level folder
// Author Mode: save current GameData to a new Level folder or overwrite an existing one

public class Modification_SaveGameState implements ModificationFromUser {

	private String myLevelName;
	
	public Modification_SaveGameState(){
		myLevelName = getSaveGameName();
		
	}
	
	
	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		myModel.getDataController().saveCurrentGameStateData(myModel.getGameData(), myLevelName);

	}

	private String getSaveGameName() {
		List<String> dialogTitles = Arrays.asList("Save Game Utility", "Please Input a Name for your save");
		String promptLabel = "Name:";
		String promptText = "";
		SingleFieldPrompt myDialog = new SingleFieldPrompt(dialogTitles, promptLabel, promptText);
		return myDialog.create();
	}

}
