package data.GamePrep;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import frontEnd.Facebook.FacebookBrowser;
import frontEnd.Facebook.FacebookBrowserImpl;
import frontEnd.Facebook.FacebookConnector;
import frontEnd.Facebook.FacebookConnectorImpl;
import frontEnd.Facebook.FacebookInteractor;
import frontEnd.Skeleton.UserTools.HelpOptions;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import frontEnd.CustomJavafxNodes.ButtonMenuImpl;
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
   	 	splash.addPrimarySimpleButtonWithHover("START", () -> showPrimaryMenu(stage), "Click to start the game!");
		splash.display(stage);
	}

	private void showPrimaryMenu(Stage stage) {
		ButtonMenuImpl primaryMenu = new ButtonMenuImpl("Games");
		primaryMenu.addPrimarySimpleButtonWithHover("Help/Instructions", () -> new HelpOptions(stage), "See the help page");
   	 	primaryMenu.addSimpleButtonWithHover("Create New Level", () -> new GameMaker(stage, consumerLoadData), "Create A New Game after selecting the size of the screen");
   	 	primaryMenu.addSimpleButtonWithHover("Modify Level", () -> chooseLevel(), "Load a level to edit");
   	 	primaryMenu.addSimpleButtonWithHover("Load Saved Game", () -> chooseSavedGame(stage), "Continue your progress by loading a user-saved game");
   	 	primaryMenu.addSimpleButtonWithHover("Play Game", () -> new GameChooserWindow(stage, consumerLoadData) , "Play game");
   	 	primaryMenu.addSimpleButtonWithHover("Connect To Facebook", () -> launchFb(stage), "Log in and connect to Facebook to see high scores, screenshots, post to the official voogasalad_su3ps1ckt34m1337 page");
		primaryMenu.display(stage);
	}

	private void chooseLevel()
	{
		loadLevel(TEMPLATE_PATH);
	}
	
	private void chooseSavedGame(Stage stage)
	{
		File file = new File(strResources.getFromFilePaths("All_Games_Path"));
		String[] directories = file.list( (File current, String name) -> {
			return new File(current,name).isDirectory();
		});
		
		VBox box = new VBox();
		for(String game: directories){
			Button gameButton = new Button(game);
			gameButton.setOnAction(e -> showSaves(game, stage));
			box.getChildren().add(gameButton);
		}
		
		stage.setScene(new Scene(box));
	}

	private void showSaves(String game, Stage stage)
	{
		File file = new File("data/games/" + game + "/saves/");
		file.mkdir();
		
		String[] directories = file.list( (File current, String name) -> {
			return new File(current,name).isDirectory();
		});
		
		VBox box = new VBox();
		for(String level: directories){
			Button gameButton = new Button(level);
			gameButton.setOnAction(e -> consumerLoadData.accept(new File("data/games/" + game + "/saves/" + level)));
			box.getChildren().add(gameButton);
		}
		
		stage.setScene(new Scene(box));
	}

	private void loadLevel(String filePath) 
	{
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setInitialDirectory(new File(filePath));
		File path = chooser.showDialog(new Stage());
		
		consumerLoadData.accept(path);
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
