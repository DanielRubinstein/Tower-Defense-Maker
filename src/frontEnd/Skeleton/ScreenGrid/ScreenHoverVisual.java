package frontEnd.Skeleton.ScreenGrid;

import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import resources.constants.StringResourceBundle;

/**
 * This class displays relevant information to be display in the ScreenGrid when the mouse hovers
 * over the screen grid. This provides additional functionality because the hoverProperty for Nodes in 
 * JavaFX is readOnly, and hence it cannot be bound to anything.
 * @author Tim
 *
 */
public class ScreenHoverVisual implements SkeletonObject{

	private Label myRoot;
	private StringResourceBundle stringResourceBundle = new StringResourceBundle();
	
	/**
	 * Initializes this class with the View. By default, this is only displayed in author mode.
	 * @param view
	 */
	public ScreenHoverVisual(BooleanProperty condition){
		myRoot = new Label();
		myRoot.visibleProperty().bind(condition);
	}
	/**
	 * Given the MouseEvent, sets the display to the location of this mouse.
	 * @param e
	 */
	public void displayLocation(MouseEvent e){
		String format = stringResourceBundle.getFromStringConstants("Position");
		myRoot.setText(String.format(format, e.getSceneX(),e.getY()));
		
	}
	
	@Override
	public Node getRoot() {
		return myRoot;
	}

}
