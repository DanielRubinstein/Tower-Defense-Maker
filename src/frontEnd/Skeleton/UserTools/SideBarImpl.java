package frontEnd.Skeleton.UserTools;

import frontEnd.View;
import frontEnd.ViewReader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SideBarImpl implements SideBar{
	
	private VBox myRoot;
	private StatusView myStatusView;
	
	
	//takes ViewEditor for now
	public SideBarImpl(ViewReader view){
		myRoot = new VBox();
		//myRoot.setBackground(new Background (new BackgroundFill(Color.CHOCOLATE, CornerRadii.EMPTY, Insets.EMPTY)));
		myRoot.getStyleClass().add("side-bar");
		createStatusView(view);
		addModeIndicator(view);
	}
	
	private void createStatusView(ViewReader view) {
		myStatusView = new StatusView(view);
		myRoot.getChildren().add(myStatusView.getRoot());
		setFacebookOptions(view);
	}
	private void setFacebookOptions(ViewReader view){
		OptionsFacebook facebookOptions = new OptionsFacebook(view,view.getAppStage());
		facebookOptions.setAlignment(Pos.TOP_LEFT,Priority.ALWAYS);
		myRoot.getChildren().add(facebookOptions.getRoot());
	}


	public Node getRoot(){
		return myRoot;
	}

	public void setWidth(double in) {
		myRoot.setPrefWidth(in);
		myRoot.setMinWidth(in+20);
		//myRoot.setMaxWidth(in);
		//myStatusView.setWidth(in);

	}
	
	private void addModeIndicator(ViewReader view) {
		ModeIndicator mI = new ModeIndicator(view);
		myRoot.getChildren().add(mI.getRoot());
	}
}
