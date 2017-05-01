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
	private StringResourceBundle strResources = new StringResourceBundle();
	private String DEFAULT_TOWER_DEFENSE = strResources.getFromHelp("DefineTowerDefenseHTML");
	private String DEFAULT_HELP = strResources.getFromHelp("HelpHTML");
	
	private ButtonMenu allOptions;
	
	public HelpOptionsImpl(){
		allOptions = new ButtonMenuImpl(strResources.getFromHelp("Help"));
		addAllOptions();
	}
	
	@Override
	public void display(Stage parentStage){
		Stage myStage = new Stage();
		myStage.initOwner(parentStage);
		myStage.initModality(Modality.APPLICATION_MODAL);
		allOptions.display(myStage);
	}

	private void addAllOptions(){
		allOptions.addSimpleButtonWithHover(strResources.getFromHelp("Tutorial"), 
				() -> loadHTMLPage(DEFAULT_HELP),strResources.getFromHelp("TutorialHover"));
		allOptions.addSimpleButtonWithHover(strResources.getFromHelp("DefineTowerDefense"), 
				() -> loadHTMLPage(DEFAULT_TOWER_DEFENSE),strResources.getFromHelp("DefineTowerDefenseHover"));
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
