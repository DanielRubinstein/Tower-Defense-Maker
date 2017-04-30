package ModificationFromUser.savingAndLoading;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import ModificationFromUser.ModificationFromUser;
import backEnd.Model;
import frontEnd.CustomJavafxNodes.SingleFieldPrompt;

// Player Mode: save current GameData to a new GameState folder within a given Level folder
// Author Mode: save current GameData to a new Level folder or overwrite an existing one

public enum Modification_SaveGameState implements ModificationFromUser {
	SAVEDGAME ((model, newName) -> {
		model.getDataController().saveGame(model.getGameData(), model.getMode().getGameMode(), newName);
		}),
	TEMPLATE ((model, newName) -> {
		model.getDataController().saveLevelTemplate(model.getGameData(), model.getMode().getGameMode(), newName);
		model.getLevelProgressionController().addLevelToCurrentGame(newName);
		});

	private String myLevelName;
	private BiConsumer<Model, String> myConsumer;
	
	Modification_SaveGameState(BiConsumer<Model, String> consumer){
		myConsumer = consumer;
	}
	
	
	@Override
	public void invoke(Model myModel) throws Exception {
		myLevelName = getSaveGameName();
		if(myLevelName == null){
			return;
		}
		myModel.getMode().setLevelMode(myLevelName);
		myConsumer.accept(myModel, myLevelName);
	}

	private String getSaveGameName() {
		List<String> dialogTitles = Arrays.asList("Save Game Utility", "Please Input the name of the level");
		String promptLabel = "Name:";
		String promptText = "";
		SingleFieldPrompt myDialog = new SingleFieldPrompt(dialogTitles, promptLabel, promptText);
		String name = myDialog.getUserInputString();
		return name;
	}

}
