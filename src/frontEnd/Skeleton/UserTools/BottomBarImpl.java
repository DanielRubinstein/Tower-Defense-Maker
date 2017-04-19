package frontEnd.Skeleton.UserTools;

import frontEnd.View;
import frontEnd.Skeleton.ScreenGrid.ScreenGrid;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
/**
 * This class represents the main rectangular chunk of the bottomRoot. This will support relevant information to the user,
 * and this can be supplement in the future by various extensions (social center perhaps).
 * @author Tim
 *
 */
public class BottomBarImpl implements BottomBar{
	
	private HBox myRoot;
	
	public BottomBarImpl(View view){
		myRoot = new HBox();
		Node sG = view.getScreenGrid();
		PalettePane pp = new PalettePane(view);
		myRoot.getChildren().add(pp.getRoot());
		HBox.setHgrow(pp.getRoot(), Priority.ALWAYS);
	}
	
	public Node getRoot(){
		return myRoot;
	}
	public void setHeight(double height){
		myRoot.setPrefHeight(height);
		myRoot.setMaxHeight(height);
	}

}
