package frontEnd.Skeleton;

import backEnd.ModelReader;
import backEnd.GameData.State.State;
import frontEnd.View;
import frontEnd.Skeleton.ScreenGrid.ScreenGrid;
import frontEnd.Skeleton.SpawnTimelineVisualization.AuthorScreenGrid;
import frontEnd.Skeleton.UserTools.UserTools;
import javafx.beans.property.SimpleBooleanProperty;
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
	private SimpleBooleanProperty myAuthProp;
	private ScreenGrid myScreenGrid;
	private AuthorScreenGrid myTimelineTabPane;

	/**
	 * Constructs a new SkeletonImpl object using view and model, which are used to get important information about the State.
	 * @param view
	 * @param model
	 */
	public SkeletonImpl(){
		myRoot = new BorderPane();
		align(Constants.WINDOW_WIDTH,Constants.WINDOW_HEIGHT);
	}
	
	public void init(View view, ModelReader model){
		State state = model.getState();
		myAuthProp = view.getBooleanAuthorModeProperty();
		myScreenGrid = new ScreenGrid(view, state, Constants.SCREEN_GRID_WIDTH, Constants.SCREEN_GRID_HEIGHT);
		myTimelineTabPane = new AuthorScreenGrid(view, myScreenGrid);
		chooseCenter();
		myAuthProp.addListener((o, oldV, newV) -> chooseCenter());
		
		//myRoot.setCenter(myScreenGrid.getRoot());
		
		userTools = new UserTools(view);
		userTools.setBottomAndSideDimensions(Constants.SIDE_WIDTH,Constants.BOTTOM_HEIGHT);
		myRoot.setRight(userTools.getSidePane());
		myRoot.setBottom(userTools.getBottomPane());
	}
	

	private void chooseCenter() {
		if(myAuthProp.getValue()){
			myRoot.setCenter(myTimelineTabPane.getRoot());
		} else {
			myRoot.setCenter(myScreenGrid.getRoot());
		}
	}

	/* (non-Javadoc)
	 * @see frontEnd.Skeleton.Skeleton#display(javafx.stage.Stage)
	 */
	public void display(Stage stage) {
		stage.setScene(myScene);
		stage.setMinWidth(Constants.WINDOW_WIDTH);
		stage.setMinHeight(Constants.WINDOW_HEIGHT);
		stage.show();
	}
	
	private void align(Double size1, Double size2){
		myRoot.setMinWidth(size1);
		myRoot.setMinHeight(size2);
   	 	myScene = new Scene(myRoot, size1, size2);
   	 	myScene.getStylesheets().add(Constants.DEFAULT_CSS);
	}
	public Node getScreenGrid(){
		return myScreenGrid.getRoot();
	}
}