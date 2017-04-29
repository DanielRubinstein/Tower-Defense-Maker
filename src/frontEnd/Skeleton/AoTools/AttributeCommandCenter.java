package frontEnd.Skeleton.AoTools;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import ModificationFromUser.AttributeOwner.Modification_Add_StraightToGrid;
import ModificationFromUser.AttributeOwner.Modification_Add_ToPalette;
import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeOwner;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.SingleFieldPrompt;
import frontEnd.Skeleton.AoTools.AttributeVisualization.AttributeEditorCreator;
import frontEnd.Skeleton.AoTools.AttributeVisualization.AttributeViewerCreator;
import frontEnd.Skeleton.AoTools.AttributeVisualization.AttributeVisualization;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import util.reflection.Reflection;

public class AttributeCommandCenter{	
	private final static String RESOURCES_PATH = "resources/";
	private static final int STANDARD_SPACING = 10;
	private final static String ALL_ATTRIBUTES_TYPES = "allAttributeTypes";
	private final static ResourceBundle myAttrNameResources = ResourceBundle
			.getBundle(RESOURCES_PATH + ALL_ATTRIBUTES_TYPES);
	private View myView;
	private VBox myRoot;
	private SimpleBooleanProperty authorProperty;
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
		AttributeCommandCenterBottomButtons attributeCommandCenterBottomButtons = new AttributeCommandCenterBottomButtons(myView, myHostStage);
		Reflection.callAllMethods(attributeCommandCenterBottomButtons, obj);
		return attributeCommandCenterBottomButtons.getRoot();
	}	

	private Node createAttributeValueViewer(AttributeOwner obj, Attribute<?> attr) {
		HBox finalViewer = new HBox();
		AttributeVisualization attributeVisualization;
		if (authorProperty.get()) {
			// Author Mode
			attributeVisualization = new AttributeEditorCreator(myView, obj, attr);
		} else {
			// Player Mode
			attributeVisualization = new AttributeViewerCreator(myView, obj, attr);
		}
		String methodNameFormat = attributeVisualization.getMethodNameFormat();
		Node right = (Node) Reflection.callMethod(attributeVisualization, String.format(methodNameFormat, myAttrNameResources.getString(attr.getName())));			
		
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
