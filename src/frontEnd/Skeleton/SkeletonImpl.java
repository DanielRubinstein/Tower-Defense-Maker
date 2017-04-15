package frontEnd.Skeleton;

import backEnd.Model;
import backEnd.Attribute.AttributeOwnerReader;
import backEnd.GameData.State.State;
import frontEnd.View;
import frontEnd.Skeleton.UserTools.UserTools;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import resources.Constants;

/**
 * This class is an implementation of Skeleton and contains all components of the UI.
 * @author Tim
 *
 */
public class SkeletonImpl implements Skeleton{
	
	private BorderPane myRoot;
	private Scene myScene;
	private UserTools userTools;

	
	
	
	private static final double MENU_HEIGHT = Constants.MENU_HEIGHT;
	private static final double MENU_WIDTH = Constants.MENU_WIDTH;
	private static final double CANVAS_HEIGHT_FACTOR = Constants.CANVAS_HEIGHT_FACTOR;
	private static final double CANVAS_WIDTH_FACTOR = Constants.CANVAS_WIDTH_FACTOR;
	private static final double BOTTOM_HEIGHT_FACTOR = Constants.BOTTOM_HEIGHT_FACTOR;
	private static final double SIDE_WIDTH_FACTOR = Constants.SIDE_WIDTH_FACTOR;
	private static final double CANVAS_WIDTH=Constants.CANVAS_WIDTH;
	private static final double CANVAS_HEIGHT=Constants.CANVAS_HEIGHT;
	

	
	public static final String DEFAULT_CSS = "/resources/css/Flatter.css";
	
	private Canvas myCanvas;

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
		myCanvas = canvas;
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
	public Node getCanvas(){
		return myCanvas.getRoot();
		
	}



}