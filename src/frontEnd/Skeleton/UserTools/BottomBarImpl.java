package frontEnd.Skeleton.UserTools;

import backEnd.Environment.Tile;
import backEnd.Environment.TileImpl;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
/**
 * This class represents the main rectangular chunk of the bottomRoot. This will support relevant information to the user,
 * and this can be supplement in the future by various extensions (social center perhaps).
 * @author Tim
 *
 */
public class BottomBarImpl implements BottomBar{
	
	private HBox myRoot;
	
	public BottomBarImpl(){
		myRoot = new HBox();
		System.out.println("butotn made");
		Button b = new Button("tile");
		Tile t = new TileImpl(null,null);
		TileCommandCenter tileTest = new TileCommandCenter(t);
		b.setOnMouseClicked(e-> tileTest.launch(e.getScreenX(),e.getScreenY()));
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
