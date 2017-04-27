package frontEnd.CustomJavafxNodes;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import resources.constants.StringResourceBundle;

public class ErrorDialog {
	private StringResourceBundle stringResourceBundle = new StringResourceBundle();
	private Alert alert;

	public ErrorDialog(){
		alert = new Alert(AlertType.ERROR);
		alert.setTitle(stringResourceBundle.getString("ErrorDialogTitle"));
	}
	
	public void create(String headerText, String contentText){
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}
}
