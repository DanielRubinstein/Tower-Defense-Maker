package frontEnd.Skeleton;

import backEnd.State.State;
import backEnd.State.StateImpl;
import backEnd.State.TileGrid;
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
	

	private static final String DEFAULT_CSS = "/resources/css/vooga.css";
	
	
	
	public SkeletonImpl(ViewEditor view){
		myRoot = new BorderPane();
		align(MENU_WIDTH,MENU_HEIGHT);
		State myState = new StateImpl((int)(MENU_WIDTH*CANVAS_WIDTH_FACTOR),(int)(MENU_WIDTH*CANVAS_WIDTH_FACTOR),4,4);
		Canvas canvas = new Canvas(view,myState);
		canvas.setSize(MENU_WIDTH*CANVAS_HEIGHT_FACTOR, MENU_WIDTH*CANVAS_WIDTH_FACTOR);
		TileGrid t = myState.getTileGrid();
		myRoot.setCenter(canvas.getRoot());
		
		userTools = new UserTools(view);
		userTools.setBottomHeight(MENU_WIDTH*BOTTOM_HEIGHT_FACTOR);
		userTools.setSideWidth(MENU_WIDTH*SIDE_WIDTH_FACTOR);
		//userTools.setPaneThickness(MENU_WIDTH*BOTTOM_HEIGHT_FACTOR);
		myRoot.setRight(userTools.getSidePane());
		myRoot.setBottom(userTools.getBottomPane());	
	}
	

	public void display(Stage stage) {

		stage.setScene(myScene);
		stage.show();
	}
	private void align(Double size1, Double size2){
		myRoot.setMinWidth(size1);
		myRoot.setMinHeight(size2);
   	 	myScene = new Scene(myRoot, size1, size2);
   	 	myScene.getStylesheets().add(DEFAULT_CSS);
	}



}
