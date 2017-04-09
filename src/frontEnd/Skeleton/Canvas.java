package frontEnd.Skeleton;


import java.util.ResourceBundle;

import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileGrid;
import frontEnd.ViewEditor;
import frontEnd.Skeleton.UserTools.TileCommandCenter;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * This class is used to represent the actual game.
 * This is all messed up not because how we set up the tiles depends entirely on whether we load or start a new game.
 * @author Tim
 *
 */
public class Canvas {
	private Group root;
	private State myState;
	private ComponentGraph myComponentGraph;
	private GridPane myGrid;
	private int myGridWidth;
	private int myGridHeight;
	private static final int TILE_WIDTH = 40;
	private static final int TILE_HEIGHT = 40;
	private static final String IMAGE_RESOURCES = "resources/images";
	private ResourceBundle myImages;
	private static String TILE_GRASS;
	private TileGrid myTileGrid;
	private ViewEditor myView;
	
	public Canvas(ViewEditor view, State state){
		myState=state;
		myView = view;
		myTileGrid=state.getTileGrid();
		root = new Group();
		getImages();
		setUpGrid();
	}
	private void setUpGrid(){
		myComponentGraph= myState.getComponentGraph();
		myGridWidth=myComponentGraph.getGridWidth();
		myGridHeight=myComponentGraph.getGridHeight();
		myGrid = new GridPane()
;		myGrid.setMinWidth(myGridWidth);
		myGrid.setMinHeight(myGridHeight);
		setTileGrid();
		root.getChildren().add(myGrid);
	}
	private void getImages(){
		myImages = ResourceBundle.getBundle(IMAGE_RESOURCES);
		TILE_GRASS = myImages.getString("grass");
	}

	public Node getRoot() {
		return root;
	}
	
	public void setSize(double width, double height){
		myGrid.setPrefWidth(width);
		myGrid.setPrefHeight(height);
	}
	
	private void setTileGrid(){
		for(int i=0;i<myGridHeight;i++){
			for(int j=0;j<myGridWidth;j++){
				Image image = new Image(getClass().getClassLoader().getResourceAsStream(TILE_GRASS));
				ImageView tileView = new ImageView(image);
				tileView.setPreserveRatio(true);
				tileView.setFitWidth(TILE_WIDTH);
				tileView.setFitHeight(TILE_HEIGHT);
				Tile t = myTileGrid.getTileByLocation(new Point2D(i,j));
				setTileInteraction(tileView,t);
				myGrid.add(tileView, j, i);
			}
		}
		
	}

	private void setTileInteraction(Node n, Tile t){
		TileCommandCenter tileInteractor = new TileCommandCenter(myView, t);
		n.setOnMouseClicked(e-> tileInteractor.launch(e.getScreenX(),e.getScreenY()));
	}
	

}
