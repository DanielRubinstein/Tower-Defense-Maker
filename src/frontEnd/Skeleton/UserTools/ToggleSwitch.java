package frontEnd.Skeleton.UserTools;

import backEnd.Mode.ModeEditor;
import frontEnd.ViewEditor;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/*
 * 
 * 
 * Heavy influence from:
 * https://gist.github.com/TheItachiUchiha/12e40a6f3af6e1eb6f75#file-toggleswitch-L9
 */
public class ToggleSwitch {
	private HBox toggle;
	private Label label;
	private Button button;
	private ModeEditor myMode;

	private SimpleBooleanProperty switchedOn;

	public SimpleBooleanProperty getSwitchOnProperty() {
		return switchedOn;
	}

	public ToggleSwitch(ModeEditor mode, String title1, String title2) {
		myMode = mode;
		toggle = new HBox();
		label = new Label();
		button = new Button();
		switchedOn = new SimpleBooleanProperty(false);

		init(title1, title2);

		switchedOn.addListener((a, oldValue, newValue) -> {
			if (newValue) {
				label.setText(title2);
				toggle.setStyle("-fx-background-color: green;");
				label.toFront();
			} else {
				label.setText(title1);
				toggle.setStyle("-fx-background-color: grey;");
				button.toFront();
			}
		});
	}

	private void init(String title1, String title2) {

		label.setText(title1);

		toggle.getChildren().addAll(label, button);
		
		
		
		button.setOnAction((e) -> {
			myMode.toggleMode();
			switchedOn.set(!switchedOn.get());
		});
		label.setOnMouseClicked((e) -> {
			myMode.toggleMode();
			switchedOn.set(!switchedOn.get());
		});
		setStyle();
		bindSizeProperties();
	}

	private void setStyle() {
		// Default Width
		toggle.setPrefWidth(80);
		toggle.setMaxWidth(80);
		label.setAlignment(Pos.CENTER);
		toggle.setStyle("-fx-background-color: grey; -fx-text-fill:black; -fx-background-radius: 4;");
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
