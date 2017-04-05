package frontEnd.Skeleton.UserTools;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

public class BottomBar{
	
	private HBox myRoot;
	
	public BottomBar(){
		myRoot = new HBox();
		Button b = new Button("test");
		b.setTooltip(new Tooltip("test"));
		myRoot.getChildren().add(b);
	}
	
	public Node getRoot(){
		return myRoot;
	}
	public void setHeight(double height){
		myRoot.setPrefHeight(height);
		myRoot.setMaxHeight(height);
	}

}
