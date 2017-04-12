package frontEnd.Skeleton.AoTools;

import java.io.File;
import java.util.List;

import ModificationFromUser.Modification_EditAttribute;
import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.NumberChanger;
import frontEnd.CustomJavafxNodes.ToggleSwitch;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class EditorCreator {
	private View myView;
	private AttributeOwnerReader myOwner;
	private Attribute<?> myAttr;
	public static final String SAVED_IMAGES_DIRECTORY = "./src/images";
	private ToggleSwitch myToggle;

	public EditorCreator(View view, AttributeOwnerReader obj, Attribute<?> attr){
		myView = view;
		myOwner = obj;
		myAttr = attr;
	}
	
	public Node extractEditor(String type) {
		Node n = null;
		switch (type) {
		case "BOOLEAN":
			n = createBooleanEditor();
			break;
		case "DOUBLE":
			n = createDoubleEditor();
			break;
		case "EDITABLESTRING":
			n = createEditableStringEditor();
			break;
		case "IMAGE":
			n = createImageEditor();
			break;
		case "INTEGER":
			n = createIntegerEditor();
			break;
		case "STRINGLIST":
			n = createStringListEditor();
			break;
		default:
			break;
		}
		return n;
	}

	private Node createIntegerEditor() {
		Node n;
		List<Double> paramList = (List<Double>) myAttr.getEditParameters();
		NumberChanger numChanger = new NumberChanger(paramList.get(0), paramList.get(1), paramList.get(2),
				paramList.get(3));
		n = numChanger.getRoot();
		numChanger.addListener((o, oldValue, newValue)  -> {
			sendModification(newValue.intValue());
		});
		return n;
	}

	private Node createEditableStringEditor() {
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

	private Node createStringListEditor() {
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

	private Node createImageEditor() {
		String imagePath = (String) myAttr.getValue();
		System.out.println(imagePath);
		Button b = new Button(imagePath);
		b.setOnAction(e -> {
			FileChooser imageChooser = new FileChooser();
			imageChooser.setTitle("Select Image");
			imageChooser.getExtensionFilters().add(new ExtensionFilter("Image Files","*.png", "*.jpg", "*.gif"));
			imageChooser.setInitialDirectory(new File(SAVED_IMAGES_DIRECTORY));
			
			File selectedFile = imageChooser.showOpenDialog(new Stage());
			String newPath = selectedFile.getPath();
			//TODO: get only the end of the path
			sendModification(newPath);
			System.out.println(newPath);
		});

		return b;
	}

	private Node createDoubleEditor() {
		Node n;
		List<Double> paramList = (List<Double>) myAttr.getEditParameters();
		NumberChanger numChanger = new NumberChanger(paramList.get(0), paramList.get(1), paramList.get(2),
				paramList.get(3));
		n = numChanger.getRoot();
		numChanger.addListener((o, oldValue, newValue)  -> {
			sendModification(newValue);
		});
		return n;
	}

	private Node createBooleanEditor() {
		Node n = null;
		myToggle = new ToggleSwitch(myView, "Off", "On",
				new SimpleBooleanProperty((Boolean) myAttr.getValue()), () -> triggerBooleanUpdate());
		n = myToggle.getRoot();
		return n;
	}
	
	private void triggerBooleanUpdate() {
		sendModification(myToggle.getSwitchedOn().getValue());
	}

	private void sendModification(Object newValue){
		myView.sendUserModification(new Modification_EditAttribute((AttributeOwner) myOwner, myAttr, newValue));
	}

}
