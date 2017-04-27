package frontEnd.CustomJavafxNodes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import resources.constants.StringResourceBundle;

/**
 * 
 * @author Miguel Anderson and http://code.makery.ch/blog/javafx-dialogs-official/
 *
 */
public class DoubleFieldPrompt {
	private StringResourceBundle stringResourceBundle = new StringResourceBundle();
	
	private List<String> myDialogTitles;
	private List<String> myPromptText;
	private List<String> myPromptLabel;

	public DoubleFieldPrompt(List<String> dialogTitles, List<String> promptText, List<String> promptLabel){
		this.myDialogTitles = dialogTitles;
		this.myPromptText = promptText;
		this.myPromptLabel = promptLabel;
	}

	public List<String> create() {
		// Create the custom dialog.
		Dialog<List<String>> dialog = new Dialog<>();
		dialog.setTitle(myDialogTitles.get(0));
		dialog.setHeaderText(myDialogTitles.get(1));

		/*
		 * // Set the icon (must be included in the project).
		 * dialog.setGraphic(new
		 * ImageView(this.getClass().getResource("login.png").toString()));
		 */

		// Set the button types.
		ButtonType createButtonType = new ButtonType(stringResourceBundle.getString("Submit"), ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField text1 = new TextField();
		text1.setPromptText(myPromptText.get(0));
		TextField text2 = new TextField();
		text2.setPromptText(myPromptText.get(1));

		grid.add(new Label(myPromptLabel.get(0)), 0, 0);
		grid.add(text1, 1, 0);
		grid.add(new Label(myPromptLabel.get(1)), 0, 1);
		grid.add(text2, 1, 1);

		// Enable/Disable login button depending on whether a username was
		// entered.
		Node createButton = dialog.getDialogPane().lookupButton(createButtonType);
		createButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		text1.textProperty().addListener((SerializableObservable, oldValue, newValue) -> {
			createButton.setDisable(newValue.trim().isEmpty() && text2.getText().trim().isEmpty());
		});
		text2.textProperty().addListener((SerializableObservable, oldValue, newValue) -> {
			createButton.setDisable(newValue.trim().isEmpty() && text1.getText().trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		// Convert the result to a username-password-pair when the login button
		// is clicked.
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == createButtonType) {
				return Arrays.asList(text1.getText().trim(), text2.getText().trim());
			}
			return null;
		});

		Optional<List<String>> result = dialog.showAndWait();

		if (result.isPresent()) {
			return result.get();
		}
		return null;
	}
}
