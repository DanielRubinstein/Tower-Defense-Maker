package frontEnd.Menus;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public interface ButtonMenu {

	public void addSimpleButton(String text, EventHandler<ActionEvent> event);
	
	public void addButton(Button newButton);
	
	public void addButtonRow(String rowTitle, Button... buttons);
	
	public void addNode(Node n);
	
	public void display(Stage stage);
}
