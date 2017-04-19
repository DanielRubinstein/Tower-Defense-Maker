package frontEnd.Skeleton.UserTools;

import ModificationFromUser.Modification_ChangeMode;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.ToggleSwitch;
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
	private OptionsFacebook facebookOptions;
	
	public OptionsCornerImpl(View view) {
		myRoot = new VBox();
		setUserOptions(view);
		setModeToggle(view);
		setFacebookOptions(view);
		myRoot.setSpacing(10d);
	}

	private void setModeToggle(View view) {
		//adding player/godmode switch
		Runnable changeMode = () -> view.sendUserModification(new Modification_ChangeMode());
		ToggleSwitch modeToggle = new ToggleSwitch("Player", "Author", view.getBooleanAuthorModeProperty(), changeMode);
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
	private void setFacebookOptions(View view){
		facebookOptions = new OptionsFacebook(view,view.getAppStage());
		facebookOptions.setAlignment(Pos.TOP_LEFT,Priority.ALWAYS);
		myRoot.getChildren().add(facebookOptions.getRoot());
	}

	@Override
	public void setSideDimensions(double width, double height) {
		//myRoot.setPrefSize(width, height);
		///myRoot.setMaxHeight(height);
		
		userOptions.setSize(width, height/4);
		facebookOptions.setSize(width,height/4);
	}


}
