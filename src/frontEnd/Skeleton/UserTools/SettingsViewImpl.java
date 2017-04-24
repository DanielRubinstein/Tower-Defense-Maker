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
		
		myMenu.addSimpleButtonWithHover("Rules", () -> {
			RulesView myRules = new RulesView(myView,myStage);
			myRules.launch();
		}, "Click to view/edit rules");
		
		myMenu.addSimpleButtonWithHover("Game Structure", () -> {
			LevelView gameStructure = new LevelView(myView.getLevelProgressionController(), myStage);
			gameStructure.launch();
		}, "See Structure");
		//adding player/godmode switch
		Runnable changeMode = () -> myView.sendUserModification(new Modification_ChangeMode());
		ToggleSwitch modeToggle = new ToggleSwitch("Player", "Author", myView.getBooleanAuthorModeProperty(), changeMode);
		myMenu.addNode(modeToggle.getRoot());
		
		myMenu.addSimpleButtonWithHover("Help", () -> new HelpOptions(myStage), "Get Help");
	}

	

}
