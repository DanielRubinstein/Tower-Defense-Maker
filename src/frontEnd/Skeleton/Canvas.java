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

public class Canvas implements SkeletonObject, Observer {
	private Group root;
	private State myState;
	private ComponentGraph myComponentGraph;
	private GridPane myGrid;
	private int numberOfTileCols;
	private int numberOfTileRows;
	private static final int TILE_WIDTH = 40;
	private static final int TILE_HEIGHT = 40;
	private TileGrid myTileGrid;
	private View myView;
	private Set<Component> myComponents;

	/**
	 * Constructs a new Canvas object given the view and state. State contains
	 * all the required backend information like location of tiles and
	 * attributes of everything needed to be displayed on screen.
	 * 
	 * @param view
	 * @param state
	 */
	public Canvas(View view, State state) {
		myState = state;
		myView = view;
		myTileGrid = state.getTileGrid();
		myComponents = new HashSet<Component>();
		root = new Group();
		setUpGrid();
	}

	private void setUpGrid() {
		myComponentGraph = myState.getComponentGraph();
		myComponentGraph.addAsObserver(this);
		numberOfTileCols = myComponentGraph.getNumColsInGrid();
		numberOfTileRows = myComponentGraph.getNumRowsInGrid();
		myGrid = new GridPane();
		myGrid.setMinWidth(numberOfTileCols);
		myGrid.setMinHeight(numberOfTileRows);
		setTileGrid();
		root.getChildren().add(myGrid);
		myGrid.toBack();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see frontEnd.Skeleton.UserTools.SkeletonObject#getRoot()
	 */
	public Node getRoot() {
		return root;
	}

	/**
	 * Sets the size of the canvas to the given parameters. Note, however, that
	 * the user can change the size of the window while running the application.
	 * 
	 * @param width
	 * @param height
	 */
	public void setSize(double width, double height) {
		myGrid.setPrefWidth(width);
		myGrid.setPrefHeight(height);
	}

	private void setTileGrid() {
		for (int row = 0; row < numberOfTileRows; row++) {
			for (int col = 0; col < numberOfTileCols; col++) {
				//System.out.println("In Canvas, my i is: "+i+" and my j is: "+j);
				AttributeOwnerReader t = myTileGrid.getTileByLocation(new Point2D(row, col));
				FrontEndAttributeOwner attrOwner = new FrontEndAttributeOwnerImpl(t);
				ImageView tileView = attrOwner.getImageView();
				organizeImageView(tileView);
				setTileInteraction(tileView, (Tile) t);
				myGrid.add(tileView, col, row); // this is correct, when you add to gridPane it is (node, col, row)

			}
		}
	}

	private void organizeImageView(ImageView tileView) {
		tileView.setPreserveRatio(false);
		tileView.setFitWidth(TILE_WIDTH);
		tileView.setFitHeight(TILE_HEIGHT);
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
		if (o == myComponentGraph) {
			for (AttributeOwner c : myComponentGraph.getAllComponents()) {
				if (!myComponents.contains(c)) {
					FrontEndAttributeOwner frontAttr = new FrontEndAttributeOwnerImpl(c);
					frontAttr.refreshXY();
					ImageView frontImage = frontAttr.getImageView();
					frontImage.setFitWidth(TILE_WIDTH / 2);
					frontImage.setFitHeight(TILE_HEIGHT / 2);
					setCommandInteraction(frontImage, c);
					root.getChildren().add(frontImage);
				}
			}
		}

	}

}