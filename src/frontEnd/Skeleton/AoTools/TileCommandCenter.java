package frontEnd.Skeleton.AoTools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import frontEnd.View;
import javafx.scene.control.Tab;


public class TileCommandCenter extends CommandCenter {

	private Collection<Component> myComponents;
	private Tile myTile;


	public TileCommandCenter(View view, Tile tile, State state) {
		super();
		myTile = tile;
		ComponentGraph myComponentGraph = state.getComponentGraph();
		myComponents = myComponentGraph.getComponentList();
		myView = view;
	}

	private Collection<Tab> createComponentTabs() {
		List<Tab> componentTabs = new ArrayList<Tab>();
		for (Component c : myComponents) {
			componentTabs.add(createAttributeOwnerTab(c));
		}
		return componentTabs;
	}

	@Override
	public void launch(double x, double y) {
		tabPane.getTabs().add(createAttributeOwnerTab(myTile));
		tabPane.getTabs().addAll(createComponentTabs());
		generate(x,y);
	}

	

}