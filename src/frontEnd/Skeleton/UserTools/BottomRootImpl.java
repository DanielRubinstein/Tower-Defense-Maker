package frontEnd.Skeleton.UserTools;

import frontEnd.View;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
/**
 * This class represents everyone that is shown and supported in the bottom pane of the border pane.
 * This class is split up into two subcomponents: the bottomBar and the OptionsCorner.
 * @author Tim
 *
 */
public class BottomRootImpl implements BottomRoot{

	private BottomBar myBottomBar;
	private OptionsCorner myOptions;
	private HBox myRoot;
	
	public BottomRootImpl(View view) {
		myRoot = new HBox();
		initializeBar(view);
		initializeCorner(view);
	}

	private void initializeBar(View view){
		myBottomBar = new BottomBarImpl(view);
		HBox.setHgrow(myBottomBar.getRoot(), Priority.ALWAYS);
		myRoot.getChildren().add(myBottomBar.getRoot());
	}
	private void initializeCorner(View view){
		myOptions = new OptionsCornerImpl(view);
		myRoot.getChildren().add(myOptions.getRoot());
	}

	public Node getRoot(){
		return myRoot;
	}
	

}
