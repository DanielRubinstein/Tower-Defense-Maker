package frontEnd.CustomJavafxNodes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ActionButton extends Button{
	
	public ActionButton(EventHandler<ActionEvent> eventHandler){
		this.setOnAction(eventHandler);
	}
	
	public ActionButton(String title, EventHandler<ActionEvent> eventHandler){
		this(eventHandler);
		this.setText(title);
	}
	
	public ActionButton(Runnable r){
		this.setOnAction(e -> r.run());
	}
	
	public ActionButton(String title, Runnable r){
		this(e -> r.run());
		this.setText(title);
	}
}
