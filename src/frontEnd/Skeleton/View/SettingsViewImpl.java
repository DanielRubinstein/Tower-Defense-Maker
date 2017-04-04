package frontEnd.Skeleton.View;

import backEnd.Mode.Mode;
import backEnd.Mode.Player;
import frontEnd.Menus.ButtonMenu;
import frontEnd.Menus.MainMenu;
import frontEnd.Menus.StartMenu;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class represents the screen that the user sees when the settings button is pressed
 * @author Tim
 *
 */
public class SettingsViewImpl implements SettingsView{

	private VBox myRoot;
	private VBox authorRoot;
	private StartMenu myStartMenu;
	private Mode myMode;
	public SettingsViewImpl() {
		myRoot = new VBox();
		myStartMenu= new MainMenu();
	}
	
	public void launchSettings(double width, double height){
		myRoot.setMinWidth(width);
		myRoot.setMinHeight(height);
		Scene scene = new Scene(myRoot, 400, 400);
		Stage stage = new Stage();
		stage.setScene(scene);
		addButtons(stage);
		stage.show();
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
		myStartMenu.makeGameSelectionButtons(menu, stage);
		menu.addButton("Save", e -> save());
		//menu.addTwoButtons("View Rules", e -> viewRules(), "Edit Rules", e -> editRules());
		HBox rules = new HBox();
		Button viewRules = new Button("View Rules");
		viewRules.setOnAction(e -> viewRules());
		Button editRules = new Button("Edit Rules");
		editRules.setOnAction(e -> editRules());
		BooleanBinding authorMode = Bindings.createBooleanBinding(()-> System.nanoTime() %2 ==1, viewRules.cancelButtonProperty());
				//myMode.authorModeProperty());
		editRules.disableProperty().bind(authorMode.not());
		//editRules.disabledProperty().addListener(new ChangeListener());
		rules.getChildren().addAll(viewRules,editRules);
		
		menu.addNodeButton(rules);
		
		//adding player/godmode switch
		Button togglePlayer = new Button("Player");
		togglePlayer.setOnAction(e->togglePlayerMode());
		Button toggleAuthor = new Button("Author");
		toggleAuthor.setOnAction(e->toggleAuthorMode());
		HBox modeButton = new HBox();
		modeButton.getChildren().addAll(togglePlayer,toggleAuthor);
		
		menu.addNodeButton(modeButton);

		menu.create(400.0, 400.0);
		stage.setScene(menu.getScene());
		stage.show();
	}
	private void save(){
		System.out.println("Saving in SettingsViewImpl");
	}
	private void viewRules(){
		System.out.println("viewing rules in SettingsViewImpl");
	}
	private void editRules(){
		System.out.println("editing rules in SettingsViewImpl");
	}
	private void togglePlayerMode(){
		System.out.print("toggling player mode");
	}
	private void toggleAuthorMode(){
		System.out.println("toggling author mode");
	}
	//fix this, dont use instanceof
	private void isPlayerMode(){
			
		
		
	}
	private boolean authorMode(){
		return false;
	}


}
