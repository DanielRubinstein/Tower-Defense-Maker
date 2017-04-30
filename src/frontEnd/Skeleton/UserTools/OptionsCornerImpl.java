package frontEnd.Skeleton.UserTools;

import ModificationFromUser.Modification_ChangeMode;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.ToggleSwitch;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import resources.constants.StringResourceBundle;
import resources.constants.numeric.NumericResourceBundle;
import resources.constants.numeric.ScreenConstants;
/**
 * This class represents the right corner of the bottomRoot (and the bottom right corner of the display). This class provides
 * functionality for the buttons the user can select and a ModeIndicator.
 * @author Tim
 *
 */
public class OptionsCornerImpl implements OptionsCorner{

	private VBox myRoot;
	private OptionsSelection userOptions;
	private ScreenConstants screenResources = new ScreenConstants();
	private NumericResourceBundle numResources = new NumericResourceBundle();
	private StringResourceBundle strResources = new StringResourceBundle();
	
	public OptionsCornerImpl(View view) {
		myRoot = new VBox();
		setDimensions();
		setUserOptions(view);
		setModeToggle(view);
		myRoot.setSpacing(numResources.getFromSizing("StandardSpacing"));
	}

	private void setDimensions() {
		myRoot.setPrefSize(screenResources.getSideWidth(), screenResources.getBottomHeight());
	}

	private void setModeToggle(View view) {
		Runnable changeMode = () -> view.sendUserModification(new Modification_ChangeMode());
		ToggleSwitch modeToggle = new ToggleSwitch(strResources.getFromStringConstants("PLAYER"), 
				strResources.getFromStringConstants("AUTHOR"), view.getBooleanAuthorModeProperty(), changeMode);
		myRoot.getChildren().add(modeToggle.getRoot());
	}

	public Node getRoot(){
		return myRoot;
	}

	private void setUserOptions(View view){
		userOptions = new OptionsSelection(view);
		userOptions.setAlignment(Pos.TOP_LEFT,Priority.ALWAYS);
		myRoot.getChildren().add(userOptions.getRoot());
	}
}
