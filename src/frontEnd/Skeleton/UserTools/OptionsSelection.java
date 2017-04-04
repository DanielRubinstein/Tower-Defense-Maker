package frontEnd.Skeleton.UserTools;

import javafx.scene.Node;
import javafx.scene.layout.TilePane;

public class OptionsSelection {

	
	private TilePane myTiles;
	public OptionsSelection() {
		myTiles = new TilePane();
	}
	
	public Node getNode(){
		return myTiles;
	}
	
	

}
