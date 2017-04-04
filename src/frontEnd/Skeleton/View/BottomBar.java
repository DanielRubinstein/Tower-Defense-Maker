package frontEnd.Skeleton.View;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class BottomBar{
	
	private HBox myRoot;
	
	public BottomBar(){
		myRoot = new HBox();
		myRoot.getChildren().add(new Button("test"));
	}
	
	public Node getRoot(){
		return myRoot;
	}
	public void setMaxHeight(double height){
		myRoot.setMaxHeight(height);
	}

}
