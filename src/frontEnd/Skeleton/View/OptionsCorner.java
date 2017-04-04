package frontEnd.Skeleton.View;

import backEnd.Environment.TileImpl;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class OptionsCorner {

	private VBox myRoot;
	
	public OptionsCorner() {
		myRoot = new VBox();
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
	
	private void addSettingsButton(){
		Button settings = new Button("Settings");
		myRoot.getChildren().add(settings);
	}
	private void addPauseButton(){
		Button pause = new Button("Pause");
		myRoot.getChildren().add(pause);
	}

}
