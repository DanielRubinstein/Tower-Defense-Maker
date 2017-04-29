package frontEnd.Skeleton.AoTools.AttributeVisualization;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;

import ModificationFromUser.AttributeOwner.Modification_EditAttribute;
import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentImpl;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.NumberChanger;
import frontEnd.CustomJavafxNodes.PositionRequester;
import frontEnd.CustomJavafxNodes.ToggleSwitch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
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

public class AttributeEditorCreator implements AttributeVisualization{
	private View myView;
	private AttributeOwner myOwner;
	private Attribute<?> myAttr;
	public static final String SAVED_IMAGES_DIRECTORY = "src" + File.separator + "resources" + File.separator + "images";
	public static final String CLASS_LOADER_DIRECTORY = "resources" + File.separator + "images" + File.separator;
	private ToggleSwitch myToggle;

	public AttributeEditorCreator(View view, AttributeOwner obj, Attribute<?> attr){
		myView = view;
		myOwner = obj;
		myAttr = attr;
	}
	
	public String getMethodNameFormat() {
		return "get%s";
	}
	
	private HBox createVisualPair(Map<HBox, ComponentImpl> toCompMap, Map<ComponentImpl, HBox> toPairMap, ComponentImpl preset){
		HBox pair = new HBox();
		
		Label name = new Label();
		name.setText(myView.getBankController().getAOName(preset));
		pair.getChildren().add(name);
		
		String presetImagePath = preset.<String>getAttribute("ImageFile").getValue();
		ImageView imv = createImageView(presetImagePath);
		pair.getChildren().add(imv);
		
		pair.setStyle("-fx-background-color: black");
		
		toCompMap.put(pair, preset);
		toPairMap.put(preset, pair);
		return pair;
	}
	
