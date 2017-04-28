package frontEnd.Skeleton.ScreenGrid;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.ComponentImpl;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.State;
import frontEnd.View;
import frontEnd.Skeleton.AoTools.GenericCommandCenter;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

/**
 * This class is used to represent the actual game, such as the Tiles and
 * Components.
 * 
 * @author Tim
 *
 */

public class ScreenGrid implements SkeletonObject {

	private TileGridVisual myTileGrid;
	private Group myRoot;
	private ComponentGraphVisual myComponentGraph;

	/**
	 * Constructs a new ScreenGrid object given the view and state. State contains
	 * all the required backend information like location of tiles and
	 * attributes of everything needed to be displayed on screen.
	 * 
	 * @param view
	 * @param state
	 * @param screenGridHeight 
	 * @param screenGridWidth 
	 */
	public ScreenGrid(View view, State state, double screenGridWidth, double screenGridHeight) {
		myTileGrid = new TileGridVisual(view,state,screenGridWidth,screenGridHeight);
		myComponentGraph = new ComponentGraphVisual(view,state);
		addGridToRoot();
		addGraphToRoot();
	}

	private void addGridToRoot() {
		myRoot = new Group();
		Node visualTileGrid = myTileGrid.getRoot();
		myRoot.getChildren().add(myTileGrid.getRoot());
		visualTileGrid.toBack();
	}
	private void addGraphToRoot(){
		myRoot.getChildren().add(myComponentGraph.getRoot());
	}
	
	public Node getRoot() {
		return myRoot;
	}
}