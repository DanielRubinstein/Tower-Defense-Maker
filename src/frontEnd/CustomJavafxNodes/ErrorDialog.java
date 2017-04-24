package frontEnd.CustomJavafxNodes;

import java.util.ResourceBundle;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ErrorDialog {
	private static final String BUNDLE_NAME = "resources.messages";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	private Alert alert;

	public ErrorDialog(){
		alert = new Alert(AlertType.ERROR);
		alert.setTitle(RESOURCE_BUNDLE.getString("ErrorDialogTitle"));
	}
	
	public void create(String headerText, String contentText){
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}
}
