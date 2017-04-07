package frontEnd.Skeleton.UserTools;

<<<<<<< HEAD
import backEnd.GameEngine.Component;
import backEnd.State.Tile;
=======
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import backEnd.Environment.Tile;
import backEnd.GameEngine.Component;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
>>>>>>> 2658b55a1c7768b5bed8c8e83980357a5d9cbe74
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TileCommandCenter {
	private TabPane tabPane;
	private Stage myStage;
	private Collection<Component> myComponents = new ArrayList<Component>();
	
	public TileCommandCenter(Tile tile){
		tabPane = new TabPane();
		tabPane.getTabs().addAll(createTileTabs(tile));
		myStage = new Stage();
		myStage.setScene(new Scene(tabPane, 200, 300));
		myStage.setTitle("Tile Command Center");
	}
	public void launch(double x, double y){
		myStage.setX(x);
		myStage.setY(y);
		myStage.show();
	}
	public Node getNode(){
		return tabPane;
	}
	
	private Collection<Tab> createTileTabs(Tile tile) {
		List<Tab> allTabs = new ArrayList<Tab>();

		for(Component c : myComponents){
			//add component tab
			allTabs.add(createSingleTab(tile,"Component X",createGrid()));
		}
		allTabs.add(createSingleTab(tile,"Tile",createGrid()));
		return allTabs;
	}
	private GridPane createGrid(){
		GridPane contents =new GridPane();
		contents.setHgap(10);
		contents.setVgap(10);
		contents.setPadding(new Insets(10));
		contents.add(new Label("Atrributes"), 0, 0,1,1);
		contents.add(createSubContents(), 0, 1);

		contents.add(new Label("Behaviors"), 0, 2,1,1);
		contents.add(createSubContents(), 0, 3);	
		return contents;
	}
	private Node createSubContents(){
		HBox subContents = new HBox();
		subContents.getChildren().add(new Button("some attibute"));
		subContents.getChildren().add(new Label("should be stuff here"));
			
		return subContents;
	}
	
	private Tab createSingleTab(Tile tile, String name,Node contents){
		Tab aTab = new Tab(name);
		aTab.setClosable(false);
		aTab.setContent(contents);
		return aTab;
	}


}
