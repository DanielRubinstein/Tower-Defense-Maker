package frontEnd.CustomJavafxNodes;

import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.VBox;

public class ButtonScrollPane implements SkeletonObject {
	private ScrollPane myRoot;
	private VBox myInnerRoot;

	public ButtonScrollPane(){
		myRoot = new ScrollPane();
		myRoot.setHbarPolicy(ScrollBarPolicy.NEVER);
		myRoot.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		myRoot.setPadding(new Insets(10));
		myRoot.setMaxHeight(400);
		
		myInnerRoot = new VBox();
		myInnerRoot.setSpacing(20);
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
