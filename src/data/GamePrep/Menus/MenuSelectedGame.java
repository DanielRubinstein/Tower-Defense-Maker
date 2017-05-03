package data.GamePrep.Menus;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import data.XMLReader;
import data.XMLReaderImpl;
import data.XMLReadingException;
import data.GamePrep.GameMaker;
import frontEnd.CustomJavafxNodes.ActionButton;
import frontEnd.CustomJavafxNodes.ButtonMenuImpl;
import frontEnd.CustomJavafxNodes.ButtonScrollPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import resources.constants.StringResourceBundle;

public class MenuSelectedGame {
	private static final StringResourceBundle strResources = new StringResourceBundle();
	private ButtonMenuImpl primaryMenu;
	private Stage myStage;
	private Consumer<Object> myConsumerLoadData;

	public MenuSelectedGame(ButtonMenuImpl previousMenu, Stage stage, Consumer<Object> consumerLoadData) {
		myStage = stage;
		myConsumerLoadData = consumerLoadData;
		File file = new File(strResources.getFromFilePaths("All_Games_Path"));
		String[] directories = file.list((File current, String name) -> {
			return new File(current, name).isDirectory();
		});

		primaryMenu = new ButtonMenuImpl("Select a Game");

		ButtonScrollPane buttonScrollPane = new ButtonScrollPane();
		for (String gameName : directories) {
			buttonScrollPane.add(new ActionButton(gameName, () -> showGameMenu(primaryMenu, stage, gameName)));
		}
		primaryMenu.addNode(buttonScrollPane.getRoot());
		primaryMenu.addBackButton(previousMenu, stage);

	}

	public void display() {
		primaryMenu.display(myStage);
	}

	private void showGameMenu(ButtonMenuImpl previousMenu, Stage stage, String game) {
		ButtonMenuImpl primaryMenu = new ButtonMenuImpl("Select a Game");
		primaryMenu.addNode(new Label("Game Name: " + game));
		primaryMenu.addSimpleButtonWithHover("Create New Level", () -> {
			GameMaker gameMaker = new GameMaker(stage, myConsumerLoadData, game);
			gameMaker.display();
		}, "Create a new level for this game");
		primaryMenu.addSimpleButtonWithHover("Choose Level", () -> chooseLevel(primaryMenu, stage, "templates", game),
				"Load a level to edit");
		primaryMenu.addSimpleButtonWithHover("Play Game from Start", () -> playFromStart(stage, game),
				"Play from the first level");
		primaryMenu.addSimpleButtonWithHover("Load Saved Game", () -> chooseLevel(primaryMenu, stage, "saves", game),
				"Continue your progress by loading a user-saved game");
		primaryMenu.addBackButton(previousMenu, stage);
		primaryMenu.display(stage);
	}

	private void playFromStart(Stage stage, String game)
	{
		XMLReader reader = new XMLReaderImpl();
		
		try {
			Map<String, List<String>> map = (Map<String, List<String>>) reader.loadGamesMap("data/UniversalGameData/");
			myConsumerLoadData.accept(new File("data/games/" + game + "/templates/", map.get(game).get(0)));
			stage.close();
		} catch (XMLReadingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void chooseLevel(ButtonMenuImpl previousMenu, Stage stage, String type, String game) {
		String folder = "data/games/" + game + "/" + type + "/";

		File file = new File(folder);
		file.mkdir();

		Collection<String> directories = getValidGameDirectories(file);
		if (directories.isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("No files found");
			alert.setHeaderText("There are no " + type + " files here");
			alert.setContentText("Please select a different option");

			alert.showAndWait();
		} else {
			ButtonMenuImpl levelMenu = new ButtonMenuImpl("Pick a Level!");
			ButtonScrollPane buttonScrollPane = new ButtonScrollPane();
			for (String levelName : directories) {
				buttonScrollPane.add(new ActionButton(levelName, () -> {
					myConsumerLoadData.accept(new File(folder + levelName));
					stage.close();
				}));
			}
			levelMenu.addNode(buttonScrollPane.getRoot());
			levelMenu.addBackButton(previousMenu, stage);
			levelMenu.display(stage);
		}
	}

	/**
	 * http://stackoverflow.com/questions/1844688/read-all-files-in-a-folder
	 * 
	 * @param folder
	 */
	private Collection<String> getValidGameDirectories(final File folder) {
		Collection<String> validGameDirectories = new ArrayList<>();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory() && isValidGameFolder(fileEntry.getPath())) {
				validGameDirectories.add(fileEntry.getName());
			}
		}
		return validGameDirectories;
	}

	private boolean isValidGameFolder(String string) {
		List<String> necessaryFilePaths = getNecessaryFilePaths(string);
		for (String necessaryFilePath : necessaryFilePaths) {
			File file = new File(necessaryFilePath);
			if (!file.exists()) {
				return false;
			}
		}
		return true;
	}

	private List<String> getNecessaryFilePaths(String string) {
		List<String> necessaryFilePaths = new ArrayList<>();
		List<String> necessaryFileNames = Arrays.asList(strResources.getFromFilePaths("TileGrid_FileName"),
				strResources.getFromFilePaths("ComponentGraph_FileName"),
				strResources.getFromFilePaths("PlayerStatus_FileName"), strResources.getFromFilePaths("Rules_FileName"),
				strResources.getFromFilePaths("Spawns_FileName"));

		for (String necessaryFileName : necessaryFileNames) {
			necessaryFilePaths
					.add(string + File.separator +  necessaryFileName + strResources.getFromStringConstants("GAME_FILE_EXTENSION"));
		}
		return necessaryFilePaths;
	}

}
