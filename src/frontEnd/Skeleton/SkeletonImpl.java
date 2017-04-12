package frontEnd.Skeleton;

import backEnd.Model;
import backEnd.GameData.State.State;
import backEnd.GameData.State.StateImpl;
import frontEnd.View;
import frontEnd.Skeleton.UserTools.UserTools;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * This class is an implementation of Skeleton and contains all components of the UI.
 * @author Tim
 *
 */
public class SkeletonImpl implements Skeleton{
	
	private BorderPane myRoot;
	private Scene myScene;
	private UserTools userTools;

	public static final double MENU_HEIGHT = 650d;
	public static final double MENU_WIDTH = 750d;
	private static final double CANVAS_HEIGHT_FACTOR = 0.7;
	private static final double CANVAS_WIDTH_FACTOR = 0.8;
	private static final double BOTTOM_HEIGHT_FACTOR = 1 - CANVAS_HEIGHT_FACTOR;
	private static final double SIDE_WIDTH_FACTOR = 1 - CANVAS_WIDTH_FACTOR;
	private static final double CANVAS_WIDTH=MENU_WIDTH * CANVAS_WIDTH_FACTOR;
	private static final double CANVAS_HEIGHT=MENU_HEIGHT * CANVAS_HEIGHT_FACTOR;
	
	public static final String DEFAULT_CSS = "/resources/css/Flatter.css";
	

	/**
	 * Constructs a new SkeletonImpl object using view and model, which are used to get important information about the State.
	 * @param view
	 * @param model
	 */
	public SkeletonImpl(View view, Model model){
		myRoot = new BorderPane();
		align(MENU_WIDTH,MENU_HEIGHT);
		State myState = model.getState();//new StateImpl(GRID_WIDTH,GRID_HEIGHT,(int)CANVAS_WIDTH,(int)CANVAS_HEIGHT);
		Canvas canvas = new Canvas(view,myState);
		canvas.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);

		myRoot.setCenter(canvas.getRoot());
		userTools = new UserTools(view);
		userTools.setBottomAndSideDimensions(MENU_WIDTH*SIDE_WIDTH_FACTOR,MENU_HEIGHT*BOTTOM_HEIGHT_FACTOR);
		myRoot.setRight(userTools.getSidePane());
		myRoot.setBottom(userTools.getBottomPane());	
	}
	

	/* (non-Javadoc)
	 * @see frontEnd.Skeleton.Skeleton#display(javafx.stage.Stage)
	 */
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