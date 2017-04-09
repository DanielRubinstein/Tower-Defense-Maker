package frontEnd.Skeleton;


import backEnd.GameData.State.State;
import backEnd.GameData.State.TileGrid;
import frontEnd.ViewEditor;
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
	private Rectangle background;
	private State myState;
	private GridPane myGrid;
	private int myGridWidth;
	private int myGridHeight;
	private static final int TILE_WIDTH = 4;
	private static final int TILE_HEIGHT = 4;
	private static final String TILE_GRASS = "images/grass.jpg";
	
	public Canvas(ViewEditor view, State state, int gridWidth, int gridHeight){
		System.out.println(gridWidth);
		myState=state;
		root = new Group();
		myGridWidth=gridWidth;
		myGridHeight=gridHeight;
		//root.getChildren().add(background);
		myGrid = new GridPane();
		myGrid.setMinWidth(gridWidth);
		TileGrid grid = state.getTileGrid();
		setTileGrid(grid);
		root.getChildren().add(myGrid);
	}

	public Node getRoot() {
		return root;
	}
	
	public void setSize(double width, double height){
		myGrid.setPrefWidth(width);
		myGrid.setPrefHeight(height);
	}
	
	private void setTileGrid(TileGrid grid){
		for(int i=0;i<myGridWidth;i++){
			for(int j=0;j<myGridHeight;j++){
				Image image = new Image(getClass().getClassLoader().getResourceAsStream(TILE_GRASS));
				ImageView tileView = new ImageView(image);
				tileView.setPreserveRatio(true);
				tileView.setFitWidth(TILE_WIDTH);
				tileView.setFitHeight(TILE_HEIGHT);
				System.out.println("adding tiel ");
				myGrid.add(tileView, j, i);
			}
		}
		
		
	}
	

}
