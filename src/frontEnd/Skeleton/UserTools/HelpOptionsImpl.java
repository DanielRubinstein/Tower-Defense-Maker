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
import resources.constants.StringResourceBundle;
import resources.constants.numeric.ScreenConstants;

/**
 * This class displays the HelpOptions available the user. These include ways to view HTML files to learn 
 * about the game.
 * @author Tim
 *
 */
public class HelpOptionsImpl implements HelpOptions {
	private static final String DEFAULT_TOWER_DEFENSE = "src/resources/html/tower_defense.htm";
	private static final String DEFAULT_HELP = "src/resources/html/help.html";
	private ScreenConstants screenResources = new ScreenConstants();
	private StringResourceBundle strResources = new StringResourceBundle();
	
	private ButtonMenu allOptions;
	
	public HelpOptionsImpl(Stage myParentStage){
		allOptions = new ButtonMenuImpl("Help");
		addAllOptions();
		Stage myStage = new Stage();
		myStage.initOwner(myParentStage);
		myStage.initModality(Modality.APPLICATION_MODAL);
		allOptions.display(myStage);
	}

	private void addAllOptions(){
		allOptions.addSimpleButtonWithHover("Help/Instructions", () -> loadHTMLPage(DEFAULT_HELP),"View Instructions");
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
