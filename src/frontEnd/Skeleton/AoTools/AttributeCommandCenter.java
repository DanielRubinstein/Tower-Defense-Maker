package frontEnd.Skeleton.AoTools;

import java.util.Arrays;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import ModificationFromUser.Modification_AddAttributeOwner;
import ModificationFromUser.Modification_AddNewPresetAttributeOwner;
import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.DoubleFieldPrompt;
import frontEnd.CustomJavafxNodes.SingleFieldPrompt;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AttributeCommandCenter extends CommandCenter{	
	private final static String RESOURCES_PATH = "resources/";
	private static final int STANDARD_SPACING = 10;
	private final static String ALL_ATTRIBUTES_TYPES = "allAttributeTypes";
	private final static ResourceBundle myAttrNameResources = ResourceBundle
			.getBundle(RESOURCES_PATH + ALL_ATTRIBUTES_TYPES);
	private View myView;
	private VBox myRoot;
	private SimpleBooleanProperty authorProperty;
	
	public AttributeCommandCenter(View view, AttributeOwnerReader obj){
		myView = view;
		authorProperty = view.getBooleanAuthorModeProperty();

		myRoot = createAttributeCommandCenter(obj);
	}
	
	private VBox createAttributeCommandCenter(AttributeOwnerReader obj) {
		VBox contents = new VBox();
		contents.setPadding(new Insets(STANDARD_SPACING, STANDARD_SPACING, STANDARD_SPACING, STANDARD_SPACING));
		contents.getChildren().add(createLocationLabel(obj));

		HBox contents_Att = createAttributeView(obj);

		contents.getChildren().add(contents_Att);
		contents.getChildren().add(createPresetButton(obj));
		
		contents.setSpacing(STANDARD_SPACING);
		// contents.setAlignment(Pos.TOP_CENTER);
		return contents;
	}
	
	public void addSubmitButton(Stage stage, AttributeOwnerReader obj){
		Button submit = new Button("Add to Grid Now");
		submit.setOnAction(e -> {
			myView.addToCanvas(obj); // FIXME this shouldn't be necessary
			myView.sendUserModification(new Modification_AddAttributeOwner((AttributeOwner) obj, askForNewPosition()));
			//stage.close();
			
		});
		myRoot.getChildren().add(submit);
	}
	
	private Point2D askForNewPosition() {
		List<String> dialogTitles = Arrays.asList("Creation Utility", "Please input a location");
		List<String> promptLabel = Arrays.asList("X Position:", "Y Position:");
		List<String> promptText = Arrays.asList("0.0", "0.0");
		DoubleFieldPrompt myDialog = new DoubleFieldPrompt(dialogTitles, promptText, promptLabel);
		List<String> results = myDialog.create();
		return new Point2D(Double.parseDouble(results.get(0)), Double.parseDouble(results.get(1)));
	}

	private HBox createAttributeView(AttributeOwnerReader obj) {
		HBox contents_Att = new HBox();
		VBox contentRow = null;
		int count = 0;
		if(obj.getMyAttributes()==null) return contents_Att;
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
		if (authorProperty.get()) {
			// Author Mode
			EditorCreator editorCreator = new EditorCreator(myView, obj, attr);
			System.out.println("a&&&&&&  " + attr.getName() +"    "  +myAttrNameResources.getString(attr.getName()));
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
			Point2D pos = (Point2D) obj.getAttribute("Position").getValue();
			return new Label(
				String.format("Location: (%.0f, %.0f)", pos.getX(), pos.getY()));
		} catch (NullPointerException | MissingResourceException e ){
			return new Label("No Position Attribute Set");
		}
	}

	public VBox get() {
		return myRoot;
	}
	


}
