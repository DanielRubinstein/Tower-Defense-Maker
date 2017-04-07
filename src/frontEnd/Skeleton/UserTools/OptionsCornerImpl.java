package frontEnd.Skeleton.UserTools;

import backEnd.State.TileImpl;
import backEnd.State.Tile;
import frontEnd.ViewEditor;
import frontEnd.ViewReader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
/**
 * This class represents the right corner of the bottomRoot (and the bottom right corner of the display). This class provides
 * functionality for the buttons the user can select and a ModeIndicator.
 * @author Tim
 *
 */
public class OptionsCornerImpl implements OptionsCorner{

	private VBox myRoot;
	private SettingsView settingsDisplay;
	private OptionsSelection userOptions;
	
	public OptionsCornerImpl(ViewEditor view) {
		myRoot = new VBox();
		setUserOptions(view);	
		testTileButton(view);
	}

	public Node getRoot(){
		return myRoot;
	}
	public void setSideLength(double height){
		myRoot.setPrefSize(height, height);
		myRoot.setMaxHeight(height);
		userOptions.setSize(height, height * 0.3);
	}
	private void setUserOptions(ViewEditor view){
		userOptions = new OptionsSelection(view);
		userOptions.setAlignment(Pos.BOTTOM_CENTER,Priority.ALWAYS);
		myRoot.getChildren().add(userOptions.getNode());
	}
	
	private void testTileButton(ViewEditor view){
		Button b = new Button("tile");
		Tile t = new TileImpl(null, null, null);
		TileCommandCenter tileTest = new TileCommandCenter(view, t);
		b.setOnMouseClicked(e-> tileTest.launch(e.getScreenX(),e.getScreenY()));
		myRoot.getChildren().add(b);
	}

}
