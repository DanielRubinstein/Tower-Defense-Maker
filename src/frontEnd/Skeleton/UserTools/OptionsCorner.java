package frontEnd.Skeleton.UserTools;

import backEnd.Environment.TileImpl;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class OptionsCorner {

	private VBox myRoot;
	private SettingsView settingsDisplay;
	private OptionsSelection userOptions;
	public OptionsCorner(double size) {
		myRoot = new VBox();
		myRoot.setPrefSize(size, size);

		settingsDisplay = new SettingsViewImpl();
		addModeIndicator();
		setUserOptions(size);
		
	}
	private void setUserOptions(double size){
		userOptions = new OptionsSelection(settingsDisplay, size, size*0.3);
		userOptions.setAlignment(Pos.BOTTOM_CENTER,Priority.ALWAYS);
		myRoot.getChildren().add(userOptions.getNode());
	}
	
	private void addModeIndicator() {
		ModeIndicator mI = new ModeIndicator();
		myRoot.getChildren().add(mI.getIndicator());
	}

	private void addTileCCButton() {
		Button tileCC = new Button("Tile Command");
		tileCC.setOnAction(event -> new TileCommandCenter(new TileImpl(null, null)));
		myRoot.getChildren().add(tileCC);
	}

	public Node getRoot(){
		return myRoot;
	}
	public void setMaxHeight(double height){
		myRoot.setMaxHeight(height);
	}
	

}
