package frontEnd.Skeleton;


import java.util.ResourceBundle;

import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileGrid;
import frontEnd.ViewEditor;
import frontEnd.Skeleton.UserTools.TileCommandCenter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class is used to represent the actual game.
 * This is all messed up not because how we set up the tiles depends entirely on whether we load or start a new game.
 * @author Tim
 *
 */
public class Canvas {
	private Group root;
	private State myState;
	private GridPane myGrid;
	private int myGridWidth;
	private int myGridHeight;
	private static final int TILE_WIDTH = 40;
	private static final int TILE_HEIGHT = 40;
	private static  String IMAGE_RESOURCES = "resources/images";
	private ResourceBundle myImages;
	private static String TILE_GRASS;
	private TileGrid myTileGrid;
	private ViewEditor myView;
	
	public Canvas(ViewEditor view, State state, int gridWidth, int gridHeight){
		myState=state;
		myView = view;
		myTileGrid=state.getTileGrid();
		root = new Group();
		myGridWidth=gridWidth;
		myGridHeight=gridHeight;
		getImages();
		myGrid = new GridPane();
		myGrid.setMinWidth(gridWidth);
		myGrid.setMinHeight(gridHeight);
		setTileGrid();
		root.getChildren().add(myGrid);
	}
	private void getImages(){
		myImages = ResourceBundle.getBundle(IMAGE_RESOURCES);
		TILE_GRASS = myImages.getString("grass");
		System.out.println(TILE_GRASS);
	}

	public Node getRoot() {
		return root;
	}
	
	public void setSize(double width, double height){
		myGrid.setPrefWidth(width);
		myGrid.setPrefHeight(height);
	}
	
	private void setTileGrid(){
		for(int i=0;i<myGridWidth;i++){
			for(int j=0;j<myGridHeight;j++){
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
