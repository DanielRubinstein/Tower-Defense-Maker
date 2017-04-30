package frontEnd.Skeleton.UserTools;

import ModificationFromUser.Modification_ChangeMode;
import ModificationFromUser.savingAndLoading.Modification_LoadLevel;
import ModificationFromUser.savingAndLoading.Modification_NewGame;
import ModificationFromUser.savingAndLoading.Modification_SaveGameState;
import frontEnd.View;
import frontEnd.CustomJavafxNodes.ButtonMenuImpl;
import frontEnd.CustomJavafxNodes.ToggleSwitch;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Bounds;
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
	
	public SettingsViewImpl(View view) {
		myView = view;
		//myParentStage = parentStage;
		authorProperty = myView.getBooleanAuthorModeProperty();
		addButtons();
	}
	
	public void launchSettings(){
		// http://stackoverflow.com/questions/29514248/javafx-how-to-focus-on-one-stage
		myStage = new Stage();
		myStage.initOwner(myView.getMainWindow());
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
		
		saveButtons();
		loadButtons();
		newGameButtons();
		rulesButtons();
		gameStructureButtons();
		modeToggleButtons();
		helpButtons();
	}

	private void helpButtons() {
		myMenu.addSimpleButtonWithHover("Help", () -> new HelpOptions(myStage), "Get Help");
	}

	private void modeToggleButtons() {
		Runnable changeMode = () -> myView.sendUserModification(new Modification_ChangeMode());
		ToggleSwitch modeToggle = new ToggleSwitch("Player", "Author", myView.getBooleanAuthorModeProperty(), changeMode);
		myMenu.addNode(modeToggle.getRoot());
	}

	private void gameStructureButtons() {
		myMenu.addSimpleButtonWithHover("Game Structure", () -> {
			LevelView gameStructure = new LevelView(myView.getLevelProgressionController(), myStage);
			gameStructure.launch();
		}, "See Structure");
	}

	private void rulesButtons() {
		myMenu.addSimpleButtonWithHover("Rules", () -> {
			RulesView myRules = new RulesView(myView,myStage);
			myRules.launch();
		}, "Click to view/edit rules");
	}

	private void newGameButtons() {
		myMenu.addSimpleButtonWithHover("New Game", () -> {
			close();
			myView.sendUserModification(new Modification_NewGame());
			}, "Create a new game from scratch");
	}

	private void loadButtons() {
		myMenu.addSimpleButtonWithHover("Load", () -> {
			close();
			myView.sendUserModification(new Modification_LoadLevel()); 
		}, "Load a saved game from the Saved Games folder");
	}

	private void saveButtons() {
		HBox bothButtons = new HBox();
		Button button1 = new Button("Save level template");
		button1.setOnAction(e ->
		{
			myView.sendUserModification(Modification_SaveGameState.TEMPLATE);
		});
		Button button2 = new Button("Save current progress");
		button2.setOnAction(e -> myView.sendUserModification(Modification_SaveGameState.SAVEDGAME));
		
		button2.disableProperty().bind(authorProperty.not());
		
		SplitPane wrapper1 = new SplitPane(button2);
		Tooltip t = new Tooltip("Only possible in Author mode");
		SplitPane wrapper2 = new SplitPane(button1);
		wrapper2.setTooltip(t);
		wrapper2.hoverProperty().addListener((a,b,c)->{
			if(wrapper2.isHover()&&button2.isDisabled()){
				Bounds scenePos= wrapper2.localToScreen(wrapper2.getBoundsInLocal());
				t.show(wrapper2, scenePos.getMaxX(), scenePos.getMinY()-scenePos.getHeight());
			}else{
				t.hide();
			}
		});
		bothButtons.getChildren().addAll(wrapper2,wrapper1);
		
		myMenu.addNode(bothButtons);
	}


	private void close()
	{
		if (myParentStage!= null) myParentStage.close();
		myStage.close();
	}

	

}
