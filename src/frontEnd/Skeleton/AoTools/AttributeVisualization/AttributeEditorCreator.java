package frontEnd.Skeleton.AoTools.AttributeVisualization;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;

import ModificationFromUser.AttributeOwner.Modification_EditAttribute;
import backEnd.Attribute.AttributeOwnerReader;
import backEnd.Attribute.AttributeReader;
import backEnd.GameData.State.Component;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.ActionButton;
import frontEnd.CustomJavafxNodes.NumberChanger;
import frontEnd.CustomJavafxNodes.PositionRequester;
import frontEnd.CustomJavafxNodes.ToggleSwitch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import resources.constants.StringResourceBundle;
import resources.constants.numeric.NumericResourceBundle;

public class AttributeEditorCreator implements AttributeVisualization{
	private View myView;
	private AttributeOwnerReader myAttributeOwnerReader;
	private AttributeReader<?> myAttributeReader;
	private static final StringResourceBundle STRING_RESOURCE_BUNDLE = new StringResourceBundle();
	private static final String SAVED_IMAGES_DIRECTORY = STRING_RESOURCE_BUNDLE.getFromFilePaths("Images_Path");
	private static final NumericResourceBundle NUMERIC_RESOURCE_BUNDLE = new NumericResourceBundle();
	private ToggleSwitch myToggle;

	public AttributeEditorCreator(View view, AttributeOwnerReader obj, AttributeReader<?> attr){
		myView = view;
		myAttributeOwnerReader = obj;
		myAttributeReader = attr;
	}
	
	public String getMethodNameFormat() {
		return STRING_RESOURCE_BUNDLE.getFromStringConstants("AttributeMethod");
	}
	
	private HBox createVisualPair(Map<HBox, Component> toCompMap, Map<Component, HBox> toPairMap, Component preset){
		HBox pair = new HBox();
		
		Label name = new Label();
		name.setText(myView.getBankControllerReader().getPresetName(preset));
		pair.getChildren().add(name);
		
		String presetImagePath = preset.<String>getAttribute(STRING_RESOURCE_BUNDLE.getFromAttributeNames("ImageFile")).getValue();
		ImageView imv = createImageView(presetImagePath);
		pair.getChildren().add(imv);
		
		pair.setStyle(STRING_RESOURCE_BUNDLE.getFromCustomCSS("darkBackground"));
		pair.setAlignment(Pos.CENTER_LEFT);
		
		toCompMap.put(pair, preset);
		toPairMap.put(preset, pair);
		return pair;
	}
	