	private ImageView createImageView(String imagePath) {
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(imagePath));
		ImageView imv = new ImageView();
		imv.setImage(image);
		imv.setPreserveRatio(true);
		imv.setFitHeight(100);
		return imv;
	}

	private List<String> getStringListOptions() {
		if(myAttr.getName().equals("SpawnTimeline")){
			return new ArrayList<String>(myView.getSpawnQueues().keySet());
		}
		return (List<String>) myAttr.getEditParameters();
	}
	
	private void triggerBooleanUpdate() {
		sendModification(myToggle.getSwitchedOn().getValue());
	}

	private void sendModification(Object newValue){
		myView.sendUserModification(new Modification_EditAttribute((AttributeOwner) myOwner, myAttr, newValue));
	}

	@Override
	public Node getIMAGE() {
		HBox both = new HBox();
		String imagePath = (String) myAttr.getValue();
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(imagePath));
		ImageView imv = new ImageView();
		imv.setImage(image);
		imv.setPreserveRatio(true);
		
		Button b = new Button("Change Image");
		b.setOnAction(e -> {
			FileChooser imageChooser = new FileChooser();
			imageChooser.setTitle("Select Image");
			imageChooser.getExtensionFilters().add(new ExtensionFilter("Image Files","*.png", "*.jpg", "*.gif"));
			imageChooser.setInitialDirectory(new File(SAVED_IMAGES_DIRECTORY));
			
			File selectedFile = imageChooser.showOpenDialog(new Stage());
			try{
				String newPath = CLASS_LOADER_DIRECTORY + selectedFile.getParentFile().getName()+ File.separator + selectedFile.getName();
				
				Image newImage = new Image(getClass().getClassLoader().getResourceAsStream(newPath));
				imv.setImage(newImage);
				sendModification(newPath);
				
			}
			catch (NullPointerException exception){
				System.out.println("Did not select an image- SAD!");
			}

		});
		
		
		// To bind the heights without the sizing screwing up
		b.setPrefHeight(70);
		imv.setFitHeight(b.getPrefHeight());
		b.heightProperty().addListener((o , oldV, newV) -> {
			imv.setFitHeight(newV.doubleValue());
		});
		
		both.getChildren().add(imv);
		both.getChildren().add(b);		
		both.setSpacing(20);
		return both;
	}

	@Override
	public Node getDOUBLE() {
		Node n;
		List<Double> paramList = (List<Double>) myAttr.getEditParameters();
		Double curValue = (Double) myAttr.getValue();
		NumberChanger numChanger = new NumberChanger(paramList.get(0), paramList.get(1), curValue,
				paramList.get(3));
		n = numChanger.addDoubleIndicator();
		numChanger.addListener((o, oldValue, newValue)  -> {
			sendModification(newValue);
		});
		return n;
	}

	@Override
	public Node getCOMPONENT() {
		Map<HBox, ComponentImpl> toCompMap = new HashMap<HBox, ComponentImpl>();
		Map<ComponentImpl, HBox> toPairMap = new HashMap<ComponentImpl, HBox>();
		Collection<ComponentImpl> presetComponents = myView.getBankController().getComponentMap().values();
		Collection<HBox> visualPair = new ArrayList<HBox>();
		for(ComponentImpl preset : presetComponents){
			visualPair.add(createVisualPair(toCompMap, toPairMap, preset));
		}
		ObservableList<HBox> options = (ObservableList<HBox>) FXCollections.observableArrayList(visualPair);
		ComboBox<HBox> optionsBox = new ComboBox<HBox>(options);
		try {
			// TODO this will work as long as there is an attribute there
			optionsBox.getSelectionModel().select(toPairMap.get((String) myAttr.getValue()));
		} catch (NullPointerException e) {
			// do nothing
		}
		optionsBox.valueProperty().addListener((o, oldValue, newValue) -> {
			// where the actual modification gets sent
			String componentName = myView.getBankControllerReader().getComponentName(toCompMap.get(newValue));
			sendModification(componentName);
		});
		return optionsBox;
	}

	@Override
	public Node getPOSITION() {
		String stringFormatter = "(%.0f, %.0f)";
		try{
			Point2D pos = (Point2D) myAttr.getValue();
			Button b = new Button(String.format(stringFormatter, pos.getX(), pos.getY()));
			b.setOnAction(e -> {
				PositionRequester positionRequester = new PositionRequester();
				Point2D newPoint = positionRequester.promptUser(pos);
				sendModification(newPoint);
				b.setText(String.format(stringFormatter, newPoint.getX(), newPoint.getY()));
			});
			return b;
		} catch (NullPointerException | MissingResourceException e ){
			return new Label("NoneSet");
		}
	}

	@Override
	public Node getINTEGER() {
		Node n;
		List<Integer> paramList = (List<Integer>) myAttr.getEditParameters();
		Integer curValue = (Integer) myAttr.getValue();
		NumberChanger numChanger = new NumberChanger(paramList.get(0).doubleValue(), paramList.get(1).doubleValue(), 
				curValue, paramList.get(3).doubleValue());
		n = numChanger.addIntegerIndicator();
		numChanger.addListener((o, oldValue, newValue)  -> {
			sendModification(newValue.intValue());
		});
		return n;
	}

	@Override
	public Node getBOOLEAN() {
		myToggle = new ToggleSwitch("Off", "On", (Boolean) myAttr.getValue(), () -> triggerBooleanUpdate());
		return myToggle.getRoot();
	}

	@Override
	public Node getSTRINGLIST() {
		List<String> editParameters = getStringListOptions();
		ObservableList<String> options = (ObservableList<String>) FXCollections.observableArrayList(editParameters);
		ComboBox<String> optionsBox = new ComboBox<String>(options);
		try {
			// TODO this will work as long as there is an attribute there
			optionsBox.getSelectionModel().select((String) myAttr.getValue());
		} catch (NullPointerException e) {
			// do nothing
		}
		optionsBox.valueProperty().addListener((o, oldValue, newValue) -> {
			// where the actual modification gets sent
			sendModification(newValue);
		});
		return optionsBox;
	}

	@Override
	public Node getEDITABLESTRING() {
		HBox editor = new HBox();
		
		TextField textField = new TextField((String) myAttr.getValue());
		Button submit = new Button("Submit");
		submit.setOnAction((e) -> {
			sendModification(textField.getText());
		});
		
		editor.getChildren().add(textField);
		editor.getChildren().add(submit);
		
		return editor;
	}

}

