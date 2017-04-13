package frontEnd.Skeleton.AoTools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import backEnd.Attribute.AttributeOwnerReader;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import frontEnd.View;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

/**
 * This class is the command center that pops up when a user clicks on a tile. It presents information about the tile
 * and the components on it. If the user is in author mode, they can also edit their attributes. 
 * @author Miguel, Tim
 *
 */
public class TileCommandCenterImpl extends CommandCenter implements TileCommandCenter{
	public static final String DEFAULT_CSS = "/resources/css/Flatter.css";
	private Stage myStage;
	private Scene myScene;
	
	private View myView;
	private TabPane tabPane;
	
	private Collection<Component> myComponents;
	private Tile myTile;


	public TileCommandCenterImpl(View view, Tile tile, State state) {
		myView = view;
		myTile = tile;
		ComponentGraph myComponentGraph = state.getComponentGraph();
		myComponents = myComponentGraph.getComponentList();
		tabPane = new TabPane();
	}

	private Collection<Tab> createComponentTabs() {
		List<Tab> componentTabs = new ArrayList<Tab>();
		for (Component c : myComponents) {
			componentTabs.add(createAttributeOwnerTab(c));
		}
		return componentTabs;
	}

	
	
	private Tab createAttributeOwnerTab(AttributeOwnerReader obj) {
		AttributeCommandCenter aCC = new AttributeCommandCenter(myView, obj);
		String fuckedUpName = obj.toString();
		fuckedUpName = fuckedUpName.substring(fuckedUpName.lastIndexOf('.') + 1, fuckedUpName.length());
		return createSingleTab(fuckedUpName, aCC.get());
	}
	
	private Tab createSingleTab(String name, Node contents) {
		Tab tab = new Tab(name);
		tab.setClosable(false);
		tab.setContent(contents);
		return tab;
	}

	/* (non-Javadoc)
	 * @see frontEnd.Skeleton.AoTools.TileCommandCenterI#getRoot()
	 */
	@Override
	public Node getRoot() {
		return tabPane;
	}
	
	private void generate(double x, double y) {
		myStage = new Stage();
		try{
			myScene = new Scene(tabPane);
		} catch (IllegalArgumentException e){
			myScene.setRoot(tabPane);
		}
		myScene.getStylesheets().add(DEFAULT_CSS);
		myStage.setScene(myScene);
		myStage.setTitle("Command Center");
		myStage.setX(x);
		myStage.setY(y);
		myStage.show();
	}
	
	/* (non-Javadoc)
	 * @see frontEnd.Skeleton.AoTools.TileCommandCenterI#launch(double, double)
	 */
	@Override
	public void launch(double x, double y) {
		tabPane.getTabs().clear();
		tabPane.getTabs().add(createAttributeOwnerTab(myTile));
		tabPane.getTabs().addAll(createComponentTabs());
		generate(x,y);
	}
}
