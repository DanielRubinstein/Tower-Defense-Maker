package frontEnd.CustomJavafxNodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import resources.constants.StringResourceBundle;
import resources.constants.numeric.NumericResourceBundle;

public class MultiFieldPrompt {
	private StringResourceBundle stringResourceBundle = new StringResourceBundle();
	private NumericResourceBundle numericResourceBundle = new NumericResourceBundle();
	private Double standardSpacing = numericResourceBundle.getFromSizing("StandardSpacing");
	
	private List<String> myDialogTitles;
	private List<String> myPromptText;
	private List<String> myPromptLabel;
	
	private List<TextField> myTextFields;
	
	private Integer myNumberOfFields;

	public MultiFieldPrompt(Integer numOfFields, List<String> dialogTitles, List<String> promptText, List<String> promptLabel){
		this.myNumberOfFields = numOfFields;
		this.myDialogTitles = dialogTitles;
		this.myPromptText = promptText;
		this.myPromptLabel = promptLabel;
		this.myTextFields = new ArrayList<TextField>();
	}
	
	public List<String> create() {
		// Create the custom dialog.
		Dialog<List<String>> dialog = new Dialog<>();
		dialog.setTitle(myDialogTitles.get(0));
		dialog.setHeaderText(myDialogTitles.get(1));
		
		// Set the button types.
		ButtonType createButtonType = new ButtonType(stringResourceBundle.getFromStringConstants("Submit"), ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);
		Node createButton = dialog.getDialogPane().lookupButton(createButtonType);
		createButton.setDisable(true);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(standardSpacing);
		grid.setVgap(standardSpacing);
		grid.setPadding(new Insets(standardSpacing));
		
		for(int i = 0 ; i < this.myNumberOfFields ; i++){
			TextField text1 = new TextField();
			text1.setPromptText(myPromptText.get(i));
			grid.add(new Label(myPromptLabel.get(i)), 0, i);
			grid.add(text1, 1, i);
			text1.textProperty().addListener((SerializableObservable, oldValue, newValue) -> {
				createButton.setDisable(notReadyForSubmission());
			});
			myTextFields.add(text1);
		}
		

		dialog.getDialogPane().setContent(grid);

		// Convert the result to a username-password-pair when the login button
		// is clicked.
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == createButtonType) {
				List<String> results = new ArrayList<>();
				for(TextField textField : myTextFields){
					results.add(textField.getText().trim());
				}
				return results;
			}
			return null;
		});

		Optional<List<String>> result = dialog.showAndWait();

		if (result.isPresent()) {
			return result.get();
		}
		return null;

	}

	private boolean notReadyForSubmission() {
		for(TextField textField : myTextFields){
			if(!textField.getText().trim().isEmpty()){
				return false;
			}
		}
		return true;
	}

}
