package frontEnd.CustomJavafxNodes;

import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import resources.constants.numeric.NumericResourceBundle;

public class ButtonScrollPane implements SkeletonObject {
	private NumericResourceBundle numericResourceBundle = new NumericResourceBundle();
	private ScrollPane myRoot;
	private VBox myInnerRoot;

	public ButtonScrollPane(){
		myRoot = new ScrollPane();
		myRoot.setHbarPolicy(ScrollBarPolicy.NEVER);
		myRoot.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		myRoot.setPadding(new Insets(numericResourceBundle.getFromSizing("StandardSpacing")));
		myRoot.setMaxHeight(numericResourceBundle.getFromSizing("MaxMenuContentHeight"));
		myInnerRoot = new VBox();
		myInnerRoot.setSpacing(numericResourceBundle.getFromSizing("StandardSpacing"));
		myRoot.setBorder(new Border(new BorderStroke(Color.ORANGE, 
	            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		myRoot.setContent(myInnerRoot);
	}
	
	public void add(Button button){
		myInnerRoot.getChildren().add(button);
	}
	
	@Override
	public Node getRoot() {
		return myRoot;
	}

	
}
