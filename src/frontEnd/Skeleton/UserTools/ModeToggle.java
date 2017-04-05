package frontEnd.Skeleton.UserTools;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ModeToggle {
	private HBox toggle;

	public ModeToggle(String title1, EventHandler<ActionEvent> opt1, String title2, EventHandler<ActionEvent> opt2){
		Button toggle1 = new Button(title1);
		toggle1.setOnAction(opt1);
		Button toggle2 = new Button(title2);
		toggle2.setOnAction(opt2);
		toggle = new HBox();
		toggle.setAlignment(Pos.CENTER);
		toggle.getChildren().addAll(toggle1,toggle2);
	}
	
	public Node getRoot(){
		return this.toggle;
	}
}
