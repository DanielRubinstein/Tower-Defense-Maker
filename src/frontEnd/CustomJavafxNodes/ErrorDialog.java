package frontEnd.CustomJavafxNodes;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ErrorDialog {
	private Alert alert;

	public ErrorDialog(){
		alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Dialog");
	}
	
	public void create(String headerText, String contentText){
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}
}
