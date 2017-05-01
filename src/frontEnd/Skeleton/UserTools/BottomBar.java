package frontEnd.Skeleton.UserTools;

import frontEnd.View;
import frontEnd.Skeleton.UserTools.Presets.PalettePane;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import resources.constants.numeric.ScreenConstants;
import resources.constants.StringResourceBundle;
/**
 * This class represents the main rectangular chunk of the bottomRoot and excludes the right corner of BottomRoot.
 * @author Tim
 *
 */
public class BottomBar implements SkeletonObject{
	
	private HBox myRoot;
	private ScreenConstants screenResources = new ScreenConstants();
	private StringResourceBundle strResources = new StringResourceBundle();
	
	public BottomBar(View view){
		myRoot = new HBox();
		setDimensions();
		setContents(view);
	}
	
	private void setContents(View view){
		PalettePane pp = new PalettePane(view);
		myRoot.getChildren().add(pp.getRoot());
		HBox.setHgrow(pp.getRoot(), Priority.ALWAYS);
		myRoot.setStyle(strResources.getFromCustomCSS("bottomBarBorder"));
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
