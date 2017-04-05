package frontEnd.Skeleton.View;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Canvas {
	private Group root;
	private Rectangle background;
	
	public Canvas(){
		root = new Group();
		background = new Rectangle();
		background.setFill(Color.BLACK);
		root.getChildren().add(background);
		
	}

	public Node getRoot() {
		return root;
	}
	

}
