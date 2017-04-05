package frontEnd.Menus;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	
	public void addSimpleButton(String text, EventHandler<ActionEvent> event){
   	 	Button myButton = new Button(text);
		myButton.setOnAction(event);
		myRoot.getChildren().add(myButton);
	}
	
	public void addButton(Button newButton){
		myRoot.getChildren().add(newButton);
	}
	
	public void addButtonRow(String rowTitle, Button... buttons){
		HBox box = new HBox();
		Label rowLabel = new Label(rowTitle);
		box.getChildren().add(rowLabel);
		box.getChildren().addAll(buttons);
		box.setAlignment(Pos.CENTER);
		myRoot.getChildren().add(box);
	}
	
	public void addNode(Node n){
		myRoot.getChildren().add(n);
	}
	
	public void addBackButton(EventHandler<ActionEvent> event) {
		this.addSimpleButton("Go Back", event);
		
	}
	
	public void create(Double size1, Double size2) {
   	 	myRoot.setAlignment(Pos.CENTER);
   	 	myScene = new Scene(myRoot, size1, size2);
   	 	setSpacing(size1);

	}
	private void setSpacing(Double size1){
		
		double spacing = myRoot.getChildren().size()==0 ? 50 : size1 / (myRoot.getChildren().size()*2);
		
		myRoot.setSpacing(spacing);
	}
	
	
	public Scene getScene(){
		return myScene;
	}


	public void create() {
		myRoot.setAlignment(Pos.CENTER);
   	 	myScene = new Scene(myRoot);
	}


	
}
