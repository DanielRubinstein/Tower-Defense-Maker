package frontEnd.Skeleton.UserTools;

import backEnd.Environment.TileImpl;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class OptionsCorner {


	private VBox myRoot;
	private SettingsView settingsDisplay;
	private OptionsSelection userOptions;
	
	public OptionsCorner() {
		myRoot = new VBox();
		settingsDisplay = new SettingsViewImpl();
		addModeIndicator();
		setUserOptions();
		
	}
	private void setUserOptions(){
		userOptions = new OptionsSelection(settingsDisplay);
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
	public void setSideLength(double height){
		myRoot.setPrefSize(height, height);
		myRoot.setMaxHeight(height);
		userOptions.setSize(height, height * 0.3);
	}


}
