package frontEnd.Skeleton.ScreenGrid;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import ModificationFromUser.AttributeOwner.Modification_Add_PaletteToGrid;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.SerializableObservable;
import backEnd.GameData.State.SerializableObserver;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileGrid;
import backEnd.GameData.State.TileImpl;
import frontEnd.View;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class TileGridVisual implements SerializableObserver, SkeletonObject{

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
	private TileGridInteractor myInteractor;

	
	public static final String ARROW_LOADER_DIRECTORY = "resources" + File.separator + "images" + File.separator + "Arrows" + File.separator;
	
	public TileGridVisual(View view, State state, double sceneWidth, double sceneHeight){
		myView = view;
		myRoot = new GridPane();
		myTiles = new HashMap<>();
		myTileImages = new HashMap<>();
		myInteractor = new TileGridInteractor(view,this,state);
		myState = state;
		myWidth = sceneWidth;
		myHeight = sceneHeight;
		observedTileGrid = myState.getTileGrid();
		observedTileGrid.addObserver(this);
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
		myInteractor.setTileGridInteraction(myRoot);
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

	private void updateTilesOnGrid() {
		for(Tile tile : observedTileGrid.getAllTiles()){
			addTileToGrid(tile, tile.<Point2D>getAttribute("Position").getValue());
		}
	}
	private void updateCorrespondingGrid(Tile tile) {
		
		addTileToGrid(tile, tile.<Point2D>getAttribute("Position").getValue());
	}
	
	private void addTileToGrid(Tile t, Point2D pos) {
		AttributeOwnerVisual attrOwner = new AttributeOwnerVisualImpl(t);
		ImageView tileView = attrOwner.getImageView();
		organizeImageView(tileView);
		myInteractor.setTileInteraction(tileView,  t);
		if(myTiles.containsKey(pos) && !myTiles.get(pos).equals(t)){
			myRoot.getChildren().remove(myTileImages.get(myTiles.get(pos)));
			myTileImages.remove(myTiles.get(pos));
		}
		Point2D gridPosition = getGridPosition(pos);
		myRoot.add(tileView, (int) gridPosition.getX(), (int) gridPosition.getY());
		myTiles.put(pos, t);
		myTileImages.put(t, tileView);
	}
	
	private Point2D getGridPosition(Point2D screenPosition) {
		int column = (int) Math.floor(screenPosition.getX() / tileWidth);
		int row = (int) Math.floor(screenPosition.getY() / tileHeight);
		return new Point2D(column, row);
	}
	
	@Override
	public void update(SerializableObservable so, Object obj) {
		if(obj != null){
			updateCorrespondingGrid((TileImpl)obj);
		} else {
			adjustSize();
			int counter = 0;
			for (Tile x : myTiles.values()){
				counter++;
				System.out.println("test10000" + counter);
				AttributeOwnerVisual attrOwner = new AttributeOwnerVisualImpl(x);
				ImageView tileView = attrOwner.getImageView();
				organizeImageView(tileView);
			}
		}
	}
	
	public ImageView addArrowToVisual(Tile tile){
		String moveDirection = tile.getMyAttributes().<String>get("MoveDirection").getValue();
		Point2D screenPosition = tile.getMyAttributes().<Point2D>get("Position").getValue();
		Point2D gridPosition = this.getGridPosition(screenPosition);
		if (moveDirection == null || moveDirection.equals("")){
			return null;
		}
		Image newImage = new Image(getClass().getClassLoader().getResourceAsStream(ARROW_LOADER_DIRECTORY + moveDirection + ".png"));
		ImageView imageView = new ImageView(newImage);

		myRoot.add(imageView, (int) gridPosition.getX(), (int) gridPosition.getY());
		imageView.setFitWidth(30);
		imageView.setFitHeight(30);
		return imageView;
	}
	
	void forEachTile(Consumer<? super Tile> method){
		myTileImages.keySet().forEach(method);
	}

	@Override
	public Node getRoot() {
		return myRoot;
	}

	public void addPreset(Tile presetAO, Point2D pos) {
		myInteractor.forEachSelectedTile(e -> {
			myView.sendUserModification(new Modification_Add_PaletteToGrid(presetAO, e.<Point2D>getAttribute("Position").getValue()));
		});
		myView.sendUserModification(new Modification_Add_PaletteToGrid(presetAO, pos));
	}
}
