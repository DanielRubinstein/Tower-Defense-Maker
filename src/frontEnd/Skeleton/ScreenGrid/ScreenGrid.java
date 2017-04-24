package frontEnd.Skeleton.ScreenGrid;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.ComponentGraphImpl;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileGrid;
import frontEnd.View;
import frontEnd.Skeleton.AoTools.GenericCommandCenter;
import frontEnd.Skeleton.AoTools.OnGridTileCommandCenter;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * This class is used to represent the actual game, such as the Tiles and
 * Components.
 * 
 * @author Tim
 *
 */

public class ScreenGrid implements SkeletonObject, Observer {
	private View myView;
	private State myState;
	
	private Group myRoot;
	private GridPane myGrid;
	
	private TileGrid observedTileGrid;
	private ComponentGraph observedComponentGraph;
	
	private Set<Component> myComponents;
	private Map<Component, ImageView> myComponentImages;
	private Map<Point2D, Tile> myTiles;
	private Map<Tile, ImageView> myTileImages;
	
	private double myWidth;
	private double myHeight;
	private int numberOfTileCols;
	private int numberOfTileRows;
	private double tileWidth;
	private double tileHeight;
	
	
	

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
		myView = view;
		myState = state;
		extractAoCollectionsFromState();

		adjustSize(screenGridWidth,screenGridHeight);
		initializeGrid();
		myTiles = new HashMap<>();
		myTileImages = new HashMap<>();
		updateTilesOnGrid();
		addGridToRoot();
		placeComponents();
	}
	
	private void adjustSize(double screenGridWidth, double screenGridHeight){
		myWidth = screenGridWidth;
		myHeight = screenGridHeight;
		numberOfTileCols = observedTileGrid.getNumColsInGrid();
		numberOfTileRows = observedTileGrid.getNumRowsInGrid();
		tileWidth = myWidth / numberOfTileCols;
		tileHeight = myHeight / numberOfTileRows;
		observedTileGrid.setTileSize(tileWidth, tileHeight);
	}

	private void placeComponents() {
		myComponents = new HashSet<>();
		myComponentImages = new HashMap<>();
		updateComponentsOnGrid();
	}

	private void addGridToRoot() {
		myRoot = new Group();
		myRoot.getChildren().add(myGrid);
		myGrid.toBack();
	}

	private void extractAoCollectionsFromState() {
		observedTileGrid = myState.getTileGrid();
		observedTileGrid.addAsObserver(this);
		observedComponentGraph = myState.getComponentGraph();
		observedComponentGraph.addAsObserver(this);
	}

	private void initializeGrid() {
		myGrid = new GridPane();
		myGrid.setMinWidth(numberOfTileCols);
		myGrid.setMinHeight(numberOfTileRows);
		myGrid.setPrefWidth(myWidth);
		myGrid.setPrefHeight(myHeight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see frontEnd.Skeleton.UserTools.SkeletonObject#getRoot()
	 */
	public Node getRoot() {
		return myRoot;
	}

	private void organizeImageView(ImageView tileView) {
		tileView.setPreserveRatio(false);
		tileView.setFitWidth(tileWidth);
		tileView.setFitHeight(tileHeight);
		tileView.fitWidthProperty().bind(myGrid.widthProperty().divide(numberOfTileCols));
		tileView.fitHeightProperty().bind(myGrid.heightProperty().divide(numberOfTileRows));
	}

	private void setTileInteraction(Node n, Tile t) {
		n.setOnMouseClicked(e -> {
			OnGridTileCommandCenter tileInteractor = new OnGridTileCommandCenter(myView, t, myState);
			tileInteractor.launch("On-Screen Tile" ,e.getScreenX(), e.getScreenY());
		});
	}

	private void setCommandInteraction(Node n, AttributeOwner c) {
		n.setOnMouseClicked(e -> {
			GenericCommandCenter comCenter = new GenericCommandCenter(myView, c);
			comCenter.launch("On-Screen Component", e.getSceneX(), e.getSceneY());
		});
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o == observedComponentGraph) {
			observedComponentGraph=(ComponentGraphImpl) o;
			updateComponentsOnGrid();
		} else if (o == observedTileGrid){
			updateTilesOnGrid();
		}
	}

	private void updateTilesOnGrid() {
		for (int row = 0; row < numberOfTileRows; row++) {
			for (int col = 0; col < numberOfTileCols; col++) {
				Tile t = observedTileGrid.getTileByGridPosition(col, row);
				Point2D pos = new Point2D(col, row);
				
				if(!myTiles.containsKey(pos) || !myTiles.get(pos).equals(t)){
					FrontEndAttributeOwner attrOwner = new FrontEndAttributeOwnerImpl(t);
					ImageView tileView = attrOwner.getImageView();
					organizeImageView(tileView);
					setTileInteraction(tileView,  t);
					if(myTiles.containsKey(pos) && !myTiles.get(pos).equals(t)){
						myGrid.getChildren().remove(myTileImages.get(myTiles.get(pos)));
					}
					myGrid.add(tileView, col, row); 
					// this is correct, when you add to gridPane it is (node, col, row)
					myTiles.put(pos, t);
					myTileImages.put(t, tileView);
				}
			}
		}
	}

	private void updateComponentsOnGrid() {	
		for (Component c : observedComponentGraph.getAllComponents()) {
			if (!myComponents.contains(c)) {
				//System.out.println("in screenGrid, updateComponentsOnGrid() got called");
				addComponentToGrid(c);
			}
		}
		Set<Component> myComponentsCopy=new HashSet<>(myComponents);
		for(Component c : myComponentsCopy){
			if(!observedComponentGraph.getAllComponents().contains(c)){
				removeComponentFromGrid(c);
			}
		}
	}
	
	private void removeComponentFromGrid(Component c) {
		myComponents.remove(c);
		myRoot.getChildren().remove(myComponentImages.get(c));
		myComponentImages.remove(c);
	}

	private void addComponentToGrid(Component c) {
		FrontEndAttributeOwner frontAttr = new FrontEndAttributeOwnerImpl(c);
		frontAttr.refreshXY();
		
		ImageView frontImage = frontAttr.getImageView();
		frontImage.setFitWidth(tileWidth / 2);
		frontImage.setFitHeight(tileHeight / 2);
		setCommandInteraction(frontImage, c);
		myComponents.add(c);
		myComponentImages.put(c, frontImage);
		myRoot.getChildren().add(frontImage);
	}

}