package frontEnd.Menus;

import java.io.File;

import backEnd.GameData;
import backEnd.Data.GameReader;
import backEnd.Data.GameReaderInterface;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GameLoader {
	public static final String SAVED_GAMES_DIRECTORY = "./data/SavedGames";
	public static final String GAME_FILE_EXTENSION = ".xml";
	private GameReaderInterface gameReader;
	
	public GameLoader(){
		gameReader = new GameReader();
	}

	public GameData loadGame() {
		return gameReader.Load(chooseFile());
		
	}
	
	private File chooseFile(){
		FileChooser xmlChooser = new FileChooser();
		xmlChooser.setTitle("Choose File");
		xmlChooser.setInitialDirectory(new File(SAVED_GAMES_DIRECTORY));
		File file = xmlChooser.showOpenDialog(new Stage());
		if(file != null){
			String name = file.getName();
			String fileType = name.substring(name.lastIndexOf("."), name.length());
			if(!fileType.equals(GAME_FILE_EXTENSION)){
				return null;
			}
			return file;
		}
		return null;
	}

	public GameData loadPredefinedGame(String predefinedGame) {
		
		// TODO Auto-generated method stub
		return null;
	}

}
