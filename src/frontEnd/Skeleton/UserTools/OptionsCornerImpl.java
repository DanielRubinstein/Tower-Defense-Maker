package frontEnd.Skeleton.UserTools;

import backEnd.Environment.TileImpl;
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
	
	public OptionsCornerImpl() {
		myRoot = new VBox();
		settingsDisplay = new SettingsViewImpl();
		addModeIndicator();
		setUserOptions();	
	}

	public Node getRoot(){
		return myRoot;
	}
	public void setSideLength(double height){
		myRoot.setPrefSize(height, height);
		myRoot.setMaxHeight(height);
		userOptions.setSize(height, height * 0.3);
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




}
