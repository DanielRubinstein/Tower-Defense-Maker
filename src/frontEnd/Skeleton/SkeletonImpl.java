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
import resources.constants.StringResourceBundle;
import resources.constants.numeric.NumericResourceBundle;
import resources.constants.numeric.ScreenConstants;

/**
 * This class is an implementation of Skeleton and contains all components of the UI.
 * @author Tim
 *
 */
public class SkeletonImpl implements Skeleton{
	private ScreenConstants myScreenConstants;
	private StringResourceBundle stringResourceBundle = new StringResourceBundle();
	private BorderPane myRoot;
	private Scene myScene;
	private UserTools userTools;
	private SimpleBooleanProperty myAuthProp;
	private ScreenGrid myScreenGrid;
	private AuthorScreenGrid myTimelineTabPane;

	/**
	 * Constructs a new SkeletonImpl object.
	 */
	public SkeletonImpl(){
		myRoot = new BorderPane();
		setupScreenConstants();
		align(myScreenConstants.getWindowWidth(),myScreenConstants.getWindowHeight());
	}
	
	private void setupScreenConstants(){
		NumericResourceBundle numericResourceBundle = new NumericResourceBundle();
		myScreenConstants = numericResourceBundle.getScreenConstants();
	}
	
	/**
	 * Initializes the relevant aspects of this class using view and model.
	 * @param view
	 * @param model
	 */
	public void init(View view, ModelReader model){
		State state = model.getState();
		myAuthProp = view.getBooleanAuthorModeProperty();
		myScreenGrid = new ScreenGrid(view, state, myScreenConstants.getScreenGridWidth(), myScreenConstants.getScreenGridHeight());
		myTimelineTabPane = new AuthorScreenGrid(view, myScreenGrid);
		chooseCenter();
		myAuthProp.addListener((o, oldV, newV) -> chooseCenter());
		
		userTools = new UserTools(view);
		userTools.setBottomAndSideDimensions(myScreenConstants.getSideWidth(),myScreenConstants.getBottomHeight());
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
		stage.setMinWidth(myScreenConstants.getWindowWidth());
		stage.setMinHeight(myScreenConstants.getWindowHeight());
		stage.show();
	}
	
	private void align(Double size1, Double size2){
		myRoot.setMinWidth(size1);
		myRoot.setMinHeight(size2);
   	 	myScene = new Scene(myRoot, size1, size2);
   	 	myScene.getStylesheets().add(stringResourceBundle.getFromStringConstants("DEFAULT_CSS"));
	}
}