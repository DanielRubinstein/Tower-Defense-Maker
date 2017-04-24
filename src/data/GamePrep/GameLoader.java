package data.GamePrep;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import backEnd.GameData.GameData;
import data.XMLReadingException;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GameLoader {
	public static final String SAVED_GAMES_DIRECTORY = "./data/SavedGames/";
	public static final String TEMPLATE_GAMES_DIRECTORY = "./data/LevelTemplates";
	public static final String GAME_FILE_EXTENSION = ".xml";

	public File loadGame() throws XMLReadingException {
		return loadGameFile(SAVED_GAMES_DIRECTORY);
	}

	private File loadGameFile(String searchDirectory) throws XMLReadingException {
		FileChooser xmlChooser = new FileChooser();
		xmlChooser.setTitle("Choose File");
		
		xmlChooser.setInitialDirectory(new File(searchDirectory));
		xmlChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sup3rS1ckT34m1337 GameData File", "*.xml"));
		File file = xmlChooser.showOpenDialog(new Stage());
		
		throw new XMLReadingException();
		
	}

	private Boolean isProperGameFile(File file) {
		String name = file.getName();
		String fileType = name.substring(name.lastIndexOf("."), name.length());
		return fileType.equals(GAME_FILE_EXTENSION);
	}

	public GameData loadTemplateGame(String templateGame) {
		for(File template : getFileList(TEMPLATE_GAMES_DIRECTORY)){
			if(extractGameTitle(template).equals(templateGame)){
				//return gameReader.Load(template);
				return null;
			}
		}
		return null; // TODO maybe add exception
	}

	public List<String> getTemplateTitleList() {
		return getFileList(TEMPLATE_GAMES_DIRECTORY).stream()
				.filter(file -> isProperGameFile(file))
				.map(file -> extractGameTitle(file))
				.collect(Collectors.toList());
	}

	private String extractGameTitle(File file) {
		return file.getName().substring(0, file.getName().lastIndexOf("."));
	}

	private List<File> getFileList(String searchDirectory) {
		List<File> fileList = new ArrayList<File>();
		try (Stream<Path> paths = Files.walk(Paths.get(searchDirectory))) {
			paths.forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					fileList.add(filePath.toFile());
				}
			});
		} catch (IOException e) {
			// TODO throw exception
			System.out.println("Bad Directory");
		}

		return fileList;

	}
	
	public List<String> getTemplateTitleListStupid(){
		List<String> templateTitleList = Arrays .asList(new String[] {
				"Bloons Tower Defense", 
				"Plants vs. Zombies", 
				"Desktop Tower Defense"
		}); ; 
		return templateTitleList;
	}

}
