package frontEnd.Skeleton.UserTools;

import frontEnd.View;
import frontEnd.Skeleton.SkeletonObject;
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

	private SkeletonObject myBottomBar;
	private OptionsCorner myOptions;
	private HBox myRoot;
	
	public BottomRootImpl(View view) {
		myRoot = new HBox();
		initializeBar(view);
		initializeCorner(view);
	}

	private void initializeBar(View view){
		myBottomBar = new BottomBar(view);
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
