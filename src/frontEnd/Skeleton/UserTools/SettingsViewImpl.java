package frontEnd.Skeleton.UserTools;

import frontEnd.Menus.ButtonMenu;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * This class represents the screen that the user sees when the settings button is pressed
 * @author Tim
 *
 */
public class SettingsViewImpl implements SettingsView{
	private Stage myStage;
	
	
	public SettingsViewImpl() {
		myStage = new Stage();
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
		
		
		Button viewRules = new Button("View Rules");
		viewRules.setOnAction(e -> viewRules());
		Button editRules = new Button("Edit Rules");
		editRules.setOnAction(e -> editRules());
		BooleanBinding authorMode = Bindings.createBooleanBinding(()-> System.nanoTime() %2 ==1, viewRules.cancelButtonProperty());
				//myMode.authorModeProperty());
		editRules.disableProperty().bind(authorMode.not());
		//editRules.disabledProperty().addListener(new ChangeListener());
		menu.addButtonRow("Rules", viewRules, editRules);
	
		
		//adding player/godmode switch
		ModeToggle modeToggle = new ModeToggle("Player", e-> togglePlayerMode(), "Author", e -> toggleAuthorMode());
		menu.addNode(modeToggle.getRoot());
		
		menu.create();
		stage.setScene(menu.getScene());
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
	private void isPlayerMode(){
			
		
		
	}
	private boolean authorMode(){
		return false;
	}


}
