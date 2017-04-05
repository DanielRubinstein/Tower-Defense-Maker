package frontEnd.Skeleton.UserTools;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

public class BottomRoot {

	private BottomBar myBottomBar;
	private OptionsCorner myOptions;
	private HBox myRoot;
	private SideBar mySideBar;
	public BottomRoot(double height) {
		initializeRoot(height);
		initializeBar(height);
		initializeCorner(height);
	}
	
	private void initializeRoot(double height){
		myRoot = new HBox();
		myRoot.setPrefHeight(height);
		myRoot.setBackground( new Background(new BackgroundFill(Color.BLANCHEDALMOND, CornerRadii.EMPTY, Insets.EMPTY)));
	}
	private void initializeBar(double height){
		myBottomBar = new BottomBar(height);
		HBox.setHgrow(myBottomBar.getRoot(), Priority.ALWAYS);
		myRoot.getChildren().add(myBottomBar.getRoot());
	}
	private void initializeCorner(double size){
		myOptions = new OptionsCorner(size);
		myRoot.getChildren().add(myOptions.getRoot());
	}

	public Node getRoot(){
		return myRoot;
	}
	

}
