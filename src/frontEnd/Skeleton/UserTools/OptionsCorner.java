package frontEnd.Skeleton.UserTools;

import backEnd.Environment.TileImpl;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OptionsCorner {

	private HBox myRoot;
	private static final String SETTINGS_IMAGE = "images/Settings.jpg";
	private SettingsView settingsView;
	public OptionsCorner() {
		myRoot = new HBox();
		addModeIndicator();
		addSettingsButton();
		addPauseButton();
		addTileCCButton();
		
	}
	
	private void addModeIndicator() {
		ModeIndicator mI = new ModeIndicator();
		myRoot.getChildren().add(mI.getIndicator());
	}

	private void addTileCCButton() {
		Button tileCC = new Button("Tile Command Center Test");
		tileCC.setOnAction(event -> new TileCommandCenter(new TileImpl(null, null)));
		myRoot.getChildren().add(tileCC);
	}

	public Node getRoot(){
		return myRoot;
	}
	public void setMaxHeight(double height){
		myRoot.setMaxHeight(height);
	}
	
	private void addSettingsButton(){
		settingsView = new SettingsViewImpl();
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(SETTINGS_IMAGE));
		ImageView settingsImage = new ImageView(image);

		Button settings = new Button();
		settingsImage.setPreserveRatio(true);
		settingsImage.setFitHeight(50);
		settings.setGraphic(settingsImage); 
		System.out.println();
		settings.setOnAction(e -> settingsView.launchSettings());
		myRoot.getChildren().add(settings);

	}
	private void addPauseButton(){
		Button pause = new Button("Pause");
		myRoot.getChildren().add(pause);
		//myRoot.getChildren().add(new Button("test"));
	}

}
