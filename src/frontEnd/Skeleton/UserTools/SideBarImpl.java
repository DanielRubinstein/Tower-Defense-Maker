package frontEnd.Skeleton.UserTools;

import frontEnd.View;
import frontEnd.ViewReader;
import frontEnd.Skeleton.SkeletonObject;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import resources.constants.StringResourceBundle;
import resources.constants.numeric.ScreenConstants;

public class SideBarImpl implements SkeletonObject{
	
	private VBox myRoot;
	private ScreenConstants screenResources = new ScreenConstants();
	private StringResourceBundle strResources = new StringResourceBundle();
	
	public SideBarImpl(View view){
		initializeRoot();
		createStatusView(view);
		addModeIndicator(view);
	}
	
	private void initializeRoot() {
		myRoot = new VBox();
		myRoot.getStyleClass().add(strResources.getFromCustomCSS("SideBar"));
		myRoot.setPrefWidth(screenResources.getSideWidth());
	}

	private void createStatusView(View view) {
		StatusView myStatusView = new StatusView(view);
		myRoot.getChildren().add(myStatusView.getRoot());
		setFacebookOptions(view);
	}
	private void setFacebookOptions(ViewReader view){
		OptionsFacebook facebookOptions = new OptionsFacebook(view);
		facebookOptions.setAlignment(Pos.TOP_LEFT,Priority.ALWAYS);
		myRoot.getChildren().add(facebookOptions.getRoot());
	}


	public Node getRoot(){
		return myRoot;
	}
	
	private void addModeIndicator(ViewReader view) {
		ModeIndicator mI = new ModeIndicator(view);
		myRoot.getChildren().add(mI.getRoot());
	}
}
