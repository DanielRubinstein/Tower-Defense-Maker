package frontEnd.Skeleton.ScreenGrid;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


import ModificationFromUser.AttributeOwner.Modification_Add_PaletteToGrid;
import backEnd.BankController;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileImpl;
import frontEnd.View;
import frontEnd.Skeleton.AoTools.GenericCommandCenter;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
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
		addGridToRoot();
		addGraphToRoot();
		setDrag();
		myHover = new ScreenHoverVisual(myView,myRoot);
		myRoot.getChildren().add(myHover.getRoot());
	}
	private void setDrag(){
		BankController bank = myView.getBankController();
		myRoot.setOnMouseMoved(e -> {
			myHover.display(e);
			//myRoot.hoverProperty().addListener((o, oldV, newV) -> myHover.tryDisplay(e,o,oldV,newV));
		});
		
		myRoot.setOnDragOver(e -> e.acceptTransferModes(TransferMode.ANY));
		myRoot.setOnDragDropped(e -> {
			String presetName = e.getDragboard().getString();
			AttributeOwner presetAO = bank.getPreset(presetName);
			Point2D pos = new Point2D(e.getX(), e.getY());
			for(Class<?> i : presetAO.getClass().getInterfaces()){
				System.out.println(i.getSimpleName());
				
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
		
	}
	private void addGraphToRoot(){
		myRoot.getChildren().add(myComponentGraph.getRoot());
	}
	
	public Node getRoot() {
		return myRoot;
	}
}