package frontEnd.Skeleton.UserTools;

import frontEnd.ViewEditor;
import frontEnd.Menus.ButtonMenuImpl;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * This class represents the screen that the user sees when the settings button is pressed
 * @author Tim
 *
 */
public class SettingsViewImpl implements SettingsView{
	private Stage myStage;
	private SimpleBooleanProperty authorProperty;
	private ViewEditor myView;
	private ButtonMenuImpl myMenu;
	
	public SettingsViewImpl(ViewEditor view) {
		myView = view;
		myStage = new Stage();
		authorProperty = myView.getBooleanAuthorModeProperty();
		addButtons(myStage);
	}
	
	public void launchSettings(){
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
	private void addButtons(Stage stage){
		myMenu = new ButtonMenuImpl("Settings");
		myMenu.addSimpleButton("Save", e -> myView.save());
		
		myMenu.addSimpleButton("Load", e -> myView.load());
		
		myMenu.addSimpleButton("New Game", e -> myView.newGame());
		
		Node ruleButtons = createRulesButtons();
		myMenu.addNode(ruleButtons);
		
		//adding player/godmode switch
		ToggleSwitch modeToggle = new ToggleSwitch(myView,"Player", "Author", authorProperty);
		myMenu.addNode(modeToggle.getRoot());
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
