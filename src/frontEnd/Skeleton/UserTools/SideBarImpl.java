package frontEnd.Skeleton.UserTools;

import frontEnd.ViewReader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SideBarImpl implements SideBar{
	
	private VBox myRoot;
	private StatusView myStatusView;
	
	//takes ViewEditor for now
	public SideBarImpl(ViewReader view){
		myRoot = new VBox();
		myRoot.setBackground(new Background (new BackgroundFill(Color.CHOCOLATE, CornerRadii.EMPTY, Insets.EMPTY)));
		System.out.println("set sidebar bakcground");
		
		myStatusView = new StatusView(view);
		myRoot.getChildren().add(myStatusView.getRoot());
		
		addModeIndicator(view);
	}
	
	public Node getRoot(){
		return myRoot;
	}

	public void setWidth(double in) {
		myRoot.setPrefWidth(in);
		myRoot.setMaxWidth(in);

	}
	
	private void addModeIndicator(ViewReader view) {
		ModeIndicator mI = new ModeIndicator(view);
		myRoot.getChildren().add(mI.getRoot());
	}
}
