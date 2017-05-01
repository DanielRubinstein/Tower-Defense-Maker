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
import resources.constants.StringResourceBundle;

/**
 * This class represents the screen that the user sees when the settings button is pressed
 * @author Tim, Miguel Anderson
 *
 */
public class SettingsViewImpl implements SettingsView, PopUp{
	private SimpleBooleanProperty authorProperty;
	private StringResourceBundle strResources = new StringResourceBundle();
	private View myView;
	private ButtonMenuImpl myMenu;
	private Stage myStage;
	
	public SettingsViewImpl(View view) {
		myView = view;
		authorProperty = myView.getBooleanAuthorModeProperty();
		addButtons();
	}

	@Override
	public void displayOnStage(Stage stage) {
		myStage = stage;
		myStage.initOwner(myView.getMainWindow());
		myStage.initModality(Modality.APPLICATION_MODAL);
		myMenu.display(stage);
	}

	private void addButtons(){
		myMenu = new ButtonMenuImpl(strResources.getFromStringConstants("Settings"));
		saveButtons();
		loadButtons();
		newGameButtons();
		rulesButtons();
		gameStructureButtons();
		modeToggleButtons();
		helpButtons();
	}

	private void helpButtons() {
		myMenu.addSimpleButtonWithHover(strResources.getFromHelp("Help"), () -> {
			HelpOptions help = new HelpOptionsImpl();
			help.displayOnStage(new Stage());
		}, strResources.getFromHelp("HelpHover"));
	}

	private void modeToggleButtons() {
		Runnable changeMode = () -> myView.sendUserModification(new Modification_ChangeMode());
		ToggleSwitch modeToggle = new ToggleSwitch(strResources.getFromStringConstants("PLAYER"), 
				strResources.getFromStringConstants("AUTHOR"), myView.getBooleanAuthorModeProperty(), changeMode);
		myMenu.addNode(modeToggle.getRoot());
	}

	private void gameStructureButtons() {
		myMenu.addSimpleButtonWithHover(strResources.getFromStringConstants("GameStructure"), () -> {
			LevelView gameStructure = new LevelView(myView.getLevelProgressionController());
			gameStructure.displayOnStage(null);
		}, strResources.getFromStringConstants("GameStructureHover"));
	}

	private void rulesButtons() {
		myMenu.addSimpleButtonWithHover(strResources.getFromStringConstants("Rules"), () -> {
			RulesView myRules = new RulesView(myView,myStage);
			myRules.launch();
		}, strResources.getFromStringConstants("RulesHover"));
	}

	private void newGameButtons() {
		myMenu.addSimpleButtonWithHover(strResources.getFromStringConstants("NewGame"), () -> {
			close();
			myView.sendUserModification(new Modification_NewGame());
			}, strResources.getFromStringConstants("NewGameHover"));
	}

	private void loadButtons() {
		myMenu.addSimpleButtonWithHover(strResources.getFromStringConstants("LoadGame"), () -> {
			close();
			myView.sendUserModification(new Modification_LoadLevel()); 
		}, strResources.getFromStringConstants("LoadGameHover"));
	}

	private void saveButtons() {
		HBox bothButtons = new HBox();
		Button button1 = new Button(strResources.getFromStringConstants("SaveTemplate"));
		button1.setOnAction(e -> {
			myView.sendUserModification(Modification_SaveGameState.TEMPLATE);
		});
		Button button2 = new Button(strResources.getFromStringConstants("SaveProgress"));
		button2.setOnAction(e -> {
			myView.sendUserModification(Modification_SaveGameState.SAVEDGAME);
		});
		
		button1.disableProperty().bind(authorProperty.not());
		
		SplitPane wrapper1 = new SplitPane(button2);
		Tooltip t = new Tooltip(strResources.getFromStringConstants("OnlyAuthorMode"));
		SplitPane wrapper2 = new SplitPane(button1);
		wrapper2.setTooltip(t);
		wrapper2.hoverProperty().addListener((a,b,c)->{
			if(wrapper2.isHover()&&button1.isDisabled()){
				Bounds scenePos= wrapper2.localToScreen(wrapper2.getBoundsInLocal());
				t.show(wrapper2, scenePos.getMaxX(), scenePos.getMinY()-scenePos.getHeight());
			}else{
				t.hide();
			}
		});
		bothButtons.getChildren().addAll(wrapper2,wrapper1);
		
		myMenu.addNode(bothButtons);
	}

	private void close(){
		myStage.close();
	}

}
