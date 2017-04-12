package frontEnd.CustomJavafxNodes;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.TextInputDialog;

public class SingleFieldPrompt {
	private List<String> myDialogTitles;
	private String myPromptText;
	private String myPromptLabel;

	public SingleFieldPrompt(List<String> dialogTitles, String promptLabel, String promptText){
		this.myDialogTitles = dialogTitles;
		this.myPromptText = promptText;
		this.myPromptLabel = promptLabel;
	}
	
	public String create(){
		TextInputDialog dialog = new TextInputDialog(myPromptText);
		dialog.setTitle(myDialogTitles.get(0));
		dialog.setHeaderText(myDialogTitles.get(1));
		dialog.setContentText(myPromptLabel);

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    return result.get();
		}
		return null;
	}
}
