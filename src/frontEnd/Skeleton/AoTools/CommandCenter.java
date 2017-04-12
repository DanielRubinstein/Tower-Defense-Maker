package frontEnd.Skeleton.AoTools;

import java.util.Arrays;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import ModificationFromUser.Modification_AddNewPresetAttributeOwner;
import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.SingleFieldPrompt;
import frontEnd.Skeleton.UserTools.SkeletonObject;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
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

public abstract class CommandCenter implements SkeletonObject{
	private static final int STANDARD_SPACING = 10;
	public static final String DEFAULT_CSS = "/resources/css/Flatter.css";
	private Stage myStage;
	private Scene myScene;
	private final static String RESOURCES_PATH = "resources/";
	private final static String ALL_ATTRIBUTES_TYPES = "allAttributeTypes";
	private final static ResourceBundle myAttrNameResources = ResourceBundle
			.getBundle(RESOURCES_PATH + ALL_ATTRIBUTES_TYPES);
	
	protected View myView;
	protected TabPane tabPane;
	
	public CommandCenter(View view){
		myView = view;
		tabPane = new TabPane();
	}
	
	protected Tab createAttributeOwnerTab(AttributeOwnerReader obj) {
		VBox contents = new VBox();
		contents.setPadding(new Insets(STANDARD_SPACING, STANDARD_SPACING, STANDARD_SPACING, STANDARD_SPACING));
		contents.getChildren().add(createLocationLabel(obj));

		HBox contents_Att = createAttributeView(obj);

		contents.getChildren().add(contents_Att);
		contents.getChildren().add(createPresetButton(obj));
		contents.setSpacing(STANDARD_SPACING);
		// contents.setAlignment(Pos.TOP_CENTER);

		return createSingleTab("Tile", contents);
	}

	private HBox createAttributeView(AttributeOwnerReader obj) {
		HBox contents_Att = new HBox();
		VBox contentRow = null;
		int count = 0;
		for (Attribute<?> attr : obj.getMyAttributes().getAttributeMap().values()) {
			if (count % 3 == 0) {
				contentRow = new VBox();
			}
			HBox singleAttEditor = createAttributeValuePair(obj, attr);
			contentRow.getChildren().add(singleAttEditor);
			if (count % 3 == 2) {
				contentRow.setSpacing(STANDARD_SPACING);
				contents_Att.getChildren().add(contentRow);
			}
			count++;
		}
		contents_Att.setSpacing(STANDARD_SPACING);
		return contents_Att;
	}

	private Node createPresetButton(AttributeOwnerReader obj){
		List<String> dialogTitles = Arrays.asList("Preset Creation Utility", "Please Input a Name for your new preset");
		String promptLabel = "New preset name:";
		String promptText = "";
		SingleFieldPrompt myDialog = new SingleFieldPrompt(dialogTitles, promptLabel, promptText);
		Button preset = new Button("Save as preset");
		preset.setOnAction((e) -> {
			myView.sendUserModification(new Modification_AddNewPresetAttributeOwner(myDialog.create(), obj));
		});
		return preset;
	}

	private HBox createAttributeValuePair(AttributeOwnerReader obj, Attribute<?> attr) {
		HBox singleAttEditor = new HBox();
		Label attLabel = new Label(attr.getName());
		singleAttEditor.getChildren().add(attLabel);
		Node right;
		if (myView.getBooleanAuthorModeProperty().get()) {
			// Author Mode
			EditorCreator editorCreator = new EditorCreator(myView, obj, attr);
			right = editorCreator.extractEditor(myAttrNameResources.getString(attr.getName()));
			
		} else {
			// Player Mode
			try {
				right = new Label(attr.getValue().toString());
			} catch (NullPointerException e) {
				right = new Label("No Attribute Value Stored");
			}
			// FIXME get it right
		}
		singleAttEditor.getChildren().add(new Label("    "));
		try{
			singleAttEditor.getChildren().add(right);
		} catch (Exception e){
			singleAttEditor.getChildren().add(new Label("It fucked up, deal with it"));
		}
		singleAttEditor.setAlignment(Pos.CENTER_RIGHT);
		return singleAttEditor;
	}

	private Label createLocationLabel(AttributeOwnerReader obj) {
		// TODO maybe add sell feature here
		try{
			Point2D pos = (Point2D) obj.getAttribute("Position");
			return new Label(
				String.format("Location: (%.0f, %.0f)", pos.getX(), pos.getY()));
		} catch (NullPointerException | MissingResourceException e ){
			return new Label("Error in producing Position Attribute");
		}
	}
	
	private Tab createSingleTab(String name, Node contents) {
		Tab tab = new Tab(name);
		tab.setClosable(false);
		tab.setContent(contents);
		return tab;
	}

	public Node getRoot() {
		return tabPane;
	}
	
	public abstract void launch(double x, double y);
	
	protected void generate(double x, double y) {
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
}
