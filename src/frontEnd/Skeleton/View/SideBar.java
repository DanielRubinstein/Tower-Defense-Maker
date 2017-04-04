package frontEnd.Skeleton.View;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class SideBar{
	
	private VBox myRoot;
	
	public SideBar(){
		myRoot = new VBox();
	}
	
	public Node getRoot(){
		return myRoot;
	}
}