	private ImageView createImageView(String imagePath) {
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(imagePath));
		ImageView imv = new ImageView();
		imv.setImage(image);
		imv.setPreserveRatio(true);
		imv.setFitHeight(NUMERIC_RESOURCE_BUNDLE.getFromSizing("ImageHeight"));
		return imv;
	}

	private List<String> getStringListOptions() {
		if(myAttributeReader.getName().equals(STRING_RESOURCE_BUNDLE.getFromAttributeNames("SpawnTimeline"))){
			return new ArrayList<String>(myView.getSpawnQueues().keySet());
		}
		return (List<String>) myAttributeReader.getEditParameters();
	}
	
	private void triggerBooleanUpdate() {
		sendModification(myToggle.getSwitchedOn().getValue());
	}

	private <T> void sendModification(T newValue){
		myView.sendUserModification(new Modification_EditAttribute<T>(myAttributeOwnerReader, myAttributeReader.getName(), newValue));
	}

	@Override
	public Node getIMAGE() {
		HBox both = new HBox();
		String imagePath = (String) myAttributeReader.getValue();
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(imagePath));
		ImageView imv = new ImageView();
		imv.setImage(image);
		imv.setPreserveRatio(true);
		
		Button b = new ActionButton(STRING_RESOURCE_BUNDLE.getFromStringConstants("ChangeImage"), () -> {
			promptUserForNewImageAndSendModification(imv);
		});
		bindHeights(imv, b);
		
		both.getChildren().add(imv);
		both.getChildren().add(b);		
		both.setSpacing(NUMERIC_RESOURCE_BUNDLE.getFromSizing("StandardSpacing"));
		return both;
	}

	private void bindHeights(ImageView imv, Button b) {
		// To bind the heights without the sizing screwing up
		b.setPrefHeight(NUMERIC_RESOURCE_BUNDLE.getFromSizing("ImageHeight"));
		imv.setFitHeight(b.getPrefHeight());
		b.heightProperty().addListener((o , oldV, newV) -> {
			imv.setFitHeight(newV.doubleValue());
		});
	}

	private void promptUserForNewImageAndSendModification(ImageView imv) {
		FileChooser imageChooser = new FileChooser();
		imageChooser.setTitle(STRING_RESOURCE_BUNDLE.getFromStringConstants("SelectImage"));
		imageChooser.getExtensionFilters().add(new ExtensionFilter("Image Files","*.png", "*.jpg", "*.gif")); // TODO string in resource file?
		imageChooser.setInitialDirectory(new File(SAVED_IMAGES_DIRECTORY));
		
		File selectedFile = imageChooser.showOpenDialog(new Stage());
		if(selectedFile == null){
			return;
		}
		String newImagePathRelative = getRelativePathToImageDirectory(selectedFile);
		Image newImage = new Image(getClass().getClassLoader().getResourceAsStream(newImagePathRelative));
		imv.setImage(newImage);
		sendModification(newImagePathRelative);
	}

	private String getRelativePathToImageDirectory(File selectedFile) {
		Path imageDirectory = Paths.get(SAVED_IMAGES_DIRECTORY).toAbsolutePath(); 
		Path newImagePath = Paths.get(selectedFile.getPath()).toAbsolutePath();
		File newImageFile = new File(SAVED_IMAGES_DIRECTORY + File.separator + imageDirectory.relativize(newImagePath).toString());
		String sourceFolderPath = STRING_RESOURCE_BUNDLE.getFromFilePaths("Source_Path");
		String newImagePathRelative = newImageFile.getPath().substring(newImageFile.getPath().indexOf(sourceFolderPath) + sourceFolderPath.length());
		return newImagePathRelative;
	}

	@Override
	public Node getCOMPONENT() {
		Map<HBox, Component> toCompMap = new HashMap<HBox, Component>();
		Map<Component, HBox> toPairMap = new HashMap<Component, HBox>();
		ComboBox<HBox> optionsBox = createComboBox(toCompMap, toPairMap);
		optionsBox.valueProperty().addListener((o, oldValue, newValue) -> {
			String componentName = myView.getBankControllerReader().getPresetName(toCompMap.get(newValue));
			sendModification(componentName);
		});
		
		try {
			String currentValue = (String) myAttributeReader.getValue();
			Component currentComponent = myView.getBankControllerReader().getComponent(currentValue);
			optionsBox.getSelectionModel().select(toPairMap.get(currentComponent));
		} catch (NullPointerException e) {
			// do nothing
		}
		
		return optionsBox;
	}

	private ComboBox<HBox> createComboBox(Map<HBox, Component> toCompMap, Map<Component, HBox> toPairMap) {
		Collection<HBox> visualPair = new ArrayList<HBox>();
		Collection<Component> presetComponents = myView.getBankControllerReader().getAccessibleComponentPresets();
		for(Component preset : presetComponents){
			visualPair.add(createVisualPair(toCompMap, toPairMap, preset));
		}
		ObservableList<HBox> options = (ObservableList<HBox>) FXCollections.observableArrayList(visualPair);
		ComboBox<HBox> optionsBox = new ComboBox<HBox>(options);
		return optionsBox;
	}

	@Override
	public Node getPOSITION() {
		String stringFormatter = STRING_RESOURCE_BUNDLE.getFromStringConstants("Position");
		try{
			Point2D pos = (Point2D) myAttributeReader.getValue();
			Button b = new Button(String.format(stringFormatter, pos.getX(), pos.getY()));
			b.setOnAction(e -> {
				PositionRequester positionRequester = new PositionRequester();
				Point2D newPoint = positionRequester.promptUser(pos);
				sendModification(newPoint);
				b.setText(String.format(stringFormatter, newPoint.getX(), newPoint.getY()));
			});
			return b;
		} catch (NullPointerException | MissingResourceException e ){
			return new Label(STRING_RESOURCE_BUNDLE.getFromStringConstants("NoneSelected"));
		}
	}

	@Override
	public Node getINTEGER() {
		return this.<Integer>createNumberChanger().addIntegerIndicator();
	}
	
	private <T extends Number> NumberChanger createNumberChanger(){
		List<T> paramList = (List<T>) myAttributeReader.getEditParameters();
		T currentValue = (T) myAttributeReader.getValue();
		NumberChanger numChanger = new NumberChanger(paramList.get(0), paramList.get(1), currentValue,
				paramList.get(3));
		numChanger.addListener((o, oldValue, newValue)  -> {
			if(Integer.class.equals(currentValue.getClass())){
				sendModification((int) Math.round((double)newValue));
			}else{
				sendModification(newValue);
			}
			
		});
		return numChanger;
		
	}
	
	@Override
	public Node getDOUBLE() {
		return this.<Double>createNumberChanger().addDoubleIndicator();
	}

	@Override
	public Node getBOOLEAN() {
		myToggle = new ToggleSwitch(STRING_RESOURCE_BUNDLE.getFromStringConstants("Off"), STRING_RESOURCE_BUNDLE.getFromStringConstants("On"), (Boolean) myAttributeReader.getValue(), () -> triggerBooleanUpdate());
		return myToggle.getRoot();
	}

	@Override
	public Node getSTRINGLIST() {
		List<String> editParameters = getStringListOptions();
		ObservableList<String> options = (ObservableList<String>) FXCollections.observableArrayList(editParameters);
		ComboBox<String> optionsBox = new ComboBox<String>(options);
		try {
			optionsBox.getSelectionModel().select((String) myAttributeReader.getValue());
		} catch (NullPointerException e) {
			// do nothing
		}
		optionsBox.valueProperty().addListener((o, oldValue, newValue) -> {
			sendModification(newValue);
		});
		return optionsBox;
	}

	@Override
	public Node getEDITABLESTRING() {
		HBox editor = new HBox();
		
		TextField textField = new TextField((String) myAttributeReader.getValue());
		Button submit = new Button(STRING_RESOURCE_BUNDLE.getFromStringConstants("Submit"));
		submit.setOnAction((e) -> {
			sendModification(textField.getText());
		});
		
		editor.getChildren().add(textField);
		editor.getChildren().add(submit);
		
		return editor;
	}

}

