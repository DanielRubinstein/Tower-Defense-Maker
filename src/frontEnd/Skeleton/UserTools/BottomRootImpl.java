package frontEnd.Skeleton.UserTools;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
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
	public BottomRootImpl() {
		myRoot = new HBox();
		initializeBar();
		initializeCorner();
	}
	
	public void setHeight(double height){
		myRoot.setPrefHeight(height);
		myRoot.setBackground( new Background(new BackgroundFill(Color.BLANCHEDALMOND, CornerRadii.EMPTY, Insets.EMPTY)));
		
		myBottomBar.setHeight(height);
		myOptions.setSideLength(height);
	}
	private void initializeBar(){
		myBottomBar = new BottomBarImpl();
		HBox.setHgrow(myBottomBar.getRoot(), Priority.ALWAYS);
		myRoot.getChildren().add(myBottomBar.getRoot());
	}
	private void initializeCorner(){
		myOptions = new OptionsCornerImpl();
		myRoot.getChildren().add(myOptions.getRoot());
	}

	public Node getRoot(){
		return myRoot;
	}
	

}
