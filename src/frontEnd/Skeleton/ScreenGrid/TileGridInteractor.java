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
public class TileGridInteractor implements GridInteractor<Tile> {

	private Map<Tile,Node> selectedTiles;
	private Collection<ImageView> arrowSet;
	private View myView;
	private TileGridVisual myTileGridVisual;
	private State myState;
	
	private StringResourceBundle strResources = new StringResourceBundle();
	
	
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
				clearSelection();
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see frontEnd.Skeleton.ScreenGrid.GridInteractor#setGridInteraction(javafx.scene.layout.Pane)
	 */
	@Override
	public void setGridInteraction(Pane p){
		p.setFocusTraversable(true);
		p.requestFocus();
		p.setOnKeyPressed(e -> {	
			if(isAMoveDirection(e)){
				String toSend = e.getCode().toString().charAt(0) + e.getCode().toString().substring(1).toLowerCase();
				selectedTiles.keySet().forEach(t -> {
					myView.sendUserModification(new Modification_EditAttribute<String>(t,strResources.getFromAttributeNames("MoveDirection"),toSend));
				});
				clearSelection();
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
	
	/* (non-Javadoc)
	 * @see frontEnd.Skeleton.ScreenGrid.GridInteractor#setInteraction(javafx.scene.Node, Tile)
	 */
	@Override
	public void setInteraction(Node n, Tile t) {
		n.setOnMouseClicked(e ->{
			myTileGridVisual.getRoot().requestFocus();
			if(e.getClickCount()==2){
				OnGridTileCommandCenter tileInteractor = new OnGridTileCommandCenter(myView, t, myState);
				tileInteractor.launch(strResources.getFromStringConstants("CommandCenterTile") ,e.getScreenX(), e.getScreenY());
			}else if(e.isControlDown() && myView.getBooleanAuthorModeProperty().get()){
				addToTileSelection(n,t);
			}else{
				clearSelection();
			}
		});
	}
	
	private void addToTileSelection(Node n, Tile t){
		selectedTiles.put(t, n);
		n.setStyle(strResources.getFromCustomCSS("SelectedEffect"));
	}

	private void showArrowsOnTiles() {
		if(arrowSet != null && !arrowSet.isEmpty()) return;
		arrowSet = new ArrayList<ImageView>();
		myTileGridVisual.forEach(e -> {
			ImageView imageView = myTileGridVisual.addArrowToVisual(e);
			if(imageView!=null){
				arrowSet.add(imageView);
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see frontEnd.Skeleton.ScreenGrid.GridInteractor#clearSelection()
	 */
	@Override
	public void clearSelection() {
		selectedTiles.values().forEach(f -> f.setStyle(""));
		selectedTiles.clear();
	}
	
	private void hideArrowsOnTiles(Pane p) {
		p.getChildren().removeAll(arrowSet);
		arrowSet.clear();
	}
	
	/* (non-Javadoc)
	 * @see frontEnd.Skeleton.ScreenGrid.GridInteractor#forEachSelected(java.util.function.Consumer)
	 */
	@Override
	public void forEachSelected(Consumer<? super Tile> method){
		selectedTiles.keySet().forEach(method);
		clearSelection();
	}

}
