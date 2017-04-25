package frontEnd.Skeleton.ScreenGrid;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import ModificationFromUser.AttributeOwner.Modification_EditAttribute;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileGrid;
import backEnd.GameData.State.TileImpl;
import frontEnd.View;
import frontEnd.Skeleton.AoTools.OnGridTileCommandCenter;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

public class TileGridVisual implements Observer, SkeletonObject{

	private GridPane myRoot;
	private Map<Point2D, Tile> myTiles;
	private Map<Tile, ImageView> myTileImages;
	private TileGrid observedTileGrid;
	private int numberOfTileCols;
	private int numberOfTileRows;
	private double tileWidth;
	private double tileHeight;
	private double myWidth;
	private double myHeight;
	private View myView;
	private State myState;
	private Map<Tile,Node> selectedTiles;
	
	public TileGridVisual(View view, State state, double sceneWidth, double sceneHeight){
		myView = view;
		myRoot = new GridPane();
		myTiles = new HashMap<>();
		myTileImages = new HashMap<>();
		selectedTiles = new HashMap<>();
		myState = state;
		myWidth = sceneWidth;
		myHeight = sceneHeight;
		observedTileGrid = myState.getTileGrid();
		observedTileGrid.addAsObserver(this);
		initializeGrid();
		adjustSize();
		updateTilesOnGrid();
	}
	private void initializeGrid() {
		myRoot = new GridPane();
		myRoot.setMinWidth(numberOfTileCols);
		myRoot.setMinHeight(numberOfTileRows);
		myRoot.setPrefWidth(myWidth);
		myRoot.setPrefHeight(myHeight);
		myRoot.setFocusTraversable(true);
		myRoot.requestFocus();
		myRoot.setOnKeyPressed(e -> {			
			if(e.getCode().equals(KeyCode.LEFT)||e.getCode().equals(KeyCode.RIGHT)||e.getCode().equals(KeyCode.DOWN)||e.getCode().equals(KeyCode.UP)){
				String toSend = e.getCode().toString().charAt(0) + e.getCode().toString().substring(1).toLowerCase();
				selectedTiles.keySet().forEach(t -> {
					myView.sendUserModification(new Modification_EditAttribute<String>(t,t.getAttribute("MoveDirection"),toSend));
				});
			}
		});
	}
	private void adjustSize(){
		numberOfTileCols = observedTileGrid.getNumColsInGrid();
		numberOfTileRows = observedTileGrid.getNumRowsInGrid();
		tileWidth = myWidth / numberOfTileCols;
		tileHeight = myHeight / numberOfTileRows;
		observedTileGrid.setTileSize(tileWidth, tileHeight);
	}

	private void organizeImageView(ImageView tileView) {
		tileView.setPreserveRatio(false);
		tileView.setFitWidth(tileWidth);
		tileView.setFitHeight(tileHeight);
		tileView.fitWidthProperty().bind(myRoot.widthProperty().divide(numberOfTileCols));
		tileView.fitHeightProperty().bind(myRoot.heightProperty().divide(numberOfTileRows));
	}
	private void setTileInteraction(Node n, Tile t) {
		n.setOnMouseClicked(e ->{
			myRoot.requestFocus();
			if(e.getClickCount()==2){
				OnGridTileCommandCenter tileInteractor = new OnGridTileCommandCenter(myView, t, myState);
				tileInteractor.launch("On-Screen Tile" ,e.getScreenX(), e.getScreenY());
			}else if(e.isControlDown()){
				selectedTiles.put(t, n);
				ColorAdjust color = new ColorAdjust();
				color.setBrightness(0.4);
				color.setContrast(-0.5);
				n.setEffect(color);
			}else{
				selectedTiles.values().forEach(f -> f.setEffect(null));
				selectedTiles.clear();
			}
		});

	}
	private void updateTilesOnGrid() {
		for (int row = 0; row < numberOfTileRows; row++) {
			for (int col = 0; col < numberOfTileCols; col++) {
				Tile t = observedTileGrid.getTileByGridPosition(col, row);
				Point2D pos = new Point2D(col, row);
				if(!myTiles.containsKey(pos) || !myTiles.get(pos).equals(t)){
					addTileToGrid(t, pos);
				}
			}
		}
	}
	private void updateCorrespondingGrid(TileImpl arg) {
		Point2D newTileScreenPosition = arg.getMyAttributes().<Point2D>get("Position").getValue();
		Point2D newTileGridPosition =observedTileGrid.getGridPositionFromScreenPosition(newTileScreenPosition);
		addTileToGrid(arg, newTileGridPosition);

	}
	private void addTileToGrid(Tile t, Point2D pos) {
		AttributeOwnerVisual attrOwner = new AttributeOwnerVisualImpl(t);
		ImageView tileView = attrOwner.getImageView();
		organizeImageView(tileView);
		setTileInteraction(tileView,  t);
		if(myTiles.containsKey(pos) && !myTiles.get(pos).equals(t)){
			myRoot.getChildren().remove(myTileImages.get(myTiles.get(pos)));
		}
		myRoot.add(tileView, (int) pos.getX(), (int) pos.getY()); 
		myTiles.put(pos, t);
		myTileImages.put(t, tileView);
	}
	@Override
	public void update(Observable o, Object arg) {
		updateCorrespondingGrid((TileImpl)arg);
	}
	@Override
	public Node getRoot() {
		return myRoot;
	}
}
