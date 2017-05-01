package frontEnd.CustomJavafxNodes;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import resources.constants.StringResourceBundle;

/*
 * 
 * 
 * Heavy influence from:
 * https://gist.github.com/TheItachiUchiha/12e40a6f3af6e1eb6f75#file-toggleswitch-L9
 */
public class ToggleSwitch {
	private StringResourceBundle stringResourceBundle = new StringResourceBundle();
	private HBox toggle;
	private Label label;
	private Button button;
	private Runnable myR;
	private SimpleBooleanProperty switchedOn;
	

	public SimpleBooleanProperty getSwitchedOn() {
		return switchedOn;
	}

	public ToggleSwitch(String title1, String title2, SimpleBooleanProperty booleanProperty, Runnable r) {
		toggle = new HBox();
		label = new Label();
		button = new Button();
		switchedOn = booleanProperty;
		myR = r;

		init(title1, title2);

		switchedOn.addListener((a, oldValue, newValue) -> {
			bringCorrespondingOptionToFront(title1, title2, newValue);
		});
		
		bringCorrespondingOptionToFront(title1, title2, switchedOn.get());
	}

	public ToggleSwitch(String title1, String title2, Boolean value, Runnable modRunnable) {
		this(title1, title2, new SimpleBooleanProperty(value), modRunnable);
		myR = () -> {
			switchedOn.set(!switchedOn.get());
			modRunnable.run();
		};
		
	}

	private void bringCorrespondingOptionToFront(String title1, String title2, Boolean newValue) {
		if (newValue) {
			label.setText(title2);
			toggle.setStyle(stringResourceBundle.getFromCustomCSS("toggleOnBackground"));
			label.toFront();
		} else {
			label.setText(title1);
			toggle.setStyle(stringResourceBundle.getFromCustomCSS("toggleOffBackground"));
			button.toFront();
		}
	}

	private void init(String title1, String title2) {
		label.setText(title1);

		toggle.getChildren().addAll(label, button);
		
		button.setOnAction((e) -> {
			myR.run();
		});
		label.setOnMouseClicked((e) -> {
			myR.run();
		});
		
		setStyle();
		bindSizeProperties();
	}

	private void setStyle() {		
		// Default Width
		toggle.setMinWidth(80);
		//toggle.setMaxWidth(80);
		label.setAlignment(Pos.CENTER);
		toggle.setStyle(stringResourceBundle.getFromCustomCSS("toggleCSS"));
		toggle.setAlignment(Pos.CENTER_LEFT);
	}

	private void bindSizeProperties() {
		label.prefWidthProperty().bind(toggle.widthProperty().divide(2));
		label.prefHeightProperty().bind(toggle.heightProperty());
		button.prefWidthProperty().bind(toggle.widthProperty().divide(2));
		button.prefHeightProperty().bind(toggle.heightProperty());
	}

	public Node getRoot() {
		return this.toggle;
	}
}
