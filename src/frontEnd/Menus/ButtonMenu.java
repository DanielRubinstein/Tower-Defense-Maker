package frontEnd.Menus;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ButtonMenu {
	private VBox myRoot;
	private Scene myScene;
	
	public ButtonMenu(){
		myRoot = new VBox();
	}

	public void setText(String text){
		Text title = new Text(text);
		title.setTextAlignment(TextAlignment.CENTER);
		//title.setFont(font);
		myRoot.getChildren().add(title);
	}

	public void create(Double size1, Double size2) {
   	 	myRoot.setSpacing(50);
   	 	myRoot.setAlignment(Pos.CENTER);
   	 	myScene = new Scene(myRoot, size1, size2);
	}
	
	public void addButton(String text, EventHandler<ActionEvent> event){
   	 	Button myButton = new Button(text);
		myButton.setOnAction(event);
		myRoot.getChildren().add(myButton);
	}
	
	public Scene getScene(){
		return myScene;
	}

	public void addBackButton(EventHandler<ActionEvent> event) {
		this.addButton("Back", event);
		
	}
	
}
