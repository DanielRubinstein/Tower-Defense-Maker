package frontEnd.Skeleton;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileGrid;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.FrontEndAttributeOwner;
import frontEnd.CustomJavafxNodes.FrontEndAttributeOwnerImpl;
import frontEnd.Skeleton.AoTools.ComponentCommandCenter;
import frontEnd.Skeleton.AoTools.TileCommandCenter;
import frontEnd.Skeleton.AoTools.TileCommandCenterImpl;
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
	
	private TileGrid myTileGrid;
	private ComponentGraph observedComponentGraph;
	
	private Set<Component> myComponents;
	
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
		myWidth = screenGridWidth;
		myHeight = screenGridHeight;
		extractAoCollectionsFromState();
		numberOfTileCols = myTileGrid.getNumColsInGrid();
		numberOfTileRows = myTileGrid.getNumRowsInGrid();
		tileWidth = myWidth / numberOfTileCols;
		tileHeight = myHeight / numberOfTileRows;
		myTileGrid.setTileSize(tileWidth, tileHeight);
		placeComponents();
		initializeGrid();
		placeTileGridOnScreenGrid();
		addGridToRoot();
	}

	private void placeComponents() {
		myComponents = new HashSet<>();
		updateComponentsOnGrid();
	}

	private void addGridToRoot() {
		myRoot = new Group();
		myRoot.getChildren().add(myGrid);
		myGrid.toBack();
	}

	private void extractAoCollectionsFromState() {
		myTileGrid = myState.getTileGrid();
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

	private void placeTileGridOnScreenGrid() {
		for (int row = 0; row < numberOfTileRows; row++) {
			for (int col = 0; col < numberOfTileCols; col++) {
				//System.out.println("In ScreenGrid, my i is: "+i+" and my j is: "+j);
				AttributeOwnerReader t = myTileGrid.getTileByGridPosition(col, row);
				
				// this is where the tile can learn its position
				
				FrontEndAttributeOwner attrOwner = new FrontEndAttributeOwnerImpl(t);
				ImageView tileView = attrOwner.getImageView();
				organizeImageView(tileView);
				setTileInteraction(tileView, (Tile) t);
				myGrid.add(tileView, col, row); 
				// this is correct, when you add to gridPane it is (node, col, row)
			}
		}
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
			TileCommandCenter tileInteractor = new TileCommandCenterImpl(myView, t, myState);
			tileInteractor.launch(e.getScreenX(), e.getScreenY());
		});
	}

	private void setCommandInteraction(Node n, AttributeOwner c) {
		n.setOnMouseClicked(e -> {
			ComponentCommandCenter comCenter = new ComponentCommandCenter(myView, c);
			comCenter.launch(e.getSceneX(), e.getSceneY());
		});
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o == observedComponentGraph) {
			updateComponentsOnGrid();
		}

	}

	private void updateComponentsOnGrid() {
		for (Component c : observedComponentGraph.getAllComponents()) {
			if (!myComponents.contains(c)) {
				addComponentToGrid(c);
			}
		}
	}

	private void addComponentToGrid(Component c) {
		FrontEndAttributeOwner frontAttr = new FrontEndAttributeOwnerImpl(c);
		frontAttr.refreshXY();
		ImageView frontImage = frontAttr.getImageView();
		frontImage.setFitWidth(tileWidth / 2);
		frontImage.setFitHeight(tileHeight / 2);
		setCommandInteraction(frontImage, c);
		myComponents.add(c);
		myRoot.getChildren().add(frontImage);
	}

}