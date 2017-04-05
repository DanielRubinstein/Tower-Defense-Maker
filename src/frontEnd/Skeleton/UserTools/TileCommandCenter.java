package frontEnd.Skeleton.UserTools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import backEnd.Environment.Tile;
import backEnd.GameEngine.Component;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TileCommandCenter {
	private TabPane tabPane;
	private Stage myStage;
	
	public TileCommandCenter(Tile tile){
		tabPane = new TabPane();
		tabPane.getTabs().addAll(createTileTabs(tile));
		
		myStage = new Stage();
		myStage.setScene(new Scene(tabPane, 150, 300));
		myStage.setTitle("Tile Command Center");;
	}
	public void launch(){
		myStage.show();
	}
	public Node getNode(){
		return tabPane;
	}
	
	private Collection<Tab> createTileTabs(Tile tile) {
		VBox attContents = new VBox();
		VBox behavContents = new VBox();
		List<Tab> allTabs = new ArrayList<Tab>();

		allTabs.add(createSingleTab(tile,"Attributes",attContents));
		allTabs.add(createSingleTab(tile,"Behaviors",behavContents));
		return allTabs;
	}
	private Tab createSingleTab(Tile tile, String name,Node contents){
		Tab aTab = new Tab(name);
		aTab.setClosable(false);
		aTab.setContent(contents);
		return aTab;
	}

	public void addComponentTab(Component component){
		
	}

}
