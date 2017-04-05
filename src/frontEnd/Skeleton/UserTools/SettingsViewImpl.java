package frontEnd.Skeleton.UserTools;

import frontEnd.Menus.ButtonMenu;
import frontEnd.Menus.MainMenu;
import frontEnd.Menus.StartMenu;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;

import javax.swing.event.ChangeEvent;

/**
 * This class represents the screen that the user sees when the settings button is pressed
 * @author Tim
 *
 */
public class SettingsViewImpl implements SettingsView{
	private Stage myStage;
	private SettingsBinding myBinding;
	
	
	public SettingsViewImpl() {
		myStage = new Stage();
		myBinding = new SettingsBinding();
		addButtons(myStage);
	}
	
	public void launchSettings(){
		myStage.show();
	}
	/*
	 * Buttons to add:
	 * New
	 * Load
	 * Save
	 * Rules
	 * Author/Player toggle
	 */
	private void addButtons(Stage stage){
		ButtonMenu menu = new ButtonMenu();
		
		menu.setText("Settings");
		
		// add New and Load
		
		menu.addSimpleButton("Save", e -> save());
		
		HBox ruleButtons = createRulesButtons("View Rules",e -> viewRules(),"Edit Rules",e->editRules(),null,Bindings.or(myBinding.valueProperty(), myBinding.valueProperty()).not());
		
		menu.addNode(ruleButtons);
	
		
		//adding player/godmode switch
		ToggleSwitch modeToggle = new ToggleSwitch("Player", e-> togglePlayerMode(), "Author", e -> toggleAuthorMode());
		menu.addNode(modeToggle.getRoot());
		
		menu.create();
		stage.setScene(menu.getScene());
	}

	
	/**
	 * Adds two side by side buttons to the button menu. If a button is meant to always be enabled, simply pass null as b1/b2
	 * @param text1
	 * @param event1
	 * @param text2
	 * @param event2
	 * @param b1
	 * @param b2
	 * @return 
	 */
	private HBox createRulesButtons(String text1, EventHandler<ActionEvent> event1, String text2, EventHandler<ActionEvent> event2, ObservableValue<? extends Boolean> b1, ObservableValue<? extends Boolean> b2){
	
		HBox bothButtons = new HBox();
		Button button1 = new Button(text1);
		button1.setOnAction(event1);
		Button button2 = new Button(text2);
		button2.setOnAction(event2);
		
		if(b1!=null)
			button1.disableProperty().bind(b1);
		
		if(b2!=null)
			button2.disableProperty().bind(b2);
		
		bothButtons.getChildren().addAll(button1,button2);
		return bothButtons;
			
	}
	
	
	private void save(){
		System.out.println("Saving in SettingsViewImpl");
	}
	private void viewRules(){
		System.out.println("viewing rules in SettingsViewImpl");
		myBinding.addOne();

	}
	private void editRules(){
		System.out.println("editing rules in SettingsViewImpl");
		myBinding.addOne();
		System.out.println(myBinding.valueProperty());
	}
	private void togglePlayerMode(){
		System.out.print("toggling player mode");
	}
	private void toggleAuthorMode(){
		System.out.println("toggling author mode");
	}

}
