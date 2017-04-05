package frontEnd.Skeleton.UserTools;

import backEnd.Environment.Tile;
import backEnd.Environment.TileImpl;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

public class BottomBar{
	
	private HBox myRoot;
	
	public BottomBar(){
		myRoot = new HBox();
		Button b = new Button("tile test");
		
		Tile t = new TileImpl(null,null);
		TileCommandCenter tileTest = new TileCommandCenter(t);
		b.setOnMouseClicked(e-> tileTest.launch(e.getScreenX(),e.getScreenY()));
		myRoot.getChildren().add(b);
		//myRoot.getChildren().add(tileTest.getNode());
	}
	
	public Node getRoot(){
		return myRoot;
	}
	public void setHeight(double height){
		myRoot.setPrefHeight(height);
		myRoot.setMaxHeight(height);
	}

}
