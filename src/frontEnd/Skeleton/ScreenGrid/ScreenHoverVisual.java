package frontEnd.Skeleton.ScreenGrid;

import frontEnd.View;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

public class ScreenHoverVisual implements SkeletonObject{

	private View myView;
	private Node myParent;
	private Label myRoot;
	
	public ScreenHoverVisual(View view,Node parent){
		myView = view;
		myParent = parent;
		myRoot = new Label();
		myRoot.visibleProperty().bind(myView.getBooleanAuthorModeProperty());
	}
	public void display(MouseEvent e){
		String format = "(%.0f, %.0f)";
		myRoot.setText(String.format(format, e.getSceneX(),e.getY()));
		
	}
	
	@Override
	public Node getRoot() {
		return myRoot;
	}

}
