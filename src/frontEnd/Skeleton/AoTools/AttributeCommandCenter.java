package frontEnd.Skeleton.AoTools;

import java.util.ArrayList;
import java.util.List;

import backEnd.Attribute.AttributeOwnerReader;
import backEnd.Attribute.AttributeReader;
import frontEnd.View;
import frontEnd.Skeleton.AoTools.AttributeVisualization.AttributeEditorCreator;
import frontEnd.Skeleton.AoTools.AttributeVisualization.AttributeViewerCreator;
import frontEnd.Skeleton.AoTools.AttributeVisualization.AttributeVisualization;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import resources.constants.StringResourceBundle;
import resources.constants.numeric.NumericResourceBundle;
import util.reflection.Reflection;

public class AttributeCommandCenter{	
	private static final NumericResourceBundle NUMERIC_RESOURCE_BUNDLE = new NumericResourceBundle();
	private Double STANDARD_SPACING = NUMERIC_RESOURCE_BUNDLE.getFromSizing("StandardSpacing");
	private static final StringResourceBundle STRING_RESOURCE_BUNDLE = new StringResourceBundle();
	private View myView;
	private VBox myRoot;
	private SimpleBooleanProperty authorProperty;
	private Label titleLbl;
	private Stage myHostStage;
	
	public AttributeCommandCenter(View view, Stage hostStage , AttributeOwnerReader attributeOwnerReader, String title){
		myView = view;
		myHostStage = hostStage;
		authorProperty = view.getBooleanAuthorModeProperty();
		setText(title);
		myRoot = createAttributeCommandCenter(attributeOwnerReader);
	}
	
	private void setText(String text){
		titleLbl = new Label(text);
		titleLbl.setFont(Font.font(NUMERIC_RESOURCE_BUNDLE.getFromSizing("TitleFontSize")));
		titleLbl.setUnderline(true);
	}
	
	private VBox createAttributeCommandCenter(AttributeOwnerReader attributeOwnerReader) {
		VBox contents = new VBox();

		contents.getChildren().add(titleLbl);
		contents.getChildren().add(createAttributeView(attributeOwnerReader));
		contents.getChildren().add(createBottomButtons(attributeOwnerReader));
		
		contents.setPadding(new Insets(STANDARD_SPACING));
		contents.setSpacing(STANDARD_SPACING);
		return contents;
	}
	
	private Node createAttributeView(AttributeOwnerReader attributeOwnerReader) {
		if(attributeOwnerReader.getMyAttributes()==null){
			return new Label(STRING_RESOURCE_BUNDLE.getFromStringConstants("NoAttribute"));
		}
		
		List<String> attributeCategories = createAttributeCategoryList(attributeOwnerReader);
		TabPane attributeTabPane = new TabPane();
		for(String attributeCategory : attributeCategories){
			Node sP = createAttributeCategoryView(attributeCategory, attributeOwnerReader);
			Tab tab = new Tab(attributeCategory);
			tab.setClosable(false);
			tab.setContent(sP);
			attributeTabPane.getTabs().add(tab);
		}
		
		return attributeTabPane;
	}

	private List<String> createAttributeCategoryList(AttributeOwnerReader attributeOwnerReader) {
		List<String> categories = new ArrayList<>();
		for (AttributeReader<?> attributeReader : attributeOwnerReader.getMyAttributes().getAllAttributeReaders()) {
			String category = STRING_RESOURCE_BUNDLE.getFromAttributeCategories(attributeReader.getName());
			if(!categories.contains(category)){
				if(category.equals(STRING_RESOURCE_BUNDLE.getFromStringConstants("PrimaryCategory"))){
					categories.add(0, category);
				} else if (!category.equals(STRING_RESOURCE_BUNDLE.getFromStringConstants("HiddenCategory"))){
					categories.add(category);
				}
			}
		}
		return categories;
	}

	private Node createAttributeCategoryView(String attributeCategory, AttributeOwnerReader attributeOwnerReader) {
		ScrollPane sP = new ScrollPane();
		sP.setHbarPolicy(ScrollBarPolicy.NEVER);
		sP.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		sP.setPadding(new Insets(STANDARD_SPACING));
		sP.setMaxHeight(NUMERIC_RESOURCE_BUNDLE.getFromSizing("MaxMenuContentHeight"));
		
		GridPane contents_Att = new GridPane();
		
		int count = 0;
		for (AttributeReader<?> attributeReader : attributeOwnerReader.getMyAttributes().getAllAttributeReaders()) {
			String category = STRING_RESOURCE_BUNDLE.getFromAttributeCategories(attributeReader.getName());
			if(!category.equals(attributeCategory)){
				continue;
			}
			Label attLabel = new Label(attributeReader.getName());
			contents_Att.add(attLabel, 0, count);
			GridPane.setHalignment(attLabel, HPos.RIGHT);
			Node n = createAttributeValueViewer(attributeOwnerReader, attributeReader);
			//GridPane.setHalignment(n, HPos.CENTER);
			contents_Att.add(n, 1, count);
			count++;
		}
		contents_Att.setVgap(STANDARD_SPACING);
		contents_Att.setHgap(STANDARD_SPACING);		
		sP.setContent(contents_Att);
		return sP;
	}

	private Node createBottomButtons(AttributeOwnerReader attributeOwnerReader){
		AttributeCommandCenterBottomButtons attributeCommandCenterBottomButtons = new AttributeCommandCenterBottomButtons(myView, myHostStage);
		Reflection.callAllMethods(attributeCommandCenterBottomButtons, attributeOwnerReader);
		return attributeCommandCenterBottomButtons.getRoot();
	}	

	private Node createAttributeValueViewer(AttributeOwnerReader attributeOwnerReader, AttributeReader<?> attributeReader) {
		HBox finalViewer = new HBox();
		AttributeVisualization attributeVisualization;
		if (authorProperty.get()) {
			// Author Mode
			attributeVisualization = new AttributeEditorCreator(myView, attributeOwnerReader, attributeReader);
		} else {
			// Player Mode
			attributeVisualization = new AttributeViewerCreator(myView, attributeReader);
		}
		String methodNameFormat = attributeVisualization.getMethodNameFormat();
		Node right = (Node) Reflection.callMethod(attributeVisualization, String.format(methodNameFormat, STRING_RESOURCE_BUNDLE.getFromAttributeTypes(attributeReader.getName())));			
		
		try {
			finalViewer.getChildren().add(right);
		} catch (NullPointerException e) {
			right = new Label(STRING_RESOURCE_BUNDLE.getFromStringConstants("NoAttribute"));
			finalViewer.getChildren().add(right);
		}
		return finalViewer;
	}

	public VBox get() {
		return myRoot;
	}

}
