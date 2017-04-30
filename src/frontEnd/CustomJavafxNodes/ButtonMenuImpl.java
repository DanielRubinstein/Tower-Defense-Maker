package frontEnd.CustomJavafxNodes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import resources.constants.StringResourceBundle;
import resources.constants.numeric.NumericResourceBundle;

public class ButtonMenuImpl implements ButtonMenu {
	private StringResourceBundle stringResourceBundle = new StringResourceBundle();
	private NumericResourceBundle numericResourceBundle = new NumericResourceBundle();
	private Double standardSpacing = numericResourceBundle.getFromSizing("StandardSpacing");
	private GridPane myGrid;
	private VBox myButtonRoot;
	private Scene myScene;
	private Label titleLbl;
	private Label description;
	private Boolean displayHoverDescription = false;
	
	public ButtonMenuImpl(String text){
		initializeGrid();
		myButtonRoot = new VBox();
		description = new Label(stringResourceBundle.getFromStringConstants("SelectAnOption"));
		description.setWrapText(true);
		setText(text);
	}
	
	public GridPane getGridPane(){
		return myGrid;
	}
	
	public VBox getButtonRoot(){
		return myButtonRoot;
	}
	

	private void initializeGrid() {
		myGrid = new GridPane();
	    myGrid.setHgap(standardSpacing);
	    myGrid.setVgap(standardSpacing);
	    myGrid.setPadding(new Insets(standardSpacing));
	    myGrid.setMinWidth(numericResourceBundle.getFromSizing("ButtonWidth"));
	}


	private void setText(String text){
		titleLbl = new Label(text);
		titleLbl.setFont(Font.font(numericResourceBundle.getFromSizing("TitleFontSize")));
		titleLbl.setUnderline(true);
		//titleLbl.setStyle("");
	}
	
	public void addSimpleButtonWithHover(String text, Runnable event, String hoverText){
		ActionButton b = new ActionButton(text, event);
		addButtonWithHover(b, hoverText);
	}
	
	public void addButtonWithHover(Button newButton, String hoverText){
		displayHoverDescription = true;
		newButton.hoverProperty().addListener((event, oldVal, newVal) -> {
			if(newVal){
				description.setText(hoverText);
			} else {
				description.setText(stringResourceBundle.getFromStringConstants("SelectAnOption"));
			}
		});
		addButton(newButton);
	}
	
	@Override
	public void addSimpleButton(String text, Runnable event){
   	 	ActionButton b = new ActionButton(text, event);
		addButton(b);
	}
	
	@Override
	public void addButton(Button newButton){
		newButton.setOnKeyPressed(key -> {
			if(key.getCode().equals(KeyCode.ENTER)){
				newButton.getOnAction().handle(null);
			}
		});
		myButtonRoot.getChildren().add(newButton);
	}
	
	@Override
	public void addButtonRow(String rowTitle, Button... buttons){
		HBox box = new HBox();
		Label rowLabel = new Label(rowTitle);
		box.getChildren().add(rowLabel);
		box.getChildren().addAll(buttons);
		box.setAlignment(Pos.CENTER);
		addNode(box);
	}
	
	@Override
	public void addNode(Node n){
		myButtonRoot.getChildren().add(n);
	}
	
	public void addBackButton(ButtonMenuImpl previousMenu, Stage stage) {		
		this.addSimpleButton("Go Back", () -> previousMenu.display(stage) );	
	}
		
	
	public void display(Stage stage){
		if(myScene == null){
			create();
		}		
		stage.setScene(myScene);
		stage.show();
	}


	private void create() {
		myButtonRoot.setSpacing(standardSpacing);
		myButtonRoot.setMinWidth(numericResourceBundle.getFromSizing("MinButtonMenuButtonWidth"));
		
		myGrid.add(titleLbl, 0, 0, 2, 1);
		myGrid.add(myButtonRoot, 0, 1);
		if(displayHoverDescription){
			myGrid.add(description, 1, 1);
		}
		myGrid.add(new Label(stringResourceBundle.getFromStringConstants("TEAMNAME")), 0, 2, 2, 1);
		//myButtonRoot.setAlignment(Pos.CENTER);
   	 	myScene = new Scene(myGrid);
   	 	myScene.getStylesheets().add(stringResourceBundle.getFromStringConstants("DEFAULT_CSS"));
		description.setMaxWidth(numericResourceBundle.getFromSizing("DescriptionTextBoxWidth"));
	}
}
