package frontEnd.Skeleton.UserTools;

import java.io.File;
import java.net.MalformedURLException;

import frontEnd.CustomJavafxNodes.ButtonMenu;
import frontEnd.CustomJavafxNodes.ButtonMenuImpl;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HelpOptions {
	private static final String DEFAULT_TOWER_DEFENSE = "src/resources/html/tower_defense.htm";
	private ButtonMenu allOptions;
	
	public HelpOptions(Stage myParentStage){
		allOptions = new ButtonMenuImpl("Help");
		addAllOptions();
		Stage myStage = new Stage();
		myStage.initOwner(myParentStage);
		myStage.initModality(Modality.APPLICATION_MODAL);
		allOptions.display(myStage);
	}

	private void addAllOptions(){
		allOptions.addPrimarySimpleButtonWithHover("What is Tower Defense?", () -> loadHTMLPage(DEFAULT_TOWER_DEFENSE),"View Instructions");
		allOptions.addSimpleButtonWithHover("What is Tower Defense?", () -> loadHTMLPage(DEFAULT_TOWER_DEFENSE),"Get a basic overview");
	}
	
	
	private void loadHTMLPage(String path){
		try {
			
			WebView browser = new WebView();
			WebEngine webEngine = browser.getEngine();
			webEngine.load(new File(path).toURI().toURL().toExternalForm());
			browser.autosize();
			Stage dialog = new Stage();
			Scene scene = new Scene(browser, browser.getPrefWidth(), browser.getPrefHeight());
			dialog.setScene(scene);
			dialog.show();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
}
