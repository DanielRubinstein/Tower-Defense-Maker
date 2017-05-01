package frontEnd.Skeleton.SplashScreens;

import java.util.ResourceBundle;

import backEnd.GameData.State.PlayerStatusReader;
import frontEnd.Skeleton.UserTools.StatusView;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import resources.constants.StringResourceBundle;

public abstract class SplashScreenImpl implements SplashScreen {
	private StringResourceBundle stringResourceBundle = new StringResourceBundle();
	private GridPane myGP;
	private HBox buttonBox = new HBox();
	private Scene myScene;
	private EventHandler restartLevel;
	private Button restartLevelButton;
	private HBox myStatusView = new HBox();
	private final static String SPLASH_PATH = "resources/splashScreen";
	private final static ResourceBundle myResources = ResourceBundle.getBundle(SPLASH_PATH);
	

	public SplashScreenImpl(EventHandler restartLevel) {
		this.restartLevel = restartLevel;
		myGP = new GridPane();
		myScene = new Scene(myGP);
		myScene.getStylesheets().add(stringResourceBundle.getFromStringConstants("DEFAULT_CSS"));
		myGP.add(buttonBox, 0, 3);
		GridPane.setHalignment(buttonBox, HPos.CENTER);
		buttonBox.setAlignment(Pos.CENTER);
		myGP.setAlignment(Pos.CENTER);
		
	}
	
	@Override
	public void addRestartLevelButton(){
		Button restartLevelButton = new Button(getResource("RestartLevelButtonText"));
		restartLevelButton.setOnMouseClicked(restartLevel);
		addButton(restartLevelButton);
		
	}
	
	

	protected void addButton(Button toAdd) {
		buttonBox.getChildren().add(toAdd);
	}
	
	protected void setImage(Image image){
		ImageView myImage = new ImageView(image);
		GridPane.setHalignment(myImage, HPos.CENTER);
		myGP.add(myImage, 0, 1);
	}
	
	protected String getResource(String key){
		return myResources.getString(key);
	}
	
	protected void setMessage(String message){
		Label myMessage = new Label(message);
		GridPane.setHalignment(myMessage, HPos.CENTER);
		myGP.add(myMessage, 0, 2);
	}

	@Override
	public void display(Stage myStage) {
		myStage.setScene(myScene);
		myStage.show();
	}

	@Override
	public void setStatusDisplayValues(PlayerStatusReader playerStatus) {
		StatusView statusContents = new StatusView(playerStatus);
		myStatusView.getChildren().add(statusContents.getRoot());
		myStatusView.setAlignment(Pos.CENTER);
		GridPane.setHalignment(myStatusView, HPos.CENTER);
		myGP.add(myStatusView, 0, 0);
		
		
	}

}
