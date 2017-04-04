package frontEnd.Menus;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
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
	public void addTwoButtons(String text1, EventHandler<ActionEvent> event1,String text2, EventHandler<ActionEvent> event2){
		HBox box = new HBox();
		Button button1 = new Button(text1);
		button1.setOnAction(event1);
		box.getChildren().add(button1);
		Button button2 = new Button(text2);
		button2.setOnAction(event2);
		box.getChildren().add(button2);
		
		
		myRoot.getChildren().add(box);
	}
	public void addButton(Button b){
		myRoot.getChildren().add(b);
	}
	public void addNodeButton(HBox n){
		System.out.println("adding node to butotnmenu");
		n.setAlignment(Pos.CENTER);
		myRoot.getChildren().add(n);
	}
	
	public Scene getScene(){
		return myScene;
	}

	public void addBackButton(EventHandler<ActionEvent> event) {
		this.addButton("Back", event);
		
	}
	
}
