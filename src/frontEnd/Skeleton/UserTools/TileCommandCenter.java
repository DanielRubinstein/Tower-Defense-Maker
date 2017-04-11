package frontEnd.Skeleton.UserTools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import backEnd.GameEngine.Component;
import frontEnd.ViewEditor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TileCommandCenter implements SkeletonObject {
	public static final String DEFAULT_CSS = "/resources/css/Flatter.css";
	private ViewEditor myView;
	private TabPane tabPane;
	private Stage myStage;
	private Collection<Component> myComponents;
	private Tile myTile;

	public TileCommandCenter(ViewEditor view, Tile tile, State state) {
		myTile = tile;
		ComponentGraph myComponentGraph = state.getComponentGraph();
		myComponents = myComponentGraph.getComponentList();
		myView = view;
	}

	private void createTabsAndStage(Tile tile) {
		tabPane = new TabPane();
		tabPane.getTabs().add(createAttributeOwnerTab(tile));
		tabPane.getTabs().addAll(createComponentTabs());
		myStage = new Stage();
		Scene myScene = new Scene(tabPane);
		myScene.getStylesheets().add(DEFAULT_CSS);
		myStage.setScene(myScene);
		myStage.setTitle("Tile Command Center");
	}

	public void launch(double x, double y) {
		createTabsAndStage(myTile);
		myStage.setX(x);
		myStage.setY(y);
		myStage.show();
	}

	public Node getRoot() {
		return tabPane;
	}

	private Collection<Tab> createComponentTabs() {
		List<Tab> componentTabs = new ArrayList<Tab>();
		for (Component c : myComponents) {
			componentTabs.add(createAttributeOwnerTab(c));
		}
		return componentTabs;
	}

	private Tab createAttributeOwnerTab(AttributeOwner obj) {
		VBox contents = new VBox();

		contents.getChildren().add(createLocationLabel());
		for (Map.Entry<String, Attribute<?>> entry : obj.getMyAttributes().getAttributeMap().entrySet()) {
			HBox singleAttEditor = new HBox();
			Label attLabel = new Label(entry.getKey());
			singleAttEditor.getChildren().add(attLabel);
			Node right;
			if (myView.getBooleanAuthorModeProperty().get()) {
				// Author Mode
				right = createEditor(entry);
			} else {
				// Player Mode
				right = new Label(entry.getValue().toString()); 
				// FIXME another getValue needed
			}
			singleAttEditor.getChildren().add(new Label("    "));
			singleAttEditor.getChildren().add(right);
			singleAttEditor.setAlignment(Pos.CENTER);
			contents.getChildren().add(singleAttEditor);
		}

		if (myView.getBooleanAuthorModeProperty().get()) {
			contents.getChildren().add(new Label("* Will be editable in Author Mode"));
			contents.getChildren().add(getAuthorButtons(null, null));
		}
		
		//contents.setAlignment(Pos.TOP_CENTER);

		return createSingleTab("Tile", contents);
	}

	private Node getAuthorButtons(EventHandler<ActionEvent> addEvent, EventHandler<ActionEvent> removeEvent) {

		Button add = new Button("ADD");
		add.setOnAction(addEvent);

		Button remove = new Button("REMOVE SELECTED");
		remove.setOnAction(removeEvent);

		HBox n = new HBox();
		n.getChildren().addAll(add, remove);
		return n;
	}

	private Label createLocationLabel() {
		// TODO maybe add sell feature here
		return new Label(String.format("Location: (%.0f, %.0f)", myTile.getLocation().getX(), myTile.getLocation().getY()));
	}

	private Node createEditor(Entry<String, Attribute<?>> entry) {
		// TODO get the type and edit method (e.g. slider, dropdown)
		return new Label(entry.getValue().toString() + "*");
	}

	private Tab createSingleTab(String name, Node contents) {
		Tab tab = new Tab(name);
		tab.setClosable(false);
		tab.setContent(contents);
		return tab;
	}

}