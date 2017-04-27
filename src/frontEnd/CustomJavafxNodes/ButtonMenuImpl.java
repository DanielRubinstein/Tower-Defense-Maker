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

public class ButtonMenuImpl implements ButtonMenu {
	private StringResourceBundle stringResourceBundle = new StringResourceBundle();
	private GridPane myGrid;
	private VBox myButtonRoot;
	private Scene myScene;
	private Label titleLbl;
	private Label description;
	
	public ButtonMenuImpl(String text){
		initializeGrid();
		myButtonRoot = new VBox();
		description = new Label(stringResourceBundle.getString("SelectAnOption"));
		description.setWrapText(true);
		setText(text);
	}
	public GridPane getGridPane()
	{
		return myGrid;
	}
	
	public VBox getButtonRoot()
	{
		return myButtonRoot;
	}
	

	private void initializeGrid() {
		myGrid = new GridPane();
	    myGrid.setHgap(10);
	    myGrid.setVgap(10);
	    myGrid.setPadding(new Insets(10));
	}


	private void setText(String text){
		titleLbl = new Label(text);
		titleLbl.setFont(Font.font(32));
		titleLbl.setUnderline(true);
		//titleLbl.setStyle("");
	}
	
	public void addSimpleButtonWithHover(String text, Runnable event, String hoverText){
		ActionButton b = new ActionButton(text, event);
		addButtonWithHover(b, hoverText);
	}
	
	public void addButtonWithHover(Button newButton, String hoverText){
		newButton.hoverProperty().addListener((event, oldVal, newVal) -> {
			if(newVal){
				description.setText(hoverText);
			} else {
				description.setText(stringResourceBundle.getString("SelectAnOption"));
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
	
	public void addBackButton(Runnable event) {
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
		myGrid.add(new Label(stringResourceBundle.getString("TEAMNAME")), 0, 2, 2, 1);
		//myButtonRoot.setAlignment(Pos.CENTER);
   	 	myScene = new Scene(myGrid);
   	 	myScene.getStylesheets().add(stringResourceBundle.getString("DEFAULT_CSS"));
		description.setMaxWidth(200d);
	}


	public void addPrimarySimpleButtonWithHover(String title, Runnable event, String hoverText) {
		ActionButton b = new ActionButton(title, event);
		b.setOnKeyPressed(key -> {
			if(key.getCode().equals(KeyCode.ENTER)){
				event.run();
			}
		});
		addButtonWithHover(b, hoverText);
		
	}
	
}
