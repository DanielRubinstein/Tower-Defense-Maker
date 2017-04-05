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
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Tooltip.*;
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
		ButtonMenu myMenu = new ButtonMenu();
		
		myMenu.setText("Settings");
		myMenu.addSimpleButton("Save", e -> save());
		
		Node ruleButtons = createRulesButtons();
		myMenu.addNode(ruleButtons);
		
		//adding player/godmode switch
		ToggleSwitch modeToggle = new ToggleSwitch("Player", e-> togglePlayerMode(), "Author", e -> toggleAuthorMode());
		myMenu.addNode(modeToggle.getRoot());
		
		myMenu.create();
		stage.setScene(myMenu.getScene());
	}

	
	/**
	 * Adds two side by side buttons to the button menu. If a button is meant to always be enabled, simply pass null as b1/b2
	 * Wraps each button to allow for the tooltip to be shown even when a button is disabled. Both buttons are wrapped for consistency
	 */
	private Node createRulesButtons(){
	
		HBox bothButtons = new HBox();
		Button button1 = new Button("View Rules");
		button1.setOnAction(e -> viewRules());
		Button button2 = new Button("Edit Rules");
		button2.setOnAction(e -> editRules());
		button2.disableProperty().bind(Bindings.or(myBinding.valueProperty(), myBinding.valueProperty()).not());
		
		SplitPane wrapper1 = new SplitPane(button1);
		Tooltip t = new Tooltip("Only possible in Author mode");
		SplitPane wrapper2 = new SplitPane(button2);
		wrapper2.setTooltip(t);
		wrapper2.hoverProperty().addListener((a,b,c)->{
			if(wrapper2.isHover()&&button2.isDisabled()){
				Bounds scenePos= wrapper2.localToScreen(wrapper2.getBoundsInLocal());
				t.show(wrapper2, scenePos.getMaxX(), scenePos.getMinY()-scenePos.getHeight());
			}else{
				t.hide();
			}
		});
		bothButtons.getChildren().addAll(wrapper1,wrapper2);	
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
