package frontEnd.CustomJavafxNodes;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import resources.constants.StringConstants;

public class ErrorDialog {
	private StringConstants stringConstants = new StringConstants();
	private Alert alert;

	public ErrorDialog(){
		alert = new Alert(AlertType.ERROR);
		alert.setTitle(stringConstants.getString("ErrorDialogTitle"));
	}
	
	public void create(String headerText, String contentText){
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}
}
