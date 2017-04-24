package frontEnd.Skeleton.AoTools;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import ModificationFromUser.AttributeOwner.Modification_Add_StraightToGrid;
import ModificationFromUser.AttributeOwner.Modification_Add_ToPalette;
import ModificationFromUser.AttributeOwner.Modification_RemoveAttributeOwner;
import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.Component;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.SingleFieldPrompt;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AttributeCommandCenter{	
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
		contents.getChildren().add(createAttributeView(obj));
		contents.getChildren().add(createBottomButtons(obj));
		
		contents.setPadding(new Insets(STANDARD_SPACING, STANDARD_SPACING, STANDARD_SPACING, STANDARD_SPACING));
		contents.setSpacing(STANDARD_SPACING);
		// contents.setAlignment(Pos.TOP_CENTER);
		return contents;
	}

	private void checkToAddAttributeOwnerSpecificButton(AttributeOwner obj, String methodName) {
		try {
			Method addSubmitButton = AttributeCommandCenter.class.getDeclaredMethod(methodName, obj.getClass());
			addSubmitButton.setAccessible(true);
			addSubmitButton.invoke(this, obj);
		} catch (NoSuchMethodException e) {
			// do nothing
			// this means the thing being put in attribute command center is a tile
		} catch (Exception e) {
			// something went wrong
			// TODO add exception?
			System.out.println("Something went wrong adding the 'add now' button");
		}
	}

	private void addSubmitButton(Component obj) {
		if(myView.getBankController().getAccessibleComponentMap().containsValue(obj)){
			Button submit = new Button("Add Now");
			submit.setOnAction(e -> {
				myView.sendUserModification(new Modification_Add_StraightToGrid(obj));
				myHostStage.close();
			});
			bottomButtons.getChildren().add(submit);
		}
	}
	
	private Node createAttributeView(AttributeOwner obj) {
		ScrollPane sP = new ScrollPane();
		sP.setHbarPolicy(ScrollBarPolicy.NEVER);
		sP.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		sP.setPadding(new Insets(5,20,5,20));
		sP.setMaxHeight(400);
		
		GridPane contents_Att = new GridPane();
		if(obj.getMyAttributes()==null){
			return contents_Att;
		}
		int count = 0;
		for (Attribute<?> attr : obj.getMyAttributes().values()) {
			
			Label attLabel = new Label(attr.getName());
			contents_Att.add(attLabel, 0, count);
			GridPane.setHalignment(attLabel, HPos.RIGHT);
			Node n = createAttributeValueViewer(obj, attr);
			//GridPane.setHalignment(n, HPos.CENTER);
			contents_Att.add(n, 1, count);
			count++;
		}
		contents_Att.setVgap(10d);
		contents_Att.setHgap(10d);		
		sP.setContent(contents_Att);
		return sP;
	}

	private Node createBottomButtons(AttributeOwner obj){
		
		bottomButtons = new HBox();
		
		bottomButtons.setSpacing(STANDARD_SPACING);
		bottomButtons.setAlignment(Pos.BOTTOM_RIGHT);  
		
		createAccessPermissionButton(obj);
		createAddToPresetButton(obj);
		checkToAddAttributeOwnerSpecificButton(obj, "addSubmitButton");
		checkToAddAttributeOwnerSpecificButton(obj, "removeButton");
		return bottomButtons;
	}

	private void removeButton(Component c) {
		if(!myView.getBankController().getAccessibleComponentMap().containsValue(c)){
			Button submit = new Button("Remove Now");
			submit.setOnAction(e -> {
				myView.sendUserModification(new Modification_RemoveAttributeOwner(c));
				myHostStage.close();
			});
			bottomButtons.getChildren().add(submit);
		}
	}

	private void createAccessPermissionButton(AttributeOwner obj) {
		AccessPermissionsViewer accessPermissionsViewer = new AccessPermissionsViewer(myHostStage, myView, obj.getAccessPermissions());
		bottomButtons.getChildren().add(accessPermissionsViewer.getRoot());
	}

	private void createAddToPresetButton(AttributeOwner obj) {
		if(!(myView.getComponentPresets().contains(obj) || myView.getTilePresets().contains(obj))){
			List<String> dialogTitles = Arrays.asList("Preset Creation Utility", "Please Input a Name for your new preset");
			String promptLabel = "New preset name:";
			String promptText = "";
			SingleFieldPrompt myNameDialog = new SingleFieldPrompt(dialogTitles, promptLabel, promptText);
			Button preset = new Button("Save a copy to preset palette");
			preset.setOnAction((e) -> {
				myView.sendUserModification(new Modification_Add_ToPalette(myNameDialog.create(), obj));
				myHostStage.close();
			});
			
			bottomButtons.getChildren().add(preset);
		}
	}

	private Node createAttributeValueViewer(AttributeOwner obj, Attribute<?> attr) {
		HBox finalViewer = new HBox();
		Node right;
		if (authorProperty.get()) {
			// Author Mode
			AttributeEditorCreator editorCreator = new AttributeEditorCreator(myView, obj, attr);
			right = editorCreator.extractEditor(myAttrNameResources.getString(attr.getName()));
			
		} else {
			// Player Mode
			AttributeViewerCreator viewerCreator = new AttributeViewerCreator(myView, obj, attr);
			right = viewerCreator.extractViewer(myAttrNameResources.getString(attr.getName()));
		}
		try {
			finalViewer.getChildren().add(right);
		} catch (NullPointerException e) {
			right = new Label("No Attribute Value Stored");
			finalViewer.getChildren().add(right);
		}
		return finalViewer;
	}

	public VBox get() {
		return myRoot;
	}

}
