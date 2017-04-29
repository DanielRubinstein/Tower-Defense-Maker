package frontEnd.Skeleton.ScreenGrid;

import java.lang.reflect.Method;

import ModificationFromUser.AttributeOwner.Modification_Add_PaletteToGrid;
import backEnd.BankController;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileImpl;
import frontEnd.View;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.TransferMode;

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
	private ComponentGridVisual myComponentGraph;
	private View myView;
	private ScreenHoverVisual myHover;

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
		myComponentGraph = new ComponentGridVisual(view,state);
		myView = view;
		myHover = new ScreenHoverVisual(myView.getBooleanAuthorModeProperty());
		addGridToRoot();
		addGraphToRoot();
		setDrag();
	}
	
	private void setDrag(){
		BankController bank = myView.getBankController();
		myRoot.setOnMouseMoved(e -> myHover.displayLocation(e));
		
		myRoot.setOnDragOver(e -> e.acceptTransferModes(TransferMode.ANY));
		myRoot.setOnDragDropped(e -> {
			String presetName = e.getDragboard().getString();
			AttributeOwner presetAO = bank.getPreset(presetName);
			Point2D pos = new Point2D(e.getX(), e.getY());
			for(Class<?> i : presetAO.getClass().getInterfaces()){
				
			}
			if(presetAO instanceof TileImpl){
				myTileGrid.addPreset((Tile) presetAO,pos);
			}
			myView.sendUserModification(new Modification_Add_PaletteToGrid(presetAO, pos));
		});
	}

	private void addGridToRoot() {
		myRoot = new Group();
		myRoot.setFocusTraversable(true);
		Node visualTileGrid = myTileGrid.getRoot();
		myRoot.getChildren().add(visualTileGrid);
		visualTileGrid.toBack();
		myRoot.getChildren().add(myHover.getRoot());
	}
	private void addGraphToRoot(){
		myRoot.getChildren().add(myComponentGraph.getRoot());
	}
	
	public Node getRoot() {
		return myRoot;
	}
}