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
import resources.constants.NumericConstants;
import resources.constants.StringConstants;

/**
 * This class is an implementation of Skeleton and contains all components of the UI.
 * @author Tim
 *
 */
public class SkeletonImpl implements Skeleton{
	private NumericConstants numericConstants = new NumericConstants();
	private StringConstants stringConstants = new StringConstants();
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
		align(numericConstants.getWindowWidth(),numericConstants.getWindowHeight());
	}
	
	public void init(View view, ModelReader model){
		State state = model.getState();
		myAuthProp = view.getBooleanAuthorModeProperty();
		myScreenGrid = new ScreenGrid(view, state, numericConstants.getScreenGridWidth(), numericConstants.getScreenGridHeight());
		myTimelineTabPane = new AuthorScreenGrid(view, myScreenGrid);
		chooseCenter();
		myAuthProp.addListener((o, oldV, newV) -> chooseCenter());
		
		//myRoot.setCenter(myScreenGrid.getRoot());
		
		userTools = new UserTools(view);
		userTools.setBottomAndSideDimensions(numericConstants.getSideWidth(),numericConstants.getBottomHeight());
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
		stage.setMinWidth(numericConstants.getWindowWidth());
		stage.setMinHeight(numericConstants.getWindowHeight());
		stage.show();
	}
	
	public void setScene(Scene scene)
	{
		myScene = scene;
	}
	
	private void align(Double size1, Double size2){
		myRoot.setMinWidth(size1);
		myRoot.setMinHeight(size2);
   	 	myScene = new Scene(myRoot, size1, size2);
   	 	myScene.getStylesheets().add(stringConstants.getDefaultCSS());
	}
	public Node getScreenGrid(){
		return myScreenGrid.getRoot();
	}
}