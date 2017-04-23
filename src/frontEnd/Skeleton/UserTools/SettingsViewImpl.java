package frontEnd.Skeleton.UserTools;

import ModificationFromUser.Modification_ChangeMode;
import ModificationFromUser.Modification_LoadLevel;
import ModificationFromUser.Modification_NewGame;
import ModificationFromUser.Modification_SaveGameState;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.ButtonMenuImpl;
import frontEnd.CustomJavafxNodes.ToggleSwitch;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tooltip;
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
	private Stage myParentStage;
	private Stage myStage;
	
	public SettingsViewImpl(View view, Stage parentStage) {
		myView = view;
		myParentStage = parentStage;
		authorProperty = myView.getBooleanAuthorModeProperty();
		addButtons();
	}
	
	public void launchSettings(){
		// http://stackoverflow.com/questions/29514248/javafx-how-to-focus-on-one-stage
		myStage = new Stage();
		myStage.initOwner(myParentStage);
		myStage.initModality(Modality.APPLICATION_MODAL);
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
		myMenu.addSimpleButtonWithHover("Save", () -> myView.sendUserModification(new Modification_SaveGameState()), "Save your current game in the Saved Games folder");
		
		myMenu.addSimpleButtonWithHover("Load", () -> myView.sendUserModification(new Modification_LoadLevel()), "Load a saved game from the Saved Games folder");
		
		myMenu.addSimpleButtonWithHover("New Game", () -> myView.sendUserModification(new Modification_NewGame()), "Create a new game from scratch");
		
		Node ruleButtons = createRulesButtons();
		myMenu.addNode(ruleButtons);
		
		myMenu.addSimpleButtonWithHover("Game Structure", () -> {
			LevelView gameStructure = new LevelView(myView, myStage);
			gameStructure.launch();
			
		}, "See Structure");
		//adding player/godmode switch
		Runnable changeMode = () -> myView.sendUserModification(new Modification_ChangeMode());
		ToggleSwitch modeToggle = new ToggleSwitch("Player", "Author", myView.getBooleanAuthorModeProperty(), changeMode);
		myMenu.addNode(modeToggle.getRoot());
		
		myMenu.addSimpleButtonWithHover("Help", () -> new HelpOptions(myStage), "Get Help");
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
