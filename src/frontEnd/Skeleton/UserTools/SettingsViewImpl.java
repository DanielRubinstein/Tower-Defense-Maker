package frontEnd.Skeleton.UserTools;

import backEnd.Mode.Mode;
import backEnd.Mode.Player;
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

	private VBox myRoot;
	private StartMenu myStartMenu;
	private Stage myStage;
	private Scene myScene;
	private SettingsBinding myBinding;
	private ButtonMenu myButtonMenu;
	
	
	public SettingsViewImpl() {
		myRoot = new VBox();
		myStartMenu= new MainMenu();
		myBinding = new SettingsBinding();
		myButtonMenu = new ButtonMenu();
		
	}
	
	public void launchSettings(double width, double height){
		if(myScene != null){
			myStage.show();
			System.out.println("already have a scene");
		}else{
			myRoot.setMinWidth(width);
			myRoot.setMinHeight(height);
			myScene = new Scene(myRoot, 400, 400);
			myStage = new Stage();
			myStage.setScene(myScene);
			addButtons(myStage);
			myStage.show();
		}

	}

	private void addButtons(Stage stage){
		myStartMenu.makeGameSelectionButtons(myButtonMenu, stage);		
		myButtonMenu.addButton("Save", e -> save());
		
		addSideBySideButtons("View Rules",e -> viewRules(),"Edit Rules",e->editRules(),null,Bindings.or(myBinding.valueProperty(), myBinding.valueProperty()).not());
		addSideBySideButtons("Player",e->togglePlayerMode(),"Author",e->toggleAuthorMode(),null,null);
		
		
		myButtonMenu.create(myScene.getWidth(), myScene.getHeight());
		stage.setScene(myButtonMenu.getScene());
		stage.show();
	} 
	
	/**
	 * Adds two side by side buttons to the button menu. If a button is meant to always be enabled, simply pass null as b1/b2
	 * @param text1
	 * @param event1
	 * @param text2
	 * @param event2
	 * @param b1
	 * @param b2
	 */
	private void addSideBySideButtons(String text1, EventHandler<ActionEvent> event1, 
			String text2, EventHandler<ActionEvent> event2, ObservableValue<? extends Boolean> b1, ObservableValue<? extends Boolean> b2){
	
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
		myButtonMenu.addNodeButton(bothButtons);
			
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
