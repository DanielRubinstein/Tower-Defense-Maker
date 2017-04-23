package frontEnd.Skeleton.AoTools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import frontEnd.View;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import resources.Constants;

/**
 * This class is the command center that pops up when a user clicks on a tile. It presents information about the tile
 * and the components on it. If the user is in author mode, they can also edit their attributes. 
 * @author Miguel, Tim
 *
 */
public class OnGridTileCommandCenter implements CommandCenter, SkeletonObject{
	private Stage myStage;
	private View myView;
	private TabPane tabPane;
	
	private Collection<Component> myComponents;
	private Tile myTile;


	public OnGridTileCommandCenter(View view, Tile tile, State state) {
		myView = view;
		myTile = tile;
		myComponents = state.getComponentsByTileGridPosition(tile.<Point2D>getAttribute("Position").getValue());
		tabPane = new TabPane();
	}

	private Collection<Tab> createComponentTabs(Stage stage) {
		List<Tab> componentTabs = new ArrayList<Tab>();
		for (Component c : myComponents) {
			componentTabs.add(createAttributeOwnerTab(c, stage));
		}
		return componentTabs;
	}

	
	
	private Tab createAttributeOwnerTab(AttributeOwnerReader obj, Stage stage) {
		AttributeCommandCenter aCC = new AttributeCommandCenter(myView, stage, (AttributeOwner) obj, "On-Screen Object");
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

	@Override
	public void launch(String title, double x, double y) {
		myStage = new Stage();
		tabPane.getTabs().clear();
		tabPane.getTabs().add(createAttributeOwnerTab(myTile, myStage));
		tabPane.getTabs().addAll(createComponentTabs(myStage));
		generate(x,y, myStage, tabPane);
	}

	@Override
	public void generate(double x, double y, Stage myStage, Parent myRoot) {
		Scene myScene = new Scene(myRoot);
		myScene.getStylesheets().add(Constants.DEFAULT_CSS);
		myStage.setScene(myScene);
		myStage.setTitle("Command Center");
		myStage.setX(x);
		myStage.setY(y);
		myStage.show();
	}
}
