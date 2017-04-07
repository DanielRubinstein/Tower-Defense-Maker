package frontEnd.Skeleton.UserTools;

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
	
	public SideBarImpl(){
		myRoot = new VBox();
		myRoot.setBackground(new Background (new BackgroundFill(Color.CHOCOLATE, CornerRadii.EMPTY, Insets.EMPTY)));
		System.out.println("set sidebar bakcground");
		
		myStatusView = new StatusView();
		myRoot.getChildren().add(myStatusView.getRoot());
	}
	
	public Node getRoot(){
		return myRoot;
	}

	public void setWidth(double in) {
		myRoot.setPrefWidth(in);
		myRoot.setMaxWidth(in);

	}
}
