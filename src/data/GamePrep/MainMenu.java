package data.GamePrep;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import com.sun.org.apache.bcel.internal.generic.NEW;

import frontEnd.Facebook.FacebookBrowser;
import frontEnd.Facebook.FacebookBrowserImpl;
import frontEnd.Facebook.FacebookConnector;
import frontEnd.Facebook.FacebookConnectorImpl;
import frontEnd.Facebook.FacebookInteractor;
import frontEnd.Skeleton.UserTools.HelpOptions;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import frontEnd.CustomJavafxNodes.ButtonMenuImpl;
import frontEnd.CustomJavafxNodes.SingleFieldPrompt;
import javafx.stage.Stage;
import resources.constants.StringResourceBundle;

/**
 * The main menu which allows the use to
 * 	1) Create a new game (via GameMaker)
 *  2) Open a Game Template
 *  3) Load a saved game
 * @author Miguel Anderson, Juan Philippe
 *
 */

public class MainMenu{
	private Consumer<Object> consumerLoadData;
	private Consumer<FacebookInteractor> setFb;
	
	private static final StringResourceBundle strResources = new StringResourceBundle();
	private static final String DEFAULT_RESOURCE_BUNDLE = "resources/facebook";
	private static final String TEMPLATE_PATH = "data/games/";
	private ResourceBundle appInfo = ResourceBundle.getBundle(DEFAULT_RESOURCE_BUNDLE);
	private String appSecret = appInfo.getString("appSecret");
	private String appID = appInfo.getString("appID");
	private String helpURL = "";
	
	
	public MainMenu(Consumer<Object> setGameData, Consumer<FacebookInteractor> setFb){
		consumerLoadData = setGameData;
		this.setFb = setFb;
	}

	public void showMenus(Stage stage) {
		splashScreen(stage);
	}
	
	private void splashScreen(Stage stage) {
   	 	ButtonMenuImpl splash = new ButtonMenuImpl("Welcome");
   	 	splash.addPrimarySimpleButtonWithHover("START", () -> showPrimaryGameMenu(stage), "Click to start the game!");
   	 	splash.addPrimarySimpleButtonWithHover("Help/Instructions", () -> new HelpOptions(stage), "See the help page");
   	 	splash.addSimpleButtonWithHover("Connect To Facebook", () -> launchFb(stage), "Log in and connect to Facebook to see high scores, screenshots, post to the official voogasalad_su3ps1ckt34m1337 page");
		splash.display(stage);
	}

	private void showPrimaryGameMenu(Stage stage) {
		ButtonMenuImpl primaryMenu = new ButtonMenuImpl("Game Menu");
		primaryMenu.addSimpleButtonWithHover("Create a New Game", () -> showNewGameMenu(stage), "Create a new game from stratch, choose everything!!");
		primaryMenu.addSimpleButtonWithHover("Select Game", () -> showSelectGameMenu(stage), "Select from already-made games. From here you can load a game, make a level or start from the beginning");
		primaryMenu.display(stage);
	}
	
	private void showNewGameMenu(Stage stage){
		String newGameName = getNewGameName();
		if(newGameName != null){
			new GameMaker(stage, consumerLoadData, newGameName); // FIXME
		}
	}
	
	private String getNewGameName() {
		List<String> dialogTitles = Arrays.asList("Welcome!", "Please Input a Name for your new game");
		String promptLabel = "Name:";
		String promptText = "";
		SingleFieldPrompt myDialog = new SingleFieldPrompt(dialogTitles, promptLabel, promptText);
		return myDialog.getUserInputString();
	}
	
	private void showSelectGameMenu(Stage stage){
		File file = new File(strResources.getFromFilePaths("All_Games_Path"));
		String[] directories = file.list( (File current, String name) -> {
			return new File(current,name).isDirectory();
		});
		
		ButtonMenuImpl primaryMenu = new ButtonMenuImpl("Select a Game");
		
		for(String game : directories){
			primaryMenu.addSimpleButton(game, () -> showGameMenu(stage, game));
		}
		primaryMenu.display(stage);
		
	}
		
	private void showGameMenu(Stage stage, String game) {
		ButtonMenuImpl primaryMenu = new ButtonMenuImpl("Select a Game");
		primaryMenu.addNode(new Label("Game Name: " + game));
		primaryMenu.addSimpleButtonWithHover("Create New Level", () -> new GameMaker(stage, consumerLoadData, game), "Create a new level for this game");
		primaryMenu.addSimpleButtonWithHover("Play Level", () -> chooseLevel(stage, "templates" , game), "Play from the first level");
		primaryMenu.addSimpleButtonWithHover("Edit Level", () -> chooseLevel(stage, "templates" , game), "Load a level to edit");
   	 	primaryMenu.addSimpleButtonWithHover("Load Saved Game", () -> chooseLevel(stage, "saves" , game), "Continue your progress by loading a user-saved game");
   	 	primaryMenu.display(stage);
	}


	private void chooseLevel(Stage stage,String type , String game)
	{
		String folder = "data/games/" + game + "/" + type + "/";
		
		File file = new File(folder);
		file.mkdir();
		
		List<String> directories = Arrays.asList(file.list( (File current, String name) -> {
			return new File(current,name).isDirectory();
		}));
		if(directories.isEmpty()){
			System.out.println("No "+ type + " games in here!");
		} else {
			ButtonMenuImpl levelMenu = new ButtonMenuImpl("Pick a Level!");
			for(String levelName: directories){
				levelMenu.addSimpleButton(levelName, () -> {
					consumerLoadData.accept(new File(folder + levelName));
					stage.close();
				});
			}
			levelMenu.display(stage);
		}
	}

	private void launchFb(Stage stage) {
		Stage loginStage = new Stage();
		loginStage.initOwner(stage);
		loginStage.initModality(Modality.APPLICATION_MODAL);
		FacebookBrowser fbBrowser = new FacebookBrowserImpl(appID);
		FacebookConnector fb = new FacebookConnectorImpl(appID,appSecret);
		ButtonMenuImpl myLoginButton = new ButtonMenuImpl("Login!");
		myLoginButton.addPrimarySimpleButtonWithHover("Login", () -> {
			fbBrowser.launchPage();
			fbBrowser.onDialogClose(e -> {
				String accessToken = fbBrowser.getAccessToken();
				fb.login(accessToken);			
				FacebookInteractor fbInter= fb.getInteractor();
				setFb.accept(fbInter);
				loginStage.close();
			});
		}, "Click to launch facebook");
		myLoginButton.display(loginStage);
	}

}
