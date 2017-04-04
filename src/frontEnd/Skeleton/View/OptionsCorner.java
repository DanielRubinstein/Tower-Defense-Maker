package frontEnd.Skeleton.View;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class OptionsCorner {

	private VBox myRoot;
	
	public OptionsCorner() {
		myRoot = new VBox();
		addSettingsButton();
		addPauseButton();
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
