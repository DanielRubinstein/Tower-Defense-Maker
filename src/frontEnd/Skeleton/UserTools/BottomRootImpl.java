package frontEnd.Skeleton.UserTools;

import frontEnd.ViewEditor;
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
	
	public BottomRootImpl(ViewEditor view) {
		myRoot = new HBox();
		initializeBar(view);
		initializeCorner(view);
	}
	/**
	 * 
	 * @param width
	 * @param height
	 */
	public void setDimensions(double width, double height){
		//myRoot.setPrefHeight(height);		
		myBottomBar.setHeight(height);
		myOptions.setSideDimensions(width, height);
	}
	private void initializeBar(ViewEditor view){
		myBottomBar = new BottomBarImpl(view);
		HBox.setHgrow(myBottomBar.getRoot(), Priority.ALWAYS);
		myRoot.getChildren().add(myBottomBar.getRoot());
	}
	private void initializeCorner(ViewEditor view){
		myOptions = new OptionsCornerImpl(view);
		myRoot.getChildren().add(myOptions.getRoot());
	}

	public Node getRoot(){
		return myRoot;
	}
	

}
