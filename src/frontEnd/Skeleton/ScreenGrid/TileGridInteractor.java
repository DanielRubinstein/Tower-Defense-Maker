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
import javafx.scene.layout.Pane;

/**
 * This class implemented the interaction involved with TileGridVisual.
 * Specifically, it dictates when happens when the user clicks on the grid, a tile, etc.
 * @author Tim
 *
 */
public class TileGridInteractor {

	private Map<Tile,Node> selectedTiles;
	private Collection<ImageView> arrowSet;
	private View myView;
	private TileGridVisual myTileGridVisual;
	private State myState;
	
	public TileGridInteractor(View view,TileGridVisual visual, State state){
		myTileGridVisual = visual;
		selectedTiles = new HashMap<>();
		myView = view;
		myState = state;
	}
	
	public void setTileGridInteraction(Pane p){
		p.setFocusTraversable(true);
		p.requestFocus();
		p.setOnKeyPressed(e -> {	
			if(e.getCode().equals(KeyCode.LEFT)||e.getCode().equals(KeyCode.RIGHT)||e.getCode().equals(KeyCode.DOWN)||e.getCode().equals(KeyCode.UP)){
				String toSend = e.getCode().toString().charAt(0) + e.getCode().toString().substring(1).toLowerCase();
				selectedTiles.keySet().forEach(t -> {
					myView.sendUserModification(new Modification_EditAttribute<String>(t,"MoveDirection",toSend));
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
	
	public void setTileInteraction(Node n, Tile t) {
		n.setOnMouseClicked(e ->{
			myTileGridVisual.getRoot().requestFocus();
			if(e.getClickCount()==2){
				OnGridTileCommandCenter tileInteractor = new OnGridTileCommandCenter(myView, t, myState);
				tileInteractor.launch("On-Screen Tile" ,e.getScreenX(), e.getScreenY());
			}else if(e.isControlDown()){
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
	
	public void forEachSelectedTile(Consumer<? super Tile> method){
		selectedTiles.keySet().forEach(method);
		clearTileSelection();
	}
	
	
	
	
}
