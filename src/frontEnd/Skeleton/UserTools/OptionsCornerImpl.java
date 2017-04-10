package frontEnd.Skeleton.UserTools;

import frontEnd.ViewEditor;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
	private OptionsSelection userOptions;
	
	public OptionsCornerImpl(ViewEditor view) {
		myRoot = new VBox();
		setUserOptions(view);	
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


}
