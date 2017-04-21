package frontEnd.CustomJavafxNodes;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public interface ButtonMenu {

	public void addSimpleButton(String text, Runnable event);
	
	public void addButton(Button newButton);
	
	public void addButtonRow(String rowTitle, Button... buttons);
	
	public void addNode(Node n);
	
	public void display(Stage stage);
}