package frontEnd.Skeleton.View;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class OptionsCorner {

	private VBox myRoot;
	private static final String SETTINGS_IMAGE = "images/Settings.jpg";
	private SettingsView settingsDisplay;
	public OptionsCorner() {
		myRoot = new VBox();
		//change this
		settingsDisplay = new SettingsViewImpl();
		addSettingsButton();
		addPauseButton();
	}
	
	public Node getRoot(){
		return myRoot;
	}
	public void setMaxHeight(double height){
		myRoot.setMaxHeight(height);
	}
	
	private void addSettingsButton(){
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(SETTINGS_IMAGE));
		ImageView settingsImage = new ImageView(image);

		Button settings = new Button();
		settingsImage.setPreserveRatio(true);
		settingsImage.setFitHeight(50);
		settings.setGraphic(settingsImage); 
		settings.setOnAction(e -> settingsDisplay.launchSettings());
		myRoot.getChildren().add(settings);

	}
	private void addPauseButton(){
		Button pause = new Button("Pause");
		myRoot.getChildren().add(pause);
		//myRoot.getChildren().add(new Button("test"));
	}

}
