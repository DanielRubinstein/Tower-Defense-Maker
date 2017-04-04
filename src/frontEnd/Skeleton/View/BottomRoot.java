package frontEnd.Skeleton.View;

import javafx.scene.Node;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class BottomRoot {

	private BottomBar myBottomBar;
	private OptionsCorner myOptions;
	private HBox myRoot;
	public BottomRoot() {
		myRoot = new HBox();
		myBottomBar = new BottomBar();
		myOptions = new OptionsCorner();
		myRoot.setHgrow(myBottomBar.getRoot(), Priority.ALWAYS);
		myRoot.getChildren().add(myBottomBar.getRoot());
		myRoot.getChildren().add(myOptions.getRoot());
		
		myRoot.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
	}
	
	
	public void setBottomBarHeight(double height){
		myRoot.setPrefHeight(height);
		
		
	}
	
	public Node getRoot(){
		return myRoot;
	}
	

}
