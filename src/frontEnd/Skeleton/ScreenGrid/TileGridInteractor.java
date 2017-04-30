package frontEnd.Skeleton.ScreenGrid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import ModificationFromUser.AttributeOwner.Modification_EditAttribute;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import frontEnd.View;
import frontEnd.Skeleton.AoTools.OnGridTileCommandCenter;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import resources.constants.numeric.NumericResourceBundle;
import resources.constants.StringResourceBundle;

/**
 * This class implemented the interaction involved with TileGridVisual.
 * Specifically, it dictates when happens when the user clicks on the grid, a tile, etc.
 * @author Tim, Miguel
 *
 */
public class TileGridInteractor {

	private Map<Tile,Node> selectedTiles;
	private Collection<ImageView> arrowSet;
	private View myView;
	private TileGridVisual myTileGridVisual;
	private State myState;
	
	private StringResourceBundle strResources = new StringResourceBundle();
	private NumericResourceBundle numResourceBundle = new NumericResourceBundle();
	
	
	public TileGridInteractor(View view,TileGridVisual visual, State state){
		myTileGridVisual = visual;
		selectedTiles = new HashMap<>();
		myView = view;
		myState = state;
		setBooleanBehavior();
	}
	
	private void setBooleanBehavior(){
		myView.getBooleanAuthorModeProperty().addListener((o, oldV, newV) ->{
			if(!newV){
				clearTileSelection();
			}
		});
	}
	
	public void setTileGridInteraction(Pane p){
		p.setFocusTraversable(true);
		p.requestFocus();
		p.setOnKeyPressed(e -> {	
			if(isAMoveDirection(e)){
				String toSend = e.getCode().toString().charAt(0) + e.getCode().toString().substring(1).toLowerCase();
				selectedTiles.keySet().forEach(t -> {
					myView.sendUserModification(new Modification_EditAttribute<String>(t,strResources.getFromAttributeNames("MoveDirection"),toSend));
				});
				clearTileSelection();
			} else if (e.getCode().equals(KeyCode.SPACE)){
				showArrowsOnTiles();
			}
		});
		p.setOnKeyReleased(e -> {
			if(e.getCode().equals(KeyCode.SPACE)){
				hideArrowsOnTiles(p);
			}
		});
	}

	private boolean isAMoveDirection(KeyEvent e) {
		return e.getCode().equals(KeyCode.LEFT)
				|| e.getCode().equals(KeyCode.RIGHT)
				|| e.getCode().equals(KeyCode.DOWN)
				|| e.getCode().equals(KeyCode.UP);
	}
	
	/**
	 * Sets the interaction for Tiles. Specifically, this determines what happens when a Tile is clicked on.
	 * If the Tile is double clicked, its CommandCenter is launched. If it is clicked while the Control Key is
	 * pushed down, it is "selected". Multiple tiles can be selected, and if one presses an arrow key, this changes
	 * the MoveDirection for all of them. If one adds a Preset Tile to one of this, this changes all of them.
	 * @param n Node representing the Tile visually on screen.
	 * @param t Tile that will be interacted with.
	 */
	public void setTileInteraction(Node n, Tile t) {
		n.setOnMouseClicked(e ->{
			myTileGridVisual.getRoot().requestFocus();
			if(e.getClickCount()==2){
				OnGridTileCommandCenter tileInteractor = new OnGridTileCommandCenter(myView, t, myState);
				tileInteractor.launch(strResources.getFromStringConstants("CommandCenterTile") ,e.getScreenX(), e.getScreenY());
			}else if(e.isControlDown() && myView.getBooleanAuthorModeProperty().get()){
				addToTileSelection(n,t);
			}else{
				clearTileSelection();
			}
		});
	}
	
	private void addToTileSelection(Node n, Tile t){
		selectedTiles.put(t, n);
		ColorAdjust color = new ColorAdjust();
		color.setBrightness(0.4);
		color.setContrast(-0.5);
		n.setEffect(color);
	}

	private void showArrowsOnTiles() {
		if(arrowSet != null && !arrowSet.isEmpty()) return;
		arrowSet = new ArrayList<ImageView>();
		myTileGridVisual.forEachTile(e -> {
			ImageView imageView = myTileGridVisual.addArrowToVisual(e);
			if(imageView!=null){
				arrowSet.add(imageView);
			}
		});
		
	}
	public void clearTileSelection() {
		selectedTiles.values().forEach(f -> f.setEffect(null));
		selectedTiles.clear();
	}
	
	private void hideArrowsOnTiles(Pane p) {
		p.getChildren().removeAll(arrowSet);
		arrowSet.clear();
	}
	
	/**
	 * Performs the Consumer on every Tile. 
	 * @param method
	 */
	public void forEachSelectedTile(Consumer<? super Tile> method){
		selectedTiles.keySet().forEach(method);
		clearTileSelection();
	}

}
