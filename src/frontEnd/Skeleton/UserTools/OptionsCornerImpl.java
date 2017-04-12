package frontEnd.Skeleton.UserTools;

import frontEnd.View;
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
	
	public OptionsCornerImpl(View view) {
		myRoot = new VBox();
		setUserOptions(view);	
	}

	public Node getRoot(){
		return myRoot;
	}

	private void setUserOptions(View view){
		userOptions = new OptionsSelection(view);
		userOptions.setAlignment(Pos.TOP_LEFT,Priority.ALWAYS);
		myRoot.getChildren().add(userOptions.getNode());
	}

	@Override
	public void setSideDimensions(double width, double height) {
		System.out.println("in options conrer implt width and height " + width + "   " +height);
		//myRoot.setPrefSize(width, height);
		///myRoot.setMaxHeight(height);
		
		userOptions.setSize(width, height);
	}


}
