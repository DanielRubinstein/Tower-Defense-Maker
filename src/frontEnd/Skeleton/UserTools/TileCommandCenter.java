package frontEnd.Skeleton.UserTools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import ModificationFromUser.Modification_EditAttribute;
import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.NumberChanger;
import frontEnd.CustomJavafxNodes.ToggleSwitch;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TileCommandCenter implements SkeletonObject {
	private static final int STANDARD_SPACING = 10;
	public static final String DEFAULT_CSS = "/resources/css/Flatter.css";
	private View myView;
	private TabPane tabPane;
	private Stage myStage;
	private Collection<Component> myComponents;
	private Tile myTile;
	private final static String RESOURCES_PATH = "resources/";
	private final static String ALL_ATTRIBUTES_TYPES = "allAttributeTypes";
	private final static ResourceBundle myAttrNameResources = ResourceBundle.getBundle(RESOURCES_PATH + ALL_ATTRIBUTES_TYPES);

	public TileCommandCenter(View view, Tile tile, State state) {
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

	private Tab createAttributeOwnerTab(AttributeOwnerReader obj) {
		VBox contents = new VBox();
		contents.setPadding(new Insets(STANDARD_SPACING, STANDARD_SPACING, STANDARD_SPACING, STANDARD_SPACING));
		contents.getChildren().add(createLocationLabel());

		HBox contents_Att = createAttributeView(obj);
		
		contents.getChildren().add(contents_Att);

		if (myView.getBooleanAuthorModeProperty().get()) {
			contents.getChildren().add(getAuthorButtons(null, null));
		}
		
		contents.setSpacing(STANDARD_SPACING);
		//contents.setAlignment(Pos.TOP_CENTER);

		return createSingleTab("Tile", contents);
	}

	private HBox createAttributeView(AttributeOwnerReader obj) {
		HBox contents_Att = new HBox();
		VBox contentRow = null;
		int count = 0;
		for (Attribute<?> attr : obj.getMyAttributes().getAttributeMap().values()) {
			if (count % 3 == 0){
				contentRow = new VBox();
			}
			HBox singleAttEditor = createAttributeValuePair(obj, attr);
			contentRow.getChildren().add(singleAttEditor);
			if (count % 3 == 2){
				contentRow.setSpacing(STANDARD_SPACING);
				contents_Att.getChildren().add(contentRow);
			}
			count++;
		}
		contents_Att.setSpacing(STANDARD_SPACING);
		return contents_Att;
	}

	private HBox createAttributeValuePair(AttributeOwnerReader obj, Attribute<?> attr) {
		HBox singleAttEditor = new HBox();
		Label attLabel = new Label(attr.getName());
		singleAttEditor.getChildren().add(attLabel);
		Node right;
		if (myView.getBooleanAuthorModeProperty().get()) {
			// Author Mode
			right = createEditor(obj, attr);
		} else {
			// Player Mode
			try{
				right = new Label(attr.getValue().toString());
			} catch (NullPointerException e){
				right = new Label("No Attribute Value Stored");
			}
			// FIXME get it right
		}
		singleAttEditor.getChildren().add(new Label("    "));
		singleAttEditor.getChildren().add(right);
		singleAttEditor.setAlignment(Pos.CENTER_RIGHT);
		return singleAttEditor;
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

	private Node createEditor(AttributeOwnerReader obj, Attribute<?> attr) {
		Node n = null;
		String type = myAttrNameResources.getString(attr.getName());
		switch(type){
		case "BOOLEAN":
			n = createBooleanEditor(attr);
			break;
		case "DOUBLE":
			n = createDoubleEditor(attr);
			break;
		case "EDITABLESTRING":
			n = createEditableStringEditor(attr);
			break;
		case "IMAGE":
			n = createImageEditor(attr);
			break;
		case "INTEGER":
			n = createIntegerEditor(attr);
			break;
		case "STRINGLIST":
			n = createStringListEditor(obj, attr);
			break;
		default:
			break;
		}
		return n;
	}

	private Node createIntegerEditor(Attribute<?> attr) {
		// TODO Auto-generated method stub
		return null;
	}

	private Node createEditableStringEditor(Attribute<?> attr) {
		// TODO Auto-generated method stub
		return null;
	}

	private Node createStringListEditor(AttributeOwnerReader obj, Attribute<?> attr) {
		Node n;
		List<String> editParameters = (List<String>) attr.getEditParameters();
		
		// TODO if doubles then make a slider not a combobox (this will be the only separate case)
		ObservableList<String> options = (ObservableList<String>) FXCollections.observableArrayList( editParameters );
		ComboBox<String> optionsBox = new ComboBox<String>(options);
		try{
			// TODO this will work as long as there is an attribute there
			optionsBox.getSelectionModel().select(attr.getValue().toString());
		} catch (NullPointerException e){
			// do nothing
		}
		optionsBox.valueProperty().addListener((o, oldValue, newValue) -> {
			// where the actual modification gets sent
			myView.sendUserModification(new Modification_EditAttribute((AttributeOwner) obj, attr, newValue));
		});
		n = optionsBox;
		return n;
	}

	private Node createImageEditor(Attribute<?> attr) {
		Node n;
		String imagePath = (String) attr.getValue();
		Label l = new Label(imagePath);
		n= l;
		return n;
	}

	private Node createDoubleEditor(Attribute<?> attr) {
		Node n;
		List<Double> paramList = (List<Double>)attr.getEditParameters();
		NumberChanger numChanger = new NumberChanger(paramList.get(0), paramList.get(1), paramList.get(2), paramList.get(3));
		n = numChanger.getRoot();
		return n;
	}

	private Node createBooleanEditor(Attribute<?> attr) {
		Node n;
		ToggleSwitch myToggle = new ToggleSwitch(myView, "On", "Off", new SimpleBooleanProperty((Boolean) attr.getValue()));
		n = myToggle.getRoot();
		return n;
	}

	private Tab createSingleTab(String name, Node contents) {
		Tab tab = new Tab(name);
		tab.setClosable(false);
		tab.setContent(contents);
		return tab;
	}

}