package frontEnd.Skeleton.UserTools;

import java.util.List;

import ModificationFromUser.Modification_EditAttribute;
import backEnd.Attribute.Attribute;
import backEnd.Attribute.AttributeOwner;
import backEnd.Attribute.AttributeOwnerReader;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.NumberChanger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class EditorCreator {
	private View myView;
	private AttributeOwnerReader myOwner;
	private Attribute<?> myAttr;

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
		// TODO Auto-generated method stub
		return null;
	}

	private Node createEditableStringEditor() {
		HBox editor = new HBox();
		
		TextField textField = new TextField((String) myAttr.getValue());
		
		
		// TODO Auto-generated method stub
		return null;
	}

	private Node createStringListEditor() {
		Node n;
		List<String> editParameters = (List<String>) myAttr.getEditParameters();

		// TODO if doubles then make a slider not a combobox (this will be the
		// only separate case)
		ObservableList<String> options = (ObservableList<String>) FXCollections.observableArrayList(editParameters);
		ComboBox<String> optionsBox = new ComboBox<String>(options);
		try {
			// TODO this will work as long as there is an attribute there
			optionsBox.getSelectionModel().select(myAttr.getValue().toString());
		} catch (NullPointerException e) {
			// do nothing
		}
		optionsBox.valueProperty().addListener((o, oldValue, newValue) -> {
			// where the actual modification gets sent
			myView.sendUserModification(new Modification_EditAttribute((AttributeOwner) myOwner, myAttr, newValue));
		});
		n = optionsBox;
		return n;
	}

	private Node createImageEditor() {
		Node n;
		String imagePath = (String) myAttr.getValue();
		Label l = new Label(imagePath);
		n = l;
		return n;
	}

	private Node createDoubleEditor() {
		Node n;
		List<Double> paramList = (List<Double>) myAttr.getEditParameters();
		NumberChanger numChanger = new NumberChanger(paramList.get(0), paramList.get(1), paramList.get(2),
				paramList.get(3));
		n = numChanger.getRoot();
		return n;
	}

	private Node createBooleanEditor() {
		Node n = null;
		/*
		Consumer<ActionEvent> actionEventConsumer = (e) -> myView
				.sendUserModification(new Modification_EditAttribute<Boolean>(myOwner, myAttr, thing));
		Consumer<MouseEvent> mouseEventConsumer = (e) -> myView
				.sendUserModification(new Modification_EditAttribute<Boolean>(myOwner, myAttr, thing));
		
		ToggleSwitch myToggle = new ToggleSwitch(myView, "Off", "On",
				new SimpleBooleanProperty((Boolean) myAttr.getValue()), actionEventConsumer, mouseEventConsumer);
		thing = myToggle.getSwitchedOn().getValue();
		n = myToggle.getRoot();
		*/
		return n;
	}
}
