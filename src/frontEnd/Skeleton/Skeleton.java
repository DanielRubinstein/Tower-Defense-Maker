package frontEnd.Skeleton;

import backEnd.Attribute.AttributeOwnerReader;
import javafx.scene.Node;
import javafx.stage.Stage;

public interface Skeleton {

	/**
	 * Displays the Skeleton.
	 * @param stage
	 */
	public void display(Stage stage);
	
	public Node getCanvas();
}

