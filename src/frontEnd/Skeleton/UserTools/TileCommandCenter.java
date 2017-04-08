package frontEnd.Skeleton.UserTools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import backEnd.GameData.State.Tile;
import backEnd.GameEngine.Attribute;
import backEnd.GameEngine.Component;
import frontEnd.ViewEditor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

public class TileCommandCenter implements SkeletonObject {
	private ViewEditor myView;
	private TabPane tabPane;
	private Stage myStage;
	private Collection<Component> myComponents;

	public TileCommandCenter(ViewEditor view, Tile tile) {
		
		myComponents = new ArrayList<Component>();
		
		
		myView = view;
		tabPane = new TabPane();
		tabPane.getTabs().add(createTileTab(tile));

		tabPane.getTabs().addAll(createComponentTabs(tile));

		myStage = new Stage();
		myStage.setScene(new Scene(tabPane));
		myStage.setTitle("Tile Command Center");
	}

	public void launch(double x, double y) {
		// myStage.setX(x);
		// myStage.setY(y);
		myStage.show();
	}

	public Node getRoot() {
		return tabPane;
	}

	private Collection<Tab> createComponentTabs(Tile tile) {
		List<Tab> componentTabs = new ArrayList<Tab>();

		for (Component c : myComponents) {
			// add component tab
			componentTabs.add(createComponentTab(c, "Component X"));
		}
		return componentTabs;
	}

	private Tab createTileTab(Tile tile) {
		GridPane contents = createGrid();
		return createSingleTab("Tile", contents);
	}

	private Tab createComponentTab(Component c, String string) {
		GridPane contents = createGrid();

		ListView<String> behaviorView = createBehaviorView();
		createSubContent(contents, "Active Behaviors", 3, 0, behaviorView, null, null);

		contents.add(new Button("DELETE / SELL THIS COMPONENT"), 1, 5, 4, 1);

		return createSingleTab(string, contents);
	}

	private GridPane createGrid() {
		GridPane contents = new GridPane();
		contents.setHgap(10);
		contents.setVgap(10);
		contents.setPadding(new Insets(10));

		TableView<String> attributeView = createAttributeView();
		createSubContent(contents, "Attributes", 1, 0, attributeView, null, null);

		return contents;
	}

	private ListView<String> createBehaviorView() {
		ListView<String> myListView = new ListView<String>();

		// extract list as observablelist
		// myListView.setItems(GET FROM BACK END);

		// TODO set hover action to give description of behavior

		return myListView;
	}

	private TableView<String> createAttributeView() {
		TableView<String> tableAttributes = new TableView<String>();
		// tableAttributes.setEditable(true); // if author mode
		TableColumn<Attribute, String> nameCol = new TableColumn<Attribute, String>("Attribute");
		nameCol.setCellValueFactory(new PropertyValueFactory<Attribute, String>("nameProp"));

		TableColumn<Attribute, Double> valueCol = new TableColumn<Attribute, Double>("Value");
		valueCol.setCellValueFactory(new PropertyValueFactory<Attribute, Double>("valueProp"));
		valueCol.setCellFactory(TextFieldTableCell.<Attribute, Double>forTableColumn(new DoubleStringConverter()));
		// again only if in author mode
		/*
		 * valueCol.setOnEditCommit(new EventHandler<CellEditEvent<Attribute,
		 * Double>>() {
		 * 
		 * @Override public void handle(CellEditEvent<Attribute, Double> t) {
		 * ((Attribute)
		 * t.getTableView().getItems().get(t.getTablePosition().getRow()))
		 * .setValue(t.getNewValue()); } });
		 */

		// tableAttributes.getColumns().addAll(nameCol, valueCol);
		// tableAttributes.setItems(GET FROM BACKEND);
		return tableAttributes;
	}

	private void createSubContent(GridPane gridPane, String title, int firstColumn, int firstRow, Node viewer,
			EventHandler<ActionEvent> addEvent, EventHandler<ActionEvent> removeEvent) {
		Button add = new Button("ADD");
		add.setOnAction(addEvent);

		Button remove = new Button("REMOVE SELECTED");
		remove.setOnAction(removeEvent);

		gridPane.add(new Label(title), firstColumn, firstRow, 2, 1);
		gridPane.add(viewer, firstColumn, 1, 2, 3);
		gridPane.add(add, firstColumn, 4, 1, 1);
		gridPane.add(remove, firstColumn + 1, 4, 1, 1);

	}

	private Tab createSingleTab(String name, Node contents) {
		Tab tab = new Tab(name);
		tab.setClosable(false);
		tab.setContent(contents);
		return tab;
	}

}
