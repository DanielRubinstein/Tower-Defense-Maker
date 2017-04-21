package ModificationFromUser;

import java.util.Arrays;
import java.util.List;

import backEnd.ModelImpl;
import frontEnd.CustomJavafxNodes.SingleFieldPrompt;

// Player Mode: save current GameData to a new GameState folder within a given Level folder
// Author Mode: save current GameData to a new Level folder or overwrite an existing one

public class Modification_Save implements ModificationFromUser {

	private String myGameName;
	
	public Modification_Save(){
		myGameName = getSaveGameName();
		
	}
	
	
	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		myModel.getDataController().saveCurrentGameStateData(myModel.getGameData(), myGameName);

	}

	private String getSaveGameName() {
		List<String> dialogTitles = Arrays.asList("Save Game Utility", "Please Input a Name for your saved game");
		String promptLabel = "Saved game name:";
		String promptText = "";
		SingleFieldPrompt myDialog = new SingleFieldPrompt(dialogTitles, promptLabel, promptText);
		return myDialog.create();
	}

}
