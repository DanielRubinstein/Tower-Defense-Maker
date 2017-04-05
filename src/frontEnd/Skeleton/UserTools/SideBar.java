package frontEnd.Skeleton.UserTools;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SideBar{
	
	private VBox myRoot;
	
	public SideBar(){
		myRoot = new VBox();
		myRoot.setBackground(new Background (new BackgroundFill(Color.CHOCOLATE, CornerRadii.EMPTY, Insets.EMPTY)));
		System.out.println("set sidebar bakcground");
	}
	
	public Node getRoot(){
		return myRoot;
	}
	public void setSideBarWeidth(double width){
		myRoot.setPrefWidth(width);
	}
}
