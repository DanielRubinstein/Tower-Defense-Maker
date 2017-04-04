package frontEnd.Skeleton.View;

import backEnd.Environment.Tile;
import backEnd.GameEngine.Component;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TileCommandCenter {
	private TabPane tabPane;
	
	public TileCommandCenter(Tile tile){
		tabPane = new TabPane();
		tabPane.getTabs().add(createTileTab(tile));
		
		Stage stage = new Stage();
		stage.setScene(new Scene(tabPane, 150, 300));
		stage.show();
		
	}
	
	private Tab createTileTab(Tile tile) {
		Tab tileTab = new Tab("Tile");
		VBox tabContents = new VBox();
		
		tileTab.setContent(tabContents);
		return tileTab;
	}

	public void addComponentTab(Component component){
		
	}

}
