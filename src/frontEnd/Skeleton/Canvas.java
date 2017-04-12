package frontEnd.Skeleton;


import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.TreeMap;

import backEnd.Attribute.AttributeImpl;
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileGrid;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.FrontEndAttributeOwnerImpl;
import frontEnd.Skeleton.AoTools.TileCommandCenter;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	private static String DEFAULT_TILE;
	private TileGrid myTileGrid;
	private View myView;
	//NOTE WE HAD TO USE TREEMAP b/c otherwise we can't use an interface b/c a hascode is needed...
	private HashMap<AttributeOwner,ImageView> allTiles;// String is name of Image path
	private HashMap<AttributeOwner,String> attrToPath;

	/**
	 * Constructs a new Canvas object given the view and state.
	 * State contains all the required backend information like location of tiles and attributes of everything needed to be
	 * displayed on screen.
	 * @param view
	 * @param state 
	 */
	public Canvas(View view, State state){
		allTiles = new HashMap<>();
		attrToPath = new HashMap<>();
		myState=state;
		myView = view;
		myTileGrid=state.getTileGrid();
		state.addAsObserver(this);
		root = new Group();
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
				AttributeOwnerReader t = myTileGrid.getTileByLocation(new Point2D(i,j));
				FrontEndAttributeOwnerImpl attrOwner = new FrontEndAttributeOwnerImpl(t);

				ImageView tileView = attrOwner.getImageView();
				organizeImageView(tileView);
				allTiles.put((AttributeOwner)t,tileView);
				setTileInteraction(tileView,(Tile)t);
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
		System.out.println("updated canvas");
		
		for(AttributeOwner c : myState.getComponentGraph().getAllComponents()){
			
			String imagePath = (String)c.getAttribute("IMAGE").getValue();
			if(!allTiles.keySet().contains(c)){
				Image image = new Image(getClass().getClassLoader().getResourceAsStream(imagePath));
				ImageView imView = new ImageView(image);
				allTiles.put(c, imView);
			}
			String oldImPath = attrToPath.get(c);
			ImageView oldImView = allTiles.get(c);
			
			Point2D pos =(Point2D) c.getAttribute("Position").getValue();
					
			//if old not equal to new
			//can you just compare doubles like this? precision?
			if(!(imagePath).equals(oldImPath) || (pos.getX()!=oldImView.getX() && pos.getY()!=oldImView.getY())){
				Image image = new Image(getClass().getClassLoader().getResourceAsStream(imagePath));
				ImageView imView = new ImageView(image);
				allTiles.put(c, imView);
				attrToPath.put(c,imagePath);
				imView.setX(pos.getX());
				imView.setY(pos.getX());
				
				myGrid.getChildren().remove(oldImView);
				myGrid.getChildren().add(imView);
			}		
		}
	}
	

}