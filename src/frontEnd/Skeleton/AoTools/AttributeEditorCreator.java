package frontEnd.Skeleton.AoTools;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;
import java.util.MissingResourceException;

import ModificationFromUser.Modification_EditAttribute;
import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeOwner;
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
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class AttributeEditorCreator {
	private View myView;
	private AttributeOwner myOwner;
	private Attribute<?> myAttr;
	public static final String SAVED_IMAGES_DIRECTORY = "./src/resources/images";
	private ToggleSwitch myToggle;

	public AttributeEditorCreator(View view, AttributeOwner obj, Attribute<?> attr){
		myView = view;
		myOwner = obj;
		myAttr = attr;
	}
	
	public Node extractEditor(String type) {
		Node n = null;
		String stringFormatForEditorMethod = "create_%s_Editor";
		try{
			Method createEditor = AttributeEditorCreator.class.getDeclaredMethod(String.format(stringFormatForEditorMethod, type));
			createEditor.setAccessible(true);
			n = (Node) createEditor.invoke(this);
		} catch (NoSuchMethodException e){
			System.out.println("attribute type not yet coded");
		} catch (Exception e){
			// fuck
		}
		return n;
	}

	private Node create_COMPONENT_Editor() {
		return new Label("inDev");
	}

	private Node create_POSITION_Editor() {
		String stringFormatter = "(%.0f, %.0f)";
		try{
			Point2D pos = (Point2D) myAttr.getValue();
			Button b = new Button(String.format(stringFormatter, pos.getX(), pos.getY()));
			b.setOnAction(e -> {
				Point2D newPoint = PositionRequester.askUserForPosition(pos);
				sendModification(newPoint);
				b.setText(String.format(stringFormatter, newPoint.getX(), newPoint.getY()));
			});
			return b;
		} catch (NullPointerException | MissingResourceException e ){
			return new Label("NoneSet");
		}
	}

	private Node create_INTEGER_Editor() {
		Node n;
		List<Integer> paramList = (List<Integer>) myAttr.getEditParameters();
		NumberChanger numChanger = new NumberChanger(paramList.get(0).doubleValue(), paramList.get(1).doubleValue(), 
				paramList.get(2).doubleValue(), paramList.get(3).doubleValue());
		n = numChanger.addIntegerIndicator();
		numChanger.addListener((o, oldValue, newValue)  -> {
			sendModification(newValue.intValue());
		});
		return n;
	}

	private Node create_EDITABLESTRING_Editor() {
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

	private Node create_STRINGLIST_Editor() {
		Node n;
		List<String> editParameters = (List<String>) myAttr.getEditParameters();
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
		n = optionsBox;
		return n;
	}

	private HBox create_IMAGE_Editor() {
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
				String newPath = selectedFile.getPath();
				String newValue = newPath.substring(newPath.indexOf("resources"), newPath.length());
				sendModification(newValue);
				Image newImage = new Image(getClass().getClassLoader().getResourceAsStream(newValue));
				imv.setImage(newImage);
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

	private Node create_DOUBLE_Editor() {
		Node n;
		List<Double> paramList = (List<Double>) myAttr.getEditParameters();
		System.out.println(" %%%% " + myAttr +"    "  +myAttr.getName() +"   " +paramList);
		NumberChanger numChanger = new NumberChanger(paramList.get(0), paramList.get(1), paramList.get(2),
				paramList.get(3));
		n = numChanger.addDoubleIndicator();
		numChanger.addListener((o, oldValue, newValue)  -> {
			sendModification(newValue);
		});
		return n;
	}

	private Node create_BOOLEAN_Editor() {
		myToggle = new ToggleSwitch("Off", "On", (Boolean) myAttr.getValue(), () -> triggerBooleanUpdate());
		return myToggle.getRoot();
	}
	
	private void triggerBooleanUpdate() {
		sendModification(myToggle.getSwitchedOn().getValue());
	}

	private void sendModification(Object newValue){
		myView.sendUserModification(new Modification_EditAttribute((AttributeOwner) myOwner, myAttr, newValue));
	}

}
