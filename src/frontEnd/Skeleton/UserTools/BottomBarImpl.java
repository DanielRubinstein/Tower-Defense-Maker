package frontEnd.Skeleton.UserTools;

import frontEnd.View;
import frontEnd.Skeleton.UserTools.Presets.PalettePane;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import resources.constants.numeric.ScreenConstants;
import resources.constants.StringResourceBundle;
/**
 * This class represents the main rectangular chunk of the bottomRoot. This will support relevant information to the user,
 * and this can be supplement in the future by various extensions (social center perhaps).
 * @author Tim
 *
 */
public class BottomBarImpl implements BottomBar{
	
	private HBox myRoot;
	private ScreenConstants screenResources = new ScreenConstants();
	private StringResourceBundle strResources = new StringResourceBundle();
	
	public BottomBarImpl(View view){
		myRoot = new HBox();
		setDimensions();
		setContents(view);
	}
	
	private void setContents(View view){
		PalettePane pp = new PalettePane(view);
		myRoot.getChildren().add(pp.getRoot());
		HBox.setHgrow(pp.getRoot(), Priority.ALWAYS);
		myRoot.setStyle(strResources.getFromToggle("bottomBarBorder"));
	}
	
	@Override
	public Node getRoot(){
		return myRoot;
	}
	
	private void setDimensions(){
		double height = screenResources.getBottomHeight();
		myRoot.setPrefHeight(height);
		myRoot.setMaxHeight(height);
	}


}
