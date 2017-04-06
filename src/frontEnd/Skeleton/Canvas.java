package frontEnd.Skeleton;

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
	
	public void setSize(double height, double width){
		this.background.setHeight(height);
		this.background.setWidth(width);
	}
	

}
