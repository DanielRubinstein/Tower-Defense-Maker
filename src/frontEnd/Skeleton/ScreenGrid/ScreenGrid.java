package frontEnd.Skeleton.ScreenGrid;

import java.lang.reflect.Method;

import backEnd.Attribute.AttributeOwnerReader;
import backEnd.Bank.BankControllerReader;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import frontEnd.View;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.TransferMode;

/**
 * This class is used to represent the actual game, such as the Tiles and Components.
 * @author Tim
 *
 */

public class ScreenGrid implements SkeletonObject {

	private GridVisual<Tile> myTileGrid;
	private Group myRoot;
	private GridVisual<Component> myComponentGrid;
	private View myView;
	private ScreenHoverVisual myHover;
	private GridAdder myAdder;

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
		myComponentGrid = new ComponentGridVisual(view,state);
		myView = view;
		myHover = new ScreenHoverVisual(myView.getBooleanAuthorModeProperty());
		myAdder = new GridAdder(myTileGrid, myComponentGrid);
		addGridToRoot();
		addGraphToRoot();
		setDrag();
	}
	
	/**
	 * Sets what happens when something is dragged over the root or when the mouse moves. 
	 * Uses reflection to find the appropriate method to call in GridAdder.
	 */
	private void setDrag(){
		BankControllerReader bank = myView.getBankControllerReader();
		String methodName = "addToGrid";
		myRoot.setOnMouseMoved(e -> myHover.displayLocation(e));
		
		myRoot.setOnDragOver(e -> e.acceptTransferModes(TransferMode.ANY));
		myRoot.setOnDragDropped(e -> {
			String presetName = e.getDragboard().getString();
			AttributeOwnerReader presetAO = bank.getPreset(presetName);
			Point2D pos = new Point2D(e.getX(), e.getY());
			for(Class<?> inter : presetAO.getClass().getInterfaces()){
				try {
					Method toCall = myAdder.getClass().getDeclaredMethod(methodName, inter, Point2D.class);
					toCall.invoke(myAdder, presetAO, pos);
					break;
				} catch (Exception e1) {
					continue;
				}
			}
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
		myRoot.getChildren().add(myComponentGrid.getRoot());
	}
	
	public Node getRoot() {
		return myRoot;
	}
}