package frontEnd.Skeleton.AoTools;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import ModificationFromUser.Modification_AddNewAttributeOwnerToGrid;
import ModificationFromUser.Modification_AddNewPresetAttributeOwner;
import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeOwner;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.SingleFieldPrompt;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
	private HBox bottomButtons;
	private Label titleLbl;
	private Stage myHostStage;
	
	public AttributeCommandCenter(View view, Stage hostStage , AttributeOwner obj, String title){
		myView = view;
		myHostStage = hostStage;
		authorProperty = view.getBooleanAuthorModeProperty();
		setText(title);
		myRoot = createAttributeCommandCenter(obj);
	}
	
	private void setText(String text){
		titleLbl = new Label(text);
		titleLbl.setFont(Font.font(32));
		titleLbl.setUnderline(true);
		//titleLbl.setStyle("");
	}
	
	private VBox createAttributeCommandCenter(AttributeOwner obj) {
		VBox contents = new VBox();
		
		contents.getChildren().add(titleLbl);
		
		HBox contents_Att = createAttributeView(obj);
		contents.getChildren().add(contents_Att);
		
		contents.getChildren().add(createPresetButton(obj));
		
		contents.setPadding(new Insets(STANDARD_SPACING, STANDARD_SPACING, STANDARD_SPACING, STANDARD_SPACING));
		contents.setSpacing(STANDARD_SPACING);
		// contents.setAlignment(Pos.TOP_CENTER);
		return contents;
	}

	public void addSubmitButton(AttributeOwner obj) {
		Button submit = new Button("Add Now");
		submit.setOnAction(e -> {
			myView.sendUserModification(new Modification_AddNewAttributeOwnerToGrid(obj));
			myHostStage.close();
		});
		bottomButtons.getChildren().add(submit);
	}
	
	private HBox createAttributeView(AttributeOwner obj) {
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

	private Node createPresetButton(AttributeOwner obj){
		
		bottomButtons = new HBox();
		
		bottomButtons.setSpacing(STANDARD_SPACING);
		bottomButtons.setAlignment(Pos.BOTTOM_RIGHT);  
		
		if(!(myView.getComponentPresets().contains(obj) || myView.getTilePresets().contains(obj))){
			List<String> dialogTitles = Arrays.asList("Preset Creation Utility", "Please Input a Name for your new preset");
			String promptLabel = "New preset name:";
			String promptText = "";
			SingleFieldPrompt myNameDialog = new SingleFieldPrompt(dialogTitles, promptLabel, promptText);
			Button preset = new Button("Save a copy to preset palette");
			preset.setOnAction((e) -> {
				myView.sendUserModification(new Modification_AddNewPresetAttributeOwner(myNameDialog.create(), obj));
				myHostStage.close();
			});
			
			bottomButtons.getChildren().add(preset);
		}
		return bottomButtons;
	}

	private HBox createAttributeValuePair(AttributeOwner obj, Attribute<?> attr) {
		HBox singleAttEditor = new HBox();
		Label attLabel = new Label(attr.getName());
		singleAttEditor.getChildren().add(attLabel);
		Node right;
		if (authorProperty.get()) {
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
			System.out.println(myAttrNameResources.getString(attr.getName()));
			singleAttEditor.getChildren().add(new Label("Editor in production"));
		}
		singleAttEditor.setAlignment(Pos.CENTER_RIGHT);
		return singleAttEditor;
	}

	public VBox get() {
		return myRoot;
	}

}
