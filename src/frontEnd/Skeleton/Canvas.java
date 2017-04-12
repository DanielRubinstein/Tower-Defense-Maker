package frontEnd.Skeleton;


import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileGrid;
import frontEnd.View;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import frontEnd.Skeleton.UserTools.TileCommandCenter;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * This class is used to represent the actual game, such as the Tiles and Components.
 * @author Tim
 *
 */

public class Canvas implements SkeletonObject, Observer{
	private StackPane root;
	private State myState;
	private Group allComponents;
	private ComponentGraph myComponentGraph;
	private GridPane myGrid;
	private int myGridWidth;
	private int myGridHeight;
	private static final int TILE_WIDTH = 40;
	private static final int TILE_HEIGHT = 40;
	private static final String IMAGE_RESOURCES = "resources/images";
	private ResourceBundle myImages;
	private static String DEFAULT_TILE;
	private TileGrid myTileGrid;
	private View myView;
	

	/**
	 * Constructs a new Canvas object given the view and state.
	 * State contains all the required backend information like location of tiles and attributes of everything needed to be
	 * displayed on screen.
	 * @param view
	 * @param state 
	 */
	public Canvas(View view, State state){
		myState=state;
		myView = view;
		myTileGrid=state.getTileGrid();
		
		root = new StackPane();
		allComponents = new Group();
		getImages();
		setUpGrid();
	}
	private void setUpGrid(){
		myComponentGraph= myState.getComponentGraph();
		myGridWidth=myComponentGraph.getGridWidth();
		myGridHeight=myComponentGraph.getGridHeight();
		myGrid = new GridPane();	
		myGrid.setMinWidth(myGridWidth);
		myGrid.setMinHeight(myGridHeight);
		setTileGrid();
		root.getChildren().add(myGrid);
		myGrid.toBack();
	}
	private void getImages(){
		myImages = ResourceBundle.getBundle(IMAGE_RESOURCES);
		DEFAULT_TILE = myImages.getString("default_tile");
	}

	/* (non-Javadoc)
	 * @see frontEnd.Skeleton.UserTools.SkeletonObject#getRoot()
	 */
	public Node getRoot() {
		return root;
	}
	
	/**
	 * Sets the size of the canvas to the given parameters. Note, however, that the user can change the size of the window
	 * while running the application. 
	 * @param width
	 * @param height
	 */
	public void setSize(double width, double height){
		myGrid.setPrefWidth(width);
		myGrid.setPrefHeight(height);
	}
	
	private void setTileGrid(){
		for(int i=0;i<myGridHeight;i++){
			for(int j=0;j<myGridWidth;j++){
				
				Image image = new Image(getClass().getClassLoader().getResourceAsStream(DEFAULT_TILE));
				ImageView tileView = new ImageView(image);
				organizeImageView(tileView);
				Tile t = myTileGrid.getTileByLocation(new Point2D(i,j));
				
				setTileInteraction(tileView,t);
				myGrid.add(tileView, j, i);
			}
		}		
	}
	private void organizeImageView(ImageView tileView){
		tileView.setPreserveRatio(false);
		tileView.setFitWidth(TILE_WIDTH);
		tileView.setFitHeight(TILE_HEIGHT);
		tileView.fitWidthProperty().bind(myGrid.widthProperty().divide(myGridWidth));
		tileView.fitHeightProperty().bind(myGrid.heightProperty().divide(myGridHeight));
	}
	
	private void setUpComponents(){
		
	}

	private void setTileInteraction(Node n, Tile t){
		TileCommandCenter tileInteractor = new TileCommandCenter(myView, t, myState);
		n.setOnMouseClicked(e-> tileInteractor.launch(e.getScreenX(),e.getScreenY()));
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
	}
	

}