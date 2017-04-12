package frontEnd.Skeleton.UserTools;

import java.util.function.Consumer;

import ModificationFromUser.Modification_ChangeMode;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.ToggleSwitch;
import frontEnd.Menus.ButtonMenuImpl;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class represents the screen that the user sees when the settings button is pressed
 * @author Tim
 *
 */
public class SettingsViewImpl implements SettingsView{
	private SimpleBooleanProperty authorProperty;
	private View myView;
	private ButtonMenuImpl myMenu;
	
	public SettingsViewImpl(View view) {
		myView = view;
		authorProperty = myView.getBooleanAuthorModeProperty();
		addButtons();
	}
	
	public void launchSettings(Stage parentStage){
		// http://stackoverflow.com/questions/29514248/javafx-how-to-focus-on-one-stage
		Stage myStage = new Stage();
		myStage.initOwner(parentStage);
		myStage.initModality(Modality.WINDOW_MODAL);
		myMenu.display(myStage);
	}
	/*
	 * Buttons to add:
	 * New
	 * Load
	 * Save
	 * Rules
	 * Author/Player toggle
	 */
	private void addButtons(){
		myMenu = new ButtonMenuImpl("Settings");
		myMenu.addSimpleButtonWithHover("Save", e -> myView.save(), "Save your current game in the Saved Games folder");
		
		myMenu.addSimpleButtonWithHover("Load", e -> myView.load(), "Load a saved game from the Saved Games folder");
		
		myMenu.addSimpleButtonWithHover("New Game", e -> myView.newGame(), "Create a new game from scratch");
		
		Node ruleButtons = createRulesButtons();
		myMenu.addNode(ruleButtons);
		
		//adding player/godmode switch
		Consumer<ActionEvent> actionEventConsumer = (e) -> myView.sendUserModification(new Modification_ChangeMode());
		Consumer<MouseEvent> mouseEventConsumer = (e) -> myView.sendUserModification(new Modification_ChangeMode());
		ToggleSwitch modeToggle = new ToggleSwitch(myView,"Player", "Author", authorProperty, actionEventConsumer, mouseEventConsumer);
		myMenu.addNode(modeToggle.getRoot());
		
		myMenu.addSimpleButtonWithHover("Help", e -> new HelpOptions(), "Get Help");
	}

	
	/**
	 * Adds two side by side buttons to the button menu. 
	 * Wraps each button to allow for the tooltip to be shown even when a button is disabled. Both buttons are wrapped for consistency
	 */
	private Node createRulesButtons(){
	
		HBox bothButtons = new HBox();
		Button button1 = new Button("View Rules");
		button1.setOnAction(e -> myView.viewRules());
		Button button2 = new Button("Edit Rules");
		button2.setOnAction(e -> myView.editRules());
		
		button2.disableProperty().bind(authorProperty.not());
		
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

}
