package frontEnd.Skeleton;

import backEnd.GameData.State.State;
import backEnd.GameData.State.StateImpl;
import backEnd.GameData.State.TileGrid;
import frontEnd.ViewEditor;
import frontEnd.Skeleton.UserTools.UserTools;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SkeletonImpl implements Skeleton{
	
	private BorderPane myRoot;
	private Scene myScene;
	private UserTools userTools;

	public static final double MENU_HEIGHT = 500d;
	public static final double MENU_WIDTH = 500d;
	private static final double CANVAS_HEIGHT_FACTOR = 0.8;
	private static final double CANVAS_WIDTH_FACTOR = 0.8;
	private static final double BOTTOM_HEIGHT_FACTOR = 0.2;
	private static final double SIDE_WIDTH_FACTOR = 0.2;
	private static final int TILE_WIDTH = 40;
	private static final int TILE_HEIGHT = 40;
	private static final double CANVAS_WIDTH=MENU_WIDTH * CANVAS_WIDTH_FACTOR;
	private static final double CANVAS_HEIGHT=MENU_HEIGHT * CANVAS_HEIGHT_FACTOR;
	private static final int GRID_WIDTH= (int) (CANVAS_WIDTH/TILE_WIDTH);
	private static final int GRID_HEIGHT= (int) (CANVAS_HEIGHT/TILE_HEIGHT);
	
	//public static final String DEFAULT_CSS = "/resources/css/vooga.css";
	public static final String DEFAULT_CSS = "/resources/css/Flatter.css";
	
	public SkeletonImpl(ViewEditor view){
		myRoot = new BorderPane();
		align(MENU_WIDTH,MENU_HEIGHT);
		State myState = new StateImpl(GRID_WIDTH,GRID_HEIGHT,TILE_WIDTH,TILE_HEIGHT);
		Canvas canvas = new Canvas(view,myState, GRID_WIDTH,GRID_HEIGHT);
		canvas.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);

		myRoot.setCenter(canvas.getRoot());
		userTools = new UserTools(view);
		userTools.setBottomHeight(MENU_HEIGHT*BOTTOM_HEIGHT_FACTOR);
		userTools.setSideWidth(MENU_WIDTH*SIDE_WIDTH_FACTOR);
		//userTools.setPaneThickness(MENU_WIDTH*BOTTOM_HEIGHT_FACTOR);
		myRoot.setRight(userTools.getSidePane());
		myRoot.setBottom(userTools.getBottomPane());	
	}
	

	public void display(Stage stage) {
		stage.setScene(myScene);
		stage.setMinWidth(MENU_WIDTH);
		stage.setMinHeight(MENU_HEIGHT);
		stage.show();
	}
	
	private void align(Double size1, Double size2){
		myRoot.setMinWidth(size1);
		myRoot.setMinHeight(size2);
   	 	myScene = new Scene(myRoot, size1, size2);
   	 	myScene.getStylesheets().add(DEFAULT_CSS);
	}



}
