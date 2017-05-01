package frontEnd.Skeleton.SplashScreens;

import backEnd.GameData.State.PlayerStatusReader;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import resources.constants.StringResourceBundle;

public abstract class SplashScreenImpl implements SplashScreen {
	private StringResourceBundle stringResourceBundle = new StringResourceBundle();
	private BorderPane myBP;
	private HBox buttonBox = new HBox();
	private Scene myScene;
	private EventHandler restartLevel;
	private Button restartLevelButton;

	public SplashScreenImpl(EventHandler restartLevel) {
		this.restartLevel = restartLevel;
		myBP = new BorderPane();
		myScene = new Scene(myBP);
		myScene.getStylesheets().add(stringResourceBundle.getFromStringConstants("DEFAULT_CSS"));
		BorderPane.setAlignment(buttonBox, Pos.CENTER);
		myBP.setBottom(buttonBox);
		
	}
	
	@Override
	public void addRestartLevelButton(){
		Button restartLevelButton = new Button("Restart Level");
		restartLevelButton.setOnMouseClicked(restartLevel);
		addButton(restartLevelButton);
		
	}
	
	

	protected void addButton(Button toAdd) {
		buttonBox.getChildren().add(toAdd);
	}
	
	protected void setImage(Image image){
		ImageView myImage = new ImageView(image);
		myBP.setCenter(myImage);
	}

	@Override
	public void display(Stage myStage) {
		myStage.setScene(myScene);
		myStage.show();
	}

	@Override
	public void setStatusDisplayValues(PlayerStatusReader playerStatus) {
		// TODO Auto-generated method stub
		
	}

}
