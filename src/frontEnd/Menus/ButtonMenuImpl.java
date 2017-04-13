package frontEnd.Menus;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ButtonMenuImpl implements ButtonMenu {
	public static final String DEFAULT_CSS = "/resources/css/Flatter.css";
	private GridPane myGrid;
	private VBox myButtonRoot;
	private Scene myScene;
	private Label titleLbl;
	private Label description;
	
	public ButtonMenuImpl(String text){
		initializeGrid();
		myButtonRoot = new VBox();
		description = new Label("Please Select an Option");
		description.setWrapText(true);
		setText(text);
	}
	

	private void initializeGrid() {
		myGrid = new GridPane();
	    myGrid.setHgap(10);
	    myGrid.setVgap(10);
	    myGrid.setPadding(new Insets(10));
	}


	public void setText(String text){
		titleLbl = new Label(text);
		titleLbl.setFont(Font.font(32));
		titleLbl.setUnderline(true);
		//titleLbl.setStyle("");
	}
	
	public void addSimpleButtonWithHover(String text, EventHandler<ActionEvent> event, String hoverText){
		Button b = new Button(text);
		b.setOnAction(event);
		addButtonWithHover(b, hoverText);
	}
	
	public void addButtonWithHover(Button newButton, String hoverText){
		newButton.hoverProperty().addListener((event, oldVal, newVal) -> {
			if(newVal){
				description.setText(hoverText);
			} else {
				description.setText("Please Select an Option");
			}
		});
		addButton(newButton);
	}
	
	@Override
	public void addSimpleButton(String text, EventHandler<ActionEvent> event){
   	 	Button myButton = new Button(text);
		myButton.setOnAction(event);
		myButtonRoot.getChildren().add(myButton);
	}
	
	@Override
	public void addButton(Button newButton){
		myButtonRoot.getChildren().add(newButton);
	}
	
	@Override
	public void addButtonRow(String rowTitle, Button... buttons){
		HBox box = new HBox();
		Label rowLabel = new Label(rowTitle);
		box.getChildren().add(rowLabel);
		box.getChildren().addAll(buttons);
		box.setAlignment(Pos.CENTER);
		myButtonRoot.getChildren().add(box);
	}
	
	@Override
	public void addNode(Node n){
		myButtonRoot.getChildren().add(n);
	}
	
	public void addBackButton(EventHandler<ActionEvent> event) {
		this.addSimpleButton("Go Back", event);
		
	}
	

	private void setSpacing(Double size1){
		
		double spacing = myButtonRoot.getChildren().size()==0 ? 50 : size1 / (myButtonRoot.getChildren().size()*2);
		
		myButtonRoot.setSpacing(spacing);
	}
	
	
	public void display(Stage stage){
		if(myScene == null){
			create();
		}
		
		stage.setScene(myScene);
		stage.show();
	}


	private void create() {
		
		setSpacing(200d);
		
		myGrid.add(titleLbl, 0, 0, 2, 1);
		myGrid.add(myButtonRoot, 0, 1);
		myGrid.add(description, 1, 1);
		myGrid.add(new Label("voogasalad_sup3rs1ckt34m1337"), 0, 2, 2, 1);
		//myButtonRoot.setAlignment(Pos.CENTER);
   	 	myScene = new Scene(myGrid);
   	 	myScene.getStylesheets().add(DEFAULT_CSS);
		description.setMaxWidth(200d);
	}


	
}
